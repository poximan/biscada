/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.dbf;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import comunes.controles.EMFSingleton;
import comunes.controles.ObjetosBorrables;
import comunes.modelo.ArchivoDBF;
import etl.controles.Transaccion;
import etl.controles.cruds.ServCRUDArchivoDBF;
import etl.controles.servicios.Reloj;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ProcesarMultipleArchivo implements ObjetosBorrables {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ProcesarMultipleArchivo.class);

	private Path obj_direccion;

	private ServCRUDArchivoDBF dbf_servicio_crud;

	private Reloj reloj;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ProcesarMultipleArchivo(String directorio) {

		reloj = new Reloj();
		obj_direccion = Paths.get(directorio);
		dbf_servicio_crud = new ServCRUDArchivoDBF();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * comieza el proceso de eliminacion de archivos y todos sus dependientes
	 * 
	 * @param metodo_borrado
	 * 
	 * @param lista_candidatos_extraer
	 */
	public void borrarArchivosSeleccionados(List<ArchivoDBF> lista_candidatos_extraer) {

		Transaccion metodo_borrado = new Transaccion();
		ProcesarSimpleArchivo gestor = new ProcesarSimpleArchivo();

		Iterator<ArchivoDBF> iterador = lista_candidatos_extraer.iterator();

		reloj.comenzarContar();
		metodo_borrado.beginArchivo();

		while (iterador.hasNext()) {

			ArchivoDBF archivo_actual = iterador.next();

			archivo_actual = EMFSingleton.getInstanciaEM().find(ArchivoDBF.class, archivo_actual.getId());
			gestor.borrarSimpleArchivo(dbf_servicio_crud, archivo_actual);
		}

		metodo_borrado.enviarCacheHaciaBD();
		metodo_borrado.limpiarCache();

		metodo_borrado.commitArchivo();
		reloj.terminarContar();
	}

	/**
	 * usando la carpeta origen como unico punto para recoleccion de archivos,
	 * comienza la lectura de todos ellos. algunos posiblemente hayan sido
	 * insertados en una ejecucion previa, en ese caso no se insertara
	 * nuevamente
	 */
	public void buscarNuevosArchivos() {

		try {
			DirectoryStream<Path> directorio_contenedor = Files.newDirectoryStream(obj_direccion);

			for (Path entrada_archivo : directorio_contenedor)

				if (esExtensionValida(entrada_archivo))
					if (!dbf_servicio_crud.existeEnBD(entrada_archivo))
						dbf_servicio_crud.agregarDisponibles(entrada_archivo);

			directorio_contenedor.close();
		}

		catch (NoSuchFileException excepcion) {
			log.error("no se encontro directorio");
		} catch (IOException excepcion) {
			excepcion.printStackTrace();
		}
		log.info("\n... ... ... ... ... ... ... ... ... ... ... ... ... ... ...");
		log.info(dbf_servicio_crud.getListaDisponibles().size() + " archivos disponibles");
		log.info(dbf_servicio_crud.getListaProcesados().size() + " archivos procesados");
	}

	private boolean esExtensionValida(Path entrada_archivo) {

		if (entrada_archivo.toString().toUpperCase().endsWith(".DBF"))
			return true;
		return false;
	}

	public ServCRUDArchivoDBF getDbf_servicio_crud() {
		return dbf_servicio_crud;
	}

	/**
	 * comieza el proceso ETL de todos los archivos validos para ser insertados
	 * 
	 * @param lista_candidatos_procesar
	 */
	public void insertarArchivosSeleccionados(List<ArchivoDBF> lista_candidatos_procesar) {

		int totales = lista_candidatos_procesar.size(), actual = 1;

		Transaccion metodo_insercion = new Transaccion();
		ProcesarSimpleArchivo gestor = new ProcesarSimpleArchivo();

		Iterator<ArchivoDBF> iterador = lista_candidatos_procesar.iterator();

		reloj.comenzarContar();

		while (iterador.hasNext()) {

			metodo_insercion.beginArchivo();

			ArchivoDBF archivo_actual = iterador.next();
			gestor.mostarInfo(archivo_actual, totales, actual++);

			gestor.insertarSimpleArchivo(dbf_servicio_crud, archivo_actual);
			gestor.liberarObjetos();

			metodo_insercion.enviarCacheHaciaBD();
			metodo_insercion.limpiarCache();

			metodo_insercion.commitArchivo();
		}
		reloj.terminarContar();

		mostarInfo();
	}

	@Override
	public void liberarObjetos() {
		dbf_servicio_crud.liberarObjetos();
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	private void mostarInfo() {

		System.out.println();

		log.info("la operacion demoro " + reloj.getTiempoEnSegundos() + " segundos");
		log.info("se extrajeron " + ProcesarSimpleArchivo.getTotalizador_extraidas() + " filas de potenciales alarmas");
		log.info("se transformaron " + ProcesarSimpleArchivo.getTotalizador_transformadas()
				+ " filas del total extraidas");
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
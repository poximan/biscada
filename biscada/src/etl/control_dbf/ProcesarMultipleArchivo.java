/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.control_dbf;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import comunes.control_general.EMFSingleton;
import comunes.control_general.ObjetosBorrables;
import comunes.modelo.ArchivoDBF;
import etl.control_CRUDs.ServCRUDArchivoDBF;
import etl.control_etl.Transaccion;

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

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ProcesarMultipleArchivo(String directorio) {

		obj_direccion = Paths.get(directorio);
		dbf_servicio_crud = new ServCRUDArchivoDBF();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

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

	/**
	 * comieza el proceso ETL de todos los archivos validos para ser insertados
	 * 
	 * @param lista_candidatos_procesar
	 */
	public void insertarArchivosSeleccionados(List<ArchivoDBF> lista_candidatos_procesar) {

		int totales = lista_candidatos_procesar.size(), actual = 1;

		ParametrosConexion parametros = new ParametrosConexion(482, 164);
		Transaccion metodo_insercion = new Transaccion();
		ProcesarSimpleArchivo gestor = new ProcesarSimpleArchivo();

		Iterator<ArchivoDBF> iterador = lista_candidatos_procesar.iterator();

		while (iterador.hasNext()) {

			ArchivoDBF archivo_actual = iterador.next();
			gestor.mostarInfo(archivo_actual, totales, actual++);

			metodo_insercion.beginArchivo();

			gestor.insertarSimpleArchivo(dbf_servicio_crud, archivo_actual, parametros);

			metodo_insercion.enviarCacheHaciaBD();
			metodo_insercion.limpiarCache();

			metodo_insercion.commitArchivo();
		}
		mostarInfo();
	}

	private void mostarInfo() {

		log.info("se extrajeron " + ProcesarSimpleArchivo.getTotalizador_extraidas() + " filas de potenciales alarmas");
		log.info("se transformaron " + ProcesarSimpleArchivo.getTotalizador_transformadas()
				+ " filas del total extraidas");
	}

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

		while (iterador.hasNext()) {

			ArchivoDBF archivo_actual = iterador.next();

			metodo_borrado.beginArchivo();

			archivo_actual = EMFSingleton.getInstanciaEM().find(ArchivoDBF.class, archivo_actual.getId());
			gestor.borrarSimpleArchivo(dbf_servicio_crud, archivo_actual);

			metodo_borrado.enviarCacheHaciaBD();
			metodo_borrado.limpiarCache();

			metodo_borrado.commitArchivo();
		}
	}

	@Override
	public void liberarObjetos() {
		dbf_servicio_crud.liberarObjetos();
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public ServCRUDArchivoDBF getDbf_servicio_crud() {
		return dbf_servicio_crud;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
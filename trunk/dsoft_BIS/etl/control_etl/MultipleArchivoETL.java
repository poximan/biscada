/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_etl;

import java.beans.Beans;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.persistence.EntityManager;

import modelo.ArchivoDBF;

import org.apache.log4j.Logger;

import control_CRUDs.ServCRUDArchivoDBF;
import control_dbf.ParametrosConexion;
import control_general.EMFSingleton;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class MultipleArchivoETL {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(MultipleArchivoETL.class);

	private Path obj_direccion;

	private ServCRUDArchivoDBF dbf_servicio_crud;

	private EntityManager em;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public MultipleArchivoETL(String directorio) {

		em = Beans.isDesignTime() ? null : EMFSingleton.getInstanciaEM();

		obj_direccion = Paths.get(directorio);
		dbf_servicio_crud = new ServCRUDArchivoDBF();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * usando la carpeta origen como unico punto para recoleccion de archivos, comienza la lectura de todos ellos.
	 * algunos posiblemente hayan sido insertados en una ejecucion previa, en ese caso no se insertara nuevamente
	 */
	public void buscarNuevosArchivos() {

		try {
			DirectoryStream<Path> directorio_contenedor = Files.newDirectoryStream(obj_direccion);

			for (Path entrada_archivo : directorio_contenedor)

				if (esExtensionValida(entrada_archivo))
					if (dbf_servicio_crud.existeEnBD(entrada_archivo))
						dbf_servicio_crud.agregarProcesados(entrada_archivo);
					else
						dbf_servicio_crud.agregarDisponibles(entrada_archivo);

			directorio_contenedor.close();
		}

		catch (NoSuchFileException excepcion) {
			log.error("no se encontro directorio");
		}
		catch (IOException excepcion) {
			excepcion.printStackTrace();
		}
		log.info("\n... ... ... ... ... ... ... ... ... ... ... ... ... ... ...");
		log.info(dbf_servicio_crud.getCantDisponibles() + " archivos disponibles");
		log.info(dbf_servicio_crud.getCantProcesados() + " archivos procesados");
	}

	private boolean esExtensionValida(Path entrada_archivo) {

		if (entrada_archivo.toString().toUpperCase().endsWith(".DBF"))
			return true;
		return false;
	}

	/**
	 * comieza el proceso ETL de todos los archivos validos para ser insertados
	 */
	public void insertarArchivosSeleccionados() {

		ParametrosConexion parametros = new ParametrosConexion(481, 164);
		SimpleArchivoETL gestor = new SimpleArchivoETL();
		ArchivoDBF archivo_actual;

		Iterator<ArchivoDBF> iterador = dbf_servicio_crud.getIteradorDisponibles();

		for (int numero_archivo = 1; iterador.hasNext(); numero_archivo++) {

			archivo_actual = iterador.next();

			log.info("\nETL en archivo "
					+ archivo_actual.getRuta().substring(archivo_actual.getRuta().lastIndexOf("\\") + 1) + " ["
					+ numero_archivo + "-" + dbf_servicio_crud.getCantDisponibles() + "] ");

			em.getTransaction().begin();
			gestor.insertarSimpleArchivo(dbf_servicio_crud, archivo_actual, parametros);
			em.getTransaction().commit();
			em.clear();

			iterador.remove();
		}

		log.info("se extrajeron " + SimpleArchivoETL.getTotalizador_extraidas() + " filas de potenciales alarmas");
		log.info("se transformaron " + SimpleArchivoETL.getTotalizador_transformadas() + " filas del total extraidas");
	}

	public void borrarArchivosSeleccionados() {

		ParametrosConexion parametros = new ParametrosConexion(481, 164);
		SimpleArchivoETL gestor = new SimpleArchivoETL();
		ArchivoDBF archivo_actual;

		Iterator<ArchivoDBF> iterador = dbf_servicio_crud.getIteradorProcesados();

		while (iterador.hasNext()) {

			archivo_actual = iterador.next();

			em.getTransaction().begin();
			gestor.borrarSimpleArchivo(dbf_servicio_crud, archivo_actual, parametros);
			em.getTransaction().commit();
			em.clear();

			iterador.remove();
		}
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
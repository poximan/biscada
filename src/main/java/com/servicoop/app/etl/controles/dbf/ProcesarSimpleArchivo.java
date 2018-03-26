/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.etl.controles.dbf;

import java.util.List;

import org.apache.log4j.Logger;

import main.java.com.servicoop.app.comunes.controles.ObjetosBorrables;
import main.java.com.servicoop.app.comunes.entidades.ArchivoDBF;
import main.java.com.servicoop.app.etl.controles.ETL0Extraer;
import main.java.com.servicoop.app.etl.controles.ETL1Transformar;
import main.java.com.servicoop.app.etl.controles.ETL2Cargar;
import main.java.com.servicoop.app.etl.controles.servicios.CampoTextoDefectuoso;
import main.java.com.servicoop.app.etl.controles.servicios.Reloj;
import main.java.com.servicoop.app.etl.controles.servicios.cruds.ServCRUDArchivoDBF;
import main.java.com.servicoop.app.etl.entidades.ArchAlarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO, un servicio de procesamiento simple de archivo dbf. soy simple
 * porque me dedico al tratamiento de un unico archivo por instancia.
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO, proceso archivos dbf con intencion de insertar en BD nuevas
 * alarmas. para ello llamo a cada una de las partes del proceso ETL cuando son
 * requeridas (metodo insertarSimpleArchivo(...)).
 *
 * proceso archivos dbf con intencion de eliminar de BD, todas las alarmas
 * obtenidas desde ese archivo. para ello llamo al servicio
 * etl.controles.cruds.ServCRUDArchivoDBF (metodo borrarSimpleArchivo(...)).
 *
 * LO QUE CONOZCO, las partes del proceso ETL y el servicio
 * etl.controles.cruds.ServCRUDArchivoDBF
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL, es el conjunto de clases {
 * etl.controles.ETL0Extraer, etl.controles.ETL1Transformar,
 * etl.controles.ETL2Cargar }
 *
 * COMO INTERACTUO CON MI COLABORADOR, siguiendo la idea de tunel (la salida de
 * uno en la entrada del siguiente)
 *
 * creo extractor etl.controles.ETL0Extraer y le pido las alarmas. entrega una
 * lista de etl.modelo.ArchAlarma (dato crudo, campos de texto sin tratamiento).
 *
 * creo un transformador etl.controles.ETL1Transformar con la lista anterior y
 * le pido que la transforme. entrega una lista de comunes.modelo.Alarma (dato
 * util al sistema y que cumple las restricciones de persistencia en BD)
 *
 * creo un cargador etl.controles.ETL2Cargar con la lista anterior y le pido que
 * la cargue en BD.
 *
 * @author hdonato
 *
 */
public class ProcesarSimpleArchivo implements ObjetosBorrables {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ProcesarSimpleArchivo.class);

	private static int totalizador_extraidas = 0;
	private static int totalizador_transformadas = 0;

	private Reloj reloj;

	public static int getTotalizador_extraidas() {
		return totalizador_extraidas;
	}

	public static int getTotalizador_transformadas() {
		return totalizador_transformadas;
	}

	private ETL0Extraer extractor;

	private ETL1Transformar transformador;

	private ETL2Cargar cargador;

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	private List<ArchAlarma> alarmas_extraidas;

	public ProcesarSimpleArchivo() {
		reloj = new Reloj();
	}

	private void actualizarTotalizadores(int extraidas, int transformadas) {
		totalizador_extraidas += extraidas;
		totalizador_transformadas += transformadas;
	}

	public void borrarSimpleArchivo(ServCRUDArchivoDBF dbf_servicio_crud, ArchivoDBF archivo_actual) {
		dbf_servicio_crud.borrar(archivo_actual);
	}

	public void insertarSimpleArchivo(ServCRUDArchivoDBF dbf_servicio_crud, ArchivoDBF archivo_actual) {

		int extraidas;
		CampoTextoDefectuoso alarmas_defectuosas = new CampoTextoDefectuoso();

		dbf_servicio_crud.inicia(archivo_actual);

		reloj.comenzarContar();
		primerEtapa(archivo_actual);
		reloj.terminarContar();
		log.info("la extraccion demoro " + reloj.getTiempoEnSegundos() + " segundos");

		extraidas = alarmas_extraidas.size();

		reloj.comenzarContar();
		segundaEtapa(archivo_actual, alarmas_defectuosas);
		reloj.terminarContar();
		log.info("la transformacion demoro " + reloj.getTiempoEnSegundos() + " segundos");

		reloj.comenzarContar();
		tercerEtapa(dbf_servicio_crud, archivo_actual);
		reloj.terminarContar();
		log.info("la carga demoro " + reloj.getTiempoEnSegundos() + " segundos");

		reportar(extraidas, transformador.getAlarmas().size(), alarmas_defectuosas);
		actualizarTotalizadores(extraidas, transformador.getAlarmas().size());
	}

	private void primerEtapa(ArchivoDBF archivo_actual) {
		extractor = new ETL0Extraer(archivo_actual);
		alarmas_extraidas = extractor.getAlarmas();
	}

	private void segundaEtapa(ArchivoDBF archivo_actual, CampoTextoDefectuoso alarmas_defectuosas) {
		transformador = new ETL1Transformar(alarmas_extraidas);
		transformador.transformarAlarmas(archivo_actual, alarmas_defectuosas);
	}

	private void tercerEtapa(ServCRUDArchivoDBF dbf_servicio_crud, ArchivoDBF archivo_actual) {
		cargador = new ETL2Cargar(transformador.getAlarmas(), dbf_servicio_crud);
		dbf_servicio_crud.termina(archivo_actual);

		if (!transformador.getAlarmas().isEmpty())
			cargador.cargarAlarmasAceptadas();
		else
			cargador.rechazarArchivo(archivo_actual);
	}

	@Override
	public void liberarObjetos() {

		extractor.cerrarArchivo();
		transformador.liberarObjetos();
		cargador.liberarObjetos();
		System.gc();
	}

	/**
	 * muestra resultados eliminacion de un archivo
	 *
	 * @param archivo_actual
	 */
	public void mostarInfo(ArchivoDBF archivo_actual) {
		log.debug("Se elimino archivo "
				+ archivo_actual.getRuta().substring(archivo_actual.getRuta().lastIndexOf("\\") + 1));
	}

	/**
	 * muestra resultados creacion de un archivo
	 *
	 * @param archivo_actual
	 * @param totales
	 * @param totales
	 */
	public void mostarInfo(ArchivoDBF archivo_actual, int totales, int actual) {

		System.out.println();

		log.debug("ETL en archivo " + archivo_actual.getRuta().substring(archivo_actual.getRuta().lastIndexOf("\\") + 1)
				+ " [" + actual + "-" + totales + "]");
	}

	private void reportar(int extraidas, int transformadas, CampoTextoDefectuoso alarmas_defectuosas) {

		log.debug("se transformaron " + transformadas + " de " + extraidas + " leidas desde archivo");

		if (!alarmas_defectuosas.estaVacia()) {
			log.debug("se encontraron defectos en...");
			log.debug(alarmas_defectuosas.toString());
		}
	}
}

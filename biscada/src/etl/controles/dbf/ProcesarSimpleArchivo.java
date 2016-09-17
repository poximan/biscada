/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.dbf;

import java.util.List;

import org.apache.log4j.Logger;

import comunes.controles.ObjetosBorrables;
import comunes.modelo.ArchivoDBF;
import etl.controles.CampoTextoDefectuoso;
import etl.controles.ETL0Extraer;
import etl.controles.ETL1Transformar;
import etl.controles.ETL2Cargar;
import etl.controles.cruds.ServCRUDArchivoDBF;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ProcesarSimpleArchivo implements ObjetosBorrables {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ProcesarSimpleArchivo.class);

	private static int totalizador_extraidas = 0;
	private static int totalizador_transformadas = 0;

	public static int getTotalizador_extraidas() {
		return totalizador_extraidas;
	}
	public static int getTotalizador_transformadas() {
		return totalizador_transformadas;
	}
	private ETL0Extraer extractor;

	private ETL1Transformar transformador;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	private ETL2Cargar cargador;

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	private List<ArchAlarma> alarmas_extraidas;

	public ProcesarSimpleArchivo() {
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

		dbf_servicio_crud.actualizar(archivo_actual);

		extractor = new ETL0Extraer(archivo_actual);
		alarmas_extraidas = extractor.extraerAlarmas();
		extraidas = alarmas_extraidas.size();

		transformador = new ETL1Transformar(alarmas_extraidas);
		transformador.transformarAlarmas(archivo_actual, alarmas_defectuosas);

		cargador = new ETL2Cargar(transformador.getAlarmas_transformadas(), dbf_servicio_crud);

		if (!transformador.getAlarmas_transformadas().isEmpty())
			cargador.cargarAlarmasAceptadas();
		else
			cargador.rechazarArchivo(archivo_actual);

		reportar(extraidas, transformador.getAlarmas_transformadas().size(), alarmas_defectuosas);
		actualizarTotalizadores(extraidas, transformador.getAlarmas_transformadas().size());

		dbf_servicio_crud.actualizar(archivo_actual);
	}

	@Override
	public void liberarObjetos() {

		extractor.liberarObjetos();
		transformador.liberarObjetos();
		cargador.liberarObjetos();
	}

	/**
	 * muestra resultados eliminacion de un archivo
	 * 
	 * @param archivo_actual
	 */
	public void mostarInfo(ArchivoDBF archivo_actual) {
		log.info("Se elimino archivo "
				+ archivo_actual.getRuta().substring(archivo_actual.getRuta().lastIndexOf("\\") + 1));
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	/**
	 * muestra resultados creacion de un archivo
	 * 
	 * @param archivo_actual
	 * @param totales
	 * @param totales
	 */
	public void mostarInfo(ArchivoDBF archivo_actual, int totales, int actual) {

		log.info("ETL en archivo " + archivo_actual.getRuta().substring(archivo_actual.getRuta().lastIndexOf("\\") + 1)
				+ " [" + actual + "-" + totales + "]");
	}

	private void reportar(int extraidas, int transformadas, CampoTextoDefectuoso alarmas_defectuosas) {

		log.info("se extrajeron " + extraidas + " filas del archivo DBF");
		log.info("se transformaron " + transformadas + " filas de las extraidas");

		if (!alarmas_defectuosas.estaVacia()) {
			log.info("se encontraron defectos en...");
			log.trace(alarmas_defectuosas.toString());
			log.info("de las defectuosas se aceptaron aquellas con sitio y suceso");
		}
	}
}
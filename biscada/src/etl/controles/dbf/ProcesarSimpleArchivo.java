/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.dbf;

import java.util.List;

import org.apache.log4j.Logger;

import comunes.controles.ObjetosBorrables;
import comunes.modelo.ArchivoDBF;
import etl.controles.ETL1Transformar;
import etl.controles.ETL2Cargar;
import etl.controles.ETL0Extraer;
import etl.controles.cruds.ServCRUDArchivoDBF;
import etl.controles.servicios.CampoTextoDefectuoso;

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

		dbf_servicio_crud.inicia(archivo_actual);

		extractor = new ETL0Extraer(archivo_actual);
		alarmas_extraidas = extractor.getAlarmas();
		extraidas = alarmas_extraidas.size();

		transformador = new ETL1Transformar(alarmas_extraidas);
		transformador.transformarAlarmas(archivo_actual, alarmas_defectuosas);

		cargador = new ETL2Cargar(transformador.getAlarmas(), dbf_servicio_crud);

		dbf_servicio_crud.termina(archivo_actual);

		if (!transformador.getAlarmas().isEmpty())
			cargador.cargarAlarmasAceptadas();
		else
			cargador.rechazarArchivo(archivo_actual);

		reportar(extraidas, transformador.getAlarmas().size(), alarmas_defectuosas);
		actualizarTotalizadores(extraidas, transformador.getAlarmas().size());
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

		System.out.println();

		log.info("ETL en archivo " + archivo_actual.getRuta().substring(archivo_actual.getRuta().lastIndexOf("\\") + 1)
				+ " [" + actual + "-" + totales + "]");
	}

	private void reportar(int extraidas, int transformadas, CampoTextoDefectuoso alarmas_defectuosas) {

		log.info("se transformaron " + transformadas + " de " + extraidas + " leidas desde archivo");

		if (!alarmas_defectuosas.estaVacia()) {
			log.info("se encontraron defectos en...");
			log.trace(alarmas_defectuosas.toString());
		}
	}
}

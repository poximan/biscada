/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_etl;

import java.util.List;

import modelo.Alarma;
import modelo.ArchivoDBF;

import org.apache.log4j.Logger;

import control_CRUDs.ServCRUDArchivoDBF;
import control_dbf.ArchAlarma;
import control_dbf.ParametrosConexion;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class SimpleArchivoETL {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static int totalizador_extraidas = 0;
	private static int totalizador_transformadas = 0;

	private static Logger log = Logger.getLogger(SimpleArchivoETL.class);

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
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	private List<ArchAlarma> alarmas_extraidas;

	private List<Alarma> alarmas_transformadas;

	private void actualizarTotalizadores(int extraidas, int transformadas) {

		totalizador_extraidas += extraidas;
		totalizador_transformadas += transformadas;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public void insertarSimpleArchivo(ServCRUDArchivoDBF dbf_servicio_crud, ArchivoDBF archivo_actual,
			ParametrosConexion parametros) {

		int extraidas, transformadas;
		CampoTextoDefectuoso alarmas_defectuosas = new CampoTextoDefectuoso();

		dbf_servicio_crud.actualizar(archivo_actual);

		extractor = new ETL0Extraer(archivo_actual, parametros);
		alarmas_extraidas = extractor.extraerAlarmas();
		extraidas = alarmas_extraidas.size();

		transformador = new ETL1Transformar(alarmas_extraidas);
		alarmas_transformadas = transformador.transformarAlarmas(archivo_actual, alarmas_defectuosas);
		transformadas = alarmas_transformadas.size();

		if (!alarmas_transformadas.isEmpty()) {
			cargador = new ETL2Cargar(alarmas_transformadas, dbf_servicio_crud);
			cargador.cargarAlarmas(archivo_actual);
		} else
			cargador.rechazarArchivo(archivo_actual);

		reportar(extraidas, transformadas, alarmas_defectuosas);
		actualizarTotalizadores(extraidas, transformadas);

		dbf_servicio_crud.actualizar(archivo_actual);
	}

	public void borrarSimpleArchivo(ServCRUDArchivoDBF dbf_servicio_crud, ArchivoDBF archivo_actual,
			ParametrosConexion parametros) {
		// TODO hugo: etl todavia no elimina archivos ingresados a bd

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

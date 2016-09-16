/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.etl;

import java.util.List;

import comunes.controles.ObjetosBorrables;
import comunes.modelo.ArchivoDBF;
import etl.controles.dbf.ArchAlarma;
import etl.controles.dbf.ServConexionArchivo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * La primera parte del proceso ETL consiste en extraer los datos desde el
 * origen, en este caso un archivo de texto. La extracci�n convierte los datos
 * a un formato preparado para iniciar el proceso de transformaci�n. Previo a
 * ello se verifica si los datos cumplen la pauta o estructura que se esperaba.
 * De no ser as� los datos son rechazados.
 * 
 * @author hugo
 * 
 */
public class ETL0Extraer implements ObjetosBorrables {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private ServConexionArchivo serv_conexion_archivo;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ETL0Extraer(ArchivoDBF archivo_actual) {
		serv_conexion_archivo = new ServConexionArchivo(archivo_actual);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public List<ArchAlarma> extraerAlarmas() {
		return serv_conexion_archivo.getAlarmas();
	}

	@Override
	public void liberarObjetos() {

		serv_conexion_archivo.cerrarArchivo();
		System.gc();
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */
}

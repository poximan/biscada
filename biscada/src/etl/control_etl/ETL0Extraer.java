/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.control_etl;

import java.util.LinkedList;
import java.util.List;

import comunes.control_general.ObjetosBorrables;
import comunes.modelo.ArchivoDBF;
import etl.control_dbf.ArchAlarma;
import etl.control_dbf.ParametrosConexion;
import etl.control_dbf.ServConexionArchivo;
import etl.control_dbf.ServConvertirArchivo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * La primera parte del proceso ETL consiste en extraer los datos desde el origen, en este caso un archivo de texto. La
 * extracci�n convierte los datos a un formato preparado para iniciar el proceso de transformaci�n. Previo a ello se
 * verifica si los datos cumplen la pauta o estructura que se esperaba. De no ser as� los datos son rechazados.
 * 
 * @author hugo
 * 
 */
public class ETL0Extraer implements ObjetosBorrables {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private ArchivoDBF archivo_actual;
	private ParametrosConexion parametros;

	private ServConexionArchivo stream_archivo_dbf;

	private List<String> alarmas_extraidas_no_convertidas;
	private List<ArchAlarma> alarmas_extraidas_convertidas;

	private ServConvertirArchivo conversion_archivo_dbf;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ETL0Extraer(ArchivoDBF archivo_actual, ParametrosConexion parametros) {

		this.archivo_actual = archivo_actual;
		this.parametros = parametros;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	private void accederArchivo() {
		stream_archivo_dbf = new ServConexionArchivo(archivo_actual, parametros);
	}

	private void convertirTodasLasFila() {

		conversion_archivo_dbf = new ServConvertirArchivo();
		alarmas_extraidas_convertidas = new LinkedList<>();

		for (String texto_extraido : alarmas_extraidas_no_convertidas) {

			ArchAlarma nueva_alarma = new ArchAlarma();
			nueva_alarma = conversion_archivo_dbf.completarPropiedades(texto_extraido);

			alarmas_extraidas_convertidas.add(nueva_alarma);
		}
	}

	public List<ArchAlarma> extraerAlarmas() {

		accederArchivo();
		extraerTodasLasFilas();
		convertirTodasLasFila();

		return alarmas_extraidas_convertidas;
	}

	private String extraerFila() {

		return stream_archivo_dbf.getProximaAlarma();
	}

	private void extraerTodasLasFilas() {

		String ultima_alarma_extraida_no_convertida;
		alarmas_extraidas_no_convertidas = new LinkedList<>();

		while ((ultima_alarma_extraida_no_convertida = extraerFila()) != null)
			alarmas_extraidas_no_convertidas.add(ultima_alarma_extraida_no_convertida);
	}

	@Override
	public void liberarObjetos() {

		stream_archivo_dbf.cerrarArchivo();

		alarmas_extraidas_no_convertidas.clear();
		alarmas_extraidas_convertidas.clear();

		conversion_archivo_dbf.liberarLista();

		System.gc();
	}

	@Override
	public String toString() {

		return alarmas_extraidas_convertidas.toString();
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

}

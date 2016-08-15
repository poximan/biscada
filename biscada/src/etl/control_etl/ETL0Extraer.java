/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.control_etl;

import java.io.IOException;
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
 * La primera parte del proceso ETL consiste en extraer los datos desde el
 * origen, en este caso un archivo de texto. La extracciï¿½n convierte los datos
 * a un formato preparado para iniciar el proceso de transformaciï¿½n. Previo a
 * ello se verifica si los datos cumplen la pauta o estructura que se esperaba.
 * De no ser asï¿½ los datos son rechazados.
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

	private ServConexionArchivo serv_conexion_archivo;

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
		serv_conexion_archivo = new ServConexionArchivo(archivo_actual, parametros);
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

		String fila_alarma_convertida = serv_conexion_archivo.getProximaAlarma();

		if (!tieneAnchoValido(fila_alarma_convertida))
			corregirLectura(fila_alarma_convertida);

		serv_conexion_archivo.incPuntero(fila_alarma_convertida.length());

		return fila_alarma_convertida;
	}

	/**
	 * en caso que la lectura de la fila haya superado la longitud para esa
	 * fila. esto es importante porque el ancho de fila es variable.
	 * 
	 * @param fila_alarma_convertida
	 */
	private void corregirLectura(String fila_alarma_convertida) {
		// TODO Auto-generated method stub

	}

	/**
	 * si se ha podido comprobar que solo se trajo informacion de una fila, sin
	 * faltantes ni sobrantes.
	 *
	 * esto es importante para las filas siguientes, que podrían falsear sus
	 * propios datos por desplazamientos en lecturas anteriores.
	 * 
	 * @param fila_alarma_convertida
	 * @return
	 */
	private boolean tieneAnchoValido(String fila_alarma_convertida) {
		// TODO Auto-generated method stub
		return false;
	}

	private void extraerTodasLasFilas() {

		String ultima_alarma;
		alarmas_extraidas_no_convertidas = new LinkedList<>();

		try {
			serv_conexion_archivo.saltarEncabezado();
		} catch (IOException e) {

			e.printStackTrace();
		}

		while ((ultima_alarma = extraerFila()) != null)
			alarmas_extraidas_no_convertidas.add(ultima_alarma);
	}

	@Override
	public void liberarObjetos() {

		serv_conexion_archivo.cerrarArchivo();

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

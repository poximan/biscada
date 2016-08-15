/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.control_dbf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import comunes.excepciones.ReiniciarAplicacionExcepcion;
import comunes.modelo.ArchivoDBF;
import etl.excepciones.CerrarArchivoExcepcion;
import etl.excepciones.LeerArchivoExcepcion;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * establece la comunicacion con el archivo fuente extrae encabezado y filas de
 * alarmas. todo sin formato
 * 
 * @author hugo
 * 
 */
public class ServConexionArchivo {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private FileInputStream inputStream;

	private String str_archivo_actual;
	private int tamano_fila_alarma;

	@SuppressWarnings("unused")
	private int bytes_leidos;

	private byte[] buffer_encabezado;

	private int puntero_fd;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServConexionArchivo(ArchivoDBF archivo_actual, ParametrosConexion parametros) {

		str_archivo_actual = archivo_actual.getRuta();
		this.tamano_fila_alarma = parametros.getTamano_fila_alarma();

		puntero_fd = 0;

		try {
			inputStream = new FileInputStream(str_archivo_actual);
			buffer_encabezado = new byte[parametros.getTamano_encabezado()];
		}

		catch (FileNotFoundException ex) {
			new ReiniciarAplicacionExcepcion("Error: no se pudo abrir archivo '" + archivo_actual + "'");
		}
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void cerrarArchivo() {

		try {
			inputStream.close();
		} catch (IOException excepcion) {
			new CerrarArchivoExcepcion("Error: no se pudo cerrar archivo '" + str_archivo_actual + "'");
		}
	}

	public void saltarEncabezado() throws IOException {

		inputStream.skip(buffer_encabezado.length);
		incPuntero(buffer_encabezado.length);
	}

	public String getProximaAlarma() {

		byte[] buffer_fila_alarma = new byte[tamano_fila_alarma];
		String fila_alarma_convertida = null;

		try {
			/*
			 * se lee del stream hasta a lo sumo llenar el buffer, retorna
			 * numero de bytes leidos.
			 */
			inputStream.read(buffer_fila_alarma);
			fila_alarma_convertida = new String(buffer_fila_alarma);

		} catch (IOException excepcion) {
			new LeerArchivoExcepcion("Error: no se pudo leer archivo '" + str_archivo_actual + "'");
		}
		return fila_alarma_convertida;
	}

	public void incPuntero(int offset) {
		puntero_fd += offset;
	}
}

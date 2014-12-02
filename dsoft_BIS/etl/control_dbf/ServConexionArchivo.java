/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_dbf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import modelo.ArchivoDBF;
import excepciones.CerrarArchivoExcepcion;
import excepciones.LeerArchivoExcepcion;
import excepciones.ReiniciarAplicacionExcepcion;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * establece la comunicacion con el archivo fuente extrae encabezado y filas de alarmas. todo sin formato
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
	private String encabezado_convertido;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServConexionArchivo(ArchivoDBF archivo_actual, ParametrosConexion parametros) {

		str_archivo_actual = archivo_actual.getRuta();
		this.tamano_fila_alarma = parametros.getTamano_fila_alarma();

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
		}
		catch (IOException excepcion) {
			new CerrarArchivoExcepcion("Error: no se pudo cerrar archivo '" + str_archivo_actual + "'");
		}
	}

	private boolean esFilaNula(byte[] buffer_fila_alarma) {

		if (Byte.compare(buffer_fila_alarma[1], (byte) '\0') == 0)
			return true;
		return false;
	}

	public String getEncabezado() {

		try {
			/*
			 * se lee del stream hasta a lo sumo llenar el buffer, retorna numero de bytes leidos.
			 */
			if ((bytes_leidos = inputStream.read(buffer_encabezado)) != -1)
				encabezado_convertido = new String(buffer_encabezado);
		}
		catch (IOException excepcion) {
			new LeerArchivoExcepcion("Error: no se pudo leer archivo '" + str_archivo_actual + "'");
		}

		return encabezado_convertido;
	}

	public String getProximaAlarma() {

		byte[] buffer_fila_alarma = new byte[tamano_fila_alarma];
		String fila_alarma_convertida = null;

		if (encabezado_convertido == null)
			getEncabezado();

		try {
			/*
			 * se lee del stream hasta a lo sumo llenar el buffer, retorna numero de bytes leidos.
			 */
			if ((bytes_leidos = inputStream.read(buffer_fila_alarma)) == tamano_fila_alarma)
				if (!esFilaNula(buffer_fila_alarma))
					fila_alarma_convertida = new String(buffer_fila_alarma);

		}
		catch (IOException excepcion) {
			new LeerArchivoExcepcion("Error: no se pudo leer archivo '" + str_archivo_actual + "'");
		}
		return fila_alarma_convertida;
	}
}

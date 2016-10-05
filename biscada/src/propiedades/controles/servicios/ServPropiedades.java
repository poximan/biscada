/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package propiedades.controles.servicios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.Logger;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO,
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO,
 * 
 * LO QUE CONOZCO,
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL,
 * 
 * COMO INTERACTUO CON MI COLABORADOR,
 *
 */
/**
 * recupera y de ser necesario actualiza el archivo de propiedades de arranque
 * de la aplicacion.
 * 
 * mediante singleton asegura una unica via unidireccional de acceso al archivo.
 * 
 * @author hugo
 *
 */
public class ServPropiedades {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ServPropiedades.class);

	private static String NOMBRE_ARCHIVO_PROPIEDADES = "cfg.properties";

	private static Properties propiedades = null;
	private static ServPropiedades instancia_unica = null;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public static Properties getInstancia() {

		if (instancia_unica == null)
			instancia_unica = new ServPropiedades();

		return propiedades;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public static void guardarCambios() {

		try {
			FileOutputStream archivo_salida = new FileOutputStream(NOMBRE_ARCHIVO_PROPIEDADES);
			propiedades.store(archivo_salida, Calendar.getInstance().getTime().toString());
			archivo_salida.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ServPropiedades() {

		propiedades = new Properties();

		try {
			FileInputStream archivo_entrada = new FileInputStream(NOMBRE_ARCHIVO_PROPIEDADES);
			propiedades.load(archivo_entrada);
			archivo_entrada.close();
		} catch (IllegalArgumentException | IOException excepcion) {
			log.error("leyendo archivo de propiedades");
		}
	}

	public Properties getPropiedades() {
		return propiedades;
	}
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServPropiedades {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ServPropiedades.class);

	private static String NOMBRE_ARCHIVO_PROPIEDADES = "cfg.properties";

	private Properties propiedades = null;
	private static ServPropiedades instancia = null;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public static ServPropiedades getInstancia() {

		if (instancia == null)
			instancia = new ServPropiedades();

		return instancia;
	}

	private ServPropiedades() {

		propiedades = new Properties();

		try {
			FileInputStream archivo_entrada = new FileInputStream(NOMBRE_ARCHIVO_PROPIEDADES);
			propiedades.load(archivo_entrada);
			archivo_entrada.close();
		}
		catch (IllegalArgumentException | IOException excepcion) {
			log.error("leyendo archivo de propiedades");
		}
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public Properties getPropiedades() {
		return propiedades;
	}
}

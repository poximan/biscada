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

	public static final String NOMBRE_ARCHIVO_PROPIEDADES = "cfg.properties";

	private static Properties propiedades = null;
	private static ServPropiedades instancia = null;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public synchronized static Properties getInstancia() {

		if (instancia == null)
			instancia = new ServPropiedades();

		return propiedades;
	}

	private ServPropiedades() {

		ServPropiedades.propiedades = new Properties();

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
}

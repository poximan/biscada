/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

	private static Properties propiedades = null;
	private static ServPropiedades instancia = null;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

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

	public static Properties getInstancia() {

		if (instancia == null)
			instancia = new ServPropiedades();

		return propiedades;
	}

	public Properties getPropiedades() {
		return propiedades;
	}

	public static void guardarCambios() {

		try {
			FileOutputStream archivo_salida = new FileOutputStream(NOMBRE_ARCHIVO_PROPIEDADES);
			propiedades.store(archivo_salida, "ddkjfklsdjghfklsdj");
			archivo_salida.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
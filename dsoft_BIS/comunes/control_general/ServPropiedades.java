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

	private Properties propiedades = null;
	private static ServPropiedades instancia = null;

	public static final String NOMBRE_ARCHIVO_PROPIEDADES = "cfg.properties";
	public static final String PORCENTAGE_ACEPTACION_RESPECTO_MEDIA = "Graficos.PORCENTAGE_ACEPTACION_RESPECTO_MEDIA";
	public static final String PISO_RUIDO_ALARMA = "Ruido.PISO_RUIDO_ALARMA";
	public static final String TECHO_RUIDO_ALARMA = "Ruido.TECHO_RUIDO_ALARMA";

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public synchronized static ServPropiedades getInstancia() {

		if (instancia == null)
			instancia = new ServPropiedades();

		return instancia;
	}

	private ServPropiedades() {

		this.propiedades = new Properties();

		try {
			FileInputStream in = new FileInputStream(NOMBRE_ARCHIVO_PROPIEDADES);
			propiedades.load(in);
			in.close();
		}
		catch (IllegalArgumentException | IOException excepcion) {
			log.error("leyendo archivo de propiedades");
		}
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public int getProp(String key) {
		return Integer.parseInt(propiedades.getProperty(key));
	}

	public void setProp(String key, String nuevo_valor) {
		this.propiedades.setProperty(key, nuevo_valor);
	}
}

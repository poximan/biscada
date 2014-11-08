package control_general;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ServPropiedades {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/**
	 * herramienta de manejo de logging. existe una por clase.
	 */
	private static Logger log = Logger.getLogger(ServPropiedades.class);

	Properties propiedades = null;

	/**
	 * instancia unica de la clase.
	 */
	private static ServPropiedades instancia = null;

	/* ................................................................................ */

	/**
	 * nombre del archivo de configuracion. debera radicar en la raiz del proyecto
	 */
	public static final String CONFIG_FILE_NAME = "cfg.properties";

	/* ............................................. */
	/* ............................................. */
	/* ......... CLASE Cliente ..................... */
	/* ............................................. */
	/**
	 * maximo tiempo (expresado en milisegundos) que esperara un cliente antes de abandonar la cola.
	 */
	public static final String MAX_TMP_ESPERA_CLI = "Cliente.MAX_TMP_ESPERA_CLI";

	/**
	 * minimo tiempo (expresado en milisegundos) que esperara un cliente antes de abandonar la cola.
	 */
	public static final String MIN_TMP_ESPERA_CLI = "Cliente.MIN_TMP_ESPERA_CLI";

	/* ............................................. */
	/* ............................................. */
	/* ......... CLASE Gestor_cola ................. */
	/* ............................................. */
	/**
	 * longitud maxima que puede tomar la cola. aquellos clientes que ingresen al sistema en momentos en que la cola
	 * este llena, no se uniran.
	 */
	public static final String MAX_LONG_COLA = "Gestor_cola.MAX_LONG_COLA";

	/**
	 * indicador de cuantos clientes esta permitido que no se unan antes de generar una solicitud de apertura de un
	 * nuevo servidor.
	 */
	public static final String TOLERANCIA_NO_UNIDOS = "Gestor_cola.TOLERANCIA_NO_UNIDOS";

	/**
	 * indicador de cuantos clientes esta permitido que abandonen la cola antes de generar una solicitud de apertura de
	 * un nuevo servidor.
	 */
	public static final String TOLERANCIA_ABANDONARON = "Gestor_cola.TOLERANCIA_ABANDONARON";

	/* ............................................. */
	/* ............................................. */
	/* ......... CLASE Gestor_simulacion ........... */
	/* ............................................. */
	/**
	 * maximo tiempo (expresado en milisegundos) que pasara entre nuevos ingresos de clientes al sistema
	 */
	public static final String MAX_FREC_LLEGADA_CLI = "Gestor_simulacion.MAX_FREC_LLEGADA_CLI";

	/**
	 * minimo tiempo (expresado en milisegundos) que pasara entre nuevos ingresos de clientes al sistema
	 */
	public static final String MIN_FREC_LLEGADA_CLI = "Gestor_simulacion.MIN_FREC_LLEGADA_CLI";

	/**
	 * maxima cantidad de servidores conque trabajara el sistema
	 */
	public static final String MAX_CANT_SERVIDORES = "Gestor_simulacion.MAX_CANT_SERVIDORES";

	/**
	 * minima cantidad de servidores conque trabajara el sistema
	 */
	public static final String MIN_CANT_SERVIDORES = "Gestor_simulacion.MIN_CANT_SERVIDORES";

	/* ............................................. */
	/* ............................................. */
	/* ......... CLASE Servidor .................... */
	/* ............................................. */
	/**
	 * maximo tiempo (expresado en milisegundos) que se tomara un servidor para despechar a un cliente, contando desde
	 * que lo toma
	 */
	public static final String MAX_TMP_ATENCION_SERV = "Servidor.MAX_TMP_ATENCION_SERV";

	/**
	 * minimo tiempo (expresado en milisegundos) que se tomara un servidor para despechar a un cliente, contando desde
	 * que lo toma
	 */
	public static final String MIN_TMP_ATENCION_SERV = "Servidor.MIN_TMP_ATENCION_SERV";

	/* ............................................. */
	/* ............................................. */
	/* ......... CLASE Reporte .................... */
	/* ............................................. */
	public static final String REFRESCO_REPORTES = "Reporte.REFRESCO_REPORTES";

	/* ................................................................................ */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/**
	 * Implementando Singleton
	 * 
	 * @return la unica instancia del objeto Config.
	 */
	public synchronized static ServPropiedades getInstancia() {

		if (instancia == null)
			instancia = new ServPropiedades();

		return instancia;
	}

	private ServPropiedades() {

		this.propiedades = new Properties();

		try {
			FileInputStream in = new FileInputStream(CONFIG_FILE_NAME);
			propiedades.load(in);
			in.close();
		}
		catch (IllegalArgumentException excepcion) {
			log.error("argumento ilegal");
		}
		catch (IOException ex) {
			log.error("error en e/s");
		}
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * Retorna la propiedad de configuración solicitada
	 * 
	 * @param key
	 * @return el parametro entero solicitado
	 */
	public int getProp(String key) {
		return Integer.parseInt(propiedades.getProperty(key));
	}

	public void setProp(String key, String nuevo_valor) {
		this.propiedades.setProperty(key, nuevo_valor);
	}
}

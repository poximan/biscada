/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.apache.log4j.Logger;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * instancia unica de esta clase. entre sus atributos contiene la unica instancia de entity manager factory que debe
 * manejar la aplicacion
 */
public class EMFSingleton {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static EMFSingleton instancia_unica = null;

	@PersistenceUnit
	private static EntityManagerFactory emf;
	private static EntityManager em;

	private static Logger log = Logger.getLogger(EMFSingleton.class);

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public synchronized static EntityManager getInstanciaEM() {

		getInstanciaEMF();
		return em;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public synchronized static EntityManagerFactory getInstanciaEMF() {

		if (instancia_unica == null) {

			log.trace("se crea EMF por primera vez");
			instancia_unica = new EMFSingleton();
		}
		return emf;
	}

	private EMFSingleton() {

		Map<String, String> persistenceMap = new HashMap<String, String>();

		String dir_fija = "jdbc:mysql://";
		String dir_variable = ServPropiedades.getInstancia().getProperty("Conexion.URL") + "/";
		String id_bd = "bis_bd";
		String charset = "?useUnicode=yes&amp;characterEncoding=UTF-8";

		String url = dir_fija + dir_variable + id_bd + charset;

		persistenceMap.put("javax.persistence.jdbc.url", url);
		persistenceMap.put("javax.persistence.jdbc.user", ServPropiedades.getInstancia()
				.getProperty("Conexion.USUARIO"));
		persistenceMap.put("javax.persistence.jdbc.password",
				ServPropiedades.getInstancia().getProperty("Conexion.CONTRASENIA"));

		try {
			emf = Persistence.createEntityManagerFactory("dsoft_BIS", persistenceMap);
			em = emf.createEntityManager();
		}
		catch (Exception excepcion) {
			log.error("no se pudo crear EMF, revisar modelo");
			log.error(excepcion.getMessage());
		}
	}
}
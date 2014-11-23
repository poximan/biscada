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

	private static EMFSingleton instanciaEMFSingleton = null;

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

		if (instanciaEMFSingleton == null) {

			log.trace("se crea EMF por primera vez");
			instanciaEMFSingleton = new EMFSingleton();
		}
		return emf;
	}

	private EMFSingleton() {

		Map<String, String> persistenceMap = new HashMap<String, String>();

		String url = "jdbc:mysql://" + ServPropiedades.getInstancia().getProperty("Conexion.URL") + "/bis_bd";
		persistenceMap.put("javax.persistence.jdbc.url", url);
		persistenceMap.put("javax.persistence.jdbc.user", ServPropiedades.getInstancia()
				.getProperty("Conexion.USUARIO"));
		persistenceMap.put("javax.persistence.jdbc.password",
				ServPropiedades.getInstancia().getProperty("Conexion.CONTRASENIA"));

		try {
			emf = Persistence.createEntityManagerFactory("dsoft_BIS", persistenceMap);
			// emf = Persistence.createEntityManagerFactory("dsoft_BIS");
			em = emf.createEntityManager();
		}
		catch (Exception excepcion) {
			log.error("no se pudo crear EMF, revisar modelo");
			log.error(excepcion.getMessage());
		}
	}
}
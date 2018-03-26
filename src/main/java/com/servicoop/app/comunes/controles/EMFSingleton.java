/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.comunes.controles;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import main.java.com.servicoop.app.propiedades.controles.servicios.ServPropiedades;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO, un wrapper de emf
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO, garantizo una implementacion Singleton de emf
 *
 * LO QUE CONOZCO, la unidad de persistencia configurada via persistence.xml
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL, en la api de JPA para pedir un emf
 *
 * COMO INTERACTUO CON MI COLABORADOR, a traves del metodo
 * Persistence.createEntityManagerFactory()
 *
 * @author hugo
 *
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

	private synchronized static EntityManagerFactory getInstanciaEMF() {

		if (instancia_unica == null) {

			log.info("se crea EMF por primera vez");
			instancia_unica = new EMFSingleton();
		}
		return emf;
	}

	private EMFSingleton() {

		try {
			emf = Persistence.createEntityManagerFactory("dsoft_BIS");
			em = emf.createEntityManager();
		} catch (Exception excepcion) {
			log.error("no se pudo crear EMF, revisar modelo");
			log.error(excepcion.getMessage());

			notificarError(excepcion.getCause().getMessage());
		}
	}

	public void notificarError(String mensaje) {

		JOptionPane optionPane = new JOptionPane(mensaje, JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog("error");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
}

/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.etl.controles;

import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import org.apache.log4j.Logger;

import main.java.com.servicoop.app.comunes.controles.EMFSingleton;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO, al wrapper de una transaccion sobre BD
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO, ofrezco una manera unificada de iniciar y terminar
 * transacciones, incluyendo rollback de ser necesario.
 *
 * LO QUE CONOZCO,
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL, el manejador de transacciones nativo de JPA
 *
 * COMO INTERACTUO CON MI COLABORADOR, lo llamo para pedir la transaccion en
 * curso o crear una nueva en otro caso. luego lo vuelvo a llamar para commit.
 *
 * @author hdonato
 *
 */
public class Transaccion {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(Transaccion.class);

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public Transaccion() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void beginArchivo() {

		EntityTransaction transaccion = EMFSingleton.getInstanciaEM().getTransaction();

		if (transaccion.isActive())
			commitArchivo();

		transaccion.begin();
	}

	public void commitArchivo() {

		try {
			EMFSingleton.getInstanciaEM().getTransaction().commit();
		} catch (RollbackException excepcion) {
			log.error("comienza rollback");

			if (EMFSingleton.getInstanciaEM().getTransaction().isActive())
				EMFSingleton.getInstanciaEM().getTransaction().rollback();
			throw excepcion;
		}
	}

	public void enviarCacheHaciaBD() {
		EMFSingleton.getInstanciaEM().flush();
	}

	public void limpiarCache() {
		EMFSingleton.getInstanciaEM().clear();
	}
}

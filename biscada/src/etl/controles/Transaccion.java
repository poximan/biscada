/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles;

import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import org.apache.log4j.Logger;

import comunes.controles.EMFSingleton;

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
 * util para optimizar insercion o eliminacion de archivos. los objetos que lo
 * implementen manejaran de forma dinamica el uso o no de transacciones a nivel
 * de lote o de archivo.
 * 
 * @author hugo
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

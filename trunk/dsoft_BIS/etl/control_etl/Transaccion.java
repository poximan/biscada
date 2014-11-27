/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_etl;

import javax.persistence.RollbackException;

import org.apache.log4j.Logger;

import control_general.EMFSingleton;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * util para optimizar insercion o eliminacion de archivos. los objetos que lo implementen manejaran de forma dinamica
 * el uso o no de transacciones a nivel de lote o de archivo.
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

	public void limpiarCache() {
		EMFSingleton.getInstanciaEM().clear();
	}

	public void enviarCacheHaciaBD() {
		EMFSingleton.getInstanciaEM().flush();
	}

	public void beginArchivo() {
		EMFSingleton.getInstanciaEM().getTransaction().begin();
	}

	public void commitArchivo() {

		try {
			EMFSingleton.getInstanciaEM().getTransaction().commit();
		}
		catch (RollbackException excepcion) {
			log.error("comienza rollback");

			if (EMFSingleton.getInstanciaEM().getTransaction().isActive())
				EMFSingleton.getInstanciaEM().getTransaction().rollback();
			throw excepcion;
		}
	}
}

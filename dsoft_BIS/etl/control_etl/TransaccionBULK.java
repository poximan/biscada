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

public class TransaccionBULK extends Transaccion {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(TransaccionBULK.class);

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public TransaccionBULK() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void beginBULK() {
		EMFSingleton.getInstanciaEM().getTransaction().begin();
	}

	@Override
	public void commitBULK() {

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

	@Override
	public void limpiarCache() {
		EMFSingleton.getInstanciaEM().clear();
	}

	@Override
	public void beginArchivo() {
	}

	@Override
	public void commitArchivo() {
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}

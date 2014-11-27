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

public class TransaccionArchivo extends Transaccion {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(TransaccionArchivo.class);

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public TransaccionArchivo() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

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

	@Override
	public void beginBULK() {
	}

	@Override
	public void commitBULK() {
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

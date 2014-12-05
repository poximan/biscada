/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_CRUDs;

import java.beans.Beans;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import modelo.Alarma;

import org.apache.log4j.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import control_general.EMFSingleton;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServCRUDAlarma implements InterfazCRUD {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ServCRUDAlarma.class);

	private EntityManager em;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServCRUDAlarma() {
		em = Beans.isDesignTime() ? null : EMFSingleton.getInstanciaEM();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void actualizar(Object entidad) {

	}

	@Override
	public void borrar(Object entidad) {

	}

	@Override
	public void crear(Object entidad) {

		Alarma alarma_actual = (Alarma) entidad;

		try {
			em.persist(alarma_actual);
		}
		catch (RollbackException excepcion) {

			if (excepcion.getCause() instanceof NullPointerException) {
				log.error("puntero a null");
			} else
				if (excepcion.getCause() instanceof DatabaseException) {

					log.error("excepcion en BD");
					DatabaseException excepcion_bd = (DatabaseException) excepcion.getCause();

					if (excepcion_bd.getInternalException() instanceof MySQLIntegrityConstraintViolationException) {

						MySQLIntegrityConstraintViolationException integridad = (MySQLIntegrityConstraintViolationException) excepcion_bd
								.getInternalException();

						if (integridad.getMessage().contains("suceso"))
							log.error("suceso repetido, se intenta salvar");
					}
				}
		}
	}

	@Override
	public Object leer(Object entidad) {
		return null;
	}
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.cruds;

import java.beans.Beans;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import org.apache.log4j.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import comunes.controles.EMFSingleton;
import comunes.modelo.Alarma;

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
 * @author hdonato
 * 
 */
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
		} catch (RollbackException excepcion) {

			if (excepcion.getCause() instanceof NullPointerException) {
				log.error("puntero a null");
			} else if (excepcion.getCause() instanceof DatabaseException) {

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
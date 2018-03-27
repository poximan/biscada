/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.etl.controles.servicios.cruds;

import java.beans.Beans;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import org.apache.log4j.Logger;

import main.java.com.servicoop.app.comunes.controles.EMFSingleton;
import main.java.com.servicoop.app.comunes.entidades.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO, al servicio crud concreto para el tratamiento de
 * comunes.modelo.Alarma
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO, persisto nuevas alarmas
 *
 * LO QUE CONOZCO, Alarmas a ser persistidas
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL, entity manager de JPA
 *
 * COMO INTERACTUO CON MI COLABORADOR, utilizo su metodo persist para persistir
 * nuevas alarmas. protejo la transaccion mediante rollback de ser necesario.
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
			}
		}
	}
}

/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.etl.controles.servicios.cruds;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, una interfaz aplicable a los servicios CRUD's
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, establezco la forma estandar que deben tener las consultas
 * CRUD's (en este caso solo alta y baja) a BD
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
public interface InterfazCRUD {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void borrar(Object entidad);

	public void crear(Object entidad);
}
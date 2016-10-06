/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.cruds;

import javax.persistence.Query;

import comunes.modelo.Alarma;

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
 * LO QUE HAGO, establezco la forma estandar que deben tener las consultas a BD
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
public interface ClaveIdentificable {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void actualizarLista();

	public void buscarEnMemoriaPrimaria(Alarma alarma_actual);

	public Query getQueryTodos();
}
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
 * interfaz de uso en el ambito del paquete etl.control_CRUDs
 * 
 * 
 * @author hugo
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
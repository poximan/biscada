/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.control_CRUDs;

import java.beans.Beans;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jdesktop.observablecollections.ObservableCollections;

import comunes.control_general.EMFSingleton;
import comunes.modelo.Alarma;
import comunes.modelo.TipoDeEquipo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServCRUDTipoDeEquipo implements InterfazCRUD, ClaveIdentificable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private EntityManager em;

	private List<Object> lista;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServCRUDTipoDeEquipo() {
		em = Beans.isDesignTime() ? null : EMFSingleton.getInstanciaEM();
		actualizarLista();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void actualizar(Object entidad) {
	}

	@Override
	@SuppressWarnings("unchecked")
	public void actualizarLista() {

		lista = Beans.isDesignTime() ? Collections.emptyList()
				: ObservableCollections.observableList(getQueryTodos().getResultList());
	}

	@Override
	public void borrar(Object entidad) {
	}

	@Override
	public void buscarEnMemoriaPrimaria(Alarma alarma_actual) {

		int indice;
		TipoDeEquipo tipo_de_equipo_actual = new TipoDeEquipo(
				alarma_actual.getEquipo_en_sitio().getTipo_de_equipo().getDescripcion());

		if ((indice = lista.lastIndexOf(tipo_de_equipo_actual)) != -1)
			alarma_actual.getEquipo_en_sitio().setTipo_de_equipo((TipoDeEquipo) lista.get(indice));
		else {
			crear(tipo_de_equipo_actual);
			alarma_actual.getEquipo_en_sitio().setTipo_de_equipo(tipo_de_equipo_actual);
			actualizarLista();
		}
	}

	@Override
	public void crear(Object entidad) {
		em.persist(entidad);
	}

	@Override
	public Query getQueryTodos() {
		return em.createNamedQuery("TipoDeEquipo.buscTodos");
	}

	@Override
	public Object leer(Object entidad) {
		return null;
	}
}
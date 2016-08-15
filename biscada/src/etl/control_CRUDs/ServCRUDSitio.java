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
import comunes.modelo.Sitio;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServCRUDSitio implements InterfazCRUD, ClaveIdentificable {

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

	public ServCRUDSitio() {

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
		Sitio sitio_actual = new Sitio(alarma_actual.getSitio().getDescripcion());

		if ((indice = lista.lastIndexOf(sitio_actual)) != -1)
			alarma_actual.setSitio((Sitio) lista.get(indice));
		else {
			crear(sitio_actual);
			alarma_actual.setSitio(sitio_actual);
			actualizarLista();
		}
	}

	@Override
	public void crear(Object entidad) {
		em.persist(entidad);
	}

	@Override
	public Query getQueryTodos() {
		return em.createNamedQuery("Sitio.buscTodos");
	}

	@Override
	public Object leer(Object entidad) {
		return null;
	}
}
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
import comunes.modelo.EquipoEnSitio;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServCRUDEquipoEnSitio implements InterfazCRUD, ClaveIdentificable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private EntityManager em;

	private List<EquipoEnSitio> lista;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServCRUDEquipoEnSitio() {
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
		alarma_actual.getEquipo_en_sitio().setSitio(alarma_actual.getSitio());

		EquipoEnSitio equipo_en_sitio_actual = alarma_actual.getEquipo_en_sitio();

		if ((indice = lista.lastIndexOf(equipo_en_sitio_actual)) != -1)
			alarma_actual.setEquipo_en_sitio(lista.get(indice));
		else {
			crear(equipo_en_sitio_actual);
			actualizarLista();
		}
	}

	@Override
	public void crear(Object entidad) {
		em.persist(entidad);
	}

	@Override
	public Query getQueryTodos() {
		return em.createNamedQuery("EquipoEnSitio.buscTodos");
	}

	@Override
	public Object leer(Object entidad) {
		return null;
	}
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_CRUDs;

import java.beans.Beans;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Alarma;
import modelo.Familia;

import org.jdesktop.observablecollections.ObservableCollections;

import control_general.EMFSingleton;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServCRUDFamilia implements InterfazCRUD, ClaveIdentificable {

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

	public ServCRUDFamilia() {

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

		lista = Beans.isDesignTime() ? Collections.emptyList() : ObservableCollections.observableList(getQueryTodos()
				.getResultList());
	}

	@Override
	public void borrar(Object entidad) {
	}

	@Override
	public void buscarEnMemoriaPrimaria(Alarma alarma_actual) {

		int indice;
		Familia familia_actual = new Familia(alarma_actual.getFamilia().getDescripcion());

		if ((indice = lista.lastIndexOf(familia_actual)) != -1)
			alarma_actual.setFamilia((Familia) lista.get(indice));
		else {
			crear(familia_actual);
			alarma_actual.setFamilia(familia_actual);
			actualizarLista();
		}
	}

	@Override
	public void crear(Object entidad) {
		em.persist(entidad);
	}

	@Override
	public Query getQueryTodos() {

		return em.createNamedQuery("Familia.buscTodos");
	}

	@Override
	public Object leer(Object entidad) {
		return null;
	}
}
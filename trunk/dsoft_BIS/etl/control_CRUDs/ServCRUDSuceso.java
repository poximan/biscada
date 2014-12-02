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
import modelo.Suceso;

import org.jdesktop.observablecollections.ObservableCollections;

import control_general.EMFSingleton;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServCRUDSuceso implements InterfazCRUD, ClaveIdentificable {

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

	public ServCRUDSuceso() {
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
		Suceso suceso_actual = new Suceso(alarma_actual.getSuceso().getDescripcion());

		if ((indice = lista.lastIndexOf(suceso_actual)) != -1)
			alarma_actual.setSuceso((Suceso) lista.get(indice));
		else {
			crear(suceso_actual);
			alarma_actual.setSuceso(suceso_actual);
			actualizarLista();
		}
	}

	@Override
	public void crear(Object entidad) {
		em.persist(entidad);
	}

	@Override
	public Query getQueryTodos() {

		return Beans.isDesignTime() ? null : EMFSingleton.getInstanciaEM().createNamedQuery("Suceso.buscTodos");
	}

	@Override
	public Object leer(Object entidad) {
		return null;
	}
}
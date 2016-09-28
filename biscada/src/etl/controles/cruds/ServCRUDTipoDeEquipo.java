/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.cruds;

import java.beans.Beans;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import comunes.controles.EMFSingleton;
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

	private List<TipoDeEquipo> lista;

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
				: new ArrayList<TipoDeEquipo>(getQueryTodos().getResultList());
	}

	@Override
	public void borrar(Object entidad) {
	}

	@Override
	public void buscarEnMemoriaPrimaria(Alarma alarma_actual) {

		int indice = lista.lastIndexOf(alarma_actual.getEquipo_en_sitio().getTipo_de_equipo());

		if (indice != -1)
			alarma_actual.getEquipo_en_sitio().setTipo_de_equipo(lista.get(indice));
		else {
			TipoDeEquipo nuevo_objeto = new TipoDeEquipo(
					alarma_actual.getEquipo_en_sitio().getTipo_de_equipo().getDescripcion());
			crear(nuevo_objeto);
			lista.add(nuevo_objeto);
			buscarEnMemoriaPrimaria(alarma_actual);
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
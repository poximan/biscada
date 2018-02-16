/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.servicios.cruds;

import java.beans.Beans;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import comunes.controles.EMFSingleton;
import comunes.entidades.Alarma;
import comunes.entidades.EquipoEnSitio;

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
 * comunes.modelo.EquipoEnSitio
 * 
 * colaboro directamente con la fase de Carga del ETL. la fase anterior de
 * transformacion completa los atributos del objeto Alarma con nuevos tipos,
 * algunos obtenidos directamente y otros a traves de fabricas abstractas.
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, la fase de Carga del proceso ETL me entrega la proxima Alarma
 * que pretende persistir. de ella analizo los datos que son de mi
 * responsabilidad. si son datos nuevos los persisto y devuelvo el tipo creado.
 * Caso contrario obtengo el de BD y lo devuelvo.
 * 
 * LO QUE CONOZCO, la lista de EquipoEnSitio ya persistidas en BD.
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, mi lista interna de EquipoEnSitio
 * 
 * COMO INTERACTUO CON MI COLABORADOR, mediante el metodo
 * buscarEnMemoriaPrimaria(). alli obtengo el indice del objeto si es que ya
 * existe, o creo la nueva instancia y actualizo mi lista interna.
 *
 * @author hdonato
 * 
 */
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
	@SuppressWarnings("unchecked")
	public void actualizarLista() {
		lista = Beans.isDesignTime() ? Collections.emptyList()
				: new ArrayList<EquipoEnSitio>(getQueryTodos().getResultList());
	}

	@Override
	public void borrar(Object entidad) {
	}

	@Override
	public void buscarEnMemoriaPrimaria(Alarma alarma_actual) {

		int indice = lista.lastIndexOf(alarma_actual.getEquipo_en_sitio());

		alarma_actual.getEquipo_en_sitio().setSitio(alarma_actual.getSitio());

		if (indice != -1)
			alarma_actual.setEquipo_en_sitio(lista.get(indice));
		else {
			crear(alarma_actual.getEquipo_en_sitio());
			lista.add(alarma_actual.getEquipo_en_sitio());
			buscarEnMemoriaPrimaria(alarma_actual);
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
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.cruds;

import java.beans.Beans;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jdesktop.observablecollections.ObservableCollections;

import comunes.controles.EMFSingleton;
import comunes.entidades.Alarma;
import comunes.entidades.Suceso;

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
 * comunes.modelo.Suceso
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
 * LO QUE CONOZCO, la lista de Sucesos ya persistidos en BD.
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, mi lista interna de Sucesos
 * 
 * COMO INTERACTUO CON MI COLABORADOR, mediante el metodo
 * buscarEnMemoriaPrimaria(). alli obtengo el indice del objeto si es que ya
 * existe, o creo la nueva instancia y actualizo mi lista interna.
 *
 * @author hdonato
 * 
 */
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

		int indice = lista.lastIndexOf(alarma_actual.getSuceso());

		if (indice != -1)
			alarma_actual.setSuceso((Suceso) lista.get(indice));
		else {
			Suceso nuevo_objeto = new Suceso(alarma_actual.getSuceso().getDescripcion());
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
		return Beans.isDesignTime() ? null : EMFSingleton.getInstanciaEM().createNamedQuery("Suceso.buscTodos");
	}
}
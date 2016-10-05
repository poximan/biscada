/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles;

import java.util.List;

import comunes.controles.EMFSingleton;
import comunes.modelo.Familia;
import comunes.modelo.Sitio;
import comunes.modelo.Suceso;
import comunes.modelo.TipoDeEquipo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO la clase para realizar consultas estaticas a la BD
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO construyo NamedQuerys
 * 
 * LO QUE CONOZCO al framework JPA para mapeo ORM, y el formato impuesto para
 * NamedQuery que me entrega la clase objetivo de la consulta.
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL es JPA
 * 
 * COMO INTERACTUO CON MI COLABORADOR, creo una instancia de NamedQuery segun el
 * formato impuesto por su propietario (ver clases en comunes.modelo.*), llamo
 * al gestor de entidades de JPA y le entrego la NamedQuery creada.
 * 
 * @author hdonato
 *
 */
public class ServConsultaEstatica {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	@SuppressWarnings({ "unchecked" })
	public static List<TipoDeEquipo> getListaEquipos() {
		return EMFSingleton.getInstanciaEM().createNamedQuery("TipoDeEquipo.buscTodos").getResultList();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@SuppressWarnings({ "unchecked" })
	public static List<Familia> getListaFamilia() {
		return EMFSingleton.getInstanciaEM().createNamedQuery("Familia.buscTodos").getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	public static List<Sitio> getListaSitios() {
		return EMFSingleton.getInstanciaEM().createNamedQuery("Sitio.buscTodos").getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	public static List<Suceso> getListaSucesos() {
		return EMFSingleton.getInstanciaEM().createNamedQuery("Suceso.buscTodos").getResultList();
	}

	public ServConsultaEstatica() {
	}
}
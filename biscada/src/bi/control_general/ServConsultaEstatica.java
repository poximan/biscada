/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.control_general;

import java.util.List;

import comunes.control_general.EMFSingleton;
import comunes.modelo.Familia;
import comunes.modelo.Sitio;
import comunes.modelo.Suceso;
import comunes.modelo.TipoDeEquipo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServConsultaEstatica {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServConsultaEstatica() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@SuppressWarnings({ "unchecked" })
	public static List<TipoDeEquipo> getListaEquipos() {
		return EMFSingleton.getInstanciaEM().createNamedQuery("TipoDeEquipo.buscTodos").getResultList();
	}

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
}
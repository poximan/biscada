/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.cruds;

import java.beans.Beans;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import comunes.controles.EMFSingleton;
import comunes.controles.ObjetosBorrables;
import comunes.modelo.Alarma;
import comunes.modelo.ArchivoDBF;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO,
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO,
 * 
 * LO QUE CONOZCO,
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL,
 * 
 * COMO INTERACTUO CON MI COLABORADOR,
 *
 * @author hdonato
 * 
 */
public class ServCRUDArchivoDBF implements InterfazCRUD, ClaveIdentificable, ObjetosBorrables {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private EntityManager em;

	private List<ArchivoDBF> list_disponibles;
	private List<ArchivoDBF> list_procesados;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServCRUDArchivoDBF() {

		em = Beans.isDesignTime() ? null : EMFSingleton.getInstanciaEM();

		list_disponibles = new ArrayList<ArchivoDBF>();
		actualizarLista();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	@SuppressWarnings("unchecked")
	public void actualizarLista() {
		list_procesados = getQueryTodos().getResultList();
	}

	public void agregarDisponibles(Path path_entrada_archivo) {

		ArchivoDBF archivo_propietario = new ArchivoDBF(path_entrada_archivo.toString());
		list_disponibles.add(archivo_propietario);
	}

	@Override
	public void borrar(Object entidad) {
		em.remove(entidad);
	}

	@Override
	public void buscarEnMemoriaPrimaria(Alarma alarma_actual) {

		int indice;

		if ((indice = getIndiceCacheBD(alarma_actual)) != -1) {
			alarma_actual.setArchivo_propietario(list_procesados.get(indice));
			alarma_actual.getArchivo_propietario().setValido(true);
		} else {
			crear(new ArchivoDBF(alarma_actual.getArchivo_propietario().getRuta(),
					alarma_actual.getArchivo_propietario().getComienzo()));
			actualizarLista();
			buscarEnMemoriaPrimaria(alarma_actual);
		}
	}

	@Override
	public void crear(Object entidad) {
		em.persist(entidad);
	}

	public boolean existeEnBD(Path entrada_archivo) {

		ArchivoDBF archivo_propietario = new ArchivoDBF();
		archivo_propietario.setRuta(entrada_archivo.toString());

		return list_procesados.contains(archivo_propietario);
	}

	private int getIndiceCacheBD(Alarma alarma_actual) {
		return list_procesados.lastIndexOf(alarma_actual.getArchivo_propietario());
	}

	public List<ArchivoDBF> getListaDisponibles() {
		return list_disponibles;
	}

	public List<ArchivoDBF> getListaProcesados() {
		actualizarLista();
		return list_procesados;
	}

	@Override
	public Query getQueryTodos() {
		return Beans.isDesignTime() ? null : EMFSingleton.getInstanciaEM().createNamedQuery("ArchivoDBF.buscTodos");
	}

	public void inicia(ArchivoDBF archivo_actual) {
		archivo_actual.setComienzo(Calendar.getInstance());
	}

	@Override
	public void liberarObjetos() {

		list_disponibles.clear();
		list_procesados.clear();
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public void termina(ArchivoDBF archivo_actual) {
		archivo_actual.setFin(Calendar.getInstance());
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_CRUDs;

import java.beans.Beans;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Alarma;
import modelo.ArchivoDBF;
import control_general.EMFSingleton;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServCRUDArchivoDBF implements InterfazCRUD, ClaveIdentificable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private EntityManager em;

	private List<ArchivoDBF> list_disponibles;
	private List<ArchivoDBF> list_procesados;

	private ArchivoDBF archivo_propietario;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServCRUDArchivoDBF() {

		archivo_propietario = new ArchivoDBF();

		em = Beans.isDesignTime() ? null : EMFSingleton.getInstanciaEM();

		list_disponibles = new ArrayList<ArchivoDBF>();

		actualizarLista();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void agregarDisponibles(Path entrada_archivo) {

		ArchivoDBF archivo_propietario = crearDBF(entrada_archivo);
		list_disponibles.add(archivo_propietario);
	}

	private ArchivoDBF crearDBF(Path entrada_archivo) {

		ArchivoDBF archivo_propietario = new ArchivoDBF();
		archivo_propietario.setRuta(entrada_archivo.toString());
		return archivo_propietario;
	}

	public boolean existeEnBD(Path entrada_archivo) {

		archivo_propietario.setRuta(entrada_archivo.toString());
		return list_procesados.contains(archivo_propietario);
	}

	/**
	 * cuando se lanza la ventana de operacion ETL completa los contadores y las listas con valores iniciales relativo
	 * al contexto (archivos para procesador, archivos insertados en BD). en la medida que el usuario usa la pantalla
	 * este contexto comienza a cambiar. para mantaner los contadores y listas de valores en orden se utilizan metodos
	 * de soporte, uno de ellos es este.
	 * 
	 * PRE: archivo fue borrado de BD ... ... ... ... ... ... ... ... ... ... ... ... ... ... ... ... ... ... ... ...
	 * agrega un nuevo archivo a la lista de disponibles, no debe existir en la BD.
	 * 
	 * @param archivo_actual
	 */
	public void agregarDisponible(ArchivoDBF archivo_actual) {
		list_disponibles.add(archivo_actual);
	}

	/**
	 * cuando se lanza la ventana de operacion ETL completa los contadores y las listas con valores iniciales relativo
	 * al contexto (archivos para procesador, archivos insertados en BD). en la medida que el usuario usa la pantalla
	 * este contexto comienza a cambiar. para mantaner los contadores y listas de valores en orden se utilizan metodos
	 * de soporte, uno de ellos es este.
	 * 
	 * PRE: archivo existe en BD ... ... ... ... ... ... ... ... ... ... ... ... ... ... ... ... ... ... ... ... ...
	 * quita un archivo a la lista de disponibles si es que se pudo ingresar satisfactoriamente a la BD.
	 * 
	 * @param archivo_actual
	 */
	public void quitarDisponible(ArchivoDBF archivo_actual) {
		list_disponibles.remove(archivo_actual);
	}

	public int getCantParaProcesar() {
		return list_disponibles.size();
	}

	public int getCantProcesados() {
		return list_procesados.size();
	}

	private int getIndiceCacheBD(Alarma alarma_actual) {
		return list_procesados.lastIndexOf(alarma_actual.getArchivo_propietario());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void actualizarLista() {

		list_procesados = Beans.isDesignTime() ? Collections.emptyList() : getQueryTodos().getResultList();
	}

	@Override
	public void buscarEnMemoriaPrimaria(Alarma alarma_actual) {

		int indice;

		if ((indice = getIndiceCacheBD(alarma_actual)) != -1)
			alarma_actual.setArchivo_propietario(list_procesados.get(indice));
		else {
			alarma_actual.getArchivo_propietario().setValido(true);
			crear(alarma_actual.getArchivo_propietario());
			actualizarLista();
		}
	}

	@Override
	public Query getQueryTodos() {

		return Beans.isDesignTime() ? null : EMFSingleton.getInstanciaEM().createNamedQuery("ArchivoDBF.buscTodos");
	}

	@Override
	public void actualizar(Object entidad) {

		ArchivoDBF archivo_actual = (ArchivoDBF) entidad;

		if (archivo_actual.getComienzo() == null)
			archivo_actual.setComienzo(Calendar.getInstance());
		else
			if (archivo_actual.getFin() == null)
				archivo_actual.setFin(Calendar.getInstance());
	}

	@Override
	public void borrar(Object entidad) {

		ArchivoDBF archivo_actual = (ArchivoDBF) entidad;

		em.merge(archivo_actual);
		em.getTransaction().commit();

		em.getTransaction().begin();
		em.remove(archivo_actual);
	}

	@Override
	public void crear(Object entidad) {

		ArchivoDBF archivo_actual = (ArchivoDBF) entidad;

		em.merge(archivo_actual);
		em.getTransaction().commit();

		em.getTransaction().begin();
		em.persist(archivo_actual);
	}

	@Override
	public Object leer(Object entidad) {
		return null;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public List<ArchivoDBF> getListaProcesados() {
		actualizarLista();
		return list_procesados;
	}

	public List<ArchivoDBF> getListaDisponibles() {
		return list_disponibles;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
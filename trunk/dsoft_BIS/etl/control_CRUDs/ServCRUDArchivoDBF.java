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
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import modelo.Alarma;
import modelo.ArchivoDBF;

import org.jdesktop.observablecollections.ObservableCollections;

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
	private List<ArchivoDBF> list_candidatos_procesar;
	private List<ArchivoDBF> list_candidatos_extraer;
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

		// solo para que no sean nulas, la instancia se controla desde la vista
		list_candidatos_extraer = list_candidatos_procesar = new ArrayList<ArchivoDBF>();

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

	public int getCantParaProcesar() {
		return list_candidatos_procesar.size();
	}

	public int getCantProcesados() {
		return list_procesados.size();
	}

	private int getIndiceCacheBD(Alarma alarma_actual) {
		return list_procesados.lastIndexOf(alarma_actual.getArchivo_propietario());
	}

	public Iterator<ArchivoDBF> getIteradorCandidatosProcesar() {
		return list_candidatos_procesar.iterator();
	}

	public Iterator<ArchivoDBF> getIteradorCandidatosExtraer() {
		return list_candidatos_extraer.iterator();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void actualizarLista() {

		list_procesados = Beans.isDesignTime() ? Collections.emptyList() : ObservableCollections
				.observableList(getQueryTodos().getResultList());
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

		ArchivoDBF archivo_actual = EMFSingleton.getInstanciaEM()
				.find(ArchivoDBF.class, ((ArchivoDBF) entidad).getId());

		em.refresh(archivo_actual);
		em.remove(archivo_actual);
	}

	@Override
	public void crear(Object entidad) {

		ArchivoDBF archivo_actual = (ArchivoDBF) entidad;
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

	public List<ArchivoDBF> getLista_anteriores() {
		return list_procesados;
	}

	public List<ArchivoDBF> getLista_nuevos() {
		return list_disponibles;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	public void setListaCandidatosProcesar(List<ArchivoDBF> list_candidatos_procesar) {
		this.list_candidatos_procesar = list_candidatos_procesar;
	}

	public void setListaCandidatosExtraer(List<ArchivoDBF> list_candidatos_extraer) {
		this.list_candidatos_extraer = list_candidatos_extraer;
	}
}
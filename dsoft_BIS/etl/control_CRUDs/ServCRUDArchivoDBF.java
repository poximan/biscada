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

	private List<ArchivoDBF> lista_procesados;
	private List<ArchivoDBF> lista_disponibles;
	private List<ArchivoDBF> lista_borrar;
	private ArchivoDBF ultimo_archivo_entregado;
	private ArchivoDBF archivo_propietario;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServCRUDArchivoDBF() {

		archivo_propietario = new ArchivoDBF();

		em = Beans.isDesignTime() ? null : EMFSingleton.getInstanciaEM();
		lista_disponibles = new ArrayList<ArchivoDBF>();
		actualizarLista();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void agregarDisponibles(Path entrada_archivo) {

		ArchivoDBF archivo_propietario = crearDBF(entrada_archivo);
		lista_disponibles.add(archivo_propietario);
	}

	public void agregarProcesados(Path entrada_archivo) {

		ArchivoDBF archivo_propietario = crearDBF(entrada_archivo);
		lista_procesados.add(archivo_propietario);
	}

	private ArchivoDBF crearDBF(Path entrada_archivo) {

		ArchivoDBF archivo_propietario = new ArchivoDBF();
		archivo_propietario.setRuta(entrada_archivo.toString());
		return archivo_propietario;
	}

	public boolean existeEnBD(Path entrada_archivo) {

		archivo_propietario.setRuta(entrada_archivo.toString());
		return lista_procesados.contains(archivo_propietario);
	}

	public int getCantDisponibles() {
		return lista_disponibles.size();
	}

	public int getCantProcesados() {
		return lista_procesados.size();
	}

	private int getIndiceCacheBD(Alarma alarma_actual) {
		return lista_procesados.lastIndexOf(alarma_actual.getArchivo_propietario());
	}

	public int getPosActualVentana() {
		return lista_disponibles.indexOf(ultimo_archivo_entregado) + 1;
	}

	public Iterator<ArchivoDBF> getIteradorDisponibles() {
		return lista_disponibles.iterator();
	}

	public Iterator<ArchivoDBF> getIteradorProcesados() {
		return lista_procesados.iterator();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void actualizarLista() {

		lista_procesados = Beans.isDesignTime() ? Collections.emptyList() : ObservableCollections
				.observableList(getQueryTodos().getResultList());
	}

	@Override
	public void buscarEnMemoriaPrimaria(Alarma alarma_actual) {

		int indice;

		if ((indice = getIndiceCacheBD(alarma_actual)) != -1)
			alarma_actual.setArchivo_propietario(lista_procesados.get(indice));
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
		return lista_procesados;
	}

	public List<ArchivoDBF> getLista_nuevos() {
		return lista_disponibles;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	public void setLista_anteriores(List<ArchivoDBF> lista_anteriores) {
		this.lista_procesados = lista_anteriores;
	}

	public void setLista_nuevos(List<ArchivoDBF> lista_nuevos) {
		this.lista_disponibles = lista_nuevos;
	}

	public void setLista_borrar(List<ArchivoDBF> lista_borrar) {
		this.lista_borrar = lista_borrar;
	}
}
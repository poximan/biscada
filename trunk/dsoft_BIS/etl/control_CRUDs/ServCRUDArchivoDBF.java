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

	private List<ArchivoDBF> lista_anteriores;
	private List<ArchivoDBF> lista_nuevos;
	private List<ArchivoDBF> lista_borrar;

	private Iterator<ArchivoDBF> iterador_archivos;
	private ArchivoDBF ultimo_archivo_entregado;
	private ArchivoDBF archivo_propietario;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServCRUDArchivoDBF() {

		archivo_propietario = new ArchivoDBF();

		em = Beans.isDesignTime() ? null : EMFSingleton.getInstanciaEM();
		lista_nuevos = new ArrayList<ArchivoDBF>();
		actualizarLista();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

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
	@SuppressWarnings("unchecked")
	public void actualizarLista() {

		lista_anteriores = Beans.isDesignTime() ? Collections.emptyList() : ObservableCollections
				.observableList(getQueryTodos().getResultList());
	}

	public void agendarArchivo(Path entrada_archivo) {

		ArchivoDBF archivo_propietario = new ArchivoDBF();
		archivo_propietario.setRuta(entrada_archivo.toString());

		lista_nuevos.add(archivo_propietario);
	}

	@Override
	public void borrar(Object entidad) {
	}

	@Override
	public void buscarEnMemoriaPrimaria(Alarma alarma_actual) {

		int indice;

		if ((indice = getIndiceCacheBD(alarma_actual)) != -1)
			alarma_actual.setArchivo_propietario(lista_anteriores.get(indice));
		else {
			alarma_actual.getArchivo_propietario().setValido(true);
			crear(alarma_actual.getArchivo_propietario());
			actualizarLista();
		}
	}

	@Override
	public void crear(Object entidad) {

		ArchivoDBF archivo_actual = (ArchivoDBF) entidad;
		em.persist(archivo_actual);
	}

	public boolean existeEnBD(Path entrada_archivo) {

		archivo_propietario.setRuta(entrada_archivo.toString());
		return lista_anteriores.contains(archivo_propietario);
	}

	public int getCantArchivos() {
		return lista_nuevos.size();
	}

	public int getIndiceCacheBD(Alarma alarma_actual) {
		return lista_anteriores.lastIndexOf(alarma_actual.getArchivo_propietario());
	}

	public int getPosActualVentana() {
		return lista_nuevos.indexOf(ultimo_archivo_entregado) + 1;
	}

	public ArchivoDBF getProximoArchivo() {

		if (iterador_archivos == null)
			iterador_archivos = lista_nuevos.iterator();

		while (iterador_archivos.hasNext()) {
			ultimo_archivo_entregado = iterador_archivos.next();
			return ultimo_archivo_entregado;
		}
		return null;
	}

	@Override
	public Query getQueryTodos() {

		return Beans.isDesignTime() ? null : EMFSingleton.getInstanciaEM().createNamedQuery("ArchivoDBF.buscTodos");
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
		return lista_anteriores;
	}

	public List<ArchivoDBF> getLista_nuevos() {
		return lista_nuevos;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	public void setLista_anteriores(List<ArchivoDBF> lista_anteriores) {
		this.lista_anteriores = lista_anteriores;
	}

	public void setLista_nuevos(List<ArchivoDBF> lista_nuevos) {
		this.lista_nuevos = lista_nuevos;
	}

	public void setLista_borrar(List<ArchivoDBF> lista_borrar) {
		this.lista_borrar = lista_borrar;
	}
}
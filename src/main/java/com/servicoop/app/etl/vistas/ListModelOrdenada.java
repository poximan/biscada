/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.etl.vistas;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;

import main.java.com.servicoop.app.comunes.entidades.ArchivoDBF;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO, una implementacion concreta de javax.swing.AbstractListModel
 * para manejo de comunes.entidades.ArchivoDBF
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO, altas y bajas sobre las listas de archivos disponibles y
 * procesados, de forma ordenada. al finalizar una operacion de alta o baja, la
 * lista siempre esta ordenada
 *
 * LO QUE CONOZCO,
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL, el ordenador java.util.SortedSet
 *
 * COMO INTERACTUO CON MI COLABORADOR, es mi modelo de datos, y esto me asegura
 * que internamente estan ordenados
 *
 * @author hdonato
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ListModelOrdenada extends AbstractListModel<ArchivoDBF> {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private SortedSet model;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ListModelOrdenada() {
		model = new TreeSet();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void add(Object element) {
		if (model.add(element)) {
			fireContentsChanged(this, 0, getSize());
		}
	}

	public void addAll(Object elements[]) {
		Collection c = Arrays.asList(elements);
		model.addAll(c);
		fireContentsChanged(this, 0, getSize());
	}

	public void clear() {
		model.clear();
		fireContentsChanged(this, 0, getSize());
	}

	public boolean contains(ArchivoDBF element) {
		return model.contains(element);
	}

	public Object firstElement() {
		return model.first();
	}

	@Override
	public synchronized ArchivoDBF getElementAt(int index) {
		return (ArchivoDBF) model.toArray()[index];
	}

	@Override
	public int getSize() {
		return model.size();
	}

	public Iterator iterator() {
		return model.iterator();
	}

	public Object lastElement() {
		return model.last();
	}

	public boolean removeElement(Object element) {
		boolean removed = model.remove(element);
		if (removed) {
			fireContentsChanged(this, 0, getSize());
		}
		return removed;
	}
}

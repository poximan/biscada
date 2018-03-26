/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.vistas.eventos;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import main.java.com.servicoop.app.bi.vistas.dimensiones.VistaDimFamiliaSimple;
import main.java.com.servicoop.app.comunes.entidades.Familia;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO una clase que extiende de EventoDim
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO ejecutar el evento correspondiente
 *
 * LO QUE CONOZCO
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL, EventoDim
 *
 * COMO INTERACTUO CON MI COLABORADOR, recibiendo el comportamiento segun el
 * evento que sea escuchado
 *
 */
public class EventoDimFamilia extends EventoDim implements MouseListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoDimFamilia(VistaDimFamiliaSimple vista_dimension) {
		super(vista_dimension);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void mouseClicked(MouseEvent evt) {

		JTable tabla = (JTable) evt.getSource();

		int fila = tabla.getSelectedRow();
		Familia fila_actual = (Familia) tabla.getValueAt(fila, 0);

		terminarConfigVentana(fila, fila_actual.getDescripcion());
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
	}

	@Override
	public void mouseExited(MouseEvent evt) {
	}

	@Override
	public void mousePressed(MouseEvent evt) {
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
	}
}

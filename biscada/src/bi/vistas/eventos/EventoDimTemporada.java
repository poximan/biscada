/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.eventos;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import bi.modelo.Temporada;
import bi.vistas.dimensiones.VistaDimTemporadaSimple;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoDimTemporada extends EventoDim implements MouseListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoDimTemporada(VistaDimTemporadaSimple vista_dimension) {
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
		Temporada fila_actual = (Temporada) tabla.getValueAt(fila, 0);

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
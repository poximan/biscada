/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.eventos;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import bi.entidades.TiempoDespeje;
import bi.vistas.dimensiones.VistaDimTiempoDespejeSimple;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoDimTiempoDespeje extends EventoDim implements MouseListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoDimTiempoDespeje(VistaDimTiempoDespejeSimple vista_dimension) {
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
		TiempoDespeje fila_actual = (TiempoDespeje) tabla.getValueAt(fila, 0);

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
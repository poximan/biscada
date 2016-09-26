/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.eventos;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JTable;

import bi.modelo.TiempoDespeje;
import bi.vistas.dimensiones.VistaDimTiempoDespejeSimple;
import bi.vistas.kpi.VistaKpiTiempoDespeje;

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
		TiempoDespeje tiempo_despeje_actual = (TiempoDespeje) tabla.getValueAt(fila, 0);

		JFrame frame = new JFrame();
		lanzarVentana(frame, new VistaKpiTiempoDespeje(tiempo_despeje_actual));
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
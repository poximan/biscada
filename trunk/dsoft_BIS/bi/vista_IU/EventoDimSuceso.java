/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_IU;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JTable;

import modelo.Suceso;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoDimSuceso extends EventoDim implements MouseListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoDimSuceso(VistaDimSuceso vista_dimension) {
		super(vista_dimension);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void actionPerformed(ActionEvent evt) {

		super.actionPerformed(evt);

		if (evt.getSource() == getVista_dimension().getBtnCalidadServicio()) {

			JFrame frame = new JFrame();
			lanzarVentana(frame, new VistaKpiSitioCalidadServicio(getVista_dimension().getValoresTabla()));
		}
	}

	public void lanzarVentana(JFrame frame, VistaKpiAbstract vista_dimension) {

		frame.setContentPane(vista_dimension);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent evt) {

		JTable tabla = (JTable) evt.getSource();

		int fila = tabla.getSelectedRow();
		Suceso suceso_actual = (Suceso) tabla.getValueAt(fila, 0);

		JFrame frame = new JFrame();
		lanzarVentana(frame, new VistaKpiSuceso(suceso_actual));
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
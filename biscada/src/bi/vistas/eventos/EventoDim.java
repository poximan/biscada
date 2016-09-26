/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bi.vistas.dimensiones.VistaDimAbstractSimple;
import bi.vistas.kpi.VistaKpiSitioCalidadServicio;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public abstract class EventoDim implements ActionListener, MouseListener, VentanaLanzable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private VistaDimAbstractSimple vista_dimension;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoDim(VistaDimAbstractSimple vista_dimension) {
		this.vista_dimension = vista_dimension;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void actionPerformed(ActionEvent evt) {

		if (evt.getSource() == vista_dimension.getBtnEjecutar()) {
			vista_dimension.ejecutarDimension();
		} else if (evt.getSource() == getVista_dimension().getBtnCalidadServicio()) {

			JFrame frame = new JFrame();
			lanzarVentana(frame, new VistaKpiSitioCalidadServicio(getVista_dimension().getValoresTabla()));
		}
	}

	@Override
	public void lanzarVentana(JFrame frame, JPanel vista) {

		frame.setContentPane(vista);
		frame.pack();
		frame.setVisible(true);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public VistaDimAbstractSimple getVista_dimension() {
		return vista_dimension;
	}
}
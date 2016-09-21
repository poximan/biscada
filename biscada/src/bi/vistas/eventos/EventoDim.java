/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vista_evento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import bi.vista_IU.dimensiones.VistaDimAbstractSimple;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public abstract class EventoDim implements ActionListener, MouseListener {

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
		}
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public VistaDimAbstractSimple getVista_dimension() {
		return vista_dimension;
	}
}
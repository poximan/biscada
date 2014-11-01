/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public abstract class EventoDim implements ActionListener, MouseListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private VistaDimAbstract vista_dimension;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoDim(VistaDimAbstract vista_dimension) {
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
		} else
			if (evt.getSource() == vista_dimension.getChckbxAlarmaIncompleta()) {
				vista_dimension.getServ_intervalo().getIntervalo().setPrimer_alarma(null);
				vista_dimension.getServ_intervalo().getIntervalo().setUltima_alarma(null);
			}
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public VistaDimAbstract getVista_dimension() {
		return vista_dimension;
	}
}
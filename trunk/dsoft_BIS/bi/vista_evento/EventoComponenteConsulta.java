/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_evento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import modelo.ComponenteConsulta;
import vista_IU.VistaDimAbstractSimple;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoComponenteConsulta implements ActionListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private ComponenteConsulta vista_consulta;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoComponenteConsulta(ComponenteConsulta vista_consulta) {

		this.vista_consulta = vista_consulta;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void actionPerformed(ActionEvent evt) {

		if (evt.getSource() == vista_consulta.getBtnBuscar())
			vista_consulta.buscar(evt);
	}

	public void lanzarVentanaDimension(JFrame frame, VistaDimAbstractSimple vista_dimension) {

		frame.setContentPane(vista_dimension);
		frame.pack();
		frame.setVisible(true);
	}
}
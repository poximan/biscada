/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoConsulta implements ActionListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private VistaConsultas vista_consulta;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoConsulta(VistaConsultas vista_consulta) {

		this.vista_consulta = vista_consulta;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void actionPerformed(ActionEvent evt) {

		if (evt.getSource() == vista_consulta.getBtnBuscar()) {
			vista_consulta.buscar(evt);

		} else {

			if (!(vista_consulta.getConsultas() == null || vista_consulta.getConsultas().isEmpty())) {

				if (evt.getSource() == vista_consulta.getBtnSitio()) {

					ComponenteMenuDimension frame = new ComponenteMenuDimension("Segundo nivel de evaluacion: Sitio");
					lanzarVentanaDimension(frame, new VistaDimSitio(vista_consulta.getConsultas()));
				} else
					if (evt.getSource() == vista_consulta.getBtnSuceso()) {

						ComponenteMenuDimension frame = new ComponenteMenuDimension(
								"Segundo nivel de evaluacion: Suceso");
						lanzarVentanaDimension(frame, new VistaDimSuceso(vista_consulta.getConsultas()));
					} else
						if (evt.getSource() == vista_consulta.getBtnTiempoDespeje()) {

							ComponenteMenuDimension frame = new ComponenteMenuDimension(
									"Segundo nivel de evaluacion: Tiempo de despeje de las alarmas");
							lanzarVentanaDimension(frame, new VistaDimTiempoDespeje(vista_consulta.getConsultas()));
						} else
							if (evt.getSource() == vista_consulta.getBtnTemporada()) {

								ComponenteMenuDimension frame = new ComponenteMenuDimension(
										"Segundo nivel de evaluacion: Temporada de aparicion de las alarmas");
								lanzarVentanaDimension(frame, new VistaDimTemporada(vista_consulta.getConsultas()));
							}
			} else
				notificarError("consulta vacia, cargue restricciones (opcional) y presione Buscar para obtener resultados");
		}
	}

	public void lanzarVentanaDimension(JFrame frame, VistaDimAbstract vista_dimension) {

		frame.setContentPane(vista_dimension);
		frame.pack();
		frame.setVisible(true);
	}

	public void notificarError(String mensaje) {

		JOptionPane optionPane = new JOptionPane(mensaje, JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog("error");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
}
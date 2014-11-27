/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_evento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import vista_IU.ComponenteMenuDimension;
import vista_IU.VistaConsultaSimple;
import vista_IU.VistaDimAbstract;
import vista_IU.VistaDimSitio;
import vista_IU.VistaDimSuceso;
import vista_IU.VistaDimTemporada;
import vista_IU.VistaDimTiempoDespeje;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoConsultaSimple implements ActionListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private VistaConsultaSimple vista_consulta;

	private ComponenteMenuDimension frame_menu_dimension;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoConsultaSimple(VistaConsultaSimple vista_consulta) {

		this.vista_consulta = vista_consulta;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void actionPerformed(ActionEvent evt) {

		if (!(vista_consulta.getComponenteConsulta().getConsultas() == null || vista_consulta.getComponenteConsulta()
				.getConsultas().isEmpty())) {

			if (evt.getSource() == vista_consulta.getBtnSitio()) {

				frame_menu_dimension = new ComponenteMenuDimension("Segundo nivel de evaluacion: Sitio");
				lanzarVentanaDimension(new VistaDimSitio(vista_consulta.getComponenteConsulta().getConsultas()));
			} else
				if (evt.getSource() == vista_consulta.getBtnSuceso()) {

					frame_menu_dimension = new ComponenteMenuDimension("Segundo nivel de evaluacion: Suceso");
					lanzarVentanaDimension(new VistaDimSuceso(vista_consulta.getComponenteConsulta().getConsultas()));
				} else
					if (evt.getSource() == vista_consulta.getBtnTiempoDespeje()) {

						frame_menu_dimension = new ComponenteMenuDimension(
								"Segundo nivel de evaluacion: Tiempo de despeje de las alarmas");
						lanzarVentanaDimension(new VistaDimTiempoDespeje(vista_consulta.getComponenteConsulta()
								.getConsultas()));
					} else
						if (evt.getSource() == vista_consulta.getBtnTemporada()) {

							frame_menu_dimension = new ComponenteMenuDimension(
									"Segundo nivel de evaluacion: Temporada de aparicion de las alarmas");
							lanzarVentanaDimension(new VistaDimTemporada(vista_consulta.getComponenteConsulta()
									.getConsultas()));
						}
		} else
			notificarError("consulta vacia, cargue restricciones (opcional) y presione Buscar para obtener resultados");
	}

	public void lanzarVentanaDimension(VistaDimAbstract vista_dimension) {

		frame_menu_dimension.setContentPane(vista_dimension);
		frame_menu_dimension.pack();
		frame_menu_dimension.setVisible(true);
	}

	public void notificarError(String mensaje) {

		JOptionPane optionPane = new JOptionPane(mensaje, JOptionPane.ERROR_MESSAGE);
		JDialog dialog = optionPane.createDialog("error");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
}
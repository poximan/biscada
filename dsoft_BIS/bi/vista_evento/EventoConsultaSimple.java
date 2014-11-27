/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_evento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import modelo.ComponenteMenuDimension;
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

	private VistaConsultaSimple vista_simple;

	private ComponenteMenuDimension frame_menu_dimension;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoConsultaSimple(VistaConsultaSimple vista_simple) {

		this.vista_simple = vista_simple;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void actionPerformed(ActionEvent evt) {

		if (!(vista_simple.getComponenteConsulta().getConsultas() == null || vista_simple.getComponenteConsulta()
				.getConsultas().isEmpty())) {

			if (evt.getSource() == vista_simple.getBtnSitio()) {

				frame_menu_dimension = new ComponenteMenuDimension("Segundo nivel de evaluacion: Sitio");
				lanzarVentanaDimension(new VistaDimSitio(vista_simple.getComponenteConsulta().getConsultas()));
			} else
				if (evt.getSource() == vista_simple.getBtnSuceso()) {

					frame_menu_dimension = new ComponenteMenuDimension("Segundo nivel de evaluacion: Suceso");
					lanzarVentanaDimension(new VistaDimSuceso(vista_simple.getComponenteConsulta().getConsultas()));
				} else
					if (evt.getSource() == vista_simple.getBtnTiempoDespeje()) {

						frame_menu_dimension = new ComponenteMenuDimension(
								"Segundo nivel de evaluacion: Tiempo de despeje de las alarmas");
						lanzarVentanaDimension(new VistaDimTiempoDespeje(vista_simple.getComponenteConsulta()
								.getConsultas()));
					} else
						if (evt.getSource() == vista_simple.getBtnTemporada()) {

							frame_menu_dimension = new ComponenteMenuDimension(
									"Segundo nivel de evaluacion: Temporada de aparicion de las alarmas");
							lanzarVentanaDimension(new VistaDimTemporada(vista_simple.getComponenteConsulta()
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
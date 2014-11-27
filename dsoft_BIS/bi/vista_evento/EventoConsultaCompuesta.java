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
import vista_IU.VistaConsultaCompuesta;
import vista_IU.VistaDimAbstract;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoConsultaCompuesta implements ActionListener {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private VistaConsultaCompuesta vista_compuesta;

	private ComponenteMenuDimension frame_menu_dimension;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoConsultaCompuesta(VistaConsultaCompuesta vista_compuesta) {

		this.vista_compuesta = vista_compuesta;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void actionPerformed(ActionEvent evt) {

		if (!(vista_compuesta.getComponenteConsulta().getConsultas() == null || vista_compuesta.getComponenteConsulta()
				.getConsultas().isEmpty())) {

			if (evt.getSource() == vista_compuesta.getBtnUtilizarConsulta()) {
				// TODO lanzar ventana doble tabla
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
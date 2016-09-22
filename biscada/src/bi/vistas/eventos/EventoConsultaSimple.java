/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import bi.modelo.ComponenteMenuDimension;
import bi.vistas.consultas.VistaConsultaSimple;
import bi.vistas.dimensiones.VistaDimSitioSimple;
import bi.vistas.dimensiones.VistaDimSucesoSimple;
import bi.vistas.dimensiones.VistaDimTemporadaSimple;
import bi.vistas.dimensiones.VistaDimTiempoDespejeSimple;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoConsultaSimple implements ActionListener, VentanaLanzable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private VistaConsultaSimple vista_simple;

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

		if (!(vista_simple.getComponenteConsulta().getConsultas() == null
				|| vista_simple.getComponenteConsulta().getConsultas().isEmpty())) {

			ComponenteMenuDimension frame_menu_dimension = new ComponenteMenuDimension("");

			if (evt.getSource() == vista_simple.getBtnFamilia()) {

				frame_menu_dimension.setTitle("Segundo nivel evaluacion: Dim Familia");
				lanzarVentana(frame_menu_dimension,
						new VistaDimSitioSimple(vista_simple.getComponenteConsulta().getConsultas()));

			} else if (evt.getSource() == vista_simple.getBtnSitio()) {

				frame_menu_dimension.setTitle("Segundo nivel evaluacion: Dim Sitio");
				lanzarVentana(frame_menu_dimension,
						new VistaDimSitioSimple(vista_simple.getComponenteConsulta().getConsultas()));

			} else if (evt.getSource() == vista_simple.getBtnSuceso()) {

				frame_menu_dimension.setTitle("Segundo nivel evaluacion: Dim Suceso");
				lanzarVentana(frame_menu_dimension,
						new VistaDimSucesoSimple(vista_simple.getComponenteConsulta().getConsultas()));

			} else if (evt.getSource() == vista_simple.getBtnTiempoDespeje()) {

				frame_menu_dimension.setTitle("Segundo nivel evaluacion: Dim Tiempo de despeje de las alarmas");
				lanzarVentana(frame_menu_dimension,
						new VistaDimTiempoDespejeSimple(vista_simple.getComponenteConsulta().getConsultas()));

			} else if (evt.getSource() == vista_simple.getBtnTemporada()) {

				frame_menu_dimension.setTitle("Segundo nivel evaluacion: Dim Temporada de aparicion de las alarmas");
				lanzarVentana(frame_menu_dimension,
						new VistaDimTemporadaSimple(vista_simple.getComponenteConsulta().getConsultas()));
			}
		} else
			notificarError("consulta vacia, cargue restricciones (opcional) y presione Buscar para obtener resultados");
	}

	@Override
	public void lanzarVentana(JFrame frame, JPanel vista) {

		frame.setContentPane(vista);
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
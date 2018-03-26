/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.vistas.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.java.com.servicoop.app.bi.entidades.ComponenteMenuDimension;
import main.java.com.servicoop.app.bi.vistas.consultas.VistaConsultaSimple;
import main.java.com.servicoop.app.bi.vistas.dimensiones.VistaDimSitioSimple;
import main.java.com.servicoop.app.bi.vistas.dimensiones.VistaDimSucesoSimple;
import main.java.com.servicoop.app.bi.vistas.dimensiones.VistaDimTemporadaSimple;
import main.java.com.servicoop.app.bi.vistas.dimensiones.VistaDimTiempoDespejeSimple;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO un evento generado por un accion de la pantalla Principal para
 * poder pasar al segundo nivel de evaluacion
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO lanzar la ventana del segundo nivel de evaluacion segun
 * corresponda (FAMILIA, SUCESO, ..)
 *
 * LO QUE CONOZCO el evento generado por la pantalla del BI
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL, ComponenteMenuDimension
 *
 * COMO INTERACTUO CON MI COLABORADOR, creo una instancia de el, lanzando una
 * ventana segun el tipo evento al que corresponda.
 *
 * @author hdonato
 *
 */
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

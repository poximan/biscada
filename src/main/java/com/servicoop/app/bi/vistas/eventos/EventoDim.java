/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.vistas.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.java.com.servicoop.app.bi.vistas.consultas.TableModelMedicionTemporal;
import main.java.com.servicoop.app.bi.vistas.dimensiones.VistaDimAbstractSimple;
import main.java.com.servicoop.app.bi.vistas.kpi.VistaKpiCalidadServicio;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO una clase abstracta EventoDim
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO manejar todos los eventos que seran escuchados y delegarlos a la
 * vista que lo implementa
 *
 * LO QUE CONOZCO
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL,
 *
 * COMO INTERACTUO CON MI COLABORADOR,
 *
 */
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
			lanzarVentana(frame, new VistaKpiCalidadServicio(getVista_dimension().getValoresTabla(),
					vista_dimension.getServ_periodo()));
		} else if (evt.getSource() == getVista_dimension().getChckbxContarPeriodosNulos()) {

		}
	}

	public VistaDimAbstractSimple getVista_dimension() {
		return vista_dimension;
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

	public void terminarConfigVentana(int fila, String descripcion) {

		float valores[] = ((TableModelMedicionTemporal) getVista_dimension().getComponenteTabla().getTbl_medicion()
				.getModel()).getDatosFila(fila);

		JFrame frame = new JFrame(descripcion);
		lanzarVentana(frame, new VistaKpiCalidadServicio(valores, vista_dimension.getServ_periodo()));
	}
}

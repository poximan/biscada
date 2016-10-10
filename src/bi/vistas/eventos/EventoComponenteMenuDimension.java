/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.eventos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import bi.entidades.ComponenteMenuConsulta;
import bi.entidades.ComponenteMenuDimension;
import bi.vistas.consultas.VistaConsultaCompuesta;
import bi.vistas.dimensiones.VistaDimAbstractCompuesta;
import bi.vistas.dimensiones.VistaDimFamiliaCompuesta;
import bi.vistas.dimensiones.VistaDimFamiliaSimple;
import bi.vistas.dimensiones.VistaDimSitioCompuesta;
import bi.vistas.dimensiones.VistaDimSitioSimple;
import bi.vistas.dimensiones.VistaDimSucesoCompuesta;
import bi.vistas.dimensiones.VistaDimSucesoSimple;
import bi.vistas.dimensiones.VistaDimTemporadaCompuesta;
import bi.vistas.dimensiones.VistaDimTemporadaSimple;
import bi.vistas.dimensiones.VistaDimTiempoDespejeCompuesta;
import bi.vistas.dimensiones.VistaDimTiempoDespejeSimple;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoComponenteMenuDimension implements ActionListener, VentanaLanzable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(EventoComponenteMenuDimension.class);

	private ComponenteMenuDimension menu_dimension;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EventoComponenteMenuDimension(ComponenteMenuDimension menu_dimension) {

		this.menu_dimension = menu_dimension;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void actionPerformed(ActionEvent evt) {

		if (evt.getSource() == menu_dimension.getItem_salir())
			menu_dimension.dispose();
		else if (evt.getSource() == menu_dimension.getItem_configurar()) {

			log.info("comienza consulta para utilizar como comparador");

			ComponenteMenuConsulta frame_menu_bi = new ComponenteMenuConsulta(
					"BIS - consulta para usar como comparador");

			frame_menu_bi.setContentPane(new VistaConsultaCompuesta(menu_dimension, frame_menu_bi));
			frame_menu_bi.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			frame_menu_bi.setVisible(true);

		} else if (evt.getSource() == menu_dimension.getItem_ejecutar()) {

			log.info("comienza doble tabla, consulta de interes mas consulta como comparador");

			ComponenteMenuDimension frame_menu_dimension = new ComponenteMenuDimension("BIS - comparador de periodos");
			VistaDimAbstractCompuesta vista_compuesta = null;

			if (menu_dimension.getContentPane() instanceof VistaDimFamiliaSimple)
				prepararComparadorFamilias(frame_menu_dimension, vista_compuesta);

			if (menu_dimension.getContentPane() instanceof VistaDimSitioSimple)
				prepararComparadorSitios(frame_menu_dimension, vista_compuesta);

			if (menu_dimension.getContentPane() instanceof VistaDimSucesoSimple)
				prepararComparadorSuceso(frame_menu_dimension, vista_compuesta);

			if (menu_dimension.getContentPane() instanceof VistaDimTemporadaSimple)
				prepararComparadorTemporada(frame_menu_dimension, vista_compuesta);

			if (menu_dimension.getContentPane() instanceof VistaDimTiempoDespejeSimple)
				prepararComparadorTiempoDespeje(frame_menu_dimension, vista_compuesta);
		}
	}

	@Override
	public void lanzarVentana(JFrame frame, JPanel vista) {

		frame.setContentPane(vista);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	private void prepararComparadorFamilias(ComponenteMenuDimension frame_menu_dimension,
			VistaDimAbstractCompuesta vista_compuesta) {

		VistaDimFamiliaSimple vista_temporal = (VistaDimFamiliaSimple) menu_dimension.getContentPane();
		vista_compuesta = new VistaDimFamiliaCompuesta(vista_temporal.getMedicion(), vista_temporal.getPeriodo(),
				vista_temporal.getConsulta(), menu_dimension.getConsulta_comparador(),
				vista_temporal.getChckbxContarPeriodosNulos().isSelected());

		lanzarVentana(frame_menu_dimension, vista_compuesta);
	}

	private void prepararComparadorSitios(ComponenteMenuDimension frame_menu_dimension,
			VistaDimAbstractCompuesta vista_compuesta) {

		VistaDimSitioSimple vista_temporal = (VistaDimSitioSimple) menu_dimension.getContentPane();
		vista_compuesta = new VistaDimSitioCompuesta(vista_temporal.getMedicion(), vista_temporal.getPeriodo(),
				vista_temporal.getConsulta(), menu_dimension.getConsulta_comparador(),
				vista_temporal.getChckbxContarPeriodosNulos().isSelected());

		lanzarVentana(frame_menu_dimension, vista_compuesta);
	}

	private void prepararComparadorSuceso(ComponenteMenuDimension frame_menu_dimension,
			VistaDimAbstractCompuesta vista_compuesta) {

		VistaDimSucesoSimple vista_temporal = (VistaDimSucesoSimple) menu_dimension.getContentPane();
		vista_compuesta = new VistaDimSucesoCompuesta(vista_temporal.getMedicion(), vista_temporal.getPeriodo(),
				vista_temporal.getConsulta(), menu_dimension.getConsulta_comparador(),
				vista_temporal.getChckbxContarPeriodosNulos().isSelected());

		lanzarVentana(frame_menu_dimension, vista_compuesta);
	}

	private void prepararComparadorTemporada(ComponenteMenuDimension frame_menu_dimension,
			VistaDimAbstractCompuesta vista_compuesta) {

		VistaDimTemporadaSimple vista_temporal = (VistaDimTemporadaSimple) menu_dimension.getContentPane();
		vista_compuesta = new VistaDimTemporadaCompuesta(vista_temporal.getMedicion(), vista_temporal.getPeriodo(),
				vista_temporal.getConsulta(), menu_dimension.getConsulta_comparador(),
				vista_temporal.getChckbxContarPeriodosNulos().isSelected());

		lanzarVentana(frame_menu_dimension, vista_compuesta);
	}

	private void prepararComparadorTiempoDespeje(ComponenteMenuDimension frame_menu_dimension,
			VistaDimAbstractCompuesta vista_compuesta) {

		VistaDimTiempoDespejeSimple vista_temporal = (VistaDimTiempoDespejeSimple) menu_dimension.getContentPane();
		vista_compuesta = new VistaDimTiempoDespejeCompuesta(vista_temporal.getMedicion(), vista_temporal.getPeriodo(),
				vista_temporal.getConsulta(), menu_dimension.getConsulta_comparador(),
				vista_temporal.getChckbxContarPeriodosNulos().isSelected());

		lanzarVentana(frame_menu_dimension, vista_compuesta);
	}
}
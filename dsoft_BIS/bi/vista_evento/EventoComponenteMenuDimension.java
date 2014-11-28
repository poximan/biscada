/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_evento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.WindowConstants;

import modelo.ComponenteMenuConsulta;
import modelo.ComponenteMenuDimension;

import org.apache.log4j.Logger;

import vista_IU.VistaConsultaCompuesta;
import vista_IU.VistaDimAbstractCompuesta;
import vista_IU.VistaDimSitioCompuesta;
import vista_IU.VistaDimSitioSimple;
import vista_IU.VistaDimSucesoSimple;
import vista_IU.VistaDimTemporadaSimple;
import vista_IU.VistaDimTiempoDespejeSimple;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class EventoComponenteMenuDimension implements ActionListener {

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
			System.exit(0);
		else
			if (evt.getSource() == menu_dimension.getItem_configurar()) {

				log.trace("comienza consulta para utilizar como comparador");

				ComponenteMenuConsulta frame_menu_bi = new ComponenteMenuConsulta(
						"BIS - consulta para usar como comparador");

				frame_menu_bi.setContentPane(new VistaConsultaCompuesta(menu_dimension, frame_menu_bi));
				frame_menu_bi.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				frame_menu_bi.setVisible(true);
			} else
				if (evt.getSource() == menu_dimension.getItem_ejecutar()) {

					VistaDimAbstractCompuesta vista_compuesta = null;
					
					log.trace("comienza doble tabla, consulta de interes mas consulta como comparador");

					ComponenteMenuDimension frame_menu_dimension = new ComponenteMenuDimension(
							"BIS - comparador de periodos");

					if (menu_dimension.getContentPane() instanceof VistaDimSitioSimple){
						VistaDimSitioSimple vista_temporal = (VistaDimSitioSimple)menu_dimension.getContentPane();
						vista_compuesta = new VistaDimSitioCompuesta(vista_temporal.getConsulta(), menu_dimension.getConsulta_comparador());
					}

					if (menu_dimension.getContentPane() instanceof VistaDimSucesoSimple)
						log.trace("pedido desde dim suceso");

					if (menu_dimension.getContentPane() instanceof VistaDimTemporadaSimple)
						log.trace("pedido desde dim temporada");

					if (menu_dimension.getContentPane() instanceof VistaDimTiempoDespejeSimple)
						log.trace("pedido desde dim tiempo despeje");

					frame_menu_dimension.setContentPane(vista_compuesta);
					frame_menu_dimension.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					frame_menu_dimension.setVisible(true);
				}
	}
}
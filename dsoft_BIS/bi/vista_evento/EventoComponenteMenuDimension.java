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

					log.trace("comienza doble tabla, consulta de interes mas consulta como comparador");

					ComponenteMenuDimension frame_menu_dimension = new ComponenteMenuDimension(
							"BIS - consulta para usar como comparador");

					frame_menu_dimension.setContentPane(new VistaConsultaCompuesta(frame_menu_dimension, null));
					frame_menu_dimension.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					frame_menu_dimension.setVisible(true);
				}
	}
}
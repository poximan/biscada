/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles;

import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import bi.modelo.ComponenteMenuConsulta;
import bi.vista_IU.VistaConsultaSimple;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class GestorBI {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(GestorBI.class);

	private static GestorBI main_bi;
	private ComponenteMenuConsulta frame_menu_bi;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	private GestorBI() {

		frame_menu_bi = new ComponenteMenuConsulta("BIS - software BI para SCADA");
		log.trace("se crea marco para panel consultas");

		frame_menu_bi.setContentPane(new VistaConsultaSimple(frame_menu_bi));
		log.trace("se lanza pantalla de consultas");
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public static GestorBI getSingleton() {

		if (main_bi == null)
			main_bi = new GestorBI();
		return main_bi;
	}

	public void mostrarVentana() {

		frame_menu_bi.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame_menu_bi.setVisible(true);
	}
}

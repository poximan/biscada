/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import vistas.CompMenu;
import vistas.VistaConsultas;

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
	private CompMenu frame_bi;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	private GestorBI() {

		frame_bi = new CompMenu("BIS - software BI para SCADA");
		log.trace("se crea marco para panel consultas");

		frame_bi.setContentPane(new VistaConsultas(frame_bi));
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

		frame_bi.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame_bi.setVisible(true);
	}
}

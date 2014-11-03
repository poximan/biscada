/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

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
	private JFrame frame_bi;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	private GestorBI() {

		frame_bi = new JFrame("BIS - software BI para SCADA");
		log.trace("se crea marco para panel consultas");

		frame_bi.setContentPane(new VistaConsultas());
		log.trace("se lanza pantalla de consultas");
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public static GestorBI getSingletonBI() {

		if (main_bi == null)
			main_bi = new GestorBI();
		return main_bi;
	}

	public void mostrarVentana() {

		frame_bi.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame_bi.pack();
		frame_bi.setVisible(true);
	}
}
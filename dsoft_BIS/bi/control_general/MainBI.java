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

public class MainBI {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(MainBI.class);

	private static MainBI main_bi;
	private JFrame frame_bi;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	private MainBI() {

		frame_bi = new JFrame("BIS - software BI para SCADA");
		log.trace("se crea marco para panel consultas");

		frame_bi.setContentPane(new VistaConsultas());
		log.trace("se lanza pantalla de consultas");
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public static MainBI getSingletonBI() {

		if (main_bi == null)
			main_bi = new MainBI();
		return main_bi;
	}

	public void mostrarVentana() {

		frame_bi.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame_bi.pack();
		frame_bi.setVisible(true);
	}
}

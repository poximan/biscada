/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import vistas.VistaPropiedades;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class GestorPropiedades {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(GestorPropiedades.class);

	private static GestorPropiedades main_propiedades;
	private JFrame frame_etl;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	private GestorPropiedades() {

		frame_etl = new JFrame("Parametros arranque");
		frame_etl.getContentPane().add(new VistaPropiedades(), BorderLayout.CENTER);
		log.trace("se crea panel de propiedades");
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public static GestorPropiedades getSingleton() {

		if (main_propiedades == null)
			main_propiedades = new GestorPropiedades();
		return main_propiedades;
	}

	public void mostrarVentana() {

		frame_etl.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame_etl.setBounds(100, 100, 600, 250);
		frame_etl.setVisible(true);
	}
}

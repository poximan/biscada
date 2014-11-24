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
	private JFrame frame_propiedades;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	private GestorPropiedades() {

		frame_propiedades = new JFrame("Parametros arranque");
		frame_propiedades.getContentPane().add(new VistaPropiedades(), BorderLayout.CENTER);
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

		frame_propiedades.pack();
		frame_propiedades.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame_propiedades.setVisible(true);
	}
}

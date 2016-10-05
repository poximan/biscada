/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package propiedades.controles;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import propiedades.vistas.VistaPropiedades;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO,
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO,
 * 
 * LO QUE CONOZCO,
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL,
 * 
 * COMO INTERACTUO CON MI COLABORADOR,
 *
 */
/**
 * responsable de contruir un marco y agregar all� el panel de edicion y
 * consulta para las propiedades de arranque de la aplicacion.
 * 
 * implementa singleton para asegurar que la interfaz visual de edicion sea
 * siempre la misma.
 * 
 * @author hugo
 *
 */
public class GestorPropiedades {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(GestorPropiedades.class);

	private static GestorPropiedades main_propiedades;

	public static GestorPropiedades getSingleton() {

		if (main_propiedades == null)
			main_propiedades = new GestorPropiedades();
		return main_propiedades;
	}

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	private JFrame frame_propiedades;

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	private GestorPropiedades() {

		frame_propiedades = new JFrame("Parametros arranque");
		frame_propiedades.getContentPane().add(new VistaPropiedades(), BorderLayout.CENTER);
		log.info("se crea panel de propiedades");
	}

	public void mostrarVentana() {

		frame_propiedades.pack();
		frame_propiedades.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame_propiedades.setVisible(true);
	}
}

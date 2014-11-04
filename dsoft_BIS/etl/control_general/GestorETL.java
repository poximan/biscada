/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import vistas.VistaETL;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class GestorETL {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(GestorETL.class);

	private static GestorETL main_etl;
	private JFrame frame_etl;

	/* laburo */
	private String direccion_lectura = "D:\\hugo\\proyectos\\uni\\desarrollo_soft\\datos\\Histo_alarm";

	/* casa */
	// private String direccion_lectura = "D:\\documentos\\hugo\\escull\\desarrollo_soft\\datos\\Histo_alarm";

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	private GestorETL() {

		frame_etl = new JFrame("ETL - archivos .DBF -> MySQL");
		log.trace("se crea marco para panel etl");

		frame_etl.getContentPane().add(new VistaETL(direccion_lectura), BorderLayout.CENTER);
		log.trace("se lanza pantalla etl");
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public static GestorETL getSingleton() {

		if (main_etl == null)
			main_etl = new GestorETL();
		return main_etl;
	}

	public void mostrarVentana() {

		frame_etl.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame_etl.pack();
		frame_etl.setVisible(true);
	}
}

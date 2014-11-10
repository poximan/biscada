/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import vistas.VistaETLPrueba;

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

	private String direccion_lectura = ServPropiedades.getInstancia().getPropiedades()
			.getProperty("Datos.DIRECCION_LECTURA_DATOS");

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	private GestorETL() {

		frame_etl = new JFrame("ETL - archivos .DBF -> MySQL");
		log.trace("se crea marco para panel etl");

		frame_etl.getContentPane().add(new VistaETLPrueba(direccion_lectura), BorderLayout.CENTER);
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
		frame_etl.setBounds(100, 100, 600, 500);
		frame_etl.setVisible(true);
	}
}

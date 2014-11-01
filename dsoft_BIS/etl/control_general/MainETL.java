/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import vistas.VistaETL;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */
public class MainETL {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(MainETL.class);

	private JFrame frame_etl;

	/* laburo */
	// private String direccion_lectura = "D:\\hugo\\proyectos\\uni\\desarrollo_soft\\datos\\minimo";
	// private String direccion_lectura = "D:\\hugo\\proyectos\\uni\\desarrollo_soft\\datos\\moderado";
	// private String direccion_lectura = "D:\\hugo\\proyectos\\uni\\desarrollo_soft\\datos\\Histo_alarm";

	/* casa */
	// private String direccion_lectura = "D:\\documentos\\hugo\\escull\\desarrollo_soft\\datos\\minimo";
	// private String direccion_lectura = "D:\\documentos\\hugo\\escull\\desarrollo_soft\\datos\\moderado";
	private String direccion_lectura = "D:\\documentos\\hugo\\escull\\desarrollo_soft\\datos\\Histo_alarm";

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public MainETL() {

		frame_etl = new JFrame("ETL - archivos .DBF -> MySQL");
		log.trace("se crea marco para panel etl");

		frame_etl.getContentPane().add(new VistaETL(direccion_lectura), BorderLayout.CENTER);
		log.trace("se lanza pantalla etl");

		frame_etl.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame_etl.pack();
		frame_etl.setVisible(true);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* MAIN ........................................ */
	/* ............................................. */

	public static void main(String[] args) {

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex) {
			log.error(MainETL.class.getName());
		}

		PropertyConfigurator.configure("log4j.properties");

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					new MainETL();
				}
				catch (Exception e) {
					log.error("problema en implementacion runnable: " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
}

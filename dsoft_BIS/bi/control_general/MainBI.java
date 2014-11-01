/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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

	private JFrame frame_bi;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public MainBI() {

		frame_bi = new JFrame("BIS - software BI para SCADA");
		log.trace("se crea marco para panel consultas");

		frame_bi.setContentPane(new VistaConsultas());
		log.trace("se lanza pantalla de consultas");

		frame_bi.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame_bi.pack();
		frame_bi.setVisible(true);
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
			log.error(MainBI.class.getName());
		}

		PropertyConfigurator.configure("log4j.properties");

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					new MainBI();
				}
				catch (Exception e) {
					log.error("problema en implementacion runnable: " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
}

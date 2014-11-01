/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import java.awt.EventQueue;

import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */
/*  Dh4Gk2Nz4yP9 */
public class MainComun {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(MainComun.class);

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public MainComun() {
		new MainETL();
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
			log.error(MainComun.class.getName());
		}

		PropertyConfigurator.configure("log4j.properties");

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					new MainComun();
				}
				catch (Exception e) {
					log.error("problema en implementacion runnable: " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
}

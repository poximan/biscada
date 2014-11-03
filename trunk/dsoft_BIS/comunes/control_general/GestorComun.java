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

import vistas.VistaInicio;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */
/*  Dh4Gk2Nz4yP9 */
public class GestorComun {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(GestorComun.class);

	private static GestorComun main_comun;
	private JFrame frame_comun;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/**
	 * @wbp.parser.entryPoint
	 */
	private GestorComun() {

		frame_comun = new JFrame("inicio");
		log.trace("se crea marco para panel consultas");

		frame_comun.setContentPane(new VistaInicio());
		log.trace("se lanza pantalla de consultas");
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public static GestorComun getSingletonBI() {

		if (main_comun == null)
			main_comun = new GestorComun();
		return main_comun;
	}

	public void mostrarVentana() {

		frame_comun.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame_comun.pack();
		frame_comun.setVisible(true);
	}

	/* ............................................. */
	/* ............................................. */
	/* MAIN ........................................ */
	/* ............................................. */

	public static void main(final String[] args) {

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
			log.error(GestorComun.class.getName());
		}

		PropertyConfigurator.configure("log4j.properties");

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					GestorComun.getSingletonBI().mostrarVentana();
				}
				catch (Exception e) {
					log.error("problema en implementacion runnable: " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
}

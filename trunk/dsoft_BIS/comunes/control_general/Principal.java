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

public class Principal {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(Principal.class);

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public Principal() {

		/* clave sincro svn Dh4Gk2Nz4yP9 */
		/* set max heap jvm -Xmx512m */

		long heap_bytes_reservado = Runtime.getRuntime().totalMemory();
		double heap_mbytes_reservado = heap_bytes_reservado / (Math.pow(1024, 2));

		long heap_bytes_libre = Runtime.getRuntime().freeMemory();
		double heap_mbytes_libre = heap_bytes_libre / (Math.pow(1024, 2));

		long heap_bytes_maximo = Runtime.getRuntime().maxMemory();
		double heap_mbytes_maximo = heap_bytes_maximo / (Math.pow(1024, 2));

		log.trace("heap reservado = " + heap_mbytes_reservado + " MB");
		log.trace("heap libre = " + heap_mbytes_libre + " MB");
		log.trace("heap maximo = " + heap_mbytes_maximo + " MB\n");

		GestorBI.getSingleton().mostrarVentana();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* MAIN ........................................ */
	/* ............................................. */

	public static void main(final String[] args) {

		PropertyConfigurator.configure("log4j.properties");

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
			log.error(Principal.class.getName());
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Principal();
				}
				catch (Exception e) {
					log.error("problema en implementacion runnable: " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
}

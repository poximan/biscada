/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.controles;

import java.awt.EventQueue;

import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import bi.controles.GestorBI;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */
/**
 * la primer instancia de un objeto es de esta clase. su responsabilidad es
 * llamar al gestorBI que da comienza a la interaccion del programa con el
 * usuario
 * 
 * @author hugo
 *
 */
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

	/**
	 * 
	 * se utilizo gc (garbage collector) concurrente (-XX:+UseConcMarkSweepGC)
	 * 
	 * The concurrent collector is designed for applications that prefer shorter
	 * garbage collection pauses and that can afford to share processor
	 * resources with the garbage collector while the application is running.
	 * Typically applications which have a relatively large set of long-lived
	 * data (a large tenured generation), and run on machines with two or more
	 * processors tend to benefit from the use of this collector. However, this
	 * collector should be considered for any application with a low pause time
	 * requirement; for example, good results have been observed for interactive
	 * applications with tenured generations of a modest size on a single
	 * processor, especially if using incremental mode. The concurrent collector
	 * is enabled with the command line option -XX:+UseConcMarkSweepGC. para mas
	 * informacion ver
	 * http://www.oracle.com/technetwork/java/javase/gc-tuning-6-140523.html#
	 * par_gc.oom ..............
	 * 
	 * se incremento el heap a 1024m para resolver consultas grandes (-Xmx1024m)
	 *
	 */
	public Principal() {

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

	/**
	 * aqui es donde comienza la aplicacion. el metodo estatico lanza la clase
	 * que lo contiene, es decir aqui se crea la primer instancia de un objeto.
	 * 
	 * @param args
	 *            queda por convencion, no se utilizan parametros de inicio de
	 *            aplicacion.
	 */
	public static void main(final String[] args) {

		PropertyConfigurator.configure("log4j.properties");

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex) {
			log.error(Principal.class.getName());
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Principal();
				} catch (Exception e) {
					log.error("problema en implementacion runnable: " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
}

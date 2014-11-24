/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServReiniciarApp {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void restartApplication() {

		final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		File currentJar = null;

		final ArrayList<String> command = new ArrayList<String>();
		final ProcessBuilder builder = new ProcessBuilder(command);

		try {
			currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		}
		catch (URISyntaxException excepcion) {

			excepcion.printStackTrace();
		}

		command.add(javaBin);
		command.add("-jar");
		command.add(currentJar.getPath());

		try {
			builder.start();
		}
		catch (IOException excepcion) {
			excepcion.printStackTrace();
		}
		System.exit(0);
	}
}
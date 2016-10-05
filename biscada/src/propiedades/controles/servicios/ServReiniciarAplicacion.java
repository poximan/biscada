/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package propiedades.controles.servicios;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;

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
 * -------------------- clase en desuso --------------------
 * 
 * no se pudo implementar correctamente, pero se pretend�a que la aplicacion
 * reiniciase por su sola. Esto es de utilidad cuando se realizan cambios de
 * fondo, como por ejemplo una edicion en el PU.
 * 
 * en cambio en la version actual la aplicacion se cierra automaticamente ante
 * estas ediciones, pero el usuario debe volver a ejecutar manualmente.
 * 
 * extraido de http://java.dzone.com/articles/programmatically-restart-java ...
 * ... ... ... ... ... ... ... ... ... ... otra aproximacion en
 * http://stackoverflow.com/questions/4159802/how-can-i-restart-a-java-
 * application
 * 
 * @author hugo
 */
public class ServReiniciarAplicacion {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private String principal = "D:\\documentos\\hugo\\escull\\poo\\workspace\\dsoft_BIS\\bin\\control_general";

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void reinciarAplicacion() {

		// bytecode java
		String java = System.getProperty("java.home") + "/bin/java";

		// vm arguments
		List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
		StringBuffer vmArgsOneLine = new StringBuffer();

		for (String arg : vmArguments) {
			// if it's the agent argument : we ignore it otherwise the
			// address of the old application and the new one will be in
			// conflict
			if (!arg.contains("-agentlib")) {
				vmArgsOneLine.append(arg);
				vmArgsOneLine.append(" ");
			}
		}
		// init the command to execute, add the vm args
		final StringBuffer cmd = new StringBuffer("\"" + java + "\" " + vmArgsOneLine);
		cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" " + principal);

		// execute the command in a shutdown hook, to be sure that all the
		// resources have been disposed before
		// restarting the application

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					Runtime.getRuntime().exec(cmd.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
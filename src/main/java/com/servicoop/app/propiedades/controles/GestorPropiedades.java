/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.propiedades.controles;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import main.java.com.servicoop.app.propiedades.vistas.VistaPropiedades;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO una clase singleton que devuelve siempre el mismo gestor para
 * la pantalla asociada al manejo del archivo properties
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO construyo un marco de contencion (JFrame) para los componentes
 * graficos
 *
 * LO QUE CONOZCO un JFrame generico para contener al panel
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL es el JFrame generico
 *
 * COMO INTERACTUO CON MI COLABORADOR creo una instancia de el, y configuro los
 * aspectos generales de los marcos, visibilidad y botones de operacion de
 * ventanas (_|#|X)
 *
 * @author hdonato
 *
 */
public class GestorPropiedades {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(GestorPropiedades.class);

	private static GestorPropiedades main_propiedades;

	public static GestorPropiedades getSingleton() {

		if (main_propiedades == null)
			main_propiedades = new GestorPropiedades();
		return main_propiedades;
	}

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	private JFrame frame_propiedades;

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	private GestorPropiedades() {

		frame_propiedades = new JFrame("Parametros arranque");
		frame_propiedades.getContentPane().add(new VistaPropiedades(), BorderLayout.CENTER);
		log.info("se crea panel de propiedades");
	}

	public void mostrarVentana() {

		frame_propiedades.pack();
		frame_propiedades.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame_propiedades.setVisible(true);
	}
}

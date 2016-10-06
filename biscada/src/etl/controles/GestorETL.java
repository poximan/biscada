/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import etl.vistas.VistaETL;

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
 * las pantallas asociadas al ETL
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
 * MI COLABORADOR PRINCIPAL es JFrame
 * 
 * COMO INTERACTUO CON MI COLABORADOR creo una instancia de el, y configuro los
 * aspectos generales de los marcos, visibilidad y botones de operacion de
 * ventanas (_|#|X)
 * 
 * @author hdonato
 *
 */
public class GestorETL {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(GestorETL.class);

	private static VistaETL vista_etl;

	public static GestorETL getSingleton() {

		if (vista_etl != null)
			vista_etl.liberarObjetos();

		return new GestorETL();
	}

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	private JFrame frame_etl;

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	private GestorETL() {

		frame_etl = new JFrame("ETL - archivos .DBF -> MySQL");
		log.info("se crea marco para panel etl");

		vista_etl = new VistaETL();

		frame_etl.getContentPane().add(vista_etl, BorderLayout.CENTER);
		log.info("se lanza pantalla etl");
	}

	public void mostrarVentana() {

		frame_etl.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame_etl.setBounds(100, 100, 600, 500);
		frame_etl.setVisible(true);
	}
}

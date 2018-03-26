/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.controles;

import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import main.java.com.servicoop.app.bi.entidades.ComponenteMenuConsulta;
import main.java.com.servicoop.app.bi.vistas.consultas.VistaConsultaSimple;

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
 * las pantallas asociadas al BI
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO construyo un marco de contencion (JFrame) para los componentes
 * graficos
 *
 * LO QUE CONOZCO bi.modelo.ComponenteMenuConsulta.ComponenteMenuConsulta es la
 * clase que conozco, ella se ocupa de devolver un JFrame adaptado cuando lo
 * necesito
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL es ComponenteMenuConsulta
 *
 * COMO INTERACTUO CON MI COLABORADOR creo una instancia de el, y configuro los
 * aspectos generales de los marcos, visibilidad y botones de operacion de
 * ventanas (_|#|X)
 *
 * @author hdonato
 *
 */
public class GestorBI {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(GestorBI.class);

	private static GestorBI main_bi;

	public static GestorBI getSingleton() {

		if (main_bi == null)
			main_bi = new GestorBI();
		return main_bi;
	}

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	private ComponenteMenuConsulta frame_menu_bi;

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	private GestorBI() {

		frame_menu_bi = new ComponenteMenuConsulta("BIS - software BI para SCADA");
		log.info("se crea marco para panel consultas");

		frame_menu_bi.setContentPane(new VistaConsultaSimple(frame_menu_bi));
		log.info("se lanza pantalla de consultas");
	}

	public void mostrarVentana() {

		frame_menu_bi.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame_menu_bi.setVisible(true);
		VistaConsultaSimple panel = (VistaConsultaSimple) frame_menu_bi.getContentPane();

		try {
			panel.getComponenteConsulta().cargarTodosLosCampos();
		} catch (NullPointerException e) {
		}
	}
}

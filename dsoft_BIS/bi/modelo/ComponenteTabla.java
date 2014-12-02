/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package modelo;

import java.util.List;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import vistas.EventoConfigurable;
import vistas.PanelIniciable;
import control_general.ObjetosBorrables;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ComponenteTabla extends JPanel implements PanelIniciable, EventoConfigurable, ObjetosBorrables {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ComponenteTabla.class);

	private static final long serialVersionUID = 1L;

	private List<Alarma> consultas;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ComponenteTabla(ComponenteMenuConsulta frame_bi) {

	}

	@Override
	public void iniciarComponentes() {

	}

	@Override
	public void configEventos() {

	}

	@Override
	public void liberarObjetos() {

		consultas.clear();
		System.gc();
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.vistas;

import javax.swing.JList;

import comunes.modelo.ArchivoDBF;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class JListProcesado extends JList<ArchivoDBF> {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	// para uso de reflexion durante el manejo de eventos generados por la lista
	public JListProcesado() {
	}

	public JListProcesado(ListModelOrdenada model_disponibles) {
		super(model_disponibles);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
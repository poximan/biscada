/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.util.List;

import modelo.Alarma;
import control_dimensiones.ServDimSitio;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaDimSitio extends VistaDimAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public VistaDimSitio(List<Alarma> consultas) {

		super(new ServDimSitio(), consultas);
		configEventos(new EventoDimSitio(this));
	}

	@Override
	public void configEventos(EventoDim eventos) {

		super.configEventos(eventos);
		getTbl_titulo_filas().addMouseListener(eventos);
	}
}
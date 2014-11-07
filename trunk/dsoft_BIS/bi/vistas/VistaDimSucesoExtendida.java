/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.util.List;

import modelo.Alarma;
import control_dimensiones.ServDimSitio;
import control_dimensiones.ServDimSuceso;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaDimSucesoExtendida extends VistaDimAbstract {

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

	public VistaDimSucesoExtendida(List<Alarma> consultas) {

		super(new ServDimSuceso(), new ServDimSitio(), consultas);
		configEventos(new EventoDimSuceso(this));
	}

	@Override
	public void configEventos(EventoDim eventos) {

		super.configEventos(eventos);
	}
}
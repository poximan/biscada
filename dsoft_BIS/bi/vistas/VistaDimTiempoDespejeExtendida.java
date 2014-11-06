/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vistas;

import java.util.List;

import modelo.Alarma;
import control_dimensiones.ServDimSitio;
import control_dimensiones.ServDimTiempoDespeje;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaDimTiempoDespejeExtendida extends VistaDimAbstract {

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

	public VistaDimTiempoDespejeExtendida(List<Alarma> consultas) {

		super(new ServDimTiempoDespeje(), new ServDimSitio(), consultas);
		configEventos(new EventoDimTiempoDespeje(this));
	}

	@Override
	public void configEventos(EventoDim eventos) {

		super.configEventos(eventos);
	}
}
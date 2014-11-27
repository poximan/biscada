/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_IU;

import java.util.List;

import vista_evento.EventoDim;
import vista_evento.EventoDimTiempoDespeje;
import modelo.Alarma;
import control_dimensiones.ServDimSitio;
import control_dimensiones.ServDimTiempoDespeje;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaDimTiempoDespeje extends VistaDimAbstract {

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

	public VistaDimTiempoDespeje(List<Alarma> consultas) {

		super(new ServDimTiempoDespeje(), new ServDimSitio(), consultas);
		configEventos(new EventoDimTiempoDespeje(this));
	}

	@Override
	public void configEventos(EventoDim eventos) {

		super.configEventos(eventos);
	}
}
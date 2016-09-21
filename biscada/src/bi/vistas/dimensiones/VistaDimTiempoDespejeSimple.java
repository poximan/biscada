/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vista_IU.dimensiones;

import java.util.List;

import bi.controles.dimensiones.ServDimSitio;
import bi.controles.dimensiones.ServDimTiempoDespeje;
import bi.vista_evento.EventoDim;
import bi.vista_evento.EventoDimTiempoDespeje;
import comunes.modelo.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaDimTiempoDespejeSimple extends VistaDimAbstractSimple {

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

	public VistaDimTiempoDespejeSimple(List<Alarma> consultas) {

		super(new ServDimTiempoDespeje(), new ServDimSitio(), consultas);
		configEventos(new EventoDimTiempoDespeje(this));
	}

	@Override
	public void configEventos(EventoDim eventos) {

		super.configEventos(eventos);
	}
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vista_IU.dimensiones;

import java.util.List;

import bi.controles.dimensiones.ServDimSitio;
import bi.controles.dimensiones.ServDimSuceso;
import bi.vista_evento.EventoDim;
import bi.vista_evento.EventoDimSuceso;
import comunes.modelo.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaDimSucesoSimple extends VistaDimAbstractSimple {

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

	public VistaDimSucesoSimple(List<Alarma> consultas) {

		super(new ServDimSuceso(), new ServDimSitio(), consultas);
		configEventos(new EventoDimSuceso(this));
	}

	@Override
	public void configEventos(EventoDim eventos) {

		super.configEventos(eventos);
	}
}
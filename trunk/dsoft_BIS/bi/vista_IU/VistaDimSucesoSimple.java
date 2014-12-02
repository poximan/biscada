/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_IU;

import java.util.List;

import modelo.Alarma;
import vista_evento.EventoDim;
import vista_evento.EventoDimSuceso;
import control_dimensiones.ServDimSitio;
import control_dimensiones.ServDimSuceso;

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
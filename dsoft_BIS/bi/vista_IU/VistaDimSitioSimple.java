/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_IU;

import java.util.List;

import modelo.Alarma;
import vista_evento.EventoDim;
import vista_evento.EventoDimSitio;
import control_dimensiones.ServDimSitio;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaDimSitioSimple extends VistaDimAbstractSimple {

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

	public VistaDimSitioSimple(List<Alarma> consultas) {

		super(new ServDimSitio(), consultas);
		configEventos(new EventoDimSitio(this));
	}

	@Override
	public void configEventos(EventoDim eventos) {

		super.configEventos(eventos);
		getTbl_titulo_filas().addMouseListener(eventos);
	}
}
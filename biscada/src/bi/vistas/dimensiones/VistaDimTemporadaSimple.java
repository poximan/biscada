/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.dimensiones;

import java.util.List;

import bi.controles.servicios.dimensiones.ServDimSitio;
import bi.controles.servicios.dimensiones.ServDimTemporada;
import bi.vistas.eventos.EventoDimTemporada;
import comunes.entidades.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaDimTemporadaSimple extends VistaDimAbstractSimple {

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

	public VistaDimTemporadaSimple(List<Alarma> consultas) {

		super(new ServDimTemporada(), new ServDimSitio(), consultas);
		configEventos(new EventoDimTemporada(this));
	}
}
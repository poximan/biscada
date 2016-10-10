/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.dimensiones;

import java.util.List;

import bi.controles.servicios.dimensiones.ServDimFamilia;
import bi.controles.servicios.dimensiones.ServDimSitio;
import bi.vistas.eventos.EventoDimFamilia;
import comunes.entidades.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaDimFamiliaSimple extends VistaDimAbstractSimple {

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

	public VistaDimFamiliaSimple(List<Alarma> consultas) {

		super(new ServDimFamilia(), new ServDimSitio(), consultas);
		configEventos(new EventoDimFamilia(this));
	}
}
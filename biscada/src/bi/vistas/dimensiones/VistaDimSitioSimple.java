/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vista_IU.dimensiones;

import java.util.List;

import bi.controles.dimensiones.ServDimSitio;
import bi.vista_evento.EventoDim;
import bi.vista_evento.EventoDimSitio;
import comunes.modelo.Alarma;

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
		getComponenteTabla().getTbl_titulo_filas().addMouseListener(eventos);
	}
}
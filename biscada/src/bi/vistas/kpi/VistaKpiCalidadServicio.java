/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.kpi;

import bi.controles.servicios.periodos.ServPeriodoAbstract;
import bi.vistas.eventos.EventoKPI;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaKpiCalidadServicio extends VistaKpiAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/**
	 * @param servPeriodoAbstract
	 * @wbp.parser.constructor
	 */
	public VistaKpiCalidadServicio(float datos[][], ServPeriodoAbstract servPeriodoAbstract) {

		super(datos, servPeriodoAbstract);
		super.configEventos(new EventoKPI(this));
	}

	/**
	 * 
	 * @param fila_datos
	 * @param servPeriodoAbstract
	 */
	public VistaKpiCalidadServicio(float[] fila_datos, ServPeriodoAbstract servPeriodoAbstract) {

		super(fila_datos, servPeriodoAbstract);
		super.configEventos(new EventoKPI(this));
	}
}
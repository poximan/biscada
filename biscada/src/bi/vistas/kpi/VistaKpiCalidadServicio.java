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
	 * utilizado cuando se selecciona una fila de sitio desde esa dimension. en
	 * otras dimensiones este constructor no tiene utilidad.
	 * 
	 * @param serv_dim_sitio
	 * @param serv_periodo
	 * @param serv_medicion
	 * @param sitio_actual
	 * @param filas_datos
	 */
	public VistaKpiCalidadServicio(float[] fila_datos, ServPeriodoAbstract servPeriodoAbstract) {

		super(fila_datos, servPeriodoAbstract);
		super.configEventos(new EventoKPI(this));
	}
}
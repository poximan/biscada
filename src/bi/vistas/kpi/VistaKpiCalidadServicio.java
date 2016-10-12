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

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO la vista KPI calidad de servicio
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO genera una vista que representa el cuarto nivel de evaluacion
 * 
 * LO QUE CONOZCO
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, VistaKpiAbstract
 * 
 * COMO INTERACTUO CON MI COLABORADOR, recibiendo el comportamiento de
 * VistaKpiAbstract
 * 
 */
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

	public VistaKpiCalidadServicio() {
		super();
	}

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
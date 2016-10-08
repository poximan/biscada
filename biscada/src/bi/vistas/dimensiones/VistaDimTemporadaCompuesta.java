/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.dimensiones;

import java.util.List;

import bi.controles.servicios.dimensiones.ServDimTemporada;
import bi.controles.servicios.mediciones.ServMedAbstract;
import bi.controles.servicios.periodos.ServPeriodoAbstract;
import comunes.entidades.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaDimTemporadaCompuesta extends VistaDimAbstractCompuesta {

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

	public VistaDimTemporadaCompuesta(ServMedAbstract serv_medicion, ServPeriodoAbstract serv_periodo,
			List<Alarma> consultas_interes, List<Alarma> consultas_comparador, boolean contar_periodos_nulos) {

		super(new ServDimTemporada(), serv_medicion, serv_periodo, consultas_interes, consultas_comparador,
				contar_periodos_nulos);
	}
}
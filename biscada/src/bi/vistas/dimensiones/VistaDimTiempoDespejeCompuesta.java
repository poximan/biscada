/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vista_IU.dimensiones;

import java.util.List;

import bi.controles.dimensiones.ServDimTiempoDespeje;
import bi.controles.mediciones.ServMedAbstract;
import bi.controles.periodos.ServPeriodoAbstract;
import comunes.modelo.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaDimTiempoDespejeCompuesta extends VistaDimAbstractCompuesta {

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

	public VistaDimTiempoDespejeCompuesta(ServMedAbstract serv_medicion, ServPeriodoAbstract serv_periodo,
			List<Alarma> consultas_interes, List<Alarma> consultas_comparador) {

		super(new ServDimTiempoDespeje(), serv_medicion, serv_periodo, consultas_interes, consultas_comparador);
	}
}
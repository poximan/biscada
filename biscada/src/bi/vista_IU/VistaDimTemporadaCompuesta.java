/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vista_IU;

import java.util.List;

import bi.controles.dimensiones.ServDimTemporada;
import bi.controles.mediciones.ServMedAbstract;
import bi.controles.periodos.ServPeriodoAbstract;
import comunes.modelo.Alarma;

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

	public VistaDimTemporadaCompuesta(ServMedAbstract serv_medicion, ServPeriodoAbstract serv_unidad_tiempo,
			List<Alarma> consultas_interes, List<Alarma> consultas_comparador) {

		super(new ServDimTemporada(), serv_medicion, serv_unidad_tiempo, consultas_interes, consultas_comparador);
	}
}
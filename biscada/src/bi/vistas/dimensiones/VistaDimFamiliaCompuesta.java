/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.dimensiones;

import java.util.List;

import bi.controles.servicios.dimensiones.ServDimFamilia;
import bi.controles.servicios.mediciones.ServMedAbstract;
import bi.controles.servicios.periodos.ServPeriodoAbstract;
import comunes.modelo.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaDimFamiliaCompuesta extends VistaDimAbstractCompuesta {

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

	public VistaDimFamiliaCompuesta(ServMedAbstract serv_medicion, ServPeriodoAbstract serv_periodo,
			List<Alarma> consultas_interes, List<Alarma> consultas_comparador, boolean contar_periodos_nulos) {

		super(new ServDimFamilia(), serv_medicion, serv_periodo, consultas_interes, consultas_comparador,
				contar_periodos_nulos);
	}
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vista_IU;

import java.util.List;

import bi.control_dimensiones.ServDimSitio;
import bi.control_dimensiones.ServDimUnidadTiempoAbstract;
import bi.control_mediciones.ServMedAbstract;
import comunes.modelo.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaDimSitioCompuesta extends VistaDimAbstractCompuesta {

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

	public VistaDimSitioCompuesta(ServMedAbstract serv_medicion, ServDimUnidadTiempoAbstract serv_unidad_tiempo,
			List<Alarma> consultas_interes, List<Alarma> consultas_comparador) {

		super(new ServDimSitio(), serv_medicion, serv_unidad_tiempo, consultas_interes, consultas_comparador);
	}
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_IU;

import java.util.List;

import modelo.Alarma;
import control_dimensiones.ServDimSitio;
import control_dimensiones.ServDimUnidadTiempoAbstract;
import control_mediciones.ServMedAbstract;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaDimSitioCompuesta extends VistaDimAbstractCompuestaModificada {

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
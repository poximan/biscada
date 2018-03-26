/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.vistas.dimensiones;

import java.util.List;

import main.java.com.servicoop.app.bi.controles.servicios.dimensiones.ServDimSitio;
import main.java.com.servicoop.app.bi.controles.servicios.mediciones.ServMedAbstract;
import main.java.com.servicoop.app.bi.controles.servicios.periodos.ServPeriodoAbstract;
import main.java.com.servicoop.app.comunes.entidades.Alarma;

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

	public VistaDimSitioCompuesta(ServMedAbstract serv_medicion, ServPeriodoAbstract serv_periodo,
			List<Alarma> consultas_interes, List<Alarma> consultas_comparador, boolean contar_periodos_nulos) {

		super(new ServDimSitio(), serv_medicion, serv_periodo, consultas_interes, consultas_comparador,
				contar_periodos_nulos);
	}
}

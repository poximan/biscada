/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.dimensiones.tiempos_despeje;

import bi.entidades.TiempoDespeje;
import comunes.entidades.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class FabricaTiempoDespeje {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private Despeje__05 despeje__05;
	private Despeje0510 despeje0510;
	private Despeje1020 despeje1020;
	private Despeje2030 despeje2030;
	private Despeje3040 despeje3040;
	private Despeje4055 despeje4055;
	private Despeje55__ despeje55__;

	private DespejeSinInicio despeje_sin_inicio;
	private DespejeSinFin despeje_sin_fin;
	private DespejeSinInicioFin despeje_sin_inicio_fin;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public FabricaTiempoDespeje() {

		despeje__05 = new Despeje__05();
		despeje0510 = new Despeje0510();
		despeje1020 = new Despeje1020();
		despeje2030 = new Despeje2030();
		despeje3040 = new Despeje3040();
		despeje4055 = new Despeje4055();
		despeje55__ = new Despeje55__();

		despeje_sin_inicio = new DespejeSinInicio();
		despeje_sin_fin = new DespejeSinFin();
		despeje_sin_inicio_fin = new DespejeSinInicioFin();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public TiempoDespeje buscarRango(Alarma alarma_actual) {

		if (despeje__05.contiene(alarma_actual))
			return despeje__05;

		if (despeje0510.contiene(alarma_actual))
			return despeje0510;

		if (despeje1020.contiene(alarma_actual))
			return despeje1020;

		if (despeje2030.contiene(alarma_actual))
			return despeje2030;

		if (despeje3040.contiene(alarma_actual))
			return despeje3040;

		if (despeje4055.contiene(alarma_actual))
			return despeje4055;

		if (despeje55__.contiene(alarma_actual))
			return despeje55__;

		if (despeje_sin_inicio.contiene(alarma_actual))
			return despeje_sin_inicio;

		if (despeje_sin_fin.contiene(alarma_actual))
			return despeje_sin_fin;

		if (despeje_sin_inicio_fin.contiene(alarma_actual))
			return despeje_sin_inicio_fin;

		return null;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

}
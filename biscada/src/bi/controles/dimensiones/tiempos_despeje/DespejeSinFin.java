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

public class DespejeSinFin extends TiempoDespeje {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public DespejeSinFin() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public boolean contiene(Alarma alarma_actual) {

		if (alarma_actual.getFecha_inicio() != null && alarma_actual.getFecha_finalizacion() == null)
			return true;

		return false;
	}

	@Override
	public String toString() {
		return "s/ fecha fin";
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
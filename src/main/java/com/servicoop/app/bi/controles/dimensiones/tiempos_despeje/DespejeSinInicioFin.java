/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.controles.dimensiones.tiempos_despeje;

import main.java.com.servicoop.app.bi.entidades.TiempoDespeje;
import main.java.com.servicoop.app.comunes.entidades.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class DespejeSinInicioFin extends TiempoDespeje {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public DespejeSinInicioFin() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public boolean contiene(Alarma alarma_actual) {

		if (alarma_actual.getFecha_inicio() == null && alarma_actual.getFecha_finalizacion() == null)
			return true;

		return false;
	}

	@Override
	public String toString() {
		return "s/ fecha ini-fin";
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

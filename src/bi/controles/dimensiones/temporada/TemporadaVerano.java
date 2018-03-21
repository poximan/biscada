/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.dimensiones.temporada;

import java.util.Calendar;

import bi.entidades.Temporada;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class TemporadaVerano extends Temporada {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static int dia_inicio = 21;
	private static int dia_fin = 20;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public TemporadaVerano() {

		super(dia_inicio, Calendar.DECEMBER, dia_fin, Calendar.MARCH);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public int correccionFin(Calendar fecha_actual) {

		if (fecha_actual.get(Calendar.MONTH) == Calendar.DECEMBER)
			return 1;
		return 0;
	}

	@Override
	public int correccionInicio(Calendar fecha_actual) {

		if (fecha_actual.get(Calendar.MONTH) == Calendar.DECEMBER)
			return 0;
		return 1;
	}

	@Override
	public String toString() {
		return "verano";
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
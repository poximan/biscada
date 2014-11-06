/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package temporadas;

import java.util.Calendar;

import modelo.Temporada;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class TemporadaVerano extends Temporada {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private int dia_inicio = 21;
	private int dia_fin = 21;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public TemporadaVerano() {

		super();
		definirDesde(dia_inicio, Calendar.DECEMBER);
		definirHasta(dia_fin, Calendar.MARCH);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

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
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

public class TemporadaPrimavera extends Temporada {

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

	public TemporadaPrimavera() {

		super();
		definirDesde(dia_inicio, Calendar.SEPTEMBER);
		definirHasta(dia_fin, Calendar.DECEMBER);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public String toString() {
		return "primavera";
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
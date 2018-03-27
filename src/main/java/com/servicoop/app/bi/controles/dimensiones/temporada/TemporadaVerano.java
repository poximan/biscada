/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.controles.dimensiones.temporada;

import java.util.Calendar;

import main.java.com.servicoop.app.bi.entidades.Temporada;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO,
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO,
 *
 * LO QUE CONOZCO,
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL,
 *
 * COMO INTERACTUO CON MI COLABORADOR,
 *
 */
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

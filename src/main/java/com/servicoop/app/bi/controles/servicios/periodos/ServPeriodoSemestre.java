/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.controles.servicios.periodos;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;

import main.java.com.servicoop.app.bi.entidades.IntervaloFechas;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO una clase que extiende de ServPeriodoAbstract
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO devolver los calculos del periodo en semestres
 *
 * LO QUE CONOZCO
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL, ServPeriodoAbst
 *
 * COMO INTERACTUO CON MI COLABORADOR,
 *
 */
public class ServPeriodoSemestre extends ServPeriodoAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String desripcion = "semestral";
	private int divisor_en_dias = 180;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServPeriodoSemestre(IntervaloFechas intervalo) {
		super(intervalo);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	protected DateTime getCampo_siguiente(DateTime campo_anterior) {
		return campo_anterior.plusMonths(6);
	}

	@Override
	public int getDivisor_en_dias() {
		return divisor_en_dias;
	}

	@Override
	public Period incrementarPeriodo() {
		setPeriodo(getPeriodo().withMonths(6));
		return getPeriodo();
	}

	@Override
	public Period nuevoPeriodo(DateTime tiempo_inicio) {
		return new Period(tiempo_inicio, tiempo_inicio.plusMonths(6));
	}

	@Override
	protected Date toDateCampo_actual(DateTime campo_actual) {
		return campo_actual.toDate();
	}

	@Override
	public String toString() {
		return desripcion;
	}

	@Override
	protected String toStringCampo_actual(DateTime campo_actual) {

		String texto;

		if (campo_actual.getMonthOfYear() <= 5)
			texto = "1�";
		else
			texto = "2�";

		String anio = campo_actual.year().getAsText().substring(2, 4);
		return new String(texto + " sem " + "'" + anio);
	}
}

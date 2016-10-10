/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.servicios.periodos;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;

import bi.entidades.IntervaloFechas;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServPeriodoMes extends ServPeriodoAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String desripcion = "mensual";
	private int divisor_en_dias = 30;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServPeriodoMes(IntervaloFechas intervalo) {
		super(intervalo);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	protected DateTime getCampo_siguiente(DateTime campo_anterior) {
		return campo_anterior.plusMonths(1);
	}

	@Override
	public int getDivisor_en_dias() {
		return divisor_en_dias;
	}

	@Override
	public Period incrementarPeriodo() {
		setPeriodo(getPeriodo().withMonths(1));
		return getPeriodo();
	}

	@Override
	public Period nuevoPeriodo(DateTime tiempo_inicio) {
		return new Period(tiempo_inicio, tiempo_inicio.plusMonths(1));
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

		String mes = campo_actual.monthOfYear().getAsText().substring(0, 3);
		String anio = campo_actual.year().getAsText().substring(2, 4);

		return new String(mes + " '" + anio);
	}
}

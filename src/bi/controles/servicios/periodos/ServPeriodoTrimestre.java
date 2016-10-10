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

public class ServPeriodoTrimestre extends ServPeriodoAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String desripcion = "trimestral";
	private int divisor_en_dias = 90;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServPeriodoTrimestre(IntervaloFechas intervalo) {
		super(intervalo);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	protected DateTime getCampo_siguiente(DateTime campo_anterior) {
		return campo_anterior.plusMonths(3);
	}

	@Override
	public int getDivisor_en_dias() {
		return divisor_en_dias;
	}

	@Override
	public Period incrementarPeriodo() {
		setPeriodo(getPeriodo().withMonths(3));
		return getPeriodo();
	}

	@Override
	public Period nuevoPeriodo(DateTime tiempo_inicio) {
		return new Period(tiempo_inicio, tiempo_inicio.plusMonths(3));
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

		String texto = "Er!";

		if (campo_actual.getMonthOfYear() <= 2)
			texto = "1º";

		if (campo_actual.getMonthOfYear() >= 3 && campo_actual.getMonthOfYear() <= 5)
			texto = "2º";

		if (campo_actual.getMonthOfYear() >= 6 && campo_actual.getMonthOfYear() <= 8)
			texto = "3º";

		if (campo_actual.getMonthOfYear() >= 9)
			texto = "4º";

		String anio = campo_actual.year().getAsText().substring(2, 4);

		return new String(texto + " tri " + "'" + anio);
	}
}

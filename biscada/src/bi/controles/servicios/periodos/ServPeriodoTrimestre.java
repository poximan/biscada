/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.periodos;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;

import bi.modelo.IntervaloFechas;

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
	public int agregarHastaProximaUnidadTiempo(Calendar fecha_alarma_actual) {

		int trimestre_original = getNumeroTrimestre(fecha_alarma_actual), nuevos_meses;

		Calendar fecha_incrementable = Calendar.getInstance();
		fecha_incrementable.setTimeInMillis(fecha_alarma_actual.getTimeInMillis());

		for (nuevos_meses = 0; trimestre_original == getNumeroTrimestre(fecha_incrementable); nuevos_meses++)
			fecha_incrementable.add(Calendar.MONTH, 1);

		return nuevos_meses;
	}

	@Override
	protected DateTime getCampo_siguiente(DateTime campo_anterior) {
		return campo_anterior.plusMonths(3);
	}

	@Override
	public int getDivisor_en_dias() {
		return divisor_en_dias;
	}

	@Override
	public Date[] getEncabezadoFecha() {

		int indice = 0;

		if (getIntervalo().getPrimer_alarma() == null || getIntervalo().getUltima_alarma() == null)
			return new Date[1];

		Date[] encabezado = new Date[getCantidadPeriodos()];

		Calendar fecha_alarma_actual = Calendar.getInstance();
		fecha_alarma_actual.setTimeInMillis(getIntervalo().getPrimer_alarma().getTimeInMillis());

		while (getCantidadPeriodos(fecha_alarma_actual, getIntervalo().getUltima_alarma()) > 0) {

			encabezado[indice++] = fecha_alarma_actual.getTime();

			fecha_alarma_actual.add(Calendar.MONTH, agregarHastaProximaUnidadTiempo(fecha_alarma_actual));
		}
		return encabezado;
	}

	private int getNumeroTrimestre(Calendar caledar_actual) {
		return caledar_actual.get(Calendar.MONTH) / 3 + 1;
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
	public String toString() {
		return desripcion;
	}

	@Override
	protected String toStringCampo_actual(DateTime campo_actual) {

		String texto;

		if (campo_actual.getMonthOfYear() <= 3)
			texto = "1º";
		else {
			if (campo_actual.getMonthOfYear() >= 8)
				texto = "3º";
			else
				texto = "2º";
		}

		String anio = campo_actual.year().getAsText().substring(2, 4);

		return new String(texto + " tri " + "'" + anio);
	}
}

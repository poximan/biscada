/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.periodos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
	public int getDivisor_en_dias() {
		return divisor_en_dias;
	}

	/**
	 * devuelve el encabezado pero en formato "Date"
	 */
	@Override
	public String[] getEncabezado() {

		int indice = 0;

		if (getIntervalo().getPrimer_alarma() == null || getIntervalo().getUltima_alarma() == null)
			return new String[1];

		String[] encabezado = new String[getCantidadPeriodos()];

		Calendar fecha_alarma_actual = Calendar.getInstance();
		fecha_alarma_actual.setTimeInMillis(getIntervalo().getPrimer_alarma().getTimeInMillis());

		while (getCantidadPeriodos(fecha_alarma_actual, getIntervalo().getUltima_alarma()) > 0) {

			encabezado[indice++] = getDescripcionColumnasPeriodo(fecha_alarma_actual) + "'"
					+ String.valueOf(fecha_alarma_actual.get(Calendar.YEAR)).substring(2);

			fecha_alarma_actual.add(Calendar.MONTH, agregarHastaProximaUnidadTiempo(fecha_alarma_actual));
		}
		return encabezado;
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
	public String getDescripcionColumnasPeriodo(Calendar fecha_alarma_actual) {
		return String.valueOf(getNumeroTrimestre(fecha_alarma_actual)) + " tri";
	}

	@Override
	public Period incrementarPeriodo() {
		return getPeriodo().withMonths(3);
	}

	@Override
	public Period nuevoPeriodo(DateTime tiempo_inicio) {
		return new Period(tiempo_inicio, tiempo_inicio.plusMonths(3));
	}

	@Override
	public String toString() {
		return desripcion;
	}
}

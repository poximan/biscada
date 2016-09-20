/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.periodos;

import java.text.DateFormatSymbols;
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
	public int agregarHastaProximaUnidadTiempo(Calendar fecha_alarma_actual) {
		return 1;
	}

	@Override
	public void contrarNuevasFraccionesTiempo(Calendar fecha_alarma_actual, Calendar proxima_fraccion,
			List<Float> fracciones_tiempo) {

		int meses_involucrados = unidadTiempoInvolucradas(fecha_alarma_actual, proxima_fraccion);

		while (meses_involucrados-- > 0)
			fracciones_tiempo.add(new Float(0));

		proxima_fraccion.setTimeInMillis(fecha_alarma_actual.getTimeInMillis());
		proxima_fraccion.add(Calendar.MONTH, agregarHastaProximaUnidadTiempo(fecha_alarma_actual));
	}

	@Override
	public int getDivisor_en_dias() {
		return divisor_en_dias;
	}

	/*
	 * Genero un nuevo m�todo para devolver el encabezado pero en formato
	 * "Date" (non-Javadoc)
	 * 
	 * @see control_dimensiones.ServDimUnidadTiempoAbstract#getEncabezado()
	 */

	@Override
	public String[] getEncabezado() {

		int indice = 0;

		if (getIntervalo().getPrimer_alarma() == null || getIntervalo().getUltima_alarma() == null)
			return new String[1];

		String[] encabezado = new String[unidadTiempoInvolucradas(getIntervalo().getPrimer_alarma(),
				getIntervalo().getUltima_alarma())];

		Calendar fecha_alarma_actual = Calendar.getInstance();
		fecha_alarma_actual.setTimeInMillis(getIntervalo().getPrimer_alarma().getTimeInMillis());

		while (unidadTiempoInvolucradas(fecha_alarma_actual, getIntervalo().getUltima_alarma()) > 0) {

			encabezado[indice++] = getTextoColumnaUnidadTiempo(fecha_alarma_actual) + "'"
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

		Date[] encabezado = new Date[unidadTiempoInvolucradas(getIntervalo().getPrimer_alarma(),
				getIntervalo().getUltima_alarma())];

		Calendar fecha_alarma_actual = Calendar.getInstance();
		fecha_alarma_actual.setTimeInMillis(getIntervalo().getPrimer_alarma().getTimeInMillis());

		while (unidadTiempoInvolucradas(fecha_alarma_actual, getIntervalo().getUltima_alarma()) > 0) {

			encabezado[indice++] = fecha_alarma_actual.getTime();

			fecha_alarma_actual.add(Calendar.MONTH, agregarHastaProximaUnidadTiempo(fecha_alarma_actual));
		}
		return encabezado;
	}

	@Override
	public String getTextoColumnaUnidadTiempo(Calendar fecha_alarma_actual) {

		int indice_mes = fecha_alarma_actual.get(Calendar.MONTH);
		String descripcion_mes = new DateFormatSymbols().getMonths()[indice_mes];

		return descripcion_mes.substring(0, 3);
	}

	@Override
	public Period incrementarPeriodo() {
		return getPeriodo().withMonths(1);
	}

	@Override
	public Period nuevoPeriodo(DateTime tiempo_inicio) {
		return new Period(tiempo_inicio, tiempo_inicio.plusMonths(1));
	}

	@Override
	public String toString() {
		return desripcion;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	@Override
	public int unidadTiempoInvolucradas(Calendar primer_alarma, Calendar ultima_alarma) {

		int dif_anios = ultima_alarma.get(Calendar.YEAR) - primer_alarma.get(Calendar.YEAR);
		return ((dif_anios * 12) + ultima_alarma.get(Calendar.MONTH) - primer_alarma.get(Calendar.MONTH)) + 1;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}

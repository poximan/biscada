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

public class ServPeriodoQuincena extends ServPeriodoAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String desripcion = "quincenal";
	private int divisor_en_dias = 15;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServPeriodoQuincena(IntervaloFechas intervalo) {
		super(intervalo);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public int agregarHastaProximaUnidadTiempo(Calendar fecha_alarma_actual) {

		Calendar temporario = Calendar.getInstance();
		temporario.setTimeInMillis(fecha_alarma_actual.getTimeInMillis());

		int nuevos_dias = 0;

		while (getNumeroQuincena(temporario) == getNumeroQuincena(fecha_alarma_actual)) {
			nuevos_dias++;
			temporario.add(Calendar.DAY_OF_MONTH, 1);
		}
		return nuevos_dias;
	}

	@Override
	protected DateTime getCampo_siguiente(DateTime campo_anterior) {
		return campo_anterior.plusDays(15);
	}

	@Override
	public int getDivisor_en_dias() {
		return divisor_en_dias;
	}

	/*
	 * Genero un nuevo mï¿½todo para devolver el encabezado pero en formato
	 * "Date"
	 */
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

			fecha_alarma_actual.add(Calendar.DAY_OF_MONTH, agregarHastaProximaUnidadTiempo(fecha_alarma_actual));
		}
		return encabezado;
	}

	/**
	 * devuelve el numero de quincena pensada como un entero entre 1-24 para
	 * representar todas las quincenas del aï¿½o
	 * 
	 * @param fecha_actual
	 * @return
	 */
	private int getNumeroQuincena(Calendar fecha_actual) {

		int quincenas_acumuladas_hasta_mes_anterior = fecha_actual.get(Calendar.MONTH) * 2;

		if (fecha_actual.get(Calendar.DAY_OF_MONTH) <= 15)
			quincenas_acumuladas_hasta_mes_anterior += 1;
		else
			quincenas_acumuladas_hasta_mes_anterior += 2;

		return quincenas_acumuladas_hasta_mes_anterior;
	}

	@Override
	public Period incrementarPeriodo() {
		setPeriodo(getPeriodo().withDays(15));
		return getPeriodo();
	}

	@Override
	public Period nuevoPeriodo(DateTime tiempo_inicio) {
		return new Period(tiempo_inicio, tiempo_inicio.plusDays(15));
	}

	@Override
	public String toString() {
		return desripcion;
	}

	@Override
	protected String toStringCampo_actual(DateTime campo_actual) {

		String texto;

		if (campo_actual.getDayOfMonth() <= 15)
			texto = "1º ";
		else
			texto = "2º ";

		String mes = campo_actual.monthOfYear().getAsText().substring(0, 3);
		String anio = campo_actual.year().getAsText().substring(2, 4);

		return new String(texto + mes + " '" + anio);
	}
}
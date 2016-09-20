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

	private ServPeriodoMes serv_mes;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServPeriodoQuincena(IntervaloFechas intervalo, ServPeriodoMes serv_mes) {
		super(intervalo);
		this.serv_mes = serv_mes;
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
	public int getDivisor_en_dias() {
		return divisor_en_dias;
	}

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

			fecha_alarma_actual.add(Calendar.DAY_OF_MONTH, agregarHastaProximaUnidadTiempo(fecha_alarma_actual));
		}
		return encabezado;
	}

	/*
	 * Genero un nuevo m�todo para devolver el encabezado pero en formato
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
	 * representar todas las quincenas del a�o
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
	public String getDescripcionColumnasPeriodo(Calendar fecha_alarma_actual) {

		String texto_quincena;

		if (fecha_alarma_actual.get(Calendar.DAY_OF_MONTH) <= 15)
			texto_quincena = "1 ";
		else
			texto_quincena = "2 ";

		String mes = serv_mes.getDescripcionColumnasPeriodo(fecha_alarma_actual);

		return new String(texto_quincena + mes);
	}

	@Override
	public Period incrementarPeriodo() {
		return getPeriodo().withDays(15);
	}

	@Override
	public Period nuevoPeriodo(DateTime tiempo_inicio) {
		return new Period(tiempo_inicio, tiempo_inicio.plusDays(15));
	}
		
	@Override
	public String toString() {
		return desripcion;
	}
}
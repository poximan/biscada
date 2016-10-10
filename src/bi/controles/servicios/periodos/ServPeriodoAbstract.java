/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.servicios.periodos;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import bi.entidades.IntervaloFechas;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO una clase Abstracta que contiene los metodos comunes
 * de las clases para calcular los periodos
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO obtener el tiempo de inicio y pasarlo a la clase del tipo de periodo correspondiente
 * 
 * LO QUE CONOZCO el periodo en el que se requieren que se realicen los calculos
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, ServDimAbstract
 * 
 * COMO INTERACTUO CON MI COLABORADOR, devolviendo los calculos requeridos
 *
 */
public abstract class ServPeriodoAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private IntervaloFechas intervalo;

	private Period periodo;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServPeriodoAbstract(IntervaloFechas intervalo) {

		this.intervalo = intervalo;

		DateTime tiempo_inicio = new DateTime(intervalo.getPrimer_alarma().getTimeInMillis());
		periodo = nuevoPeriodo(tiempo_inicio);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * partiendo del campo de interes del argumento, devuelve el siguiente
	 * 
	 * @param campo_anterior
	 * @return
	 */
	protected abstract DateTime getCampo_siguiente(DateTime campo_anterior);

	public int getCantidadPeriodos() {
		return getCantidadPeriodos(intervalo.getPrimer_alarma(), intervalo.getUltima_alarma());
	}

	public int getCantidadPeriodos(Calendar primer_alarma, Calendar ultima_alarma) {

		int contador_periodos = 1;

		DateTime tiempo_inicio = new DateTime(primer_alarma.getTimeInMillis());
		DateTime tiempo_fin = new DateTime(ultima_alarma.getTimeInMillis());

		Interval intervalo_patron = new Interval(tiempo_inicio, tiempo_fin);
		Interval intervalo_test = new Interval(tiempo_inicio, getPeriodo());

		while (intervalo_patron.overlaps(intervalo_test)) {
			intervalo_test = new Interval(intervalo_test.getEnd(), siguientePeriodo());
			contador_periodos++;
		}

		return contador_periodos;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public abstract int getDivisor_en_dias();

	public Date[] getEncabezadoFecha() {

		int total = getCantidadPeriodos();

		Date[] encabezado = new Date[total];
		DateTime campo_actual = new DateTime(intervalo.getPrimer_alarma().getTimeInMillis());

		for (int indice = 0; indice < total; indice++) {

			encabezado[indice] = toDateCampo_actual(campo_actual);

			DateTime campo_anterior = new DateTime(campo_actual);

			campo_actual = getCampo_siguiente(campo_anterior);
		}
		return encabezado;
	}

	public String[] getEncabezadoString() {

		int total = getCantidadPeriodos();

		String[] encabezado = new String[total];
		DateTime campo_actual = new DateTime(intervalo.getPrimer_alarma().getTimeInMillis());

		for (int indice = 0; indice < total; indice++) {

			encabezado[indice] = toStringCampo_actual(campo_actual);

			DateTime campo_anterior = new DateTime(campo_actual);

			campo_actual = getCampo_siguiente(campo_anterior);
		}
		return encabezado;
	}

	public IntervaloFechas getIntervalo() {
		return intervalo;
	}

	public Period getPeriodo() {
		return periodo;
	}

	protected abstract Period incrementarPeriodo();

	public abstract Period nuevoPeriodo(DateTime tiempo_inicio);

	protected void setPeriodo(Period periodo) {
		this.periodo = periodo;
	}

	public Period siguientePeriodo() {
		return incrementarPeriodo();
	}

	/**
	 * convierte a Date segun el campo de interes en el argumento.
	 * 
	 * @param campo_actual
	 * @return
	 */
	protected abstract Date toDateCampo_actual(DateTime campo_actual);

	/**
	 * convierte a String segun el campo de interes en el argumento.
	 * 
	 * @param campo_actual
	 * @return
	 */
	protected abstract String toStringCampo_actual(DateTime campo_actual);

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.periodos;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import bi.modelo.IntervaloFechas;
import comunes.modelo.Alarma;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

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

	public abstract int agregarHastaProximaUnidadTiempo(Calendar fecha_alarma_actual);

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

		int contador_periodos = 0;

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

	public String[] getEncabezado() {

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

	public abstract Date[] getEncabezadoFecha();

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
	 * convierte a String segun el campo de interes en el argumento.
	 * 
	 * @param campo_actual
	 * @return
	 */
	protected abstract String toStringCampo_actual(DateTime campo_actual);

	/**
	 * de un set de alarmas que fue fraccionado segun una unidad de tiempo como
	 * quincena, mes, etc se busca la ultima, es decir la ultima quincena,
	 * ultimo mes, etc del intervalo definido por el rango. De todas las que
	 * pertenecen a la ultima fraccion se devuelve el total de repeticiones, es
	 * decir cu�ntas ocurrencias hubieron de una determinada dimension durante
	 * la ultima fraccion de tiempo
	 * 
	 * @param lista_interes
	 * @return
	 */
	public float ultimaFraccion(List<Alarma> lista_interes) {

		float cantidad_alarmas_ultimo_periodo = 0;
		Calendar primer_alarma;
		Collections.sort(lista_interes);
		Collections.reverse(lista_interes);

		try {
			primer_alarma = lista_interes.get(0).getFecha_inicio();
		} catch (IndexOutOfBoundsException excepcion) {
			return cantidad_alarmas_ultimo_periodo;
		}

		DateTime tiempo_inicio = new DateTime(primer_alarma.getTimeInMillis());

		Interval intervalo = new Interval(getPeriodo(), tiempo_inicio);

		for (Alarma alarma_actual : lista_interes) {

			DateTime test = new DateTime(alarma_actual.getFecha_inicio().getTimeInMillis());

			if (!intervalo.contains(test))
				break;
			cantidad_alarmas_ultimo_periodo++;
		}
		return cantidad_alarmas_ultimo_periodo;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
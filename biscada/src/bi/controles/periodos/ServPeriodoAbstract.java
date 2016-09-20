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

	private int contados_periodos;
	private Period periodo;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServPeriodoAbstract(IntervaloFechas intervalo) {
		this.intervalo = intervalo;
		this.contados_periodos = 0;
	}

	public abstract int agregarHastaProximaUnidadTiempo(Calendar fecha_alarma_actual);

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void crearPeriodo(DateTime tiempo_inicio) {
		periodo = nuevoPeriodo(tiempo_inicio);
	}

	public abstract int getDivisor_en_dias();

	/**
	 * resuelve el encabezado de las columnas de una tabla, para un intervalo de
	 * tiempo dado.
	 * 
	 * @return un arreglo de string con la cantidad de elementos encontrada para
	 *         la unidad de tiempo elegida en el intervalo
	 */
	public abstract String[] getEncabezado();

	public abstract Date[] getEncabezadoFecha();

	public IntervaloFechas getIntervalo() {
		return intervalo;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public Period getPeriodo() {
		return periodo;
	}

	public abstract String getDescripcionColumnasPeriodo(Calendar fecha_alarma_actual);

	public abstract Period incrementarPeriodo();

	public abstract Period nuevoPeriodo(DateTime tiempo_inicio);

	public void setPeriodo(Period periodo) {
		this.periodo = periodo;
	}

	public Period siguientePeriodo() {

		periodo = incrementarPeriodo();
		contados_periodos++;
		return periodo;
	}

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
		crearPeriodo(tiempo_inicio);
		Interval intervalo = new Interval(getPeriodo(), tiempo_inicio);

		for (Alarma alarma_actual : lista_interes) {

			DateTime test = new DateTime(alarma_actual.getFecha_inicio().getTimeInMillis());

			if (!intervalo.contains(test))
				break;
			cantidad_alarmas_ultimo_periodo++;
		}
		return cantidad_alarmas_ultimo_periodo;
	}

	public int getCantidadPeriodos(Calendar fecha_alarma_actual, Calendar ultima_alarma) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getCantidadPeriodos() {
		return contados_periodos;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
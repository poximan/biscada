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
	}

	public abstract int agregarHastaProximaUnidadTiempo(Calendar fecha_alarma_actual);

	/**
	 * eventualmente la alarma que se esta analizando podr�a superar al mes
	 * que se espera para ella. por este motivo es necesario preguntar por >=.
	 * por ejemplo, si una lista de 4 alarmas posee 3 en marzo y la ultima en
	 * junio, entonces cuando se analice la ultima alarma, esta estara dos meses
	 * adelante del mes esperado.
	 * 
	 * @param fecha_alarma_actual
	 *            es la ultima alarma recuperada de la lista
	 * @param fecha_referencia
	 *            es el mes esperado para la proxima alarma
	 * @return
	 */
	public boolean alarmaEsMayorIgualReferencia(Calendar fecha_alarma_actual, Calendar fecha_referencia) {

		if (fecha_alarma_actual.before(fecha_referencia))
			return false;

		return true;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public abstract void contrarNuevasFraccionesTiempo(Calendar fecha_alarma_actual, Calendar fecha_referencia,
			List<Float> fracciones_tiempo);

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

	public abstract String getTextoColumnaUnidadTiempo(Calendar fecha_alarma_actual);

	public abstract Period incrementarPeriodo();

	public abstract Period nuevoPeriodo(DateTime tiempo_inicio);

	public void setPeriodo(Period periodo) {
		this.periodo = periodo;
	}

	public Period siguientePeriodo() {

		periodo = incrementarPeriodo();
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

		float valor_retorno = 0;
		Calendar primer_alarma;
		Collections.sort(lista_interes);
		Collections.reverse(lista_interes);

		try {
			primer_alarma = lista_interes.get(0).getFecha_inicio();
		} catch (IndexOutOfBoundsException excepcion) {
			return 0;
		}

		for (Alarma alarma_actual : lista_interes) {

			Calendar fecha_alarma_actual = alarma_actual.getFecha_inicio();

			if (unidadTiempoInvolucradas(fecha_alarma_actual, primer_alarma) > 1)
				break;
			valor_retorno++;
		}

		return valor_retorno;
	}

	/**
	 * cuenta la cantidad de unidades de tiempo existentes entre dos fechas
	 * dadas. por ejemplo dos fechas que se separan por un mes entre si,
	 * contadas de a mes daran como resultado 1, pero el mismo set de datos
	 * comparado contra una unidad de tiempo quincena, resultara en 4 unidades.
	 * por este motivo la responsabilidad de determinar las unidades de tiempo
	 * involucradas es establecida por la clase concreta
	 * 
	 * @param primer_alarma
	 * @param ultima_alarma
	 * @return
	 */
	public abstract int unidadTiempoInvolucradas(Calendar primer_alarma, Calendar ultima_alarma);

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
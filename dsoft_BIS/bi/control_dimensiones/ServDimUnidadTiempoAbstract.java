/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_dimensiones;

import java.util.Calendar;
import java.util.List;

import modelo.Alarma;
import modelo.IntervaloFechas;
import control_general.ServIntervaloFechas;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

public abstract class ServDimUnidadTiempoAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private IntervaloFechas intervalo;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServDimUnidadTiempoAbstract(IntervaloFechas intervalo) {
		this.intervalo = intervalo;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * eventualmente la alarma que se esta analizando podría superar al mes que se espera para ella. por este motivo es
	 * necesario preguntar por >=. por ejemplo, si una lista de 4 alarmas posee 3 en marzo y la ultima en junio,
	 * entonces cuando se analice la ultima alarma, esta estara dos meses adelante del mes esperado.
	 * 
	 * @param fecha_alarma_actual
	 *            es la ultima alarma recuperada de la lista
	 * @param ultima_fecha_registrada
	 *            es el mes esperado para la proxima alarma
	 * @return
	 */
	public boolean alarmaEsMayorIgualReferencia(Calendar fecha_alarma_actual, Calendar ultima_fecha_registrada) {

		if (fecha_alarma_actual.before(ultima_fecha_registrada))
			return false;

		return true;
	}

	public abstract void contrarNuevasFraccionesTiempo(ServIntervaloFechas serv_intervalo,
			Calendar fecha_alarma_actual, Calendar proxima_fraccion, List<Float> fracciones_tiempo);

	public abstract int getDivisor_en_dias();

	/**
	 * resuelve el encabezado de las columnas de una tabla, para un intervalo de tiempo dado.
	 * 
	 * @return un arreglo de string con la cantidad de elementos encontrada para la unidad de tiempo elegida en el
	 *         intervalo
	 */
	public abstract String[] getEncabezado();

	/**
	 * de un set de alarmas que fue fraccionado segun una unidad de tiempo como quincena, mes, etc se busca la ultima,
	 * es decir la ultima quincena, ultimo mes, etc del intervalo definido por el rango. De todas las que pertenecen a
	 * la ultima fraccion se devuelve el total de repeticiones, es decir cuántas ocurrencias hubieron de una determinada
	 * dimension durante la ultima fraccion de tiempo
	 * 
	 * @param lista_interes
	 * @return
	 */
	public float ultimaFraccion(List<Alarma> lista_interes) {
		// TODO hugo: completar ultima fraccion
		return 0;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public IntervaloFechas getIntervalo() {
		return intervalo;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
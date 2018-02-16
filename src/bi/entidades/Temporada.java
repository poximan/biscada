/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.entidades;

import java.util.Calendar;

import comunes.entidades.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * implementa patron plantilla
 * 
 * @author hdonato
 * 
 */
public abstract class Temporada {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private Calendar fecha_limite_inicio;
	private Calendar fecha_limite_fin;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public Temporada(int dia_inicio, int mes_inicio, int dia_fin, int mes_fin) {

		fecha_limite_inicio = Calendar.getInstance();
		establecerRangosInicio(dia_inicio, mes_inicio);

		fecha_limite_fin = Calendar.getInstance();
		establecerRangosFin(dia_fin, mes_fin);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public boolean contiene(Alarma alarma_actual) {

		Calendar fecha_actual = alarma_actual.getFecha_inicio();

		fecha_limite_inicio.set(Calendar.YEAR, fecha_actual.get(Calendar.YEAR) - correccionInicio(fecha_actual));
		fecha_limite_inicio.get(Calendar.YEAR);

		fecha_limite_fin.set(Calendar.YEAR, fecha_actual.get(Calendar.YEAR) + correccionFin(fecha_actual));
		fecha_limite_fin.get(Calendar.YEAR);

		return (!fecha_limite_inicio.after(fecha_actual) && !fecha_limite_fin.before(fecha_actual));
	}

	/**
	 * factor de correccion. todos los rangos de fechas se miden igual salvo el de
	 * temporada verano. alli, por como esta implementada la logica, la comparacion
	 * de fecha inicio se hace por una fecha cuyo a�o adelantado una unidad, por lo
	 * tanto es necesario corregir esta diferencia
	 * 
	 * @param fecha_actual
	 *            se toma como referencia para saber si se debe aplicar la
	 *            correccion. Incluso para la temporada verano, si la alarma es de
	 *            la parte de la temporada que cae despues de diciembre se debe
	 *            aplicar correccion.
	 * 
	 * @return segun el contexto podr�a ser 1 para el verano, y 0 para el resto de
	 *         las temporadas
	 */
	public int correccionFin(Calendar fecha_actual) {
		return 0;
	}

	/**
	 * factor de correccion. todos los rangos de fechas se miden igual salvo el de
	 * temporada verano. alli, por como esta implementada la logica, la comparacion
	 * de fecha inicio se hace por una fecha cuyo a�o adelantado una unidad, por lo
	 * tanto es necesario corregir esta diferencia
	 * 
	 * @param fecha_actual
	 *            se toma como referencia para saber si se debe aplicar la
	 *            correccion. Incluso para la temporada verano, si la alarma es de
	 *            la parte de la temporada que cae despues de diciembre se debe
	 *            aplicar correccion.
	 * 
	 * @return segun el contexto podr�a ser 1 para el verano, y 0 para el resto de
	 *         las temporadas
	 * 
	 */
	public int correccionInicio(Calendar fecha_actual) {
		return 0;
	}

	private void establecerRangosFin(int dia, int mes) {

		fecha_limite_fin.set(Calendar.HOUR_OF_DAY, 24);
		fecha_limite_fin.set(Calendar.MINUTE, 59);
		fecha_limite_fin.set(Calendar.SECOND, 59);
		fecha_limite_fin.set(Calendar.MILLISECOND, 999);

		fecha_limite_fin.set(Calendar.DAY_OF_MONTH, dia);
		fecha_limite_fin.set(Calendar.MONTH, mes);
	}

	private void establecerRangosInicio(int dia, int mes) {

		fecha_limite_inicio.set(Calendar.HOUR_OF_DAY, 0);
		fecha_limite_inicio.set(Calendar.MINUTE, 0);
		fecha_limite_inicio.set(Calendar.SECOND, 0);
		fecha_limite_inicio.set(Calendar.MILLISECOND, 0);

		fecha_limite_inicio.set(Calendar.DAY_OF_MONTH, dia);
		fecha_limite_inicio.set(Calendar.MONTH, mes);
	}

	public String getDescripcion() {
		return toString();
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
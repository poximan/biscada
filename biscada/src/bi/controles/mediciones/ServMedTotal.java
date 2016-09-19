/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.mediciones;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import bi.controles.dimensiones.ServDimUnidadTiempoAbstract;
import comunes.modelo.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServMedTotal extends ServMedAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String desripcion = "totalizador";

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public float[] completarFila(long tiempo_ini, List<Alarma> alarmas, ServDimUnidadTiempoAbstract serv_unidad_tiempo)
			throws IndexOutOfBoundsException {

		DateTime tiempo_inicio = new DateTime(tiempo_ini);
		Period periodo = new Period(tiempo_inicio, tiempo_inicio.plusMonths(1));

		Interval interval = new Interval(tiempo_inicio, periodo);

		List<Float> fracciones_tiempo = new ArrayList<Float>();
		fracciones_tiempo.add(new Float(0));

		// analiza de a una todas las alarmas clasificadas dentro del grupo
		for (Alarma alarma_actual : alarmas) {

			DateTime test = new DateTime(alarma_actual.getFecha_inicio().getTimeInMillis());

			while (!interval.contains(test)) {

				interval = new Interval(interval.getEnd(), periodo.withMonths(1));
				fracciones_tiempo.add(new Float(0));
			}
			// agrego 1 al contador de alarmas del mes
			float ultimo_valor = fracciones_tiempo.get(fracciones_tiempo.size() - 1).floatValue();
			fracciones_tiempo.set(fracciones_tiempo.size() - 1, ultimo_valor + 1);
		}
		return envolturaFloatHaciaFloat(fracciones_tiempo.toArray(new Float[fracciones_tiempo.size()]));
	}

	/*
	 * @Override public float[] completarFila(IntervaloFechas intervalo,
	 * List<Alarma> alarmas, ServDimUnidadTiempoAbstract serv_unidad_tiempo)
	 * throws IndexOutOfBoundsException {
	 * 
	 * Calendar fecha_referencia = Calendar.getInstance(), fecha_alarma_actual;
	 * List<Float> fracciones_tiempo = new ArrayList<Float>();
	 * 
	 * try { fecha_referencia.setTimeInMillis(alarmas.get(0).getFecha_inicio().
	 * getTimeInMillis()); } catch (IndexOutOfBoundsException excepcion) { throw
	 * excepcion; }
	 * 
	 * // analiza de a una todas las alarmas clasificadas dentro del grupo for
	 * (Alarma alarma_actual : alarmas) {
	 * 
	 * fecha_alarma_actual = alarma_actual.getFecha_inicio();
	 * 
	 * if (serv_unidad_tiempo.alarmaEsMayorIgualReferencia(fecha_alarma_actual,
	 * fecha_referencia)) {
	 * 
	 * serv_unidad_tiempo.contrarNuevasFraccionesTiempo(fecha_alarma_actual,
	 * fecha_referencia, fracciones_tiempo); } // agrego 1 al contador de
	 * alarmas del mes float ultimo_valor =
	 * fracciones_tiempo.get(fracciones_tiempo.size() - 1).floatValue();
	 * fracciones_tiempo.set(fracciones_tiempo.size() - 1, ultimo_valor + 1); }
	 * return envolturaFloatHaciaFloat(fracciones_tiempo.toArray(new
	 * Float[fracciones_tiempo.size()])); }
	 */
	@Override
	public String toString() {
		return desripcion;
	}
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.mediciones;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import bi.controles.periodos.ServPeriodoAbstract;
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
	public float[] completarFila(List<Alarma> alarmas, ServPeriodoAbstract serv_periodo)
			throws IndexOutOfBoundsException {

		DateTime tiempo_inicio = new DateTime(serv_periodo.getIntervalo().getPrimer_alarma().getTimeInMillis());

		Interval intervalo = new Interval(tiempo_inicio, serv_periodo.getPeriodo());

		List<Float> fracciones_tiempo = new ArrayList<Float>();
		fracciones_tiempo.add(new Float(0));

		// analiza de a una todas las alarmas clasificadas dentro del grupo
		for (Alarma alarma_actual : alarmas) {

			DateTime test = new DateTime(alarma_actual.getFecha_inicio().getTimeInMillis());

			while (!intervalo.contains(test)) {

				intervalo = new Interval(intervalo.getEnd(), serv_periodo.siguientePeriodo());
				fracciones_tiempo.add(new Float(0));
			}
			// agrego 1 al contador de alarmas del mes
			float ultimo_valor = fracciones_tiempo.get(fracciones_tiempo.size() - 1).floatValue();
			fracciones_tiempo.set(fracciones_tiempo.size() - 1, ultimo_valor + 1);
		}
		return envolturaFloatHaciaFloat(fracciones_tiempo.toArray(new Float[fracciones_tiempo.size()]));
	}

	@Override
	public String toString() {
		return desripcion;
	}
}
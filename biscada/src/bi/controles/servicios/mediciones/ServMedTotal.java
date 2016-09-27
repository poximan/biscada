/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.servicios.mediciones;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import bi.controles.servicios.periodos.ServPeriodoAbstract;
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

		int indice = 0;
		float[] fracciones_tiempo = new float[serv_periodo.getCantidadPeriodos()];

		// analiza de a una todas las alarmas clasificadas dentro del grupo
		for (Alarma alarma_actual : alarmas) {

			DateTime test = new DateTime(alarma_actual.getFecha_inicio().getTimeInMillis());

			while (!intervalo.contains(test)) {

				intervalo = new Interval(intervalo.getEnd(), serv_periodo.siguientePeriodo());
				indice++;
			}
			fracciones_tiempo[indice] += 1;
		}
		return fracciones_tiempo;
	}

	@Override
	public String toString() {
		return desripcion;
	}
}
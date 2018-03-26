/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.controles.servicios.mediciones;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import main.java.com.servicoop.app.bi.controles.servicios.periodos.ServPeriodoAbstract;
import main.java.com.servicoop.app.comunes.entidades.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO el ServMedTotal
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO completar las filas con los totales de las alarmas segun el
 * periodo de tiempo sobre el cual se este trabajando
 *
 * LO QUE CONOZCO el set de datos de alarmas y periodos recibidos
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL, ServMedAbstract
 *
 * COMO INTERACTUO CON MI COLABORADOR, devolviendo los totales calculados
 *
 */
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

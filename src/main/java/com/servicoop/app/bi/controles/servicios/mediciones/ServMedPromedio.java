/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.controles.servicios.mediciones;

import java.util.ArrayList;
import java.util.List;

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
 * YO REPRESENTO el servicio de medicion Promedio
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO calcular el promedio del set de datos recibido.
 *
 * LO QUE CONOZCO el set de datos y el periodo recibido mediante el metodo
 * completarFila
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL, ServMedAbstrac
 *
 * COMO INTERACTUO CON MI COLABORADOR, devolviendo el promedio calculado del set
 * de datos recibido.
 *
 */
public class ServMedPromedio extends ServMedAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String desripcion = "promedio";

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	private void calcularPromedio(List<Float> fracciones_tiempo, float[] totales, ServPeriodoAbstract serv_periodo) {

		for (int indice = 0; indice < totales.length; indice++) {
			Float nuevo_promedio = new Float(promedioParaUnidadTiempo(totales[indice], serv_periodo));
			fracciones_tiempo.add(nuevo_promedio);
		}
	}

	@Override
	public float[] completarFila(List<Alarma> alarmas, ServPeriodoAbstract serv_periodo)
			throws IndexOutOfBoundsException {

		List<Float> fracciones_tiempo = new ArrayList<Float>();

		ServMedTotal serv_med_total = new ServMedTotal();
		float totales[] = serv_med_total.completarFila(alarmas, serv_periodo);

		calcularPromedio(fracciones_tiempo, totales, serv_periodo);

		return envolturaFloatHaciaFloat(fracciones_tiempo.toArray(new Float[fracciones_tiempo.size()]));
	}

	private float promedioParaUnidadTiempo(float total_mes, ServPeriodoAbstract serv_periodo) {

		return total_mes / serv_periodo.getDivisor_en_dias();
	}

	@Override
	public String toString() {
		return desripcion;
	}
}

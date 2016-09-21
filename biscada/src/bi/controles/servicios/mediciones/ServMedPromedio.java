/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.servicios.mediciones;

import java.util.ArrayList;
import java.util.List;

import bi.controles.servicios.periodos.ServPeriodoAbstract;
import comunes.modelo.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

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
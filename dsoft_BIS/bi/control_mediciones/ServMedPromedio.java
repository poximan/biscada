/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_mediciones;

import java.util.ArrayList;
import java.util.List;

import modelo.Alarma;
import control_dimensiones.ServDimUnidadTiempoAbstract;
import control_general.ServIntervaloFechas;

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

	private void calcularPromedio(List<Float> fracciones_tiempo, float[] totales,
			ServDimUnidadTiempoAbstract serv_unidad_tiempo) {

		for (int indice = 0; indice < totales.length; indice++) {
			Float nuevo_promedio = new Float(promedioParaUnidadTiempo(totales[indice], serv_unidad_tiempo));
			fracciones_tiempo.add(nuevo_promedio);
		}
	}

	@Override
	public float[] completarFila(List<Alarma> alarmas, ServIntervaloFechas serv_intervalo,
			ServDimUnidadTiempoAbstract serv_unidad_tiempo) throws IndexOutOfBoundsException {

		List<Float> fracciones_tiempo = new ArrayList<Float>();

		ServMedTotal serv_med_total = new ServMedTotal();
		float totales[] = serv_med_total.completarFila(alarmas, serv_intervalo, serv_unidad_tiempo);

		calcularPromedio(fracciones_tiempo, totales, serv_unidad_tiempo);

		return envolturaFloatHaciaFloat(fracciones_tiempo.toArray(new Float[fracciones_tiempo.size()]));
	}

	private float promedioParaUnidadTiempo(float total_mes, ServDimUnidadTiempoAbstract serv_unidad_tiempo) {

		return total_mes / serv_unidad_tiempo.getDivisor_en_dias();
	}

	@Override
	public String toString() {
		return desripcion;
	}
}
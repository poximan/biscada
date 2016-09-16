/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.mediciones;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bi.controles.dimensiones.ServDimUnidadTiempoAbstract;
import bi.controles.dimensiones.ServIntervaloFechas;
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
	public float[] completarFila(List<Alarma> alarmas, ServIntervaloFechas serv_intervalo,
			ServDimUnidadTiempoAbstract serv_unidad_tiempo) throws IndexOutOfBoundsException {

		Calendar fecha_referencia = Calendar.getInstance(), fecha_alarma_actual;
		List<Float> fracciones_tiempo = new ArrayList<Float>();

		try {
			fecha_referencia.setTimeInMillis(alarmas.get(0).getFecha_inicio().getTimeInMillis());
		} catch (IndexOutOfBoundsException excepcion) {
			throw excepcion;
		}

		// analiza de a una todas las alarmas clasificadas dentro del grupo
		for (Alarma alarma_actual : alarmas) {

			fecha_alarma_actual = alarma_actual.getFecha_inicio();

			if (serv_unidad_tiempo.alarmaEsMayorIgualReferencia(fecha_alarma_actual, fecha_referencia)) {

				serv_unidad_tiempo.contrarNuevasFraccionesTiempo(serv_intervalo, fecha_alarma_actual, fecha_referencia,
						fracciones_tiempo);
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
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_dimensiones;

import java.util.Calendar;
import java.util.List;

import modelo.IntervaloFechas;
import control_general.ServIntervaloFechas;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServDimUnidadTiempoTrimestre extends ServDimUnidadTiempoAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String desripcion = "trimestral";
	private int divisor_en_dias = 90;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServDimUnidadTiempoTrimestre(IntervaloFechas intervalo) {
		super(intervalo);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public int agregarHastaProximaUnidadTiempo(Calendar fecha_alarma_actual) {

		int trimestre_original = getNumeroTrimestre(fecha_alarma_actual), nuevos_meses;

		Calendar fecha_incrementable = Calendar.getInstance();
		fecha_incrementable.setTimeInMillis(fecha_alarma_actual.getTimeInMillis());

		for (nuevos_meses = 0; trimestre_original == getNumeroTrimestre(fecha_incrementable); nuevos_meses++)
			fecha_incrementable.add(Calendar.MONTH, 1);

		return nuevos_meses;
	}

	@Override
	public void contrarNuevasFraccionesTiempo(ServIntervaloFechas serv_intervalo, Calendar fecha_alarma_actual,
			Calendar proxima_fraccion, List<Float> fracciones_tiempo) {

		int trimestres_involucrados = unidadTiempoInvolucradas(fecha_alarma_actual, proxima_fraccion);

		while (trimestres_involucrados-- > 0)
			fracciones_tiempo.add(new Float(0));

		proxima_fraccion.setTimeInMillis(fecha_alarma_actual.getTimeInMillis());
		proxima_fraccion.add(Calendar.MONTH, agregarHastaProximaUnidadTiempo(fecha_alarma_actual));
	}

	@Override
	public String[] getEncabezado() {

		int indice = 0;

		if (getIntervalo().getPrimer_alarma() == null || getIntervalo().getUltima_alarma() == null)
			return new String[1];

		String[] encabezado = new String[unidadTiempoInvolucradas(getIntervalo().getPrimer_alarma(), getIntervalo()
				.getUltima_alarma())];

		Calendar fecha_alarma_actual = Calendar.getInstance();
		fecha_alarma_actual.setTimeInMillis(getIntervalo().getPrimer_alarma().getTimeInMillis());

		while (unidadTiempoInvolucradas(fecha_alarma_actual, getIntervalo().getUltima_alarma()) > 0) {

			encabezado[indice++] = getTextoColumnaUnidadTiempo(fecha_alarma_actual) + "'"
					+ String.valueOf(fecha_alarma_actual.get(Calendar.YEAR)).substring(2);

			fecha_alarma_actual.add(Calendar.MONTH, agregarHastaProximaUnidadTiempo(fecha_alarma_actual));
		}
		return encabezado;
	}

	private int getNumeroTrimestre(Calendar caledar_actual) {
		return caledar_actual.get(Calendar.MONTH) / 3 + 1;
	}

	@Override
	public String getTextoColumnaUnidadTiempo(Calendar fecha_alarma_actual) {
		return String.valueOf(getNumeroTrimestre(fecha_alarma_actual)) + " tri";
	}

	@Override
	public int unidadTiempoInvolucradas(Calendar primer_alarma, Calendar ultima_alarma) {

		int dif_anios = ultima_alarma.get(Calendar.YEAR) - primer_alarma.get(Calendar.YEAR);
		return ((dif_anios * 4) + getNumeroTrimestre(ultima_alarma) - getNumeroTrimestre(primer_alarma)) + 1;
	}

	@Override
	public String toString() {
		return desripcion;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	@Override
	public int getDivisor_en_dias() {
		return divisor_en_dias;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}

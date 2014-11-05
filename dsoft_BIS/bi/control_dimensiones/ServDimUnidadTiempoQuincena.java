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

public class ServDimUnidadTiempoQuincena extends ServDimUnidadTiempoAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String desripcion = "quincenal";
	private int divisor_en_dias = 15;

	private ServDimUnidadTiempoMes serv_mes;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServDimUnidadTiempoQuincena(IntervaloFechas intervalo, ServDimUnidadTiempoMes serv_mes) {
		super(intervalo);
		this.serv_mes = serv_mes;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public int agregarHastaProximaUnidadTiempo(Calendar fecha_alarma_actual) {

		Calendar temporario = Calendar.getInstance();
		temporario.setTimeInMillis(fecha_alarma_actual.getTimeInMillis());

		int nuevos_dias = 0;

		while (getNumeroQuincena(temporario) == getNumeroQuincena(fecha_alarma_actual)) {
			nuevos_dias++;
			temporario.add(Calendar.DAY_OF_MONTH, 1);
		}
		return nuevos_dias;
	}

	@Override
	public void contrarNuevasFraccionesTiempo(ServIntervaloFechas serv_intervalo, Calendar fecha_alarma_actual,
			Calendar fecha_referencia, List<Float> fracciones_tiempo) {

		int quincenas_involucrados = unidadTiempoInvolucradas(fecha_alarma_actual, fecha_referencia);

		while (quincenas_involucrados-- > 0)
			fracciones_tiempo.add(new Float(0));

		fecha_referencia.setTimeInMillis(fecha_alarma_actual.getTimeInMillis());
		fecha_referencia.add(Calendar.DAY_OF_MONTH, agregarHastaProximaUnidadTiempo(fecha_alarma_actual));
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

			fecha_alarma_actual.add(Calendar.DAY_OF_MONTH, agregarHastaProximaUnidadTiempo(fecha_alarma_actual));
		}
		return encabezado;
	}

	/**
	 * devuelve el numero de quincena pensada como un entero entre 1-24 para representar todas las quincenas del año
	 * 
	 * @param fecha_actual
	 * @return
	 */
	private int getNumeroQuincena(Calendar fecha_actual) {

		int quincenas_acumuladas_hasta_mes_anterior = fecha_actual.get(Calendar.MONTH) * 2;

		if (fecha_actual.get(Calendar.DAY_OF_MONTH) <= 15)
			quincenas_acumuladas_hasta_mes_anterior += 1;
		else
			quincenas_acumuladas_hasta_mes_anterior += 2;

		return quincenas_acumuladas_hasta_mes_anterior;
	}

	@Override
	public String getTextoColumnaUnidadTiempo(Calendar fecha_alarma_actual) {

		String texto_quincena;

		if (fecha_alarma_actual.get(Calendar.WEEK_OF_MONTH) <= 2)
			texto_quincena = "1 ";
		else
			texto_quincena = "2 ";

		String mes = serv_mes.getTextoColumnaUnidadTiempo(fecha_alarma_actual);

		return new String(texto_quincena + mes);
	}

	@Override
	public String toString() {
		return desripcion;
	}

	@Override
	public int unidadTiempoInvolucradas(Calendar primer_alarma, Calendar ultima_alarma) {

		int dif_anios = ultima_alarma.get(Calendar.YEAR) - primer_alarma.get(Calendar.YEAR);
		return (dif_anios * 24) + getNumeroQuincena(ultima_alarma) - getNumeroQuincena(primer_alarma) + 1;
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
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_general;

import java.util.Calendar;
import java.util.List;

import modelo.Alarma;
import modelo.IntervaloFechas;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */
/**
 * 
 * -------------------- clase en desuso --------------------
 * 
 * antiguamente tenia la responsabilidad de manipular los intervalos de fechas dados por una consulta. por ejemplo si se
 * traian todas las alarmas de ene'14, esta clase otorgaba servicios adicionales para minupular ese intervalo, como por
 * ejemplo la alarma mas antigua y las mas reciente, o cuantas alarmas habia en ese período.
 * 
 * es el servicio de manejo de intervalos de fechas. en particular interesa en intervalo mayor, que es el que incluye a
 * la alarma mas antigua y a la mas reciente
 * 
 * @author hugo
 * 
 */
public class ServIntervaloFechas {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private IntervaloFechas intervalo;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServIntervaloFechas(IntervaloFechas intervalo) {
		this.intervalo = intervalo;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * ¿la nueva alarma es anterior a la mas vieja registrada?
	 * 
	 * @param alarma_actual
	 */
	private void actualizarPrimerAlarma(Calendar alarma_actual) {

		if (alarma_actual.before(intervalo.getPrimer_alarma()))
			intervalo.setPrimer_alarma(alarma_actual);
		else
			if (intervalo.getPrimer_alarma() == null) {
				intervalo.setPrimer_alarma(Calendar.getInstance());
				actualizarPrimerAlarma(alarma_actual);
			}
	}

	/**
	 * ¿la nueva alarma es posterior a la mas nueva registrada?
	 * 
	 * @param alarma_actual
	 */
	private void actualizarUltimaAlarma(Calendar alarma_actual) {

		if (alarma_actual.after(intervalo.getUltima_alarma()))
			intervalo.setUltima_alarma(alarma_actual);
		else
			if (intervalo.getUltima_alarma() == null) {
				Calendar nuevo_calendario = Calendar.getInstance();
				nuevo_calendario.setTimeInMillis(0);
				intervalo.setUltima_alarma(nuevo_calendario);
				actualizarUltimaAlarma(alarma_actual);
			}
	}

	public void encontrarMinimoMaximo(List<Alarma> alarmas) {

		Calendar fecha_alarma_actual;

		for (Alarma alarma_actual : alarmas) {

			fecha_alarma_actual = alarma_actual.getFecha_inicio();
			actualizarPrimerAlarma(fecha_alarma_actual);
			actualizarUltimaAlarma(fecha_alarma_actual);
		}
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public IntervaloFechas getIntervalo() {
		return intervalo;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.dimensiones;

import java.util.Calendar;
import java.util.List;

import bi.modelo.IntervaloFechas;
import comunes.modelo.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */
/**
 * es el servicio de manejo de intervalos de fechas. en particular interesa el
 * intervalo mayor, que es el que incluye a la alarma mas antigua y a la mas
 * reciente
 * 
 * @author hugo
 * 
 */
public class ServIntervaloFechas {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServIntervaloFechas() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * 
	 * la nueva alarma es anterior a la mas vieja registrada?
	 * 
	 * @param alarma_actual
	 */
	private void actualizarPrimerAlarma(IntervaloFechas intervalo, Calendar alarma_actual) {

		if (alarma_actual.before(intervalo.getPrimer_alarma()))
			intervalo.setPrimer_alarma(alarma_actual);
		else if (intervalo.getPrimer_alarma() == null) {
			intervalo.setPrimer_alarma(Calendar.getInstance());
			actualizarPrimerAlarma(intervalo, alarma_actual);
		}
	}

	/**
	 * �la nueva alarma es posterior a la mas nueva registrada?
	 * 
	 * @param alarma_actual
	 */
	private void actualizarUltimaAlarma(IntervaloFechas intervalo, Calendar alarma_actual) {

		if (alarma_actual.after(intervalo.getUltima_alarma()))
			intervalo.setUltima_alarma(alarma_actual);
		else if (intervalo.getUltima_alarma() == null) {
			Calendar nuevo_calendario = Calendar.getInstance();
			nuevo_calendario.setTimeInMillis(0);
			intervalo.setUltima_alarma(nuevo_calendario);
			actualizarUltimaAlarma(intervalo, alarma_actual);
		}
	}

	public void encontrarMinimoMaximo(IntervaloFechas intervalo, List<Alarma> alarmas) {

		Calendar fecha_alarma_actual;

		for (Alarma alarma_actual : alarmas) {

			fecha_alarma_actual = alarma_actual.getFecha_inicio();
			actualizarPrimerAlarma(intervalo, fecha_alarma_actual);
			actualizarUltimaAlarma(intervalo, fecha_alarma_actual);
		}
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package modelo;

import java.util.Calendar;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public abstract class TiempoDespeje {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private int inicio;
	private int fin;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public boolean contiene(Alarma alarma_actual) {

		try {
			int tiempo_transcurrido = diferenciaEntreFechas(alarma_actual);

			if (tiempo_transcurrido >= inicio && tiempo_transcurrido < fin)
				return true;
		}
		catch (NullPointerException excepcion) {
			return false;
		}
		return false;
	}

	public int diferenciaEntreFechas(Alarma alarma_actual) throws NullPointerException {

		int hora_inicial = 210;
		long inicio = 0, fin = 0;

		try {
			inicio = alarma_actual.getFecha_inicio().getTimeInMillis();
			fin = alarma_actual.getFecha_finalizacion().getTimeInMillis();
		}
		catch (NullPointerException excepcion) {
			throw excepcion;
		}

		Calendar diferencia = Calendar.getInstance();
		diferencia.setTimeInMillis(fin - inicio);

		return 10 * diferencia.get(Calendar.HOUR_OF_DAY) + diferencia.get(Calendar.MINUTE) - hora_inicial;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public int getFin() {
		return fin;
	}

	public int getInicio() {
		return inicio;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	public void setFin(int fin) {
		this.fin = fin;
	}

	public void setInicio(int inicio) {
		this.inicio = inicio;
	}
}
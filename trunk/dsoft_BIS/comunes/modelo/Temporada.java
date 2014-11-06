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

public abstract class Temporada {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private Calendar desde;
	private Calendar hasta;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public boolean contiene(Alarma alarma_actual) {
		return (desde.before(alarma_actual) && hasta.after(alarma_actual));
	}

	public int diferenciaEntreFechas(Alarma alarma_actual) throws NullPointerException {
		return 10;
	}

	public void definirDesde(int dia_inicio, int mes_inicio) {
		desde = Calendar.getInstance();
		definirFecha(desde, dia_inicio, mes_inicio);
	}

	public void definirHasta(int dia_fin, int mes_fin) {
		hasta = Calendar.getInstance();
		definirFecha(hasta, dia_fin, mes_fin);
	}

	private void definirFecha(Calendar fecha, int dia, int mes) {

		fecha.setTimeInMillis(0);

		fecha.set(Calendar.DAY_OF_MONTH, dia);
		fecha.set(Calendar.MONTH, mes);
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
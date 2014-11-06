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

	public Temporada(int dia_inicio, int mes_inicio, int dia_fin, int mes_fin) {

		definirFecha(desde, dia_inicio, mes_inicio);
		definirFecha(hasta, dia_fin, mes_fin);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public Temporada() {
	}

	public boolean contiene(Alarma alarma_actual) {

		desde.set(Calendar.YEAR, alarma_actual.getFecha_inicio().get(Calendar.YEAR));
		hasta.set(Calendar.YEAR, alarma_actual.getFecha_inicio().get(Calendar.YEAR));

		return (desde.before(alarma_actual.getFecha_inicio()) && hasta.after(alarma_actual.getFecha_inicio()));
	}

	private void definirFecha(Calendar fecha, int dia, int mes) {

		fecha = Calendar.getInstance();
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
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

	private Calendar fecha_inicio;
	private Calendar fecha_fin;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public Temporada(int dia_inicio, int mes_inicio, int dia_fin, int mes_fin) {

		fecha_inicio = Calendar.getInstance();
		establecerRangos(fecha_inicio, dia_inicio, mes_inicio);

		fecha_fin = Calendar.getInstance();
		establecerRangos(fecha_fin, dia_fin + 1, mes_fin);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * factor de correccion. todos los rangos de fechas se miden igual salvo el de temporada verano. alli, por como esta
	 * implementada la logica, la comparacion de fecha inicio se hace por una fecha cuyo año está desplazado una unidad,
	 * por lo tanto es necesario desplazar esta diferencia solo en ese unico caso.
	 * 
	 * @return 1 para el verano, y 0 para el resto de las temporadas
	 */
	public int correccion() {
		return 0;
	}

	private void establecerRangos(Calendar fecha, int dia, int mes) {

		fecha.set(Calendar.DAY_OF_MONTH, dia);
		fecha.set(Calendar.MONTH, mes);
	}

	public boolean contiene(Alarma alarma_actual) {

		Calendar fecha_actual = alarma_actual.getFecha_inicio();

		fecha_inicio.set(Calendar.YEAR, fecha_actual.get(Calendar.YEAR) - correccion());
		fecha_fin.set(Calendar.YEAR, fecha_actual.get(Calendar.YEAR));

		return (fecha_inicio.before(fecha_actual) && fecha_fin.after(fecha_actual));
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public Calendar getFecha_inicio() {
		return fecha_inicio;
	}

	public Calendar getFecha_fin() {
		return fecha_fin;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.servicios;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class Reloj {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private long tiempo_inicio;
	private long tiempo_fin;

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void comenzarContar() {
		tiempo_inicio = System.currentTimeMillis();
	}

	public void terminarContar() {
		tiempo_fin = System.currentTimeMillis();
	}

	public long getTiempoEnSegundos() {
		return (tiempo_fin - tiempo_inicio) / 1000;
	}
}

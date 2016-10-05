/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.servicios;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO,
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO,
 * 
 * LO QUE CONOZCO,
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL,
 * 
 * COMO INTERACTUO CON MI COLABORADOR,
 *
 * @author hdonato
 * 
 */
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

	public long getTiempoEnSegundos() {
		return (tiempo_fin - tiempo_inicio) / 1000;
	}

	public long getTiempoEnMilisegundos() {
		return tiempo_fin - tiempo_inicio;
	}

	public void terminarContar() {
		tiempo_fin = System.currentTimeMillis();
	}
}

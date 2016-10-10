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
 * YO REPRESENTO, un servicio de manejo de espacios de tiempo
 * 
 * de utilidad para calcular cuanto tiempo demoró un procesamiento. es
 * informacion que se saca por terminal o al log.txt
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, calculo el tiempo transcurrido entre una fecha inicio y una fin.
 * 
 * LO QUE CONOZCO, las fecha inicio y fin.
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

	public long getTiempoEnMilisegundos() {
		return tiempo_fin - tiempo_inicio;
	}

	public long getTiempoEnSegundos() {
		return (tiempo_fin - tiempo_inicio) / 1000;
	}

	public void terminarContar() {
		tiempo_fin = System.currentTimeMillis();
	}
}

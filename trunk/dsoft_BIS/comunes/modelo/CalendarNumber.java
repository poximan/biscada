/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package modelo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Tipo particular a la necesidad de resolver el problema del tiempo transcurrido entre la generacion y la finalizacion
 * de la alarma.
 * 
 * Esto se resuelve mediante comparacion de clases Predicado, y como solo aceptan clases Number se creó esta envoltura
 * para manipular la informacion de interes, pero oculta en un tipo compatible con los predicados utilizados en querys
 * dinamicas.
 * 
 * @author hugo
 *
 */
public class CalendarNumber extends Number {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private long diferencia_fechas;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public CalendarNumber(long diferencia_fechas) {
		this.diferencia_fechas = diferencia_fechas;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public double doubleValue() {
		return 0;
	}

	@Override
	public float floatValue() {
		return 0;
	}

	@Override
	public int intValue() {
		return 0;
	}

	@Override
	public long longValue() {
		return diferencia_fechas;
	}
}
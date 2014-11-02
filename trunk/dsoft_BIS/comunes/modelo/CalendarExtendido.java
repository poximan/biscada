/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package modelo;

import java.util.Calendar;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class CalendarExtendido extends Number {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private Calendar fecha;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public CalendarExtendido(Root<Alarma> root_alarmas, String atributo) {

		Expression<Calendar> obj = root_alarmas.get(atributo);
		this.fecha = (Calendar) root_alarmas.get(atributo);
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
		return fecha.getTimeInMillis();
	}
}
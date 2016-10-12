/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.servicios.periodos;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;

import bi.entidades.IntervaloFechas;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO una clase que extiende de ServPeriodoAbstract
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO devolver los calculos del periodo en quincenas
 * 
 * LO QUE CONOZCO
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, ServPeriodoAbst
 * 
 * COMO INTERACTUO CON MI COLABORADOR,
 *
 */
public class ServPeriodoQuincena extends ServPeriodoAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String desripcion = "quincenal";
	private int divisor_en_dias = 15;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServPeriodoQuincena(IntervaloFechas intervalo) {
		super(intervalo);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	protected DateTime getCampo_siguiente(DateTime campo_anterior) {
		return campo_anterior.plusDays(15);
	}

	@Override
	public int getDivisor_en_dias() {
		return divisor_en_dias;
	}

	@Override
	public Period incrementarPeriodo() {
		setPeriodo(getPeriodo().withDays(15));
		return getPeriodo();
	}

	@Override
	public Period nuevoPeriodo(DateTime tiempo_inicio) {
		return new Period(tiempo_inicio, tiempo_inicio.plusDays(15));
	}

	@Override
	protected Date toDateCampo_actual(DateTime campo_actual) {
		return campo_actual.toDate();
	}

	@Override
	public String toString() {
		return desripcion;
	}

	@Override
	protected String toStringCampo_actual(DateTime campo_actual) {

		String texto;

		if (campo_actual.getDayOfMonth() <= 15)
			texto = "1º ";
		else
			texto = "2º ";

		String mes = campo_actual.monthOfYear().getAsText().substring(0, 3);
		String anio = campo_actual.year().getAsText().substring(2, 4);

		return new String(texto + mes + " '" + anio);
	}
}
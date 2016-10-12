/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.entidades;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO una clase que registra la primer y ultima alarma en un
 * intervaloFecha
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO registrar la primer y ultima alarma
 * 
 * LO QUE CONOZCO
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, ServPeriodoAbs
 * 
 * COMO INTERACTUO CON MI COLABORADOR, entregando la primer o ultima alarma
 * segun la necesidad de la clase ServPeriodoAbs
 * 
 */
public class IntervaloFechas {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private Calendar primer_alarma;
	private Calendar ultima_alarma;

	private String fecha_corta = "yyyy-MM-dd";
	private String fecha_larga = "yyyy-MM-dd HH:mm:ss ms";

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public IntervaloFechas() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public String getFechaCorta(Calendar fecha_alarma) {

		SimpleDateFormat format1 = new SimpleDateFormat(fecha_corta);
		return format1.format(fecha_alarma.getTime());
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public String getFechaLarga(Calendar fecha_alarma) {

		SimpleDateFormat format1 = new SimpleDateFormat(fecha_larga);
		return format1.format(fecha_alarma.getTime());
	}

	public Calendar getPrimer_alarma() {
		return primer_alarma;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	public Calendar getUltima_alarma() {
		return ultima_alarma;
	}

	public void setPrimer_alarma(Calendar primer_alarma) {
		this.primer_alarma = primer_alarma;
	}

	public void setUltima_alarma(Calendar ultima_alarma) {
		this.ultima_alarma = ultima_alarma;
	}

	@Override
	public String toString() {
		return primer_alarma.getTime().toString() + ", " + ultima_alarma.getTime().toString();
	}
}

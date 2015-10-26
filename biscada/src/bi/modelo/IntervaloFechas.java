/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.modelo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * registra primer y ultima alarma en un intervalod de fecha. se puede pensar como una entidad, ya que si bien no se
 * persiste, tampoco posee ningun tipo de logica asociada, es decir no presta servicios de ningun tipo
 * 
 * @author hugo
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

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public String getFechaCorta(Calendar fecha_alarma) {

		SimpleDateFormat format1 = new SimpleDateFormat(fecha_corta);
		return format1.format(fecha_alarma.getTime());
	}

	public String getFechaLarga(Calendar fecha_alarma) {

		SimpleDateFormat format1 = new SimpleDateFormat(fecha_larga);
		return format1.format(fecha_alarma.getTime());
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

	public Calendar getPrimer_alarma() {
		return primer_alarma;
	}

	public Calendar getUltima_alarma() {
		return ultima_alarma;
	}

	public void setPrimer_alarma(Calendar primer_alarma) {
		this.primer_alarma = primer_alarma;
	}

	public void setUltima_alarma(Calendar ultima_alarma) {
		this.ultima_alarma = ultima_alarma;
	}
}

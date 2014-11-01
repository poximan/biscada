/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_dimensiones;

import java.util.Calendar;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

/**
 * de forma dinamica se cuentan las alarmas, a razon de todas las que entran en cada salto de la unidad de tiempo. Por
 * ejemplo, si la unidad de tiempo es quincena, y el intervalo de fecha [min-max] obtenido luego de la consulta es de un
 * mes, entonces habrá dos unidades de la unidad de tiempo dada.
 * 
 * @author hugo
 * 
 */
public interface FraccionTiempoCalculable {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public int agregarHastaProximaUnidadTiempo(Calendar fecha_alarma_actual);

	public String getTextoColumnaUnidadTiempo(Calendar fecha_alarma_actual);

	public int unidadTiempoInvolucradas(Calendar primer_alarma, Calendar ultima_alarma);
}
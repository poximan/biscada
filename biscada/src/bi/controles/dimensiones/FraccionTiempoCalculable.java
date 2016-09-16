/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.dimensiones;

import java.util.Calendar;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

/**
 * de forma dinamica se cuentan las alarmas, a razon de todas las que entran en
 * cada salto de la unidad de tiempo. Por ejemplo, si la unidad de tiempo es
 * quincena, y el intervalo de fecha [min-max] obtenido luego de la consulta es
 * de un mes, entonces habr� dos unidades de la unidad de tiempo dada.
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

	/**
	 * cuenta la cantidad de unidades de tiempo existentes entre dos fechas
	 * dadas. por ejemplo dos fechas que se separan por un mes entre si,
	 * contadas de a mes daran como resultado 1, pero el mismo set de datos
	 * comparado contra una unidad de tiempo quincena, resultara en 4 unidades.
	 * por este motivo la responsabilidad de determinar las unidades de tiempo
	 * involucradas es establecida por la clase concreta
	 * 
	 * @param primer_alarma
	 * @param ultima_alarma
	 * @return
	 */
	public int unidadTiempoInvolucradas(Calendar primer_alarma, Calendar ultima_alarma);
}
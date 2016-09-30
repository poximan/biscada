/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.servicios.dimensiones;

import java.util.List;

import bi.controles.servicios.mediciones.ServMedAbstract;
import bi.controles.servicios.periodos.ServPeriodoAbstract;
import comunes.modelo.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * solo provee servicios, de ser necesario reutilizar no es necesario clonar.
 * 
 * @author hdonato
 * @param <Tipo>
 *
 */
public abstract class ServDimAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public float[][] postProcesarTabla(float[][] valor_retorno, boolean contar_periodos_nulos) {
		return valor_retorno;
	}

	/**
	 * 
	 * @param serv_medicion
	 *            medicion que se realizar� sobre el set de datos desde el
	 *            punto de vista de una dimension determinada.
	 * @param serv_periodo
	 *            unidad de tiempo en que fraccionar� el set de datos. por
	 *            ejemplo un set que incluye todo el a�o 2012 puede
	 *            presentarse en 12 unidades de tiempo mes, en 24 unidades de
	 *            tiempo quincena, etc.
	 * @param b
	 * @return el tipo de datos el dos dimensiones que interpreta el Model de la
	 *         tabla.
	 */
	public abstract float[][] completarTabla(ServMedAbstract serv_medicion, ServPeriodoAbstract serv_periodo,
			boolean contar_periodo_nulo);

	/**
	 * pide los nombres de los grupos que se obtienen de observar una lista
	 * desde una dimension especifica.
	 * 
	 * @return con estos nombres se llenara una tabla de simple columna que
	 *         simula la seguda entrada de la que posee los datos.
	 */
	public abstract Object[] getGrupos();

	/**
	 * crea un mapa hash donde la clave es una dimension de las especificadas en
	 * el segundo nivel de evaluacion (ver documento de vision)
	 * 
	 * @param consultas
	 */
	public abstract void realizarHash(List<Alarma> consultas);
}
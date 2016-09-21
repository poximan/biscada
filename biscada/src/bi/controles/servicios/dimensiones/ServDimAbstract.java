/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.dimensiones;

import java.util.ArrayList;
import java.util.List;

import bi.controles.mediciones.ServMedAbstract;
import bi.controles.periodos.ServPeriodoAbstract;
import comunes.modelo.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * solo provee servicios, de ser necesario reutilizar no es necesario clonar.
 * 
 * @author hdonato
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
	 * @return el tipo de datos el dos dimensiones que interpreta el Model de la
	 *         tabla.
	 */
	public abstract float[][] completarTabla(ServMedAbstract serv_medicion, ServPeriodoAbstract serv_periodo);

	private void extraerCelda(float[][] valor_retorno, int columna_nula, List<Float> lista, int fila_actual) {

		for (int columna_actual = 0; columna_actual < valor_retorno[fila_actual].length; columna_actual++)
			if (columna_actual != columna_nula)
				lista.add(valor_retorno[fila_actual][columna_actual]);
	}

	/**
	 * una vez elegida la columna a eliminar de la matriz fila-columna, este
	 * metodo resuelve el desplazamiento de las columnas siguiente
	 * 
	 * @param valor_retorno
	 *            arreglo de 2 dimensiones leido como fila-columna
	 * @param columna_nula
	 * @param serv_medicion
	 */
	private void extraerColumna(float[][] valor_retorno, int columna_nula, ServMedAbstract serv_medicion) {

		List<Float> lista = new ArrayList<Float>();

		for (int fila_actual = 0; fila_actual < valor_retorno.length; fila_actual++) {

			extraerCelda(valor_retorno, columna_nula, lista, fila_actual);
			valor_retorno[fila_actual] = serv_medicion.envolturaFloatHaciaFloat(lista.toArray(new Float[lista.size()]));
		}
	}

	/**
	 * el usuario podr� decidir si las columnas sin valores deben tenerse en
	 * cuenta para los calculos estadisticos. supongamos tres columnas de las
	 * cuales una de ellas esta vacia, al promediarlas por unidad de tiempo el
	 * denominador ser� 3; sin embargo si se utiliza filtro para columnas
	 * nulas el denominador bajar� a 2.
	 * 
	 * @param valor_retorno
	 * @param serv_medicion
	 * @return
	 */
	public float[][] filtrarColumnasNulas(float[][] valor_retorno, ServMedAbstract serv_medicion) {

		int fila = 0;

		for (int columna = 0; columna < valor_retorno[fila].length; columna++, fila = 0) {

			// mientras resten filas para la columna que se esta analizando
			while (fila < valor_retorno.length) {

				if (valor_retorno[fila][columna] != 0) // si true -> se descarta
														// columna
					break;
				fila++;
			}
			if (fila == valor_retorno.length)
				extraerColumna(valor_retorno, columna, serv_medicion);
		}
		return valor_retorno;
	}

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
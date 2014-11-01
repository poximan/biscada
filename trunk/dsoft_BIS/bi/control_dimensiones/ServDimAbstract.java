/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_dimensiones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import modelo.Alarma;
import control_general.ServIntervaloFechas;
import control_mediciones.ServMedAbstract;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public abstract class ServDimAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private boolean check_calculo_anterior;
	private boolean primera_vez;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServDimAbstract() {
		primera_vez = true;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public boolean esNecesarioReconstruirHash(boolean incluir_alarmas_incompletas) {

		if (primera_vez || incluir_alarmas_incompletas != check_calculo_anterior)
			return true;

		return false;
	}

	public void invertirCheckCalculoAnterior(boolean incluir_alarmas_incompletas) {

		if (check_calculo_anterior)
			check_calculo_anterior = false;
		else
			check_calculo_anterior = true;

		check_calculo_anterior = incluir_alarmas_incompletas;
		primera_vez = false;
	}

	/**
	 * crea un mapa hash donde la clave es una dimension de las especificadas en el segundo nivel de evaluacion (ver
	 * documento de vision)
	 * 
	 * @param consultas
	 * @param incluir_alarmas_incompletas
	 */
	public abstract void realizarHash(List<Alarma> consultas, boolean incluir_alarmas_incompletas);

	/**
	 * una vez que entra a una de las claves del hash, analiza todas las alarmas que coinciden con la clave completa la
	 * fila correspondiente en la tabla
	 * 
	 * @param indice
	 * @param longitud_arreglo
	 * @param lista_alarmas_una_clave
	 * @param serv_intervalo
	 * @param serv_medicion
	 * @param serv_unidad_tiempo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public float[] procesamientoComunFila(int indice, List<Alarma> lista_alarmas_una_clave,
			ServIntervaloFechas serv_intervalo, ServMedAbstract serv_medicion,
			ServDimUnidadTiempoAbstract serv_unidad_tiempo) {

		Collections.sort(lista_alarmas_una_clave);
		serv_intervalo.encontrarMinimoMaximo(lista_alarmas_una_clave);

		return serv_medicion.completarFila(lista_alarmas_una_clave, serv_intervalo, serv_unidad_tiempo);
	}

	/**
	 * con toda la informacion recopilada en cuanto al contexto de lo que solicita el usuario, se completa una tabla de
	 * doble entrada que luego se presentara en la vista
	 * 
	 * @param serv_intervalo
	 *            intervalo de fechas [mas antigua-mas reciente] del set de resultados devuelto por la consulta a la BD
	 * @param serv_medicion
	 *            medicion que se realizará sobre el set de datos desde el punto de vista de una dimension determinada.
	 * @param serv_unidad_tiempo
	 *            unidad de tiempo en que fraccionará el set de datos. por ejemplo un set que incluye todo el año 2012
	 *            puede presentarse en 12 unidades de tiempo mes, en 24 unidades de tiempo quincena, etc.
	 * @param incluir_columnas_nulas
	 *            al terminar de completar la tabla podría suceder que existan columnas con valor 0 a lo largo de todas
	 *            sus filas. el usuario podrá elegir si visualizar estas columnas o no
	 * @return el tipo de datos el dos dimensiones que interpreta el Model de la tabla.
	 */
	public abstract float[][] completarTabla(ServIntervaloFechas serv_intervalo, ServMedAbstract serv_medicion,
			ServDimUnidadTiempoAbstract serv_unidad_tiempo, boolean incluir_columnas_nulas);

	/**
	 * pide los nombres de los grupos que se obtienen de observar una lista desde una dimension especifica.
	 * 
	 * @return con estos nombres se llenara una tabla de simple columna que simula la seguda entrada de la que posee los
	 *         datos.
	 */
	public abstract Object[] getGrupos();

	/**
	 * el usuario podrá decidir si las columnas sin valores deben tenerse en cuenta para los calculos estadisticos.
	 * supongamos tres columnas de las cuales una de ellas esta vacia, al promediarlas por unidad de tiempo el
	 * denominador será 3; sin embargo si se utiliza filtro para columnas nulas el denominador bajará a 2.
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

				if (valor_retorno[fila][columna] != 0) // si true -> se descarta columna
					break;
				fila++;
			}
			if (fila == valor_retorno.length)
				extraerColumna(valor_retorno, columna, serv_medicion);
		}

		return valor_retorno;
	}

	/**
	 * una vez elegida la columna a eliminar de la matriz fila-columna, este metodo resuelve el desplazamiento de las
	 * columnas siguiente
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

	private void extraerCelda(float[][] valor_retorno, int columna_nula, List<Float> lista, int fila_actual) {

		for (int columna_actual = 0; columna_actual < valor_retorno[fila_actual].length; columna_actual++)
			if (columna_actual != columna_nula)
				lista.add(valor_retorno[fila_actual][columna_actual]);
	}
}
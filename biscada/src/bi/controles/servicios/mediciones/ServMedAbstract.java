/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.servicios.mediciones;

import java.util.List;

import bi.controles.servicios.periodos.ServPeriodoAbstract;
import comunes.modelo.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public abstract class ServMedAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServMedAbstract() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * por cada llamado completa una fila de la matriz de dos dimensiones que
	 * luego se usara como parametro para crear el modelo de la tabla
	 * @param serv_intervalo
	 *            es el servicio de manejo de intervalos de fechas. en
	 *            particular interesa en intervalo mayor, que es el que incluye
	 *            a la alarma mas antigua y a la mas reciente
	 * @return
	 * @throws IndexOutOfBoundsException
	 * 
	 */
	public abstract float[] completarFila(List<Alarma> alarmas, ServPeriodoAbstract serv_periodo)
			throws IndexOutOfBoundsException;

	/**
	 * para ciertos tratamientos de serie de valores, ha sido mas comodo
	 * utilizar listas, pero como el Model de una tabla maneja arreglos de dos
	 * dimensiones, es necesario el uso de metodos de acople como este. de esta
	 * forma pasamos de arreglos a listas y retornamos, para obtener el mayor
	 * beneficio de ambas estructuras
	 * 
	 * @param arreglo_cantidades_para_cada_unidad_tiempo
	 * @return
	 */
	public float[] envolturaFloatHaciaFloat(Float[] arreglo_cantidades_para_cada_unidad_tiempo) {

		float[] retorno = new float[arreglo_cantidades_para_cada_unidad_tiempo.length];

		for (int i = 0; i < arreglo_cantidades_para_cada_unidad_tiempo.length; i++)
			retorno[i] = arreglo_cantidades_para_cada_unidad_tiempo[i].floatValue();

		return retorno;
	}
}
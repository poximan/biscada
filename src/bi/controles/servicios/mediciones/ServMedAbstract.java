/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.servicios.mediciones;

import java.util.List;

import bi.controles.servicios.periodos.ServPeriodoAbstract;
import comunes.entidades.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO una clase Abstracta de Servicio de Mediciones
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO pasar los datos que se encuentran en un arreglo a listas e
 * interactuar con las clases para calcular el tipo de medicion correspondiente
 * (totales o promedios).
 * 
 * LO QUE CONOZCO el set de datos recibido
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, ServDimAbstract
 * 
 * COMO INTERACTUO CON MI COLABORADOR, mediante cualquiera de las clases que
 * extiendan de ServDimAbstract
 *
 */
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
	 * 
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
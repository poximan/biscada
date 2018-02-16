/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.servicios.dimensiones;

import java.util.List;

import bi.controles.servicios.mediciones.ServMedAbstract;
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
 * YO REPRESENTO, la superclase de todos los servicios que son necesarios para
 * operar en una dimension
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, establezco la forma estandar que deben tener los metodos
 * 
 * LO QUE CONOZCO,
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL,
 * 
 * COMO INTERACTUO CON MI COLABORADOR,
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
	 *            medicion que se realizara sobre el set de datos desde el punto de
	 *            vista de una dimension determinada.
	 * 
	 * @param serv_periodo
	 *            periodo en que se fraccionara el set de datos. por ejemplo un set
	 *            que incluye todo el anio 2012 puede presentarse en 12 periodos
	 *            mensuales, en 24 quincenas, 1 anio, etc.
	 * 
	 * @return el tipo de datos el dos dimensiones que interpreta el Model de la
	 *         tabla.
	 */
	public abstract float[][] completarTabla(ServMedAbstract serv_medicion, ServPeriodoAbstract serv_periodo,
			boolean contar_periodo_nulo);

	/**
	 * pide los nombres de los grupos que se obtienen de observar una lista desde
	 * una dimension especifica.
	 * 
	 * @return con estos nombres se llenara una tabla de simple columna que simula
	 *         la seguda entrada de la que posee los datos.
	 */
	public abstract Object[] getGrupos();

	/**
	 * sin utilidad por el momento. es la interfaz para tomar una tabla y alterar su
	 * tama�o segun el parametro bool
	 * 
	 * @param valor_retorno
	 * @param contar_periodos_nulos
	 * 
	 * @return la tabla modificada
	 */
	public float[][] postProcesarTabla(float[][] valor_retorno, boolean contar_periodos_nulos) {
		return valor_retorno;
	}

	/**
	 * crea un mapa hash donde la clave es una dimension de las especificadas en el
	 * segundo nivel de evaluacion (ver documento de vision), y los valores siempre
	 * son alarmas
	 * 
	 * @param consultas
	 */
	public abstract void realizarHash(List<Alarma> consultas);
}
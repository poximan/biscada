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
 * YO REPRESENTO la clase de Servicios de Dimension
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO Proveer servicios de medicion y periodo.
 * 
 * LO QUE CONOZCO 
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, cualquier de las clases de ServDim que extiendan de esta clase.
 * 
 * COMO INTERACTUO CON MI COLABORADOR, mediante la utilizacion de cualquier clase que extienda
 * de esta, para pasar al tercer nivel de evaluacion, definiendo el tipo de medicion y el 
 * periodo de tiempo con que se quiere ver los resultados
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
	 *            
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

	public float[][] postProcesarTabla(float[][] valor_retorno, boolean contar_periodos_nulos) {
		return valor_retorno;
	}

	/**
	 * crea un mapa hash donde la clave es una dimension de las especificadas en
	 * el segundo nivel de evaluacion (ver documento de vision)
	 * 
	 * @param consultas
	 */
	public abstract void realizarHash(List<Alarma> consultas);
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.partes_alarma.equipos;

import comunes.modelo.TipoDeEquipo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO,
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO,
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
public class CentroControlMotores extends TipoDeEquipo {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String expresion_regular = "FALTA (DE )?(FASE|RED)"//
			+ "|FALTA  ?220V"//
			+ "|RFF"//
			+ "|BARRA SIN TENSION"//
			+ "|FALLA DE AC"//
			+ "|PARADA\\s(DE\\s)?EMERGENCIA"//
			+ "|PARADA EMG EN"//
			+ "|PULSADOR DE EMERGENCIA"//
			+ "|SELECTORA ESTADO"//
			+ "|SELECTORA TAB.-RTU"//
			+ "|INTERRUPTOR (PPAL|GENERAL)"//
			+ "|Selecci.n CONTROL AUTOM.TICO"//
			+ "|REPOSICION DE ALARMAS"//
			+ "|RECTIFICADOR"//
			+ "|Selecci.n CONTROL MANUAL"//
			+ "|FALLA ALIMENTACI.N DE RED"//
			+ "|SGCA"//
			+ "|Alarma CA"//
			+ "|Falla cargador"//
			+ "|ACEPTACION DE ALARMAS TPyM";

	private static String descripcion = "CCM del sitio";

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public static String getExpresion_regular() {
		return expresion_regular;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public CentroControlMotores() {
		super(descripcion);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	@Override
	public Integer getNumero(String discriminante) {
		return new Integer(1);
	}

	@Override
	public String toString() {
		return descripcion;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

}
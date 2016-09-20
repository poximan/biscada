/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.equipos;

import comunes.modelo.TipoDeEquipo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class CentroControlMotores extends TipoDeEquipo {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String expresion_regular = "FALTA (DE )?(FASE|RED)"//
			+ "|FALTA  ?220V"//
			+ "|RFF"//
			+ "|PARADA\\s(DE\\s)?EMERGENCIA"//
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
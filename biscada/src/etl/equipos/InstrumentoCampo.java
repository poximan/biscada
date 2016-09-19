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

public class InstrumentoCampo extends TipoDeEquipo {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String expresion_regular = "MEDIDA DE CAUDAL"//
			+ "|ALARMA NIVEL"//
			+ "|\\sPERA\\s"//
			+ "|ALARMA DE ALTO NIVEL"//
			+ "|SENSOR DE NIVEL"//
			+ "|ALTO NIVEL SALA BOMBAS"//
			+ "|ALARMA MAXIMO NIVEL"//
			+ "|BAJO NIVEL"//
			+ "|ALTO NIVEL";

	private static String descripcion = "instrumento de campo";

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

	public InstrumentoCampo() {
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
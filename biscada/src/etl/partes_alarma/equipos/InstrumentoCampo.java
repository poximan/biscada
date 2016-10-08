/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.partes_alarma.equipos;

import comunes.entidades.TipoDeEquipo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, la implementacion concreta de la super clase
 * comunes.modelo.TipoDeEquipo
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, expongo una instancia de mi si la fabrica concreta
 * comunes.fabrica.TipoDeEquipoFactory, concluye que mi expresion regular
 * estatica (antes de la instancia) es un buen definidor del discriminante que
 * está leyendo.
 * 
 * LO QUE CONOZCO, la expresion regular que me define, y mi descripcion para
 * mostrar en componentes visuales
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
public class InstrumentoCampo extends TipoDeEquipo {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String expresion_regular = "MEDIDA DE CAUDAL"//
			+ "|ALARMA NIVEL"//
			+ "|\\sPERA\\s"//
			+ "|ALARMA DE ALTO NIVEL"//
			+ "|MEDIDOR DE NIVEL"//
			+ "|SENSOR DE NIVEL"//
			+ "|ALTO NIVEL SALA BOMBAS"//
			+ "|ALARMA M.XIMO NIVEL"//
			+ "|BAJO NIVEL"//
			+ "|ALTO NIVEL"//
			+ "|Alto nivel"//
			+ "|NIVEL ALTO (PERA)"//
			+ "|MEDICION DE PRESION"//
			+ "|NIVEL FUERA DE RANGO"//
			+ "|BAJA PRESION"//
			+ "|NIVEL BAJO";

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
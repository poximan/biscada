/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.etl.partes_alarma.equipos;

import main.java.com.servicoop.app.comunes.entidades.TipoDeEquipo;

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
 * est� leyendo.
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
public class Plc extends TipoDeEquipo {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String expresion_regular = "FALLA COMUNC."//
			+ "|PLC"//
			+ "|(?<!SELECTORA ESTADO)\\sRTU"//
			+ "|Communication Error"//
			+ "|PERDIDA DE COMUNIC"//
			+ "|FALLA EVO (ENT|ANA)"//
			+ "|LOG (DE )?ERRORES"//
			+ "|LECTURA ANA"//
			+ "|ERROR LECTURA DE NIVEL"//
			+ "|I/O";

	private static String descripcion = "plc";

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

	public Plc() {
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

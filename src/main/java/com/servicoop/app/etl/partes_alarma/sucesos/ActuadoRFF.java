/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.etl.partes_alarma.sucesos;

import main.java.com.servicoop.app.comunes.entidades.Suceso;

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
 * comunes.modelo.Suceso
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO, expongo una instancia de mi si la fabrica concreta
 * comunes.fabrica.SucesoFactory, concluye que mi expresion regular estatica
 * (antes de la instancia) es un buen definidor del discriminante que est�
 * leyendo.
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
public class ActuadoRFF extends Suceso {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String expresion_regular = "FALTA (DE )?(FASE|RED| ?220)"//
			+ "|BARRA SIN TENSION"//
			+ "|TENSION EN BARRA"//
			+ "|ALIMENTACI.N DE RED"//
			+ "|SGCA"//
			+ "|Alarma CA"//
			+ "|FALLA DE AC"//
			+ "|Falta de fase";

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

	public ActuadoRFF() {
		super.setDescripcion(this.toString());
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	@Override
	public String toString() {
		return "rff actuado";
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

}

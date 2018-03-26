/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.etl.partes_alarma.familias;

import main.java.com.servicoop.app.comunes.entidades.Familia;

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
 * comunes.modelo.Familia
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO, expongo una instancia de mi si la fabrica concreta
 * comunes.fabrica.FamiliaFactory, concluye que mi expresion regular estatica
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
public class Cloacal extends Familia {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String expresion_regular = "CLOACAL";
	private static String descripcion = "cloacal";

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

	public Cloacal() {
		super(descripcion);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	@Override
	public String toString() {
		return descripcion;
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

}

/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.partes_alarma.sitios;

import comunes.entidades.Familia;
import comunes.entidades.Sitio;
import etl.partes_alarma.familias.Potable;

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
 * comunes.modelo.Sitio
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, expongo una instancia de mi si la fabrica concreta
 * comunes.fabrica.SitioFactory, concluye que mi expresion regular estatica
 * (antes de la instancia) es un buen definidor del discriminante que está
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
public class ReservaPlantaPotabilizadora extends Sitio {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String expresion_regular = "P[\\.\\s]POT"//
			+ "|POTABILIZADORA"//
			+ "|station\\s20\\."//
			+ "|Planta Potabilizadora";

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

	public ReservaPlantaPotabilizadora() {
		super.setDescripcion(this.toString());
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	@Override
	public Familia getFamiliaPorDefecto() {
		return new Potable();
	}

	@Override
	public String toString() {
		return "reserva p.pot";
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

}
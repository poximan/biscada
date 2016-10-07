/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.partes_alarma.equipos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jfree.util.Log;

import comunes.fabrica.Constantes;
import comunes.modelo.TipoDeEquipo;
import etl.excepciones.NumeroEquipoExcedidoExcepcion;

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
public class Bomba extends TipoDeEquipo {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String expresion_regular = "B\\d|BOMBA\\W|Bomba\\W|TERMICO ACTUADO - (BA|BB|BC)";
	private static String descripcion = "bomba";

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

	public Bomba() {
		super(descripcion);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	@Override
	public Integer getNumero(String discriminante) {

		if (discriminante.matches(Constantes.ABRE_EXP_REG + "(\\sBA|BOMBA\\s?1)" + Constantes.CIERRA_EXP_REG))
			return new Integer(1);

		if (discriminante.matches(Constantes.ABRE_EXP_REG + "(\\sBB|BOMBA\\s?2)" + Constantes.CIERRA_EXP_REG))
			return new Integer(2);

		if (discriminante.matches(Constantes.ABRE_EXP_REG + "(\\sBC|BOMBA\\s?3)" + Constantes.CIERRA_EXP_REG))
			return new Integer(3);

		Pattern p = Pattern.compile("-?\\d+");

		Matcher m = p.matcher(discriminante);
		m.find();

		Integer numero = new Integer(m.group());

		if (numero == null || numero.intValue() > Constantes.MAX_BOMBAS)
			try {
				throw new NumeroEquipoExcedidoExcepcion(numero.toString());
			} catch (NumeroEquipoExcedidoExcepcion e) {
				Log.error(e.getMessage());
			}

		return numero;
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
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

public class Tamiz extends TipoDeEquipo {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String expresion_regular = "TAMIZ";
	private static String descripcion = "tamiz";

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

	public Tamiz() {
		super(descripcion);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	@Override
	public Integer getNumero(String discriminante) {

		Pattern p = Pattern.compile("-?\\d+");

		Matcher m = p.matcher(discriminante);
		m.find();

		Integer numero = new Integer(m.group());

		if (numero == null || numero.intValue() > Constantes.MAX_TAMICES)
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
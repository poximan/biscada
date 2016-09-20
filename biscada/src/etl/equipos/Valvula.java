/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.equipos;

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

public class Valvula extends TipoDeEquipo {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String expresion_regular = "V(R|)\\d"//
			+ "|V.LVULA\\W"//
			+ "|V.lvula\\W"//
			+ "|V.LV\\.DIAFRAGMA";

	private static String descripcion = "valvula";

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

	public Valvula() {
		super(descripcion);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	@Override
	public Integer getNumero(String discriminante) {

		if (discriminante.matches(Constantes.ABRE_EXP_REG + "(V.LV\\.DIAFRAGMA|C90 - V0)" + Constantes.CIERRA_EXP_REG))
			return new Integer(0);
		
		if (discriminante.matches(Constantes.ABRE_EXP_REG + "(V(R|)1|V250)" + Constantes.CIERRA_EXP_REG))
			return new Integer(1);

		if (discriminante.matches(Constantes.ABRE_EXP_REG + "(V(R|)2|V300)" + Constantes.CIERRA_EXP_REG))
			return new Integer(2);
		
		if (discriminante.matches(Constantes.ABRE_EXP_REG + "V400" + Constantes.CIERRA_EXP_REG))
			return new Integer(3);
		
		if (discriminante.matches(Constantes.ABRE_EXP_REG + "V500" + Constantes.CIERRA_EXP_REG))
			return new Integer(4);

		Pattern p = Pattern.compile("-?\\d+");

		Matcher m = p.matcher(discriminante);
		m.find();

		Integer numero = new Integer(m.group());

		if (numero == null || numero.intValue() > Constantes.MAX_VALVULAS)
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
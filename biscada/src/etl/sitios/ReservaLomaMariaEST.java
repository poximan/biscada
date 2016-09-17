/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.sitios;

import comunes.fabrica.Constantes;
import comunes.fabrica.TipoDatoFabricable;
import comunes.modelo.Familia;
import comunes.modelo.Sitio;
import etl.excepciones.CampoTextoAmbiguoExcepcion;
import etl.familias.Potable;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ReservaLomaMariaEST extends Sitio {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String expresion_regular = "(LOMA\\s|L\\.)\\s?MARIA|station\\s35";

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

	public ReservaLomaMariaEST() {
		super.setDescripcion(this.toString());
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	@Override
	public String toString() {
		return "reserva l.maria estacion";
	}

	@Override
	public Familia getFamiliaPorDefecto() {
		return new Potable();
	}

	public static void asociar(TipoDatoFabricable valor, String discriminante) throws CampoTextoAmbiguoExcepcion {

		if (discriminante.matches(Constantes.ABRE_EXP_REG + ReservaLomaMariaEST.getExpresion_regular()
				+ Constantes.CIERRA_EXP_REG)) {

			if (valor != null)
				throw new CampoTextoAmbiguoExcepcion(
						discriminante + " [ " + ReservaLomaMariaEST.class.getSimpleName() + " - "
								+ valor.getClass().getSimpleName() + " ]");

			valor = new ReservaLomaMariaEST();
		}
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

}
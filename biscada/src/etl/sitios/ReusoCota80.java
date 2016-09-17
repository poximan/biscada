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
import etl.familias.Reuso;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ReusoCota80 extends Sitio {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String expresion_regular = "COTA\\s?80|station\\s153";

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

	public ReusoCota80() {
		super.setDescripcion(this.toString());
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	@Override
	public String toString() {
		return "reuso cota80";
	}

	@Override
	public Familia getFamiliaPorDefecto() {
		return new Reuso();
	}

	public static void asociar(TipoDatoFabricable valor, String discriminante) throws CampoTextoAmbiguoExcepcion {

		if (discriminante.matches(Constantes.ABRE_EXP_REG + ReusoCota80.getExpresion_regular()
				+ Constantes.CIERRA_EXP_REG)) {

			if (valor != null)
				throw new CampoTextoAmbiguoExcepcion(
						discriminante + " [ " + ReusoCota80.class.getSimpleName() + " - "
								+ valor.getClass().getSimpleName() + " ]");

			valor = new ReusoCota80();
		}
	}

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

}
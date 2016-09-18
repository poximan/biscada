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

public class ReservaDoradilloPresurizacion extends Sitio {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static String expresion_regular = "DORADILLO SALA PRE";

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

	public ReservaDoradilloPresurizacion() {
		super.setDescripcion(this.toString());
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	@Override
	public String toString() {
		return "reserva interna doradillo, presurizacion|station\\s22";
	}

	@Override
	public Familia getFamiliaPorDefecto() {
		return new Potable();
	}
	
	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

}
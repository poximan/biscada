/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import etl.equipos.Bomba;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * parte de la implementacion del patron AbstractFactory para resolver la
 * instancia de subclases de objetos de una clase variable.
 * 
 * @author hdonato
 *
 */
public class TipoDeEquipoFactory extends FabricaAbstracta {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public TipoDatoFabricable getInstancia(String discriminante) {

		if (discriminante.matches(Bomba.getExpresion_regular()))
			return new Bomba();
		
		if (discriminante.matches(Bomba.getExpresion_regular()))
			return new Bomba();

		return null;
	}
}
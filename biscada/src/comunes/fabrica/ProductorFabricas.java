/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

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
public class ProductorFabricas {

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

	public static FabricaAbstracta getFactory(String eleccion) {

		if (eleccion.equals(Constantes.FABRICA_TIPO_DE_EQUIPO))
			return new TipoDeEquipoFactory();

		if (eleccion.equals(Constantes.FABRICA_FAMILIA))
			return new FamiliaFactory();

		if (eleccion.equals(Constantes.FABRICA_SITIO))
			return new SitioFactory();

		if (eleccion.equals(Constantes.FABRICA_SUCESO))
			return new SucesoFactory();

		return null;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */
}

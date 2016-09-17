/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import etl.controles.CampoTextoDefectuoso;

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

	private static CampoTextoDefectuoso alarma_rechazada;

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public static CampoTextoDefectuoso getAlarma_rechazada() {
		return alarma_rechazada;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public static FabricaAbstracta getFactory(String eleccion, CampoTextoDefectuoso alarma_rechazada) {

		ProductorFabricas.alarma_rechazada = alarma_rechazada;

		if (eleccion.equals(Constantes.FABRICA_EQUIPO_EN_SITIO))
			return new EquipoEnSitioFactory(alarma_rechazada);

		if (eleccion.equals(Constantes.FABRICA_TIPO_DE_EQUIPO))
			return new TipoDeEquipoFactory(alarma_rechazada);

		if (eleccion.equals(Constantes.FABRICA_FAMILIA))
			return new FamiliaFactory(alarma_rechazada);

		if (eleccion.equals(Constantes.FABRICA_SITIO))
			return new SitioFactory(alarma_rechazada);

		if (eleccion.equals(Constantes.FABRICA_SUCESO))
			return new SucesoFactory(alarma_rechazada);

		return null;
	}
}
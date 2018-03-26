/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.comunes.fabrica;

import main.java.com.servicoop.app.etl.controles.servicios.CampoTextoDefectuoso;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 *
 * ==== parte clase =========================
 *
 * YO REPRESENTO un productor de nuevas fabricas concretas
 *
 *
 * ==== parte responsabilidad ===============
 *
 * LO QUE HAGO devuelvo instancias concretar de una fabrica entre las que
 * conozco.
 *
 * LO QUE CONOZCO todas las fabricas concretas implementadas. por ejemplo
 * conozco las fabricas de familia, sitio y suceso entre otras
 *
 *
 * ==== parte colaboracion ==================
 *
 * MI COLABORADOR PRINCIPAL ES la clase comunes.fabrica.Constantes que conoce el
 * String al que debe ser igual el discriminante
 *
 * COMO INTERACTUO CON MI COLABORADOR uso el discriminante que me entrega mi
 * cliente, y lo comparo mediante equals(Constantes.FABRICA_EQUIPO_EN_SITIO) o
 * la fabrica que corresponda.
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

/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import comunes.modelo.EquipoEnSitio;
import comunes.modelo.TipoDeEquipo;
import etl.controles.servicios.CampoTextoDefectuoso;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * parte clase ===========
 * 
 * YO REPRESENTO la implementacion concreta de una fabrica abstracta. patron de
 * diseño AbstractFactory
 * 
 * parte responsabilidad =====================
 * 
 * LO QUE HAGO devuelvo instancias concretas de la super clase para la que fui
 * creado.
 * 
 * LO QUE CONOZCO el identificador de cada sub clase concreta y su constructor
 * 
 * parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL ES quien precise una instancia de los subtipos que
 * conozco
 * 
 * COMO INTERACTUO CON MI COLABORADOR este conoce el discriminante y yo lo que
 * debo devolver nos comunicacmos a traves del mensaje getInstancia(String
 * discriminante)
 * 
 * @author hdonato
 *
 */
public class EquipoEnSitioFactory extends FabricaAbstracta {

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public EquipoEnSitioFactory(CampoTextoDefectuoso alarma_rechazada) {
		super(alarma_rechazada);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public TipoDatoFabricable getInstancia(String discriminante) {

		FabricaAbstracta fabrica_tipo_de_equipo = ProductorFabricas.getFactory(Constantes.FABRICA_TIPO_DE_EQUIPO,
				ProductorFabricas.getAlarma_rechazada());
		TipoDatoFabricable tipo_de_equipo = fabrica_tipo_de_equipo.getInstancia(discriminante);

		Integer id_equipo = null;

		try {
			id_equipo = ((TipoDeEquipo) tipo_de_equipo).getNumero(discriminante);
		} catch (IllegalStateException excepcion) {

			super.getAlarma_rechazada().agregarNuevaAlarma(EquipoEnSitioFactory.class.getSimpleName(),
					excepcion.getMessage());
		}

		return new EquipoEnSitio((TipoDeEquipo) tipo_de_equipo, id_equipo);
	}
}
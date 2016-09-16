/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import etl.controles.etl.CampoTextoDefectuoso;
import etl.excepciones.CampoTextoNoEncontradoExcepcion;

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

		try {

			FabricaAbstracta fabrica_tipo_de_equipo = ProductorFabricas.getFactory(Constantes.FABRICA_TIPO_DE_EQUIPO,
					ProductorFabricas.getAlarma_rechazada());
			TipoDatoFabricable tipo_de_equipo = fabrica_tipo_de_equipo.getInstancia(discriminante);

			throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (CampoTextoNoEncontradoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(EquipoEnSitioFactory.class.getSimpleName(),
					excepcion.getMessage(), discriminante);
		}

		return null;
	}

	private String obtenerIdEquipo(String group) {

		String[] split = group.split(" ");

		if (split.length == 1) { // si el numero de equipo viene pegado a la
									// descripcion del equipo
			split[0] = new StringBuilder(split[0]).insert(split[0].length() - 1, " ").toString();
			split = split[0].split(" ");
		}
		return split[split.length - 1];
	}
}
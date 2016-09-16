/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import etl.controles.etl.CampoTextoDefectuoso;
import etl.equipos.Bomba;
import etl.equipos.CamaraAspiracion;
import etl.equipos.Cisterna;
import etl.equipos.Forzador;
import etl.equipos.GrupoElectrogeno;
import etl.equipos.Plc;
import etl.equipos.Pozo;
import etl.equipos.SCADA;
import etl.equipos.Tamiz;
import etl.equipos.TornilloCompactador;
import etl.equipos.Valvula;
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
public class TipoDeEquipoFactory extends FabricaAbstracta {

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public TipoDeEquipoFactory(CampoTextoDefectuoso alarma_rechazada) {
		super(alarma_rechazada);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public TipoDatoFabricable getInstancia(String discriminante) {

		try {

			if (discriminante.matches(".*" + Bomba.getExpresion_regular() + ".*"))
				return new Bomba();

			if (discriminante.matches(".*" + CamaraAspiracion.getExpresion_regular() + ".*"))
				return new CamaraAspiracion();

			if (discriminante.matches(".*" + Cisterna.getExpresion_regular() + ".*"))
				return new Cisterna();

			if (discriminante.matches(".*" + Forzador.getExpresion_regular() + ".*"))
				return new Forzador();

			if (discriminante.matches(".*" + GrupoElectrogeno.getExpresion_regular() + ".*"))
				return new GrupoElectrogeno();

			if (discriminante.matches(".*" + Plc.getExpresion_regular() + ".*"))
				return new Plc();

			if (discriminante.matches(".*" + Pozo.getExpresion_regular() + ".*"))
				return new Pozo();

			if (discriminante.matches(".*" + SCADA.getExpresion_regular() + ".*"))
				return new SCADA();

			if (discriminante.matches(".*" + Tamiz.getExpresion_regular() + ".*"))
				return new Tamiz();

			if (discriminante.matches(".*" + TornilloCompactador.getExpresion_regular() + ".*"))
				return new TornilloCompactador();

			if (discriminante.matches(".*" + Valvula.getExpresion_regular() + ".*"))
				return new Valvula();

			throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (CampoTextoNoEncontradoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(TipoDeEquipoFactory.class.getSimpleName(),
					excepcion.getMessage(), discriminante);
		}

		return null;
	}
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import java.util.regex.PatternSyntaxException;

import etl.controles.CampoTextoDefectuoso;
import etl.equipos.Bomba;
import etl.equipos.CamaraAspiracion;
import etl.equipos.Cisterna;
import etl.equipos.Forzador;
import etl.equipos.GrupoElectrogeno;
import etl.equipos.Plc;
import etl.equipos.Pozo;
import etl.equipos.SCADA;
import etl.equipos.TableroSitio;
import etl.equipos.Tamiz;
import etl.equipos.TornilloCompactador;
import etl.equipos.Valvula;
import etl.excepciones.CampoTextoAmbiguoExcepcion;
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

		TipoDatoFabricable valor = null;

		try {

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + Bomba.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				valor = new Bomba();

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + CamaraAspiracion.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new CamaraAspiracion();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + Cisterna.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new Cisterna();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + Forzador.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new Forzador();
			}

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + GrupoElectrogeno.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new GrupoElectrogeno();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + Plc.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new Plc();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + Pozo.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new Pozo();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + SCADA.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new SCADA();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + Tamiz.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new Tamiz();
			}

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + TornilloCompactador.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new TornilloCompactador();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + Valvula.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new Valvula();
			}

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + TableroSitio.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new TableroSitio();
			}

			throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (PatternSyntaxException | CampoTextoNoEncontradoExcepcion | CampoTextoAmbiguoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(TipoDeEquipoFactory.class.getSimpleName(),
					excepcion.getMessage(), discriminante);
		}

		return valor;
	}
}
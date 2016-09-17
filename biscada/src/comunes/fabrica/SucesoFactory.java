/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import etl.controles.CampoTextoDefectuoso;
import etl.excepciones.CampoTextoNoEncontradoExcepcion;
import etl.sucesos.AguaEnEstator;
import etl.sucesos.AltaTemperaturaBobinado;
import etl.sucesos.ComandoParada;
import etl.sucesos.EvolucionLecturaAnalogica;
import etl.sucesos.GrupoElectrogenoFalla;
import etl.sucesos.GrupoElectrogenoMarcha;
import etl.sucesos.IncongruenciaEstado;
import etl.sucesos.IniciaSesion;
import etl.sucesos.InterruptorActuado;
import etl.sucesos.NivelAlto;
import etl.sucesos.NivelBajo;
import etl.sucesos.NivelRebalse;
import etl.sucesos.PerdidaComunicacion;
import etl.sucesos.RFFActuado;
import etl.sucesos.Robo;
import etl.sucesos.SCADABackupActivo;
import etl.sucesos.TermicoActuado;
import etl.sucesos.VibracionMotor;

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
public class SucesoFactory extends FabricaAbstracta {

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public SucesoFactory(CampoTextoDefectuoso alarma_rechazada) {
		super(alarma_rechazada);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public TipoDatoFabricable getInstancia(String discriminante) {

		try {

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + AguaEnEstator.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				return new AguaEnEstator();

			if (discriminante.matches(Constantes.ABRE_EXP_REG + AltaTemperaturaBobinado.getExpresion_regular()
					+ Constantes.CIERRA_EXP_REG))
				return new AltaTemperaturaBobinado();

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + ComandoParada.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				return new ComandoParada();

			if (discriminante.matches(Constantes.ABRE_EXP_REG + EvolucionLecturaAnalogica.getExpresion_regular()
					+ Constantes.CIERRA_EXP_REG))
				return new EvolucionLecturaAnalogica();

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + GrupoElectrogenoFalla.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				return new GrupoElectrogenoFalla();

			if (discriminante.matches(Constantes.ABRE_EXP_REG + GrupoElectrogenoMarcha.getExpresion_regular()
					+ Constantes.CIERRA_EXP_REG))
				return new GrupoElectrogenoMarcha();

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + IncongruenciaEstado.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				return new IncongruenciaEstado();

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + IniciaSesion.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				return new IniciaSesion();

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + InterruptorActuado.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				return new InterruptorActuado();

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + NivelAlto.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				return new NivelAlto();

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + NivelBajo.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				return new NivelBajo();

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + NivelRebalse.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				return new NivelRebalse();

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + PerdidaComunicacion.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				return new PerdidaComunicacion();

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + RFFActuado.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				return new RFFActuado();

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + Robo.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				return new Robo();

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + SCADABackupActivo.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				return new SCADABackupActivo();

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + TermicoActuado.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				return new TermicoActuado();

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + VibracionMotor.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				return new VibracionMotor();

			throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (CampoTextoNoEncontradoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(SucesoFactory.class.getSimpleName(), excepcion.getMessage(),
					discriminante);
		}

		return null;
	}
}
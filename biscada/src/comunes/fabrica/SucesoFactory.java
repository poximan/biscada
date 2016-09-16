/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import etl.controles.etl.CampoTextoDefectuoso;
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

			if (discriminante.matches(".*" + AguaEnEstator.getExpresion_regular() + ".*"))
				return new AguaEnEstator();

			if (discriminante.matches(".*" + AltaTemperaturaBobinado.getExpresion_regular() + ".*"))
				return new AltaTemperaturaBobinado();

			if (discriminante.matches(".*" + ComandoParada.getExpresion_regular() + ".*"))
				return new ComandoParada();

			if (discriminante.matches(".*" + EvolucionLecturaAnalogica.getExpresion_regular() + ".*"))
				return new EvolucionLecturaAnalogica();

			if (discriminante.matches(".*" + GrupoElectrogenoFalla.getExpresion_regular() + ".*"))
				return new GrupoElectrogenoFalla();

			if (discriminante.matches(".*" + GrupoElectrogenoMarcha.getExpresion_regular() + ".*"))
				return new GrupoElectrogenoMarcha();

			if (discriminante.matches(".*" + IncongruenciaEstado.getExpresion_regular() + ".*"))
				return new IncongruenciaEstado();

			if (discriminante.matches(".*" + IniciaSesion.getExpresion_regular() + ".*"))
				return new IniciaSesion();

			if (discriminante.matches(".*" + InterruptorActuado.getExpresion_regular() + ".*"))
				return new InterruptorActuado();

			if (discriminante.matches(".*" + NivelAlto.getExpresion_regular() + ".*"))
				return new NivelAlto();

			if (discriminante.matches(".*" + NivelBajo.getExpresion_regular() + ".*"))
				return new NivelBajo();

			if (discriminante.matches(".*" + NivelRebalse.getExpresion_regular() + ".*"))
				return new NivelRebalse();

			if (discriminante.matches(".*" + PerdidaComunicacion.getExpresion_regular() + ".*"))
				return new PerdidaComunicacion();

			if (discriminante.matches(".*" + RFFActuado.getExpresion_regular() + ".*"))
				return new RFFActuado();

			if (discriminante.matches(".*" + Robo.getExpresion_regular() + ".*"))
				return new Robo();

			if (discriminante.matches(".*" + SCADABackupActivo.getExpresion_regular() + ".*"))
				return new SCADABackupActivo();

			if (discriminante.matches(".*" + TermicoActuado.getExpresion_regular() + ".*"))
				return new TermicoActuado();

			if (discriminante.matches(".*" + VibracionMotor.getExpresion_regular() + ".*"))
				return new VibracionMotor();

			throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (CampoTextoNoEncontradoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(SucesoFactory.class.getSimpleName(), excepcion.getMessage(),
					discriminante);
		}

		return null;
	}
}
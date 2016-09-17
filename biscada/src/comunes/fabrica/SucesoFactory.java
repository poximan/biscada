/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import java.util.regex.PatternSyntaxException;

import etl.controles.CampoTextoDefectuoso;
import etl.excepciones.CampoTextoAmbiguoExcepcion;
import etl.excepciones.CampoTextoNoEncontradoExcepcion;
import etl.sucesos.AguaEnEstator;
import etl.sucesos.AltaTemperaturaBobinado;
import etl.sucesos.ComandoApertura;
import etl.sucesos.ComandoCierre;
import etl.sucesos.ComandoParada;
import etl.sucesos.ComandoSimultaneo;
import etl.sucesos.EstadoRTU;
import etl.sucesos.EstadoTablero;
import etl.sucesos.EvolucionLecturaAnalogica;
import etl.sucesos.GrupoElectrogenoFalla;
import etl.sucesos.GrupoElectrogenoMarcha;
import etl.sucesos.IncongruenciaEstado;
import etl.sucesos.IniciaSesion;
import etl.sucesos.InterruptorActuado;
import etl.sucesos.NivelAlto;
import etl.sucesos.NivelBajo;
import etl.sucesos.NivelRebalse;
import etl.sucesos.ParadaEmergenciaActuada;
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

		TipoDatoFabricable valor = null;

		try {

			AguaEnEstator.asociar(valor, discriminante);
			AltaTemperaturaBobinado.asociar(valor, discriminante);
			ComandoParada.asociar(valor, discriminante);
			EvolucionLecturaAnalogica.asociar(valor, discriminante);
			GrupoElectrogenoFalla.asociar(valor, discriminante);
			
			GrupoElectrogenoMarcha.asociar(valor, discriminante);
			IncongruenciaEstado.asociar(valor, discriminante);
			IniciaSesion.asociar(valor, discriminante);
			InterruptorActuado.asociar(valor, discriminante);
			NivelRebalse.asociar(valor, discriminante);

			NivelAlto.asociar(valor, discriminante);
			NivelBajo.asociar(valor, discriminante);
			PerdidaComunicacion.asociar(valor, discriminante);
			RFFActuado.asociar(valor, discriminante);
			Robo.asociar(valor, discriminante);

			SCADABackupActivo.asociar(valor, discriminante);
			TermicoActuado.asociar(valor, discriminante);
			VibracionMotor.asociar(valor, discriminante);
			ParadaEmergenciaActuada.asociar(valor, discriminante);
			ComandoSimultaneo.asociar(valor, discriminante);

			EstadoRTU.asociar(valor, discriminante);
			EstadoTablero.asociar(valor, discriminante);
			ComandoApertura.asociar(valor, discriminante);
			ComandoCierre.asociar(valor, discriminante);

			if (valor == null)
				throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (PatternSyntaxException | CampoTextoNoEncontradoExcepcion | CampoTextoAmbiguoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(SucesoFactory.class.getSimpleName(), excepcion.getMessage(),
					discriminante);
		}

		return valor;
	}
}
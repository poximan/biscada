/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import java.util.regex.PatternSyntaxException;

import etl.controles.CampoTextoDefectuoso;
import etl.controles.servicios.ServExpresionesRegulares;
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
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private ServExpresionesRegulares serv_exp_reg;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public SucesoFactory(CampoTextoDefectuoso alarma_rechazada) {
		super(alarma_rechazada);
		serv_exp_reg = new ServExpresionesRegulares();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public TipoDatoFabricable getInstancia(String discriminante) {

		TipoDatoFabricable dato_fabricado = null;

		try {

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, AguaEnEstator.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					AltaTemperaturaBobinado.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ComandoApertura.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ComandoCierre.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ComandoParada.class.getSimpleName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ComandoSimultaneo.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, EstadoRTU.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, EstadoTablero.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					EvolucionLecturaAnalogica.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					GrupoElectrogenoFalla.class.getSimpleName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					GrupoElectrogenoMarcha.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					IncongruenciaEstado.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, IniciaSesion.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					InterruptorActuado.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, NivelAlto.class.getSimpleName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, NivelBajo.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, NivelRebalse.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ParadaEmergenciaActuada.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					PerdidaComunicacion.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, RFFActuado.class.getSimpleName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Robo.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					SCADABackupActivo.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, TermicoActuado.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, VibracionMotor.class.getSimpleName());

			if (dato_fabricado == null)
				throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (PatternSyntaxException | CampoTextoAmbiguoExcepcion | CampoTextoNoEncontradoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(FamiliaFactory.class.getSimpleName(), excepcion.getMessage(),
					discriminante);
		}

		return dato_fabricado;
	}
}
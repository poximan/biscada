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
import etl.sucesos.ActuadoInterruptor;
import etl.sucesos.ActuadoParadaEmergencia;
import etl.sucesos.ActuadoRFF;
import etl.sucesos.ActuadoTermico;
import etl.sucesos.ComandoApertura;
import etl.sucesos.ComandoCierre;
import etl.sucesos.ComandoIndefinido;
import etl.sucesos.ComandoMarcha;
import etl.sucesos.ComandoParada;
import etl.sucesos.ComandoReposicionAlarmas;
import etl.sucesos.ComandoSimultaneo;
import etl.sucesos.EstadoCerrado;
import etl.sucesos.EstadoRTU;
import etl.sucesos.EstadoTablero;
import etl.sucesos.EvolucionLecturaAnalogica;
import etl.sucesos.FallaCargadorBateria;
import etl.sucesos.FallaRectificador;
import etl.sucesos.FallaRejaAutomatica;
import etl.sucesos.FallaSensorCaudal;
import etl.sucesos.FallaSensorNivel;
import etl.sucesos.FallaSensorPresion;
import etl.sucesos.GrupoElectrogenoFalla;
import etl.sucesos.GrupoElectrogenoMarcha;
import etl.sucesos.IncongruenciaEstado;
import etl.sucesos.IniciaSesion;
import etl.sucesos.MotorBobinadoAltaTemperatura;
import etl.sucesos.MotorEstatorConAgua;
import etl.sucesos.MotorVibracion;
import etl.sucesos.NivelAlto;
import etl.sucesos.NivelBajo;
import etl.sucesos.NivelRebalse;
import etl.sucesos.PlcErrorComunicacion;
import etl.sucesos.PlcErrorLog;
import etl.sucesos.PuertaAbierta;
import etl.sucesos.Robo;
import etl.sucesos.SCADABackupActivo;
import etl.sucesos.TimeOutIndefinido;

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

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ActuadoInterruptor.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ActuadoParadaEmergencia.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ActuadoRFF.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ActuadoTermico.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ComandoApertura.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ComandoCierre.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ComandoIndefinido.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ComandoMarcha.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ComandoParada.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ComandoReposicionAlarmas.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ComandoSimultaneo.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					EstadoCerrado.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, EstadoRTU.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					EstadoTablero.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					EvolucionLecturaAnalogica.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					FallaCargadorBateria.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					FallaRectificador.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					FallaRejaAutomatica.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					FallaSensorCaudal.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					FallaSensorNivel.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					FallaSensorPresion.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					GrupoElectrogenoFalla.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					GrupoElectrogenoMarcha.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					IncongruenciaEstado.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, IniciaSesion.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					MotorBobinadoAltaTemperatura.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					MotorEstatorConAgua.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					MotorVibracion.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, NivelAlto.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, NivelBajo.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, NivelRebalse.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					PlcErrorComunicacion.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, PlcErrorLog.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					PuertaAbierta.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Robo.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					SCADABackupActivo.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					TimeOutIndefinido.class.getCanonicalName());

			if (dato_fabricado == null)
				throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (PatternSyntaxException | CampoTextoAmbiguoExcepcion | CampoTextoNoEncontradoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(SucesoFactory.class.getSimpleName(), excepcion.getMessage());
		}

		return dato_fabricado;
	}
}
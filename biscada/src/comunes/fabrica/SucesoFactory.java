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
import etl.partes_alarma.sucesos.ActuadoInterruptor;
import etl.partes_alarma.sucesos.ActuadoParadaEmergencia;
import etl.partes_alarma.sucesos.ActuadoRFF;
import etl.partes_alarma.sucesos.ActuadoTermico;
import etl.partes_alarma.sucesos.ComandoApertura;
import etl.partes_alarma.sucesos.ComandoCierre;
import etl.partes_alarma.sucesos.ComandoIndefinido;
import etl.partes_alarma.sucesos.ComandoMarcha;
import etl.partes_alarma.sucesos.ComandoParada;
import etl.partes_alarma.sucesos.ComandoReposicionAlarmas;
import etl.partes_alarma.sucesos.ComandoSimultaneo;
import etl.partes_alarma.sucesos.EstadoCerrado;
import etl.partes_alarma.sucesos.EstadoRTU;
import etl.partes_alarma.sucesos.EstadoTablero;
import etl.partes_alarma.sucesos.EvolucionLecturaAnalogica;
import etl.partes_alarma.sucesos.FallaCargadorBateria;
import etl.partes_alarma.sucesos.FallaRectificador;
import etl.partes_alarma.sucesos.FallaRejaAutomatica;
import etl.partes_alarma.sucesos.FallaSensorCaudal;
import etl.partes_alarma.sucesos.FallaSensorNivel;
import etl.partes_alarma.sucesos.FallaSensorPresion;
import etl.partes_alarma.sucesos.GrupoElectrogenoFalla;
import etl.partes_alarma.sucesos.GrupoElectrogenoMarcha;
import etl.partes_alarma.sucesos.IncongruenciaEstado;
import etl.partes_alarma.sucesos.IniciaSesion;
import etl.partes_alarma.sucesos.MotorBobinadoAltaTemperatura;
import etl.partes_alarma.sucesos.MotorEstatorConAgua;
import etl.partes_alarma.sucesos.MotorVibracion;
import etl.partes_alarma.sucesos.NivelAlto;
import etl.partes_alarma.sucesos.NivelBajo;
import etl.partes_alarma.sucesos.NivelRebalse;
import etl.partes_alarma.sucesos.PlcErrorComunicacion;
import etl.partes_alarma.sucesos.PlcErrorLecturaAnalogica;
import etl.partes_alarma.sucesos.PlcErrorLog;
import etl.partes_alarma.sucesos.PresionBaja;
import etl.partes_alarma.sucesos.PuertaAbierta;
import etl.partes_alarma.sucesos.Robo;
import etl.partes_alarma.sucesos.ScadaBackupActivo;
import etl.partes_alarma.sucesos.ScadaSesion;
import etl.partes_alarma.sucesos.TimeOutIndefinido;

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
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					PlcErrorLecturaAnalogica.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, PlcErrorLog.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, PresionBaja.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					PuertaAbierta.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Robo.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ScadaBackupActivo.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ScadaSesion.class.getCanonicalName());

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
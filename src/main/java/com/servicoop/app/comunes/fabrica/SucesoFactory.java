/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.comunes.fabrica;

import java.util.regex.PatternSyntaxException;

import main.java.com.servicoop.app.etl.controles.servicios.CampoTextoDefectuoso;
import main.java.com.servicoop.app.etl.controles.servicios.ServExpresionesRegulares;
import main.java.com.servicoop.app.etl.excepciones.CampoTextoAmbiguoExcepcion;
import main.java.com.servicoop.app.etl.excepciones.CampoTextoNoEncontradoExcepcion;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ActuadoInterruptor;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ActuadoParadaEmergencia;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ActuadoRFF;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ActuadoTermico;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ComandoApertura;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ComandoCierre;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ComandoIndefinido;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ComandoMarcha;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ComandoParada;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ComandoReposicionAlarmas;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ComandoSimultaneo;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.EstadoCerrado;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.EstadoFalAla;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.EstadoRTU;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.EstadoTablero;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.EvolucionLecturaAnalogica;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.FallaCargadorBateria;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.FallaRectificador;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.FallaRejaAutomatica;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.FallaSensorCaudal;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.FallaSensorNivel;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.FallaSensorPresion;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.GrupoElectrogenoFalla;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.GrupoElectrogenoMarcha;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.IncongruenciaEstado;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.IniciaSesion;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ModoInactivo;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ModoLocal;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ModoManual;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.MotorBobinadoAltaTemperatura;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.MotorEstatorConAgua;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.MotorVibracion;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.NivelAlto;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.NivelBajo;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.NivelIndefinido;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.NivelRebalse;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.PlcErrorComunicacion;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.PlcErrorDIRemota;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.PlcErrorLecturaAnalogica;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.PlcErrorLog;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.PlcErrorModulo;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.PresionAlta;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.PresionBaja;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.PuertaAbierta;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.Robo;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ScadaBackupActivo;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.ScadaSesion;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.TemperaturaAlta;
import main.java.com.servicoop.app.etl.partes_alarma.sucesos.TimeOutIndefinido;

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
 * dise�o AbstractFactory
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
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, EstadoFalAla.class.getCanonicalName());
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

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ModoInactivo.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ModoLocal.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ModoManual.class.getCanonicalName());
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
					NivelIndefinido.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					PlcErrorComunicacion.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					PlcErrorDIRemota.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					PlcErrorLecturaAnalogica.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, PlcErrorLog.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					PlcErrorModulo.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, PresionAlta.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, PresionBaja.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					PuertaAbierta.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Robo.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ScadaBackupActivo.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ScadaSesion.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					TemperaturaAlta.class.getCanonicalName());
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

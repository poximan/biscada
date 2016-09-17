/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import java.util.regex.PatternSyntaxException;

import etl.controles.CampoTextoDefectuoso;
import etl.excepciones.CampoTextoAmbiguoExcepcion;
import etl.excepciones.CampoTextoNoEncontradoExcepcion;
import etl.familias.BackupSCADA;
import etl.familias.Cloacal;
import etl.familias.ErrorComunicacion;
import etl.familias.Login;
import etl.familias.Potable;
import etl.familias.Reuso;

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
public class FamiliaFactory extends FabricaAbstracta {

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public FamiliaFactory(CampoTextoDefectuoso alarma_rechazada) {
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
					.matches(Constantes.ABRE_EXP_REG + BackupSCADA.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				valor = new BackupSCADA();

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + Cloacal.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new Cloacal();
			}

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + ErrorComunicacion.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ErrorComunicacion();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + Login.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new Login();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + Potable.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new Potable();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + Reuso.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new Reuso();
			}
			
			if (valor == null)
				throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (PatternSyntaxException | CampoTextoAmbiguoExcepcion | CampoTextoNoEncontradoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(FamiliaFactory.class.getSimpleName(), excepcion.getMessage(),
					discriminante);
		}

		return valor;
	}
}
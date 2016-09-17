/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import etl.controles.CampoTextoDefectuoso;
import etl.excepciones.CampoTextoNoEncontradoExcepcion;
import etl.familias.BackupActivo;
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

		try {
			if (discriminante.matches(".*" + BackupActivo.getExpresion_regular() + ".*"))
				return new BackupActivo();

			if (discriminante.matches(".*" + Cloacal.getExpresion_regular() + ".*"))
				return new Cloacal();

			if (discriminante.matches(".*" + ErrorComunicacion.getExpresion_regular() + ".*"))
				return new ErrorComunicacion();

			if (discriminante.matches(".*" + Login.getExpresion_regular() + ".*"))
				return new Login();

			if (discriminante.matches(".*" + Potable.getExpresion_regular() + ".*"))
				return new Potable();

			if (discriminante.matches(".*" + Reuso.getExpresion_regular() + ".*"))
				return new Reuso();

			throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (CampoTextoNoEncontradoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(FamiliaFactory.class.getSimpleName(), excepcion.getMessage(),
					discriminante);
		}

		return null;
	}
}
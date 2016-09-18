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
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private ServExpresionesRegulares serv_exp_reg;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public FamiliaFactory(CampoTextoDefectuoso alarma_rechazada) {
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

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, BackupSCADA.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Cloacal.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ErrorComunicacion.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Login.class.getSimpleName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Potable.class.getSimpleName());
			
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Reuso.class.getSimpleName());

			if (dato_fabricado == null)
				throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (PatternSyntaxException | CampoTextoAmbiguoExcepcion | CampoTextoNoEncontradoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(FamiliaFactory.class.getSimpleName(), excepcion.getMessage(),
					discriminante);
		}

		return dato_fabricado;
	}
}
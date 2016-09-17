/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import java.util.regex.PatternSyntaxException;

import etl.controles.CampoTextoDefectuoso;
import etl.equipos.Bomba;
import etl.equipos.CamaraAspiracion;
import etl.equipos.Cisterna;
import etl.equipos.Edificio;
import etl.equipos.Forzador;
import etl.equipos.GrupoElectrogeno;
import etl.equipos.Plc;
import etl.equipos.Pozo;
import etl.equipos.SCADA;
import etl.equipos.TableroSitio;
import etl.equipos.Tamiz;
import etl.equipos.TornilloCompactador;
import etl.equipos.Valvula;
import etl.excepciones.CampoTextoAmbiguoExcepcion;
import etl.excepciones.CampoTextoNoEncontradoExcepcion;

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
public class TipoDeEquipoFactory extends FabricaAbstracta {

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public TipoDeEquipoFactory(CampoTextoDefectuoso alarma_rechazada) {
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
			
			Bomba.asociar(valor, discriminante);
			CamaraAspiracion.asociar(valor, discriminante);
			Cisterna.asociar(valor, discriminante);
			Forzador.asociar(valor, discriminante);			
			GrupoElectrogeno.asociar(valor, discriminante);
			
			Plc.asociar(valor, discriminante);
			Pozo.asociar(valor, discriminante);
			SCADA.asociar(valor, discriminante);			
			Tamiz.asociar(valor, discriminante);
			TornilloCompactador.asociar(valor, discriminante);
			
			Valvula.asociar(valor, discriminante);
			TableroSitio.asociar(valor, discriminante);			
			Edificio.asociar(valor, discriminante);

			if (valor == null)
				throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (PatternSyntaxException | CampoTextoNoEncontradoExcepcion | CampoTextoAmbiguoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(TipoDeEquipoFactory.class.getSimpleName(),
					excepcion.getMessage(), discriminante);
		}

		return valor;
	}
}
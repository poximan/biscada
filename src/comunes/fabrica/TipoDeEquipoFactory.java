/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import java.util.regex.PatternSyntaxException;

import etl.controles.servicios.CampoTextoDefectuoso;
import etl.controles.servicios.ServExpresionesRegulares;
import etl.excepciones.CampoTextoAmbiguoExcepcion;
import etl.excepciones.CampoTextoNoEncontradoExcepcion;
import etl.partes_alarma.equipos.Bomba;
import etl.partes_alarma.equipos.CamaraAspiracion;
import etl.partes_alarma.equipos.CanalDescarga;
import etl.partes_alarma.equipos.CargadorBateria;
import etl.partes_alarma.equipos.CentroControlMotores;
import etl.partes_alarma.equipos.Cisterna;
import etl.partes_alarma.equipos.Desarenador;
import etl.partes_alarma.equipos.Edificio;
import etl.partes_alarma.equipos.Forzador;
import etl.partes_alarma.equipos.GrupoElectrogeno;
import etl.partes_alarma.equipos.InstrumentoCampo;
import etl.partes_alarma.equipos.Plc;
import etl.partes_alarma.equipos.Pozo;
import etl.partes_alarma.equipos.RejaAutomatica;
import etl.partes_alarma.equipos.SCADA;
import etl.partes_alarma.equipos.Tamiz;
import etl.partes_alarma.equipos.TornilloCompactador;
import etl.partes_alarma.equipos.Transformador;
import etl.partes_alarma.equipos.Valvula;

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
public class TipoDeEquipoFactory extends FabricaAbstracta {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private ServExpresionesRegulares serv_exp_reg;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public TipoDeEquipoFactory(CampoTextoDefectuoso alarma_rechazada) {
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

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Bomba.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					CamaraAspiracion.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					CanalDescarga.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					CargadorBateria.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					CentroControlMotores.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Cisterna.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Desarenador.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Edificio.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Forzador.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					GrupoElectrogeno.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					InstrumentoCampo.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Plc.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Pozo.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					RejaAutomatica.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, SCADA.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Tamiz.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					TornilloCompactador.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					Transformador.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Valvula.class.getCanonicalName());

			if (dato_fabricado == null)
				throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (PatternSyntaxException | CampoTextoAmbiguoExcepcion | CampoTextoNoEncontradoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(TipoDeEquipoFactory.class.getSimpleName(),
					excepcion.getMessage());
		}

		return dato_fabricado;
	}
}
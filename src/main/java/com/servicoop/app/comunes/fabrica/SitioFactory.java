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
import main.java.com.servicoop.app.etl.partes_alarma.sitios.CentralSCADA;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.CloacalEE1;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.CloacalEE2;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.CloacalEE3;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.CloacalEE4;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.CloacalEPN;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.CloacalEPS;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ExemysEsclavoModbus;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.Reserva6000;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReservaCota126;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReservaCota90;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReservaDoradilloPresurizacion;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReservaKM11;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReservaLomaMariaEST;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReservaLomaMariaREP;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReservaLoteoSocial;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReservaNvaChubutBombeo;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReservaNvaChubutCist;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReservaOeste;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReservaParquePesquero;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReservaPlantaPotabilizadora;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReservaPujolBombeo;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReservaPujolPresurizacion;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReservaTomaRio;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReusoCamaraCarga;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReusoCota50;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReusoCota80;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReusoEE5;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReusoEE6;
import main.java.com.servicoop.app.etl.partes_alarma.sitios.ReusoPTN;

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
public class SitioFactory extends FabricaAbstracta {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private ServExpresionesRegulares serv_exp_reg;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public SitioFactory(CampoTextoDefectuoso alarma_rechazada) {
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

			/*
			 * sitios cloacales
			 */
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, CloacalEE1.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, CloacalEE2.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, CloacalEE3.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, CloacalEE4.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, CloacalEPN.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, CloacalEPS.class.getCanonicalName());

			/*
			 * sitios general
			 */
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, CentralSCADA.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ExemysEsclavoModbus.class.getCanonicalName());

			/*
			 * sitios agua potable
			 */
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, Reserva6000.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ReservaCota126.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ReservaCota90.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ReservaDoradilloPresurizacion.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ReservaKM11.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ReservaLomaMariaEST.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ReservaLomaMariaREP.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ReservaLoteoSocial.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ReservaNvaChubutBombeo.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ReservaNvaChubutCist.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ReservaOeste.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ReservaParquePesquero.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ReservaPlantaPotabilizadora.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ReservaPujolBombeo.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ReservaPujolPresurizacion.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ReservaTomaRio.class.getCanonicalName());

			/*
			 * sitios reuso
			 */
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante,
					ReusoCamaraCarga.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ReusoCota50.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ReusoCota80.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ReusoEE5.class.getCanonicalName());
			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ReusoEE6.class.getCanonicalName());

			dato_fabricado = serv_exp_reg.asociar(dato_fabricado, discriminante, ReusoPTN.class.getCanonicalName());

			if (dato_fabricado == null)
				throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (PatternSyntaxException | CampoTextoNoEncontradoExcepcion | CampoTextoAmbiguoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(SitioFactory.class.getSimpleName(), excepcion.getMessage());
		}
		return dato_fabricado;
	}
}

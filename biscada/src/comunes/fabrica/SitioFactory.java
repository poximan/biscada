/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import java.util.regex.PatternSyntaxException;

import etl.controles.CampoTextoDefectuoso;
import etl.excepciones.CampoTextoAmbiguoExcepcion;
import etl.excepciones.CampoTextoNoEncontradoExcepcion;
import etl.sitios.CentralSCADA;
import etl.sitios.CloacalEE1;
import etl.sitios.CloacalEE2;
import etl.sitios.CloacalEE3;
import etl.sitios.CloacalEE4;
import etl.sitios.CloacalEPN;
import etl.sitios.CloacalEPS;
import etl.sitios.Reserva6000;
import etl.sitios.ReservaCota90;
import etl.sitios.ReservaDoradilloPresurizacion;
import etl.sitios.ReservaKM11;
import etl.sitios.ReservaLomaMariaEST;
import etl.sitios.ReservaLomaMariaREP;
import etl.sitios.ReservaOeste;
import etl.sitios.ReservaParquePesquero;
import etl.sitios.ReservaPlantaPotabilizadora;
import etl.sitios.ReservaPujol;
import etl.sitios.ReservaTomaRio;
import etl.sitios.ReusoCamaraCarga;
import etl.sitios.ReusoCota50;
import etl.sitios.ReusoCota80;
import etl.sitios.ReusoEE5;
import etl.sitios.ReusoEE6;
import etl.sitios.ReusoPTN;

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
public class SitioFactory extends FabricaAbstracta {

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public SitioFactory(CampoTextoDefectuoso alarma_rechazada) {
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
					.matches(Constantes.ABRE_EXP_REG + CentralSCADA.getExpresion_regular() + Constantes.CIERRA_EXP_REG))
				valor = new CentralSCADA();

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + CloacalEE1.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new CloacalEE1();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + CloacalEE2.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new CloacalEE2();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + CloacalEE3.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new CloacalEE3();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + CloacalEE4.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new CloacalEE4();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + CloacalEPN.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new CloacalEPN();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + CloacalEPS.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new CloacalEPS();
			}

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + Reserva6000.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new Reserva6000();
			}

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + ReservaCota90.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReservaCota90();
			}

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + ReservaKM11.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReservaKM11();
			}

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + ReservaLomaMariaEST.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReservaLomaMariaEST();
			}

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + ReservaLomaMariaREP.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReservaLomaMariaREP();
			}

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + ReservaOeste.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReservaOeste();
			}

			if (discriminante.matches(Constantes.ABRE_EXP_REG + ReservaPlantaPotabilizadora.getExpresion_regular()
					+ Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReservaPlantaPotabilizadora();
			}

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + ReservaPujol.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReservaPujol();
			}

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + ReservaTomaRio.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReservaTomaRio();
			}

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + ReusoCamaraCarga.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReusoCamaraCarga();
			}

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + ReusoCota50.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReusoCota50();
			}

			if (discriminante.matches(
					Constantes.ABRE_EXP_REG + ReusoCota80.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReusoCota80();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + ReusoEE5.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReusoEE5();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + ReusoEE6.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReusoEE6();
			}

			if (discriminante
					.matches(Constantes.ABRE_EXP_REG + ReusoPTN.getExpresion_regular() + Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReusoPTN();
			}

			if (discriminante.matches(Constantes.ABRE_EXP_REG + ReservaParquePesquero.getExpresion_regular()
					+ Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReservaParquePesquero();
			}

			if (discriminante.matches(Constantes.ABRE_EXP_REG + ReservaDoradilloPresurizacion.getExpresion_regular()
					+ Constantes.CIERRA_EXP_REG)) {
				if (valor != null)
					throw new CampoTextoAmbiguoExcepcion(discriminante);
				valor = new ReservaDoradilloPresurizacion();
			}

			if (valor == null)
				throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (PatternSyntaxException | CampoTextoNoEncontradoExcepcion | CampoTextoAmbiguoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(SitioFactory.class.getSimpleName(), excepcion.getMessage(),
					discriminante);
		}

		return valor;
	}
}
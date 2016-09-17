/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.fabrica;

import etl.controles.CampoTextoDefectuoso;
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
import etl.sitios.ReservaKM11;
import etl.sitios.ReservaLomaMariaEST;
import etl.sitios.ReservaLomaMariaREP;
import etl.sitios.ReservaOeste;
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

		try {

			if (discriminante.matches(".*" + CentralSCADA.getExpresion_regular() + ".*"))
				return new CentralSCADA();

			if (discriminante.matches(".*" + CloacalEE1.getExpresion_regular() + ".*"))
				return new CloacalEE1();

			if (discriminante.matches(".*" + CloacalEE2.getExpresion_regular() + ".*"))
				return new CloacalEE2();

			if (discriminante.matches(".*" + CloacalEE3.getExpresion_regular() + ".*"))
				return new CloacalEE3();

			if (discriminante.matches(".*" + CloacalEE4.getExpresion_regular() + ".*"))
				return new CloacalEE4();

			if (discriminante.matches(".*" + CloacalEPN.getExpresion_regular() + ".*"))
				return new CloacalEPN();

			if (discriminante.matches(".*" + CloacalEPS.getExpresion_regular() + ".*"))
				return new CloacalEPS();

			if (discriminante.matches(".*" + Reserva6000.getExpresion_regular() + ".*"))
				return new Reserva6000();

			if (discriminante.matches(".*" + ReservaCota90.getExpresion_regular() + ".*"))
				return new ReservaCota90();

			if (discriminante.matches(".*" + ReservaKM11.getExpresion_regular() + ".*"))
				return new ReservaKM11();

			if (discriminante.matches(".*" + ReservaLomaMariaEST.getExpresion_regular() + ".*"))
				return new ReservaLomaMariaEST();

			if (discriminante.matches(".*" + ReservaLomaMariaREP.getExpresion_regular() + ".*"))
				return new ReservaLomaMariaREP();

			if (discriminante.matches(".*" + ReservaOeste.getExpresion_regular() + ".*"))
				return new ReservaOeste();

			if (discriminante.matches(".*" + ReservaPlantaPotabilizadora.getExpresion_regular() + ".*"))
				return new ReservaPlantaPotabilizadora();

			if (discriminante.matches(".*" + ReservaPujol.getExpresion_regular() + ".*"))
				return new ReservaPujol();

			if (discriminante.matches(".*" + ReservaTomaRio.getExpresion_regular() + ".*"))
				return new ReservaTomaRio();

			if (discriminante.matches(".*" + ReusoCamaraCarga.getExpresion_regular() + ".*"))
				return new ReusoCamaraCarga();

			if (discriminante.matches(".*" + ReusoCota50.getExpresion_regular() + ".*"))
				return new ReusoCota50();

			if (discriminante.matches(".*" + ReusoCota80.getExpresion_regular() + ".*"))
				return new ReusoCota80();

			if (discriminante.matches(".*" + ReusoEE5.getExpresion_regular() + ".*"))
				return new ReusoEE5();

			if (discriminante.matches(".*" + ReusoEE6.getExpresion_regular() + ".*"))
				return new ReusoEE6();

			if (discriminante.matches(".*" + ReusoPTN.getExpresion_regular() + ".*"))
				return new ReusoPTN();

			throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (CampoTextoNoEncontradoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(SitioFactory.class.getSimpleName(), excepcion.getMessage(),
					discriminante);
		}

		return null;
	}
}
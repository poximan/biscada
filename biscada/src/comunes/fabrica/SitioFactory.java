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

			CentralSCADA.asociar(valor, discriminante);
			CloacalEE1.asociar(valor, discriminante);
			CloacalEE2.asociar(valor, discriminante);
			CloacalEE3.asociar(valor, discriminante);
			CloacalEE4.asociar(valor, discriminante);

			CloacalEPN.asociar(valor, discriminante);
			CloacalEPS.asociar(valor, discriminante);
			Reserva6000.asociar(valor, discriminante);
			ReservaCota90.asociar(valor, discriminante);
			ReservaKM11.asociar(valor, discriminante);

			ReservaLomaMariaEST.asociar(valor, discriminante);
			ReservaLomaMariaREP.asociar(valor, discriminante);
			ReservaOeste.asociar(valor, discriminante);
			ReservaPlantaPotabilizadora.asociar(valor, discriminante);
			ReservaPujol.asociar(valor, discriminante);

			ReservaTomaRio.asociar(valor, discriminante);
			ReusoCamaraCarga.asociar(valor, discriminante);
			ReusoCota50.asociar(valor, discriminante);
			ReusoCota80.asociar(valor, discriminante);
			ReusoEE5.asociar(valor, discriminante);

			ReusoEE6.asociar(valor, discriminante);
			ReusoPTN.asociar(valor, discriminante);
			ReservaParquePesquero.asociar(valor, discriminante);
			ReservaDoradilloPresurizacion.asociar(valor, discriminante);

			if (valor == null)
				throw new CampoTextoNoEncontradoExcepcion(discriminante);

		} catch (PatternSyntaxException | CampoTextoNoEncontradoExcepcion | CampoTextoAmbiguoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(SitioFactory.class.getSimpleName(), excepcion.getMessage(),
					discriminante);
		}

		return valor;
	}
}
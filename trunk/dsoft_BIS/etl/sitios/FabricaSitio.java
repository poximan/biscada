/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package sitios;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modelo.Sitio;
import control_etl.CampoTextoDefectuoso;
import control_etl.TextoDiferenciable;
import control_general.Fabrica;
import excepciones.CampoTextoAmbiguoExcepcion;
import excepciones.CampoTextoNoEncontradoExcepcion;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * implementa el patron Factory Method. en este caso se trata de una fabrica parametrizada. Es el modulo creador
 * abstracto y concreto.
 */
public class FabricaSitio extends Fabrica implements TextoDiferenciable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private Sitio texto_propietario;
	private ExpRegSitio expresion_regular;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public FabricaSitio(CampoTextoDefectuoso alarma_rechazada) {
		super(alarma_rechazada);
		expresion_regular = new ExpRegSitio();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void prepararExpresionRegular(String campo_texto) {

		try {
			Pattern patron = Pattern.compile(expresion_regular.getExpresion_regular());
			Matcher matcher = patron.matcher(campo_texto);

			// verificar que coincida con algun grupo
			if (!matcher.find())
				throw new CampoTextoNoEncontradoExcepcion(campo_texto);

			matcher.reset();

			crearPropietario(campo_texto, matcher);
		}
		catch (CampoTextoNoEncontradoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(FabricaSitio.class.getSimpleName(), excepcion.getMessage(),
					campo_texto);
		}
		catch (CampoTextoAmbiguoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(FabricaSitio.class.getSimpleName(), excepcion.getMessage(),
					campo_texto);
		}
	}

	@Override
	public void crearPropietario(String campo_texto, Matcher matcher) throws CampoTextoNoEncontradoExcepcion,
			CampoTextoAmbiguoExcepcion {

		while (matcher.find()) {

			if (matcher.group(1) != null)
				if (texto_propietario == null || texto_propietario instanceof CentralSCADA)
					texto_propietario = new CentralSCADA();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(2) != null)
				if (texto_propietario == null || texto_propietario instanceof CloacalEE1)
					texto_propietario = new CloacalEE1();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(3) != null)
				if (texto_propietario == null || texto_propietario instanceof CloacalEE2)
					texto_propietario = new CloacalEE2();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(4) != null)
				if (texto_propietario == null || texto_propietario instanceof CloacalEE3)
					texto_propietario = new CloacalEE3();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(5) != null)
				if (texto_propietario == null || texto_propietario instanceof CloacalEE4)
					texto_propietario = new CloacalEE4();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(6) != null)
				if (texto_propietario == null || texto_propietario instanceof CloacalEPN)
					texto_propietario = new CloacalEPN();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(7) != null)
				if (texto_propietario == null || texto_propietario instanceof CloacalEPS)
					texto_propietario = new CloacalEPS();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(8) != null)
				if (texto_propietario == null || texto_propietario instanceof Reserva6000)
					texto_propietario = new Reserva6000();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(9) != null)
				if (texto_propietario == null || texto_propietario instanceof ReservaCota90)
					texto_propietario = new ReservaCota90();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(10) != null)
				if (texto_propietario == null || texto_propietario instanceof ReservaKM11)
					texto_propietario = new ReservaKM11();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(11) != null)
				if (texto_propietario == null || texto_propietario instanceof ReservaLomaMariaEST)
					texto_propietario = new ReservaLomaMariaEST();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(13) != null)
				if (texto_propietario == null || texto_propietario instanceof ReservaLomaMariaREP)
					texto_propietario = new ReservaLomaMariaREP();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(14) != null)
				if (texto_propietario == null || texto_propietario instanceof ReservaOeste)
					texto_propietario = new ReservaOeste();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(15) != null)
				if (texto_propietario == null || texto_propietario instanceof ReservaPlantaPotabilizadora)
					texto_propietario = new ReservaPlantaPotabilizadora();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(16) != null)
				if (texto_propietario == null || texto_propietario instanceof ReservaPujol)
					texto_propietario = new ReservaPujol();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(17) != null)
				if (texto_propietario == null || texto_propietario instanceof ReservaTomaRio)
					texto_propietario = new ReservaTomaRio();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(19) != null)
				if (texto_propietario == null || texto_propietario instanceof ReusoCamaraCarga)
					texto_propietario = new ReusoCamaraCarga();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(20) != null)
				if (texto_propietario == null || texto_propietario instanceof ReusoCota50)
					texto_propietario = new ReusoCota50();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(21) != null)
				if (texto_propietario == null || texto_propietario instanceof ReusoCota80)
					texto_propietario = new ReusoCota80();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(22) != null)
				if (texto_propietario == null || texto_propietario instanceof ReusoEE5)
					texto_propietario = new ReusoEE5();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(23) != null)
				if (texto_propietario == null || texto_propietario instanceof ReusoEE6)
					texto_propietario = new ReusoEE6();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(24) != null)
				if (texto_propietario == null || texto_propietario instanceof ReusoPTN)
					texto_propietario = new ReusoPTN();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);
		}
	}

	@Override
	public Object getPropietario() {
		return texto_propietario;
	}
}
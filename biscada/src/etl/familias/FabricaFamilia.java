/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.familias;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comunes.controles.Fabrica;
import comunes.modelo.Familia;
import etl.controles.etl.CampoTextoDefectuoso;
import etl.controles.etl.TextoDiferenciable;
import etl.excepciones.CampoTextoAmbiguoExcepcion;
import etl.excepciones.CampoTextoNoEncontradoExcepcion;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * implementa el patron Factory Method. en este caso se trata de una fabrica
 * parametrizada. Es el modulo creador abstracto y concreto.
 */
public class FabricaFamilia extends Fabrica implements TextoDiferenciable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private Familia texto_propietario;
	private ExpRegFamilia expresion_regular;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public FabricaFamilia(CampoTextoDefectuoso alarma_rechazada) {
		super(alarma_rechazada);
		expresion_regular = new ExpRegFamilia();
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
		} catch (CampoTextoNoEncontradoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(FabricaFamilia.class.getSimpleName(), excepcion.getMessage(),
					campo_texto);
		} catch (CampoTextoAmbiguoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(FabricaFamilia.class.getSimpleName(), excepcion.getMessage(),
					campo_texto);
		}
	}

	@Override
	public void crearPropietario(String campo_texto, Matcher matcher)
			throws CampoTextoNoEncontradoExcepcion, CampoTextoAmbiguoExcepcion {

		while (matcher.find()) {

			if (matcher.group(1) != null)
				if (texto_propietario == null || texto_propietario instanceof BackupActivo)
					texto_propietario = new BackupActivo();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(2) != null)
				if (texto_propietario == null || texto_propietario instanceof Cloacal)
					texto_propietario = new Cloacal();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(3) != null)
				if (texto_propietario == null || texto_propietario instanceof ErrorComunicacion)
					texto_propietario = new ErrorComunicacion();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(4) != null)
				if (texto_propietario == null || texto_propietario instanceof Login)
					texto_propietario = new Login();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);
			if (matcher.group(5) != null)
				if (texto_propietario == null || texto_propietario instanceof Potable)
					texto_propietario = new Potable();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(6) != null)
				if (texto_propietario == null || texto_propietario instanceof Reuso)
					texto_propietario = new Reuso();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);
		}
	}

	@Override
	public Object getPropietario() {
		return texto_propietario;
	}
}
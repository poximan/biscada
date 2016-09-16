/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.sucesos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comunes.controles.Fabrica;
import comunes.modelo.Suceso;
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
public class FabricaSuceso extends Fabrica implements TextoDiferenciable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private Suceso texto_propietario;
	private ExpRegSuceso expresion_regular;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public FabricaSuceso(CampoTextoDefectuoso alarma_rechazada) {
		super(alarma_rechazada);
		expresion_regular = new ExpRegSuceso();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public void crearPropietario(String campo_texto, Matcher matcher)
			throws CampoTextoNoEncontradoExcepcion, CampoTextoAmbiguoExcepcion {

		while (matcher.find()) {

			if (matcher.group(1) != null)
				if (texto_propietario == null || texto_propietario instanceof AguaEnEstator)
					texto_propietario = new AguaEnEstator();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(2) != null)
				if (texto_propietario == null || texto_propietario instanceof AltaTemperaturaBobinado)
					texto_propietario = new AltaTemperaturaBobinado();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(3) != null)
				if (texto_propietario == null || texto_propietario instanceof EvolucionLecturaAnalogica)
					texto_propietario = new EvolucionLecturaAnalogica();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(4) != null)
				if (texto_propietario == null || texto_propietario instanceof GrupoElectrogenoFalla)
					texto_propietario = new GrupoElectrogenoFalla();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(15) != null)
				if (texto_propietario == null || texto_propietario instanceof GrupoElectrogenoMarcha)
					texto_propietario = new GrupoElectrogenoMarcha();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(26) != null)
				if (texto_propietario == null || texto_propietario instanceof IniciaSesion)
					texto_propietario = new IniciaSesion();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(27) != null)
				if (texto_propietario == null || texto_propietario instanceof InterruptorActuado)
					texto_propietario = new InterruptorActuado();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(28) != null)
				if (texto_propietario == null || texto_propietario instanceof NivelAlto)
					texto_propietario = new NivelAlto();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(29) != null)
				if (texto_propietario == null || texto_propietario instanceof NivelBajo)
					texto_propietario = new NivelBajo();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(30) != null)
				if (texto_propietario == null || texto_propietario instanceof NivelRebalse)
					texto_propietario = new NivelRebalse();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(31) != null)
				if (texto_propietario == null || texto_propietario instanceof PerdidaComunicacion)
					texto_propietario = new PerdidaComunicacion();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(32) != null)
				if (texto_propietario == null || texto_propietario instanceof RFFActuado)
					texto_propietario = new RFFActuado();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(36) != null)
				if (texto_propietario == null || texto_propietario instanceof Robo)
					texto_propietario = new Robo();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(37) != null)
				if (texto_propietario == null || texto_propietario instanceof SCADABackupActivo)
					texto_propietario = new SCADABackupActivo();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(38) != null)
				if (texto_propietario == null || texto_propietario instanceof TermicoActuado)
					texto_propietario = new TermicoActuado();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(41) != null)
				if (texto_propietario == null || texto_propietario instanceof VibracionMotor)
					texto_propietario = new VibracionMotor();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);
		}
	}

	@Override
	public Object getPropietario() {
		return texto_propietario;
	}

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
			super.getAlarma_rechazada().agregarNuevaAlarma(FabricaSuceso.class.getSimpleName(), excepcion.getMessage(),
					campo_texto);
		} catch (CampoTextoAmbiguoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(FabricaSuceso.class.getSimpleName(), excepcion.getMessage(),
					campo_texto);
		}
	}
}
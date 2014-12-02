/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package equipos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modelo.EquipoEnSitio;
import modelo.TipoDeEquipo;
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
public class FabricaEquipo extends Fabrica implements TextoDiferenciable {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private TipoDeEquipo texto_propietario;
	private ExpRegEquipo expresion_regular;

	private EquipoEnSitio equipo_en_sitio;
	private int numero_equipo;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public FabricaEquipo(CampoTextoDefectuoso alarma_rechazada) {
		super(alarma_rechazada);
		expresion_regular = new ExpRegEquipo();
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

			numero_equipo = 0;
			crearPropietario(campo_texto, matcher);
			equipo_en_sitio = new EquipoEnSitio(texto_propietario, numero_equipo);
		}
		catch (CampoTextoNoEncontradoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(FabricaEquipo.class.getSimpleName(), excepcion.getMessage(),
					campo_texto);
		}
		catch (CampoTextoAmbiguoExcepcion excepcion) {
			super.getAlarma_rechazada().agregarNuevaAlarma(FabricaEquipo.class.getSimpleName(), excepcion.getMessage(),
					campo_texto);
		}
	}

	@Override
	public void crearPropietario(String campo_texto, Matcher matcher) throws CampoTextoNoEncontradoExcepcion,
			CampoTextoAmbiguoExcepcion {

		while (matcher.find()) {

			if (matcher.group(1) != null)
				if (texto_propietario == null || texto_propietario instanceof Bomba) {
					texto_propietario = new Bomba();
					numero_equipo = obtenerIdEquipo(matcher.group(1));
				} else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(2) != null)
				if (texto_propietario == null || texto_propietario instanceof CamaraAspiracion)
					texto_propietario = new CamaraAspiracion();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(5) != null)
				if (texto_propietario == null || texto_propietario instanceof Cisterna)
					texto_propietario = new Cisterna();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(6) != null)
				if (texto_propietario == null || texto_propietario instanceof Forzador) {
					texto_propietario = new Forzador();
					numero_equipo = obtenerIdEquipo(matcher.group(6));
				} else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(7) != null)
				if (texto_propietario == null || texto_propietario instanceof GrupoElectrogeno)
					texto_propietario = new GrupoElectrogeno();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(11) != null)
				if (texto_propietario == null || texto_propietario instanceof Plc)
					texto_propietario = new Plc();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(12) != null)
				if (texto_propietario == null || texto_propietario instanceof Pozo)
					texto_propietario = new Pozo();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(13) != null)
				if (texto_propietario == null || texto_propietario instanceof SCADA)
					texto_propietario = new SCADA();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(14) != null)
				if (texto_propietario == null || texto_propietario instanceof Tamiz) {
					texto_propietario = new Tamiz();
					numero_equipo = obtenerIdEquipo(matcher.group(14));
				} else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(15) != null)
				if (texto_propietario == null || texto_propietario instanceof TornilloCompactador)
					texto_propietario = new TornilloCompactador();
				else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);

			if (matcher.group(16) != null)
				if (texto_propietario == null || texto_propietario instanceof Valvula) {
					texto_propietario = new Valvula();
					numero_equipo = obtenerIdEquipo(matcher.group(16));
				} else
					throw new CampoTextoAmbiguoExcepcion(campo_texto);
		}
	}

	@Override
	public Object getPropietario() {
		return equipo_en_sitio;
	}

	private int obtenerIdEquipo(String group) {

		String[] split = group.split(" ");

		if (split.length == 1) { // si el numero de equipo viene pegado a la descripcion del equipo
			split[0] = new StringBuilder(split[0]).insert(split[0].length() - 1, " ").toString();
			split = split[0].split(" ");
		}

		return Integer.parseInt(split[1]);
	}
}

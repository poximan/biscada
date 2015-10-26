/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.sucesos;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ExpRegSuceso {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private String expresion_regular;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ExpRegSuceso() {

		expresion_regular = "" // expresion regular para identificar el tipo de suceso
				+ "" + // 1 grupo = gpo 1 a gpo 1
				AguaEnEstator.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 2 a gpo 2
				AltaTemperaturaBobinado.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 3 a gpo 3
				EvolucionLecturaAnalogica.getExpresion_regular()//
				+ "|" + // 11 grupos = gpo 4 a gpo 14
				GrupoElectrogenoFalla.getExpresion_regular()//
				+ "|" + // 11 grupos = gpo 15 a gpo 25
				GrupoElectrogenoMarcha.getExpresion_regular()//
				// + "|" + // 0 grupo = gpo - a gpo -
				// IncongruenciaEstado.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 26 a gpo 26
				IniciaSesion.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 27 a gpo 27
				InterruptorActuado.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 28 a gpo 28
				NivelAlto.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 29 a gpo 29
				NivelBajo.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 30 a gpo 30
				NivelRebalse.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 31 a gpo 31
				PerdidaComunicacion.getExpresion_regular()//
				+ "|" + // 4 grupos = gpo 32 a gpo 35
				RFFActuado.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 36 a gpo 36
				Robo.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 37 a gpo 37
				SCADABackupActivo.getExpresion_regular()//
				+ "|" + // 3 grupos = gpo 38 a gpo 40
				TermicoActuado.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 41 a gpo 41
				VibracionMotor.getExpresion_regular()//
		;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	public String getExpresion_regular() {
		return expresion_regular;
	}
}
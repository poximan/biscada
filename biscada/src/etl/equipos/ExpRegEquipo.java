/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.equipos;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ExpRegEquipo {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private String expresion_regular;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ExpRegEquipo() {

		expresion_regular = "" // expresion regular para identificar el equipo
								// involucrado
				+ "" + // 1 grupo = gpo 1 a gpo 1
				Bomba.getExpresion_regular()//
				+ "|" + // 3 grupo = gpo 2 a gpo 4
				CamaraAspiracion.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 5 a gpo 5
				Cisterna.getExpresion_regular()//
				+ "|" + // 1 grupos = gpo 6 a gpo 6
				Forzador.getExpresion_regular()//
				+ "|" + // 4 grupo = gpo 7 a gpo 10
				GrupoElectrogeno.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 11 a gpo 11
				Plc.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 12 a gpo 12
				Pozo.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 13 a gpo 13
				SCADA.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 14 a gpo 14
				Tamiz.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 15 a gpo 15
				TornilloCompactador.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 16 a gpo 16
				Valvula.getExpresion_regular()//
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
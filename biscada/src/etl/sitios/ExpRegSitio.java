/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.sitios;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ExpRegSitio {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private String expresion_regular;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ExpRegSitio() {

		expresion_regular = "" // expresion regular para identificar el sitio
				+ "" + // 1 grupo = gpo 1 a gpo 1
				CentralSCADA.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 2 a gpo 2
				CloacalEE1.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 3 a gpo 3
				CloacalEE2.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 4 a gpo 4
				CloacalEE3.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 5 a gpo 5
				CloacalEE4.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 6 a gpo 6
				CloacalEPN.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 7 a gpo 7
				CloacalEPS.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 8 a gpo 8
				Reserva6000.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 9 a gpo 9
				ReservaCota90.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 10 a gpo 10
				ReservaKM11.getExpresion_regular()//
				+ "|" + // 2 grupo = gpo 11 a gpo 12
				ReservaLomaMariaEST.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 13 a gpo 13
				ReservaLomaMariaREP.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 14 a gpo 14
				ReservaOeste.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 15 a gpo 15
				ReservaPlantaPotabilizadora.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 16 a gpo 16
				ReservaPujol.getExpresion_regular()//
				+ "|" + // 2 grupo = gpo 17 a gpo 18
				ReservaTomaRio.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 19 a gpo 19
				ReusoCamaraCarga.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 20 a gpo 20
				ReusoCota50.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 21 a gpo 21
				ReusoCota80.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 22 a gpo 22
				ReusoEE5.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 23 a gpo 23
				ReusoEE6.getExpresion_regular()//
				+ "|" + // 1 grupo = gpo 24 a gpo 24
				ReusoPTN.getExpresion_regular()//
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
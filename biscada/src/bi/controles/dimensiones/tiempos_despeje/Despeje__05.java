/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.dimensiones.tiempos_despeje;

import bi.modelo.TiempoDespeje;
import comunes.modelo.Alarma;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class Despeje__05 extends TiempoDespeje {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public Despeje__05() {

		super.setInicio(0);
		super.setFin(5);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public boolean contiene(Alarma alarma_actual) {

		try {
			int tiempo_transcurrido = diferenciaEntreFechas(alarma_actual);

			if (tiempo_transcurrido < getFin())
				return true;
		} catch (NullPointerException excepcion) {
			return false;
		}
		return false;
	}

	@Override
	public String toString() {
		return "0... 5 (min)";
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */

}
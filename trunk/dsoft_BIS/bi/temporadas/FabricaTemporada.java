/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package temporadas;

import modelo.Alarma;
import modelo.Temporada;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class FabricaTemporada {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private TemporadaVerano temporada_verano;
	private TemporadaOtonio temporada_otonio;
	private TemporadaInvierno temporada_invierno;
	private TemporadaPrimavera temporada_primavera;

	private TemporadaDesconocida temporada_desconocida;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public FabricaTemporada() {

		temporada_verano = new TemporadaVerano();
		temporada_otonio = new TemporadaOtonio();
		temporada_invierno = new TemporadaInvierno();
		temporada_primavera = new TemporadaPrimavera();

		temporada_desconocida = new TemporadaDesconocida();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public Temporada buscarRango(Alarma alarma_actual) {

		if (temporada_verano.contiene(alarma_actual))
			return temporada_verano;

		if (temporada_otonio.contiene(alarma_actual))
			return temporada_otonio;

		if (temporada_invierno.contiene(alarma_actual))
			return temporada_invierno;

		if (temporada_primavera.contiene(alarma_actual))
			return temporada_primavera;

		if (temporada_desconocida.contiene(alarma_actual))
			return temporada_desconocida;

		return null;
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
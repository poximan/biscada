/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.eventos;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

/**
 * normaliza la configuracion de eventos visuales. no se utilizo la interfaz
 * EventoConfigurable porque era necesario agregar un argumento adicional
 * 
 * @author hdonato
 * 
 */
public interface EventoConfigurable {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * agrega todos los eventos que sera escuchados en la vista que lo
	 * implementa
	 * 
	 * @param eventos
	 *            manejador de eventos al que le delega la responsabilidad de
	 *            ejecutar comportamiento cuando el evento sucede
	 */
	public void configEventos(EventoDim eventos);
}

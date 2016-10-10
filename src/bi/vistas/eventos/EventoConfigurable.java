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
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO una interfaz
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO normalizar la configuracion de eventos visuales
 * 
 * LO QUE CONOZCO todos los eventos que seran escuchados en la vista que lo implemente
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, EventoDim
 * 
 * COMO INTERACTUO CON MI COLABORADOR, 
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

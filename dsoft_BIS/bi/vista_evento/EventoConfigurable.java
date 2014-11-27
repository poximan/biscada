/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_evento;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

/**
 * normaliza la configuracion de eventos visuales. no se utilizo la interfaz EventoConfigurable porque era necesario
 * agregar un argumento adicional
 * 
 * @author hdonato
 * 
 */
public interface EventoConfigurable {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void configEventos(EventoDim eventos);
}

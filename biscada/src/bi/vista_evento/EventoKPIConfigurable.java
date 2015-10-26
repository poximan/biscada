/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vista_evento;

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
public interface EventoKPIConfigurable {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void configEventos(EventoKPI eventos);
}

/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.vistas;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

/**
 * interfaz estandar para todos los casos en donde sea necesario configurar eventos visuales. no aplica al caso
 * particular de configuracion de eventos en pantallas de dimension, porque alli es necesario un parametro adicional
 * 
 * @author hdonato
 * 
 */
public interface EventoConfigurable {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void configEventos();
}

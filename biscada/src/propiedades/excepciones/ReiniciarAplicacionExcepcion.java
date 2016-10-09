/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package propiedades.excepciones;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, un mensaje para solicitar el reinicio de la aplicacion
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, cuando propiedades.vistas.EventoPropiedades.analizarCambiosPU()
 * detecta que es necesario reiniciar la aplicacion, usa una instancia de mi
 * para notificar esto mas abajo en la pila de contextos de ejecucion
 * 
 * LO QUE CONOZCO,
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL,
 * 
 * COMO INTERACTUO CON MI COLABORADOR,
 *
 */
public class ReiniciarAplicacionExcepcion extends Exception {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	static final long serialVersionUID = 1;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ReiniciarAplicacionExcepcion() {
		super();
	}

	public ReiniciarAplicacionExcepcion(String descripcion_excepcion) {
		super(descripcion_excepcion);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */
}
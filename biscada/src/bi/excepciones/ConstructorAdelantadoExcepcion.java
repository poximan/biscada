package bi.excepciones;

public class ConstructorAdelantadoExcepcion extends Exception {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	static final long serialVersionUID = 1;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ConstructorAdelantadoExcepcion() {
		super();
	}

	public ConstructorAdelantadoExcepcion(String descripcion_excepcion) {
		super(descripcion_excepcion);
	}
}

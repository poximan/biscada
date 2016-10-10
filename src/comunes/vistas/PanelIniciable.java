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
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, una interfaz implementada unicamente por componentes visuales
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, genero instancias de todos los subcomponentes del componente que
 * estoy iniciando. tambien los ordeno en contenedores respetando algun layout
 * 
 * LO QUE CONOZCO, los subcomponentes que deben darse de alta
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, el propio panel que estoy iniciando
 * 
 * COMO INTERACTUO CON MI COLABORADOR, a traves de sus metodos heredados de
 * JPanel
 * 
 * @author hdonato
 *
 */
public interface PanelIniciable {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public abstract void iniciarComponentes();
}

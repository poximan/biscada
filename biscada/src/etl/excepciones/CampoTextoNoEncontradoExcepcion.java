/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.excepciones;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, la especializacion de una excepcion generica
 * 
 * soy de utilidad cuando no se encuentra ninguna coincidencia durante las
 * comparaciones por expresiones regulares. esto sucede en la etapa de
 * construccion de nuevas instancias, responsabilidad de las fabricas concretas
 * de Sitio, Suceso, etc.
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, soy lanzado al momento que se descubre mi excepcion
 * 
 * LO QUE CONOZCO, mi descripcion interna que será de utilidad para debug en
 * log.txt o por pantalla
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, es la herramienta de logs (log4j)
 * 
 * COMO INTERACTUO CON MI COLABORADOR, este atrapa mi instancia, pide mi
 * descripcion interna y la saca conjuntamente por pantalla y archivo log.txt.
 * posteriormente se puede usar esta info para debug.
 *
 * @author hdonato
 * 
 */
public class CampoTextoNoEncontradoExcepcion extends Exception {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	static final long serialVersionUID = 1;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public CampoTextoNoEncontradoExcepcion(String descripcion_excepcion) {
		super("no encontrado -> " + descripcion_excepcion);
	}
}
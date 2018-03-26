/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.comunes.controles;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, el polimorfismo de clases con contenido que debe borrarse
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, ofrezco una tratamiento polimorfico para clases diferentes
 * 
 * LO QUE CONOZCO, la secuencia de pasos para vaciar listas y llamar a gc()
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL,
 * 
 * COMO INTERACTUO CON MI COLABORADOR,
 *
 * @author hugo
 *
 */
public interface ObjetosBorrables {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void liberarObjetos();
}

/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package comunes.controles;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO,
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO,
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
/**
 * ciertas etapas de la adquisicion de informacion desde archivos dbf implican
 * gran cantidad de datos en memoria, que una vez procesados son innecesarios.
 * este interfaz debe ser implementada por toda clase que tenga objetos de gran
 * volumen y corta vida.
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

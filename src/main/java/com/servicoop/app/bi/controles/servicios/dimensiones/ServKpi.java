/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.controles.servicios.dimensiones;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, una interfaz aplicable a los servicios de indicadores de
 * rendimientos
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, establezco la forma estandar que deben tener las llamadas a
 * cuentras (valores acutales, promedio, etc), que usan los valores de la tabla,
 * que resulta de una consulta a BD
 * 
 * LO QUE CONOZCO,
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL,
 * 
 * COMO INTERACTUO CON MI COLABORADOR,
 *
 * @author hdonato
 * 
 */
public interface ServKpi {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public int actual();

	public double promedio();

	public double totalAlarmas();

}
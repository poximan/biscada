/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_IU;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

public interface DimensionCalculable {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * si el usuario selecciono algo para el segundo y tercer nivel de evaluacion (ver documento de vision
	 * "Segundo nivel de evaluación – Dimensiones", "Tercer nivel de evaluación - Mediciones") puede solicitar que se
	 * procese la informacion. esta interfaz brinda la forma normalizada de resolver la solicitud
	 * 
	 * la interfaz es implementada por todas las evaluaciones de segundo nivel
	 * 
	 */
	public void ejecutarDimension();

	public void armarSolapasGraficas();
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.dimensiones;

/* ............................................. */
/* ............................................. */
/* INTERFASE ................................... */
/* ............................................. */

public interface DimensionCalculable {

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void armarSolapasGraficas();

	/**
	 * si el usuario selecciono algo para el segundo y tercer nivel de
	 * evaluacion (ver documento de vision "Segundo nivel de evaluaci�n �
	 * Dimensiones", "Tercer nivel de evaluaci�n - Mediciones") puede
	 * solicitar que se procese la informacion. esta interfaz brinda la forma
	 * normalizada de resolver la solicitud
	 * 
	 * la interfaz es implementada por todas las evaluaciones de segundo nivel
	 * 
	 */
	public void ejecutarDimension();
}
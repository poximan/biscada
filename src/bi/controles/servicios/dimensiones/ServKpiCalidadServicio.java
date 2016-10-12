/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.servicios.dimensiones;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, la servicio para pantallas de indicadores de rendimiento.
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, realizo operaciones matematicas sobre la tabla que conozco.
 * 
 * LO QUE CONOZCO, la tabla de datos resultado de la consulta a BD
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, java.lang.Math
 * 
 * COMO INTERACTUO CON MI COLABORADOR, realiza los calculos de potencia y raiz
 * necesarios para obtener la desviacion estandar y la varianza
 *
 * @author hdonato
 * 
 */
public class ServKpiCalidadServicio implements ServKpi {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	public static String formatear(double valor) {
		NumberFormat formatter = new DecimalFormat("#0.00");
		return formatter.format(valor);
	}

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	private float datos[][];

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public ServKpiCalidadServicio(float[][] datos) {
		super();
		this.datos = datos;
	}

	@Override
	public int actual() {

		int resultado = 0;

		for (int ind_fila = 0; ind_fila < datos.length; ind_fila++)
			resultado += datos[ind_fila][datos[0].length - 1];

		return resultado;
	}

	private int celdasUtiles() {

		int celdas_utiles = 0;

		for (int fila = 0; fila < datos.length; fila++)
			for (int columna = 0; columna < datos[fila].length; columna++)
				if (esCeldaUtil(fila, columna))
					celdas_utiles++;

		return celdas_utiles;
	}

	private boolean esCeldaUtil(int fila, int columna) {
		return datos[fila][columna] > 0;
	}

	public double desviacionEstandar() {
		return Math.sqrt(varianza());
	}

	public int maximo() {

		int maximo = 0;

		for (int fila = 0; fila < datos.length; fila++)
			for (int columna = 0; columna < datos[fila].length; columna++)
				if (datos[fila][columna] > maximo)
					maximo = (int) datos[fila][columna];

		return maximo;
	}

	public int minimo() {

		int minimo = maximo();

		for (int fila = 0; fila < datos.length; fila++)
			for (int columna = 0; columna < datos[fila].length; columna++)
				if (datos[fila][columna] < minimo && datos[fila][columna] >= 0)
					minimo = (int) datos[fila][columna];

		return minimo;
	}

	@Override
	public double promedio() {
		return totalAlarmas() / celdasUtiles();
	}

	@Override
	public double totalAlarmas() {

		double resultado = 0;

		for (int fila = 0; fila < datos.length; fila++)
			for (int columna = 0; columna < datos[fila].length; columna++)
				resultado += datos[fila][columna];

		return resultado;
	}

	/**
	 * la media de las diferencias con la media, elevadas al cuadrado.
	 */
	public double varianza() {

		int indice_diferencias = 0;
		double promedio = promedio();
		double varianza = 0;

		double[] cuadrado_dif_respecto_promedio = new double[celdasUtiles()];

		/*
		 * primero se obtiene la diferencia de cada lectura respecto a la media.
		 * luego se eleva al cuadrado.
		 */
		for (int fila = 0; fila < datos.length; fila++)
			for (int columna = 0; columna < datos[fila].length; columna++)
				if (esCeldaUtil(fila, columna)) {

					double dif_respecto_promedio = promedio - datos[fila][columna];
					cuadrado_dif_respecto_promedio[indice_diferencias++] = Math.pow(dif_respecto_promedio, 2);
				}

		for (int columna = 0; columna < cuadrado_dif_respecto_promedio.length; columna++)
			varianza += cuadrado_dif_respecto_promedio[columna];

		return varianza / cuadrado_dif_respecto_promedio.length;
	}
}
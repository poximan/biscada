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

public class ServKpiCalidadServicio implements ServKpi {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private float datos[][];

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServKpiCalidadServicio(float[][] datos) {
		super();
		this.datos = datos;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public int actual() {

		int resultado = 0;

		for (int ind_fila = 0; ind_fila < datos.length; ind_fila++)
			resultado += datos[ind_fila][datos[0].length - 1];

		return resultado;
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
				if (datos[fila][columna] < minimo && datos[fila][columna] > 0)
					minimo = (int) datos[fila][columna];

		return minimo;
	}

	@Override
	public int totalAlarmas() {

		int resultado = 0;

		for (int fila = 0; fila < datos.length; fila++)
			for (int columna = 0; columna < datos[fila].length; columna++)
				resultado += datos[fila][columna];

		return resultado;
	}

	@Override
	public float promedio() {
		return totalAlarmas() / celdasUtiles();
	}

	public double varianza() {

		int indice_diferencias = 0;
		float promedio = promedio();
		float varianza = 0;

		double[] cuadrado_dif_respecto_promedio = new double[celdasUtiles()];

		/*
		 * primero se obtiene la diferencia de cada lectura respecto a la media.
		 * luego se eleva al cuadrado.
		 */
		for (int fila = 0; fila < datos.length; fila++)
			for (int columna = 0; columna < datos[fila].length; columna++)
				if (datos[fila][columna] > 0) {

					double dif_respecto_promedio = promedio - datos[fila][columna];
					cuadrado_dif_respecto_promedio[indice_diferencias++] = Math.pow(dif_respecto_promedio, 2);
				}

		for (int columna = 0; columna < cuadrado_dif_respecto_promedio.length; columna++)
			varianza += cuadrado_dif_respecto_promedio[columna];

		return varianza / cuadrado_dif_respecto_promedio.length;
	}

	public double desviacionEstandar() {
		return Math.sqrt(varianza());
	}

	private int celdasUtiles() {

		int celdas_utiles = 0;

		for (int fila = 0; fila < datos.length; fila++)
			for (int columna = 0; columna < datos[fila].length; columna++)
				if (datos[fila][columna] >= 0)
					celdas_utiles++;

		return celdas_utiles;
	}

	public static String formatear(double valor) {
		NumberFormat formatter = new DecimalFormat("#0.00");
		return formatter.format(valor);
	}
}
/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.controles.servicios.dimensiones;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bi.controles.servicios.mediciones.ServMedAbstract;
import bi.controles.servicios.periodos.ServPeriodoAbstract;
import comunes.modelo.Alarma;
import comunes.modelo.Sitio;
import etl.partes_alarma.familias.Cloacal;
import etl.partes_alarma.familias.Potable;
import etl.partes_alarma.familias.Reuso;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServKpiCalidadServicio implements ServKpi {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private float arregloVarianza[];
	
	private float varianza;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public float actualFilaMultiple(float[][] datos) {

		float[] resultado = sumarFilasAgruparEnColumna(datos);
		return resultado[resultado.length - 1];
	}

	public float actualFilaSimple(ServDimSitio serv_dim_sitio, ServPeriodoAbstract serv_periodo,
			ServMedAbstract serv_medicion, Sitio sitio_actual) {

		List<Alarma> lista_interes = new ArrayList<Alarma>();

		// iterar el set de alarmas de la consulta, pero agrupadas por Sitio
		for (Map.Entry<Sitio, List<Alarma>> hash_alarmas_sitio_interes : serv_dim_sitio.getMap().entrySet())

			// aceptar solo la clase que coincide con el sitio referenciado
			// mediante drill down
			if (hash_alarmas_sitio_interes.getKey().equals(sitio_actual)) {

				List<Alarma> alarmas_sitio_interes = hash_alarmas_sitio_interes.getValue();

				for (Alarma alarmas_nivel : alarmas_sitio_interes)
					if (esDeInteres(alarmas_nivel))
						lista_interes.add(alarmas_nivel);
			}
		return serv_periodo.ultimaFraccion(lista_interes);
	}

	// Se realiza el c�lculo del m�ximo
	public float calculo_maximo() {
		float maximo = 0;

		for (int i = 0; i < arregloVarianza.length; i++) {
			if (arregloVarianza[i] > maximo) {
				maximo = arregloVarianza[i];
			}
		}

		return maximo;
	}

	// Se realiza el c�lculo del m�nimo
	public float calculo_minimo() {
		float minimo = arregloVarianza[0];

		// Nota: El indice comienza en uno pues es inutil volver a comparar el
		// primer valor con si mismo.
		for (int i = 1; i < arregloVarianza.length; i++) {
			if (arregloVarianza[i] < minimo) {
				minimo = arregloVarianza[i];
			}
		}

		return minimo;
	}

	// Se realiza el c�lculo de la desviaci�n Estandar
	public float desviacionEstandar() {
		float desvEstandar = (float) Math.sqrt(varianza);

		return desvEstandar;
	}

	private boolean esDeInteres(Alarma alarmas_nivel) {

		if (alarmas_nivel.getFamilia().getDescripcion().equals((new Potable()).toString()))
			return true;

		if (alarmas_nivel.getFamilia().getDescripcion().equals((new Cloacal()).toString())
				|| alarmas_nivel.getFamilia().getDescripcion().equals((new Reuso()).toString()))
			return true;

		return false;
	}

	private float promediar(float[] arreglo_valores) {

		float total = 0;

		for (float valor : arreglo_valores)
			total += valor;

		return total / arreglo_valores.length;
	}

	@Override
	public float promedioFilaMultiple(float[][] datos) {

		return totalFilaMultiple(datos) / (datos.length + datos[0].length);
	}

	public float promedioFilaSimple(ServDimSitio serv_dim_sitio, ServPeriodoAbstract serv_periodo,
			ServMedAbstract serv_medicion, Sitio sitio_actual) {

		float[] arreglo_valores;
		List<Alarma> lista_interes = new ArrayList<Alarma>();

		// iterar el set de alarmas de la consulta, pero agrupadas por Sitio
		for (Map.Entry<Sitio, List<Alarma>> hash_alarmas_sitio_interes : serv_dim_sitio.getMap().entrySet())

			// aceptar solo la clase que coincide con el sitio referenciado
			// mediante drill down
			if (hash_alarmas_sitio_interes.getKey().equals(sitio_actual)) {

				List<Alarma> alarmas_sitio_interes = hash_alarmas_sitio_interes.getValue();

				for (Alarma alarmas_nivel : alarmas_sitio_interes)
					if (esDeInteres(alarmas_nivel))
						lista_interes.add(alarmas_nivel);
			}

		try {
			arreglo_valores = serv_medicion.completarFila(lista_interes, serv_periodo);
		} catch (IndexOutOfBoundsException excepcion) {
			return 0;
		}

		arregloVarianza = arreglo_valores;

		return promediar(arreglo_valores);
	}

	@Override
	public float totalFilaMultiple(float[][] datos) {

		float resultado = 0;
		float[] columna_con_filas_sumadas = sumarFilasAgruparEnColumna(datos);

		for (int ind_columna = 1; ind_columna < columna_con_filas_sumadas.length; ind_columna++)
			resultado += columna_con_filas_sumadas[ind_columna];

		return resultado;
	}

	private float[] sumarFilasAgruparEnColumna(float[][] datos) {

		float[] columna_con_filas_sumadas = new float[datos[0].length];

		for (int ind_columna = 0; ind_columna < columna_con_filas_sumadas.length; ind_columna++)
			for (int ind_fila = 0; ind_fila < datos[0].length; ind_fila++)
				columna_con_filas_sumadas[ind_columna] += datos[ind_fila][ind_columna];

		return columna_con_filas_sumadas;
	}

	public float totalFilaSimple(ServDimSitio serv_dim_sitio, Sitio sitio_actual) {

		List<Alarma> lista_interes = new ArrayList<Alarma>();

		// iterar el set de alarmas de la consulta, pero agrupadas por Sitio
		for (Map.Entry<Sitio, List<Alarma>> hash_alarmas_sitio_interes : serv_dim_sitio.getMap().entrySet())

			// aceptar solo la clase que coincide con el sitio referenciado
			// mediante drill down
			if (hash_alarmas_sitio_interes.getKey().equals(sitio_actual)) {

				List<Alarma> alarmas_sitio_interes = hash_alarmas_sitio_interes.getValue();

				for (Alarma alarmas_nivel : alarmas_sitio_interes)
					if (esDeInteres(alarmas_nivel))
						lista_interes.add(alarmas_nivel);
			}
		return lista_interes.size();
	}

	// Se realiza el c�lculo de la varianza.
	public float Varianza() {

		varianza = 0;
		float divisor = arregloVarianza.length;
		float promedioCuadrado = 0;

		for (int i = 0; i < arregloVarianza.length; i++)

			varianza = (float) (varianza + Math.pow(arregloVarianza[i], 2));

		promedioCuadrado = (float) Math.pow(promediar(arregloVarianza), 2);

		varianza = (varianza / divisor) - promedioCuadrado;

		return varianza;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
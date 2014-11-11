/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_dimensiones;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modelo.Alarma;
import modelo.Sitio;
import control_general.ServIntervaloFechas;
import control_mediciones.ServMedAbstract;
import familias.Cloacal;
import familias.Potable;
import familias.Reuso;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServKpiCalidadServicio implements ServKpi {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private int columna_mayor;
	private float arregloVarianza[];

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServKpiCalidadServicio() {
		columna_mayor = 1;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public float actualFilaSimple(ServDimSitio serv_dim_sitio, ServDimUnidadTiempoAbstract serv_unidad_tiempo,
			ServMedAbstract serv_medicion, Sitio sitio_actual) {

		List<Alarma> lista_interes = new ArrayList<Alarma>();

		// iterar el set de alarmas de la consulta, pero agrupadas por Sitio
		for (Map.Entry<Sitio, List<Alarma>> hash_alarmas_sitio_interes : serv_dim_sitio.getMap().entrySet())

			// aceptar solo la clase que coincide con el sitio referenciado mediante drill down
			if (hash_alarmas_sitio_interes.getKey().equals(sitio_actual)) {

				List<Alarma> alarmas_sitio_interes = hash_alarmas_sitio_interes.getValue();

				for (Alarma alarmas_nivel : alarmas_sitio_interes)
					if (esDeInteres(alarmas_nivel))
						lista_interes.add(alarmas_nivel);
			}
		return serv_unidad_tiempo.ultimaFraccion(lista_interes);
	}

	@Override
	public float actualFilaMultiple(float[][] datos) {
		return 0;
	}

	public float promedioFilaSimple(ServDimSitio serv_dim_sitio, ServDimUnidadTiempoAbstract serv_unidad_tiempo,
			ServMedAbstract serv_medicion, ServIntervaloFechas servIntervaloFechas, Sitio sitio_actual) {

		float[] arreglo_valores;
		List<Alarma> lista_interes = new ArrayList<Alarma>();

		// iterar el set de alarmas de la consulta, pero agrupadas por Sitio
		for (Map.Entry<Sitio, List<Alarma>> hash_alarmas_sitio_interes : serv_dim_sitio.getMap().entrySet())

			// aceptar solo la clase que coincide con el sitio referenciado mediante drill down
			if (hash_alarmas_sitio_interes.getKey().equals(sitio_actual)) {

				List<Alarma> alarmas_sitio_interes = hash_alarmas_sitio_interes.getValue();

				for (Alarma alarmas_nivel : alarmas_sitio_interes)
					if (esDeInteres(alarmas_nivel))
						lista_interes.add(alarmas_nivel);
			}

		try {
			arreglo_valores = serv_medicion.completarFila(lista_interes, servIntervaloFechas, serv_unidad_tiempo);
		}
		catch (IndexOutOfBoundsException excepcion) {
			return 0;
		}
		
		arregloVarianza = arreglo_valores;
		
		return promediar(arreglo_valores);
	}
	
	public float Varianza(){
		
		float vari = 0;
		float divisor = arregloVarianza.length;
		float promedioCuadrado = 0;
		
		for(int i = 0; i < arregloVarianza.length; i++){
			System.out.print("el cuadrado de " + arregloVarianza[i]);
			vari = (float) (vari + Math.pow(arregloVarianza[i], 2));
			System.out.println( " es " + vari);
		}
		
		System.out.println("cuadrado es " + vari + " divisor " + divisor);
		
		promedioCuadrado = (float) Math.pow(promediar(arregloVarianza), 2);		
		System.out.println("el promedio al 2 es " + promedioCuadrado);
		vari = (vari - promedioCuadrado)/ divisor;
		System.out.println("a enviar " + vari);
		return vari;
	}


	private float promediar(float[] arreglo_valores) {

		float total = 0;

		for (float valor : arreglo_valores)
			total += valor;

		return total / arreglo_valores.length;
	}

	@Override
	public float promedioFilaMultiple(float[][] datos) {

		return totalFilaMultiple(datos) / ((datos.length - 1) * columna_mayor);
	}

	public float totalFilaSimple(ServDimSitio serv_dim_sitio, Sitio sitio_actual) {

		List<Alarma> lista_interes = new ArrayList<Alarma>();

		// iterar el set de alarmas de la consulta, pero agrupadas por Sitio
		for (Map.Entry<Sitio, List<Alarma>> hash_alarmas_sitio_interes : serv_dim_sitio.getMap().entrySet())

			// aceptar solo la clase que coincide con el sitio referenciado mediante drill down
			if (hash_alarmas_sitio_interes.getKey().equals(sitio_actual)) {

				List<Alarma> alarmas_sitio_interes = hash_alarmas_sitio_interes.getValue();

				for (Alarma alarmas_nivel : alarmas_sitio_interes)
					if (esDeInteres(alarmas_nivel))
						lista_interes.add(alarmas_nivel);
			}
		return lista_interes.size();
	}

	@Override
	public float totalFilaMultiple(float[][] datos) {

		float[] columna_con_filas_sumadas = resumirEnFilaUnica(datos);
		return sumarFilasUnaColumna(columna_con_filas_sumadas);
	}

	private boolean esDeInteres(Alarma alarmas_nivel) {

		if (alarmas_nivel.getFamilia().getDescripcion().equals((new Potable()).toString()))
			return true;

		if (alarmas_nivel.getFamilia().getDescripcion().equals((new Cloacal()).toString())
				|| alarmas_nivel.getFamilia().getDescripcion().equals((new Reuso()).toString()))
			return true;

		return false;
	}

	private float[] resumirEnFilaUnica(float[][] datos) {

		try {
			float[] columna_con_filas_sumadas = new float[datos.length - 1];

			for (int ind_fila = 0; ind_fila < datos.length - 1; ind_fila++) {
				columna_con_filas_sumadas[ind_fila] = sumarFilasUnaColumna(datos[ind_fila]);

				if (datos[ind_fila].length - 1 > columna_mayor)
					columna_mayor = datos[ind_fila].length - 1;
			}

			return columna_con_filas_sumadas;
		}
		catch (NegativeArraySizeException excepcion) {
			return new float[1];
		}
	}

	private float sumarFilasUnaColumna(float[] columnas_de_fila_unica) {

		float total = 0;

		for (float fila_actual : columnas_de_fila_unica)
			total += fila_actual;
		return total;
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
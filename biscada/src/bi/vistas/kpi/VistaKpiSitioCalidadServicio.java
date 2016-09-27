/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.kpi;

import java.util.Arrays;

import bi.controles.servicios.dimensiones.ServKpiCalidadServicio;
import bi.vistas.eventos.EventoKPI;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class VistaKpiSitioCalidadServicio extends VistaKpiAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/**
	 * 
	 * @param datos
	 */
	public VistaKpiSitioCalidadServicio(float datos[][]) {

		super.configEventos(new EventoKPI(this));
		calculosComunes(datos);
	}

	/**
	 * utilizado cuando se selecciona una fila de sitio desde esa dimension. en
	 * otras dimensiones este constructor no tiene utilidad.
	 * 
	 * @param serv_dim_sitio
	 * @param serv_periodo
	 * @param serv_medicion
	 * @param sitio_actual
	 * @param filas_datos
	 */
	public VistaKpiSitioCalidadServicio(float[] filas_datos) {

		super.configEventos(new EventoKPI(this));

		float[][] matriz_datos = convertir(filas_datos);

		calculosComunes(matriz_datos);
	}

	/**
	 * convierte a dos dimensiones para tratarlo los datos de la misma forma
	 * 
	 * @param filas_datos
	 * @return
	 */
	private float[][] convertir(float[] filas_datos) {

		float[][] matriz_datos = new float[1][filas_datos.length];
		matriz_datos[0] = Arrays.copyOf(filas_datos, filas_datos.length);
		return matriz_datos;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	private void calculosComunes(float datos[][]) {

		ServKpiCalidadServicio serv_kpi_calidad_servicio = new ServKpiCalidadServicio(datos);

		getTxtMaximo().setText(String.valueOf(serv_kpi_calidad_servicio.maximo()));
		getTxtMinimo().setText(String.valueOf(serv_kpi_calidad_servicio.minimo()));

		getTxtTotal().setText(String.valueOf(serv_kpi_calidad_servicio.totalAlarmas()));
		getTxtPromedio().setText(ServKpiCalidadServicio.formatear(serv_kpi_calidad_servicio.promedio()));

		getTxtVarianza().setText(ServKpiCalidadServicio.formatear(serv_kpi_calidad_servicio.varianza()));
		getTxtD_estandar().setText(ServKpiCalidadServicio.formatear(serv_kpi_calidad_servicio.desviacionEstandar()));

		getTxtActual().setText(String.valueOf(serv_kpi_calidad_servicio.actual()));
	}
}
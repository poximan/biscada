/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vista_IU;

import bi.controles.dimensiones.ServDimSitio;
import bi.controles.dimensiones.ServDimUnidadTiempoAbstract;
import bi.controles.dimensiones.ServIntervaloFechas;
import bi.controles.dimensiones.ServKpiCalidadServicio;
import bi.controles.mediciones.ServMedAbstract;
import bi.vista_evento.EventoKPI;
import comunes.modelo.Sitio;

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
	 * @wbp.parser.constructor
	 */
	public VistaKpiSitioCalidadServicio(float datos[][]) {

		super.configEventos(new EventoKPI(this));
		llenarCampoTextos(datos);
	}

	public VistaKpiSitioCalidadServicio(ServDimSitio serv_dim_sitio, ServDimUnidadTiempoAbstract serv_unidad_tiempo,
			ServMedAbstract serv_medicion, ServIntervaloFechas servIntervaloFechas, Sitio sitio_actual,
			float[] datosH) {

		super.configEventos(new EventoKPI(this));

		try {
			llenarCampoTextos(serv_dim_sitio, serv_unidad_tiempo, serv_medicion, servIntervaloFechas, sitio_actual);

			getIndicador_kpi().cargarDatos(Float.parseFloat(getTxtTotal().getText()),
					Float.parseFloat(getTxtPromedio().getText()), Float.parseFloat(getTxtActual().getText()));
			getIndicador_kpi().createPanel();

			getHisto_kpi().cargarDatos(serv_unidad_tiempo.getEncabezadoFecha(), datosH,
					Float.parseFloat(getTxtTotal().getText()), Float.parseFloat(getTxtPromedio().getText()));
			getHisto_kpi().createPanel();

		} catch (NumberFormatException excepcion) {
			getIndicador_kpi().cargarDatos(0, 0, 0);
		} catch (NullPointerException excepcion) {
			notificarError("tabla vacia, presione Ejecutar para llenar la tabla");
		}
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	private void llenarCampoTextos(float[][] datos) {

		ServKpiCalidadServicio serv_kpi_calidad_servicio = new ServKpiCalidadServicio();

		getTxtTotal().setText(String.valueOf(serv_kpi_calidad_servicio.totalFilaMultiple(datos)));
		getTxtPromedio().setText(String.valueOf(serv_kpi_calidad_servicio.promedioFilaMultiple(datos)));
		getTxtActual().setText(String.valueOf(serv_kpi_calidad_servicio.actualFilaMultiple(datos)));
	}

	private void llenarCampoTextos(ServDimSitio serv_dim_sitio, ServDimUnidadTiempoAbstract serv_unidad_tiempo,
			ServMedAbstract serv_medicion, ServIntervaloFechas servIntervaloFechas, Sitio sitio_actual) {

		ServKpiCalidadServicio serv_kpi_calidad_servicio = new ServKpiCalidadServicio();

		getTxtTotal().setText(String.valueOf(serv_kpi_calidad_servicio.totalFilaSimple(serv_dim_sitio, sitio_actual)));
		getTxtPromedio().setText(String.valueOf(serv_kpi_calidad_servicio.promedioFilaSimple(serv_dim_sitio,
				serv_unidad_tiempo, serv_medicion, servIntervaloFechas, sitio_actual)));
		getTxtActual().setText(String.valueOf(serv_kpi_calidad_servicio.actualFilaSimple(serv_dim_sitio,
				serv_unidad_tiempo, serv_medicion, sitio_actual)));
		getTextFieldVarianza().setText(String.valueOf(serv_kpi_calidad_servicio.Varianza()));
		getTextFieldDesvEstandar().setText(String.valueOf(serv_kpi_calidad_servicio.desviacionEstandar()));

		getTextFieldMaximo().setText(String.valueOf(serv_kpi_calidad_servicio.calculo_maximo()));
		getTextFieldMinimo().setText(String.valueOf(serv_kpi_calidad_servicio.calculo_minimo()));

	}
}
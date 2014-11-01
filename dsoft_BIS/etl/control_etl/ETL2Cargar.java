/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package control_etl;

import java.util.List;

import modelo.Alarma;
import modelo.ArchivoDBF;
import control_CRUDs.ServCRUDAlarma;
import control_CRUDs.ServCRUDArchivoDBF;
import control_CRUDs.ServCRUDEquipoEnSitio;
import control_CRUDs.ServCRUDFamilia;
import control_CRUDs.ServCRUDSitio;
import control_CRUDs.ServCRUDSuceso;
import control_CRUDs.ServCRUDTipoDeEquipo;
import control_general.ObjetosBorrables;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Es la ultima fase del proceso ETL, e implica cargar los datos en el sistema de destino. Al realizar esta operación se
 * aplicarán todas las restricciones y triggers que se hayan definido (valores únicos, integridad referencial, campos
 * obligatorios, rangos de valores). Estas restricciones y triggers contribuyen a garantizar la calidad de los datos.
 * 
 * @author hugo
 * 
 */
public class ETL2Cargar implements ObjetosBorrables {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private List<Alarma> alarmas_transformadas;

	private ServCRUDArchivoDBF dbf_servicio_crud;

	private ServCRUDFamilia familia_servicios_crud;
	private ServCRUDSitio sitio_servicios_crud;
	private ServCRUDSuceso suceso_servicios_crud;
	private ServCRUDTipoDeEquipo tipo_de_equipo_servicios_crud;
	private ServCRUDEquipoEnSitio equipo_en_sitio_servicios_crud;

	private ServCRUDAlarma alarma_servicios_crud;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ETL2Cargar(List<Alarma> alarmas_transformadas, ServCRUDArchivoDBF dbf_servicio_crud) {

		this.alarmas_transformadas = alarmas_transformadas;

		this.dbf_servicio_crud = dbf_servicio_crud;
		familia_servicios_crud = new ServCRUDFamilia();
		sitio_servicios_crud = new ServCRUDSitio();
		equipo_en_sitio_servicios_crud = new ServCRUDEquipoEnSitio();
		tipo_de_equipo_servicios_crud = new ServCRUDTipoDeEquipo();
		suceso_servicios_crud = new ServCRUDSuceso();

		alarma_servicios_crud = new ServCRUDAlarma();
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void cargarAlarmas(ArchivoDBF archivo_propietario) {

		for (Alarma alarma_actual : alarmas_transformadas) {

			dbf_servicio_crud.buscarEnMemoriaPrimaria(alarma_actual);
			familia_servicios_crud.buscarEnMemoriaPrimaria(alarma_actual);
			sitio_servicios_crud.buscarEnMemoriaPrimaria(alarma_actual);
			suceso_servicios_crud.buscarEnMemoriaPrimaria(alarma_actual);

			if (alarma_actual.getEquipo_en_sitio() != null) {
				tipo_de_equipo_servicios_crud.buscarEnMemoriaPrimaria(alarma_actual);
				equipo_en_sitio_servicios_crud.buscarEnMemoriaPrimaria(alarma_actual);
			}

			alarma_servicios_crud.crear(alarma_actual);
		}
		liberarObjetos();
	}

	@Override
	public void liberarObjetos() {

		alarmas_transformadas.clear();
		System.gc();
	}

	public void rechazarArchivo(ArchivoDBF archivo_actual) {
		archivo_actual.setValido(false);
		dbf_servicio_crud.crear(archivo_actual);
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */
}

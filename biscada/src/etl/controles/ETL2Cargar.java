/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles;

import java.util.List;

import comunes.controles.ObjetosBorrables;
import comunes.modelo.Alarma;
import comunes.modelo.ArchivoDBF;
import etl.controles.cruds.ServCRUDAlarma;
import etl.controles.cruds.ServCRUDArchivoDBF;
import etl.controles.cruds.ServCRUDEquipoEnSitio;
import etl.controles.cruds.ServCRUDFamilia;
import etl.controles.cruds.ServCRUDSitio;
import etl.controles.cruds.ServCRUDSuceso;
import etl.controles.cruds.ServCRUDTipoDeEquipo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO,
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO,
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
/**
 * Es la ultima fase del proceso ETL, e implica cargar los datos en el sistema
 * de destino. Al realizar esta operaci�n se aplicar�n todas las
 * restricciones y triggers que se hayan definido (valores �nicos, integridad
 * referencial, campos obligatorios, rangos de valores). Estas restricciones y
 * triggers contribuyen a garantizar la calidad de los datos.
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

	public void cargarAlarmasAceptadas() {

		/*
		 * solo importa el dbf propietario de una alarma las demas tendran el
		 * mismo dbf
		 */
		dbf_servicio_crud.buscarEnMemoriaPrimaria(alarmas_transformadas.get(0));
		ArchivoDBF archivo = alarmas_transformadas.get(0).getArchivo_propietario();

		for (Alarma alarma_actual : alarmas_transformadas) {

			alarma_actual.setArchivo_propietario(archivo);

			familia_servicios_crud.buscarEnMemoriaPrimaria(alarma_actual);
			sitio_servicios_crud.buscarEnMemoriaPrimaria(alarma_actual);
			suceso_servicios_crud.buscarEnMemoriaPrimaria(alarma_actual);
			tipo_de_equipo_servicios_crud.buscarEnMemoriaPrimaria(alarma_actual);
			equipo_en_sitio_servicios_crud.buscarEnMemoriaPrimaria(alarma_actual);
			alarma_servicios_crud.crear(alarma_actual);
		}
	}

	@Override
	public void liberarObjetos() {
		alarmas_transformadas.clear();
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

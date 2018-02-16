/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles;

import java.util.List;

import comunes.controles.ObjetosBorrables;
import comunes.entidades.Alarma;
import comunes.entidades.ArchivoDBF;
import etl.controles.servicios.cruds.ServCRUDAlarma;
import etl.controles.servicios.cruds.ServCRUDArchivoDBF;
import etl.controles.servicios.cruds.ServCRUDEquipoEnSitio;
import etl.controles.servicios.cruds.ServCRUDFamilia;
import etl.controles.servicios.cruds.ServCRUDSitio;
import etl.controles.servicios.cruds.ServCRUDSuceso;
import etl.controles.servicios.cruds.ServCRUDTipoDeEquipo;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, al servicio que realiza la tercer fase del proceso ETL. la
 * carga de los datos en la base de datos destino.
 * 
 * mi tiempo de vida es el necesario para cargar en la BD los datos de un unico
 * archivo dbf. multiples archivos necesitaran multiples instancias de mi.
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, interactuo con la base de datos de destino. Al hacerlo aplico
 * restricciones como valores �nicos (no repetir informacion que ya existe en la
 * BD) e integridad referencial.
 * 
 * LO QUE CONOZCO, la lista del tipo Alarma que debo cargar en la BD, y los
 * servicios CRUD de todos los POJO's que componen una Alarma.
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, es etl.controles.cruds.ServCRUDAlarma
 * 
 * COMO INTERACTUO CON MI COLABORADOR, me ofrece un metodo capaz de insertar una
 * nueva fila de Alarma en la base de datos.
 * 
 * @author hdonato
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
		 * solo importa el dbf propietario de una alarma las demas tendran el mismo dbf
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

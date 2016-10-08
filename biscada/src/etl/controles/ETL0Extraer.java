/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import comunes.entidades.ArchivoDBF;
import etl.entidades.ArchAlarma;
import nl.knaw.dans.common.dbflib.CorruptedTableException;
import nl.knaw.dans.common.dbflib.DbfLibException;
import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.IfNonExistent;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;
import nl.knaw.dans.common.dbflib.ValueTooLargeException;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, al servicio que realiza la primer fase del proceso ETL. la
 * extraccion de los datos desde el archivo fuente.
 * 
 * mi tiempo de vida es el necesario para extrer datos un unico archivo dbf.
 * multiples archivos necesitaran multiples instancias de mi.
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, extraigo los datos desde el archivo dbf origen. luego los
 * convierto al tipo etl.modelo.ArchAlarma. La extracción no genera impacto en
 * el archivo origen.
 * 
 * LO QUE CONOZCO, el nombre y ruta del archivo dbf que debo procesar
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, la biblioteca externa dans_dbf para procesar
 * archivos xBase
 * 
 * COMO INTERACTUO CON MI COLABORADOR, dans_dbf me entrega los nombres de los
 * campos y el total de registros. itero los registros y sobre ellos los campos
 * que lo componen. por cada campo usando refleccion defino los valores (metodo
 * separarCampos(Record)).
 * 
 * @author hdonato
 *
 */
public class ETL0Extraer {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ETL0Extraer.class);

	private ArchivoDBF archivo_actual;

	private Table table;

	private List<Field> fields;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ETL0Extraer(ArchivoDBF archivo_actual) {

		super();
		this.archivo_actual = archivo_actual;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public void cerrarArchivo() {

		try {
			table.close();
		} catch (IOException ex) {
			log.error("ERROR: no se puede cerrar la tabla asociada al archivo");
		}
	}

	public List<ArchAlarma> getAlarmas() {

		table = new Table(new File(archivo_actual.getRuta()));
		List<ArchAlarma> alarmas_extraidas = new ArrayList<ArchAlarma>();

		try {
			table.open(IfNonExistent.ERROR);

		} catch (CorruptedTableException e) {

			log.error("ERROR: tabla no se puede leer");
			e.printStackTrace();
		} catch (IOException e) {

			log.error("ERROR: tabla no encontrada");
			e.printStackTrace();
		}

		fields = table.getFields();

		Iterator<Record> recordIterator = table.recordIterator();

		while (recordIterator.hasNext()) {

			Record record = recordIterator.next();
			alarmas_extraidas.add(separarCampos(record));
		}

		return alarmas_extraidas;
	}

	private ArchAlarma separarCampos(Record record) {

		ArchAlarma alarma_actual = new ArchAlarma();

		for (final Field field : fields) {
			try {

				byte[] rawValue = record.getRawValue(field);

				String str = (rawValue == null ? null : new String(rawValue, StandardCharsets.UTF_8));

				Method metodo = null;

				try {
					metodo = alarma_actual.getClass().getMethod("set" + field.getName(), String.class);

				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
				try {
					metodo.invoke(alarma_actual, str);

				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

			} catch (ValueTooLargeException vtle) {

				log.error("ERROR: problema desconocido en la biblioteca \"dans-dbf-lib-1.0.0-beta-10.jar\"");
			} catch (DbfLibException e) {

				log.error("ERROR: no se puede leer fila");
				e.printStackTrace();
			}
		}
		return alarma_actual;
	}
}

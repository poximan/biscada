/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package etl.controles.dbf;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.log4j.Logger;

import nl.knaw.dans.common.dbflib.DbfLibException;
import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.ValueTooLargeException;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class ServParser {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static Logger log = Logger.getLogger(ServParser.class);

	private ArchAlarma alarma_actual;

	private Record record;

	private List<Field> fields;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public ServParser(Record record, List<Field> fields) {

		this.record = record;
		this.fields = fields;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public ArchAlarma separarCampos() {

		alarma_actual = new ArchAlarma();

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

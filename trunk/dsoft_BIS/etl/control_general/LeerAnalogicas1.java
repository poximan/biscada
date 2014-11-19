package control_general;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.PropertyConfigurator;

import ru.smartflex.tools.dbf.DbfEngine;
import ru.smartflex.tools.dbf.DbfIterator;
import ru.smartflex.tools.dbf.DbfRecord;

public class LeerAnalogicas1 {

	private static void testRead() {

		InputStream entrada = LeerAnalogicas1.class
				.getResourceAsStream("D:\\hugo\\proyectos\\uni\\desarrollo_soft\\datos\\Histo_medidas\\AL010108.DBF");

		DbfIterator dbfIterator = DbfEngine.getReader(entrada, null);

		while (dbfIterator.hasMoreRecords()) {

			DbfRecord dbfRecord = dbfIterator.nextRecord();

			String string = dbfRecord.getString("string");

			float sumFloat = dbfRecord.getFloat("sum_f");

			BigDecimal sumNumeric = dbfRecord.getBigDecimal("sum_n");

			boolean bool = dbfRecord.getBoolean("bool_val");

			Date date = dbfRecord.getDate("date_val");

			System.out.println(string + " " + sumFloat + " " + sumNumeric + " " + bool + " " + date);
		}
	}

	public static void main(String[] args) {

		PropertyConfigurator.configure("log4j.properties");
		LeerAnalogicas1.testRead();
	}
}
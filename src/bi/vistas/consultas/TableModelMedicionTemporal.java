/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.vistas.consultas;

import javax.swing.table.AbstractTableModel;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO, el modelo de datos de la tabla de multiples columnas, donde se
 * visualizan todas las alarmas generadas segun la dimension elegida
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, puedo entregar el identificador String que corresponde a una
 * determinada columna, o todos los datos de una fila cuando me lo piden.
 * 
 * LO QUE CONOZCO, los nombres de todas las columnas de la dimension ejecutada,
 * y los datos que les corresponden
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, es javax.swing.table.AbstractTableModel
 * 
 * COMO INTERACTUO CON MI COLABORADOR, por extension me provee de los metodos
 * que necesito para ser un modelo de datos para JTable
 * 
 * @author hdonato
 * 
 */
public class TableModelMedicionTemporal extends AbstractTableModel {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private String[] columnNames;
	private float[][] datos;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public TableModelMedicionTemporal(float[][] datos, String[] encabezado) {

		columnNames = encabezado;
		this.datos = datos;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int columna) {
		return getValueAt(0, columna).getClass();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int columna) {
		return columnNames[columna];
	}

	public float[][] getDatos() {
		return datos;
	}

	public float[] getDatosFila(int fila) {

		if (datos.length >= fila)
			return datos[fila];
		return null;
	}

	@Override
	public int getRowCount() {
		if (datos != null)
			return datos.length;
		return 0;
	}

	@Override
	public Object getValueAt(int fila, int columna) {

		try {
			return datos[fila][columna];
		} catch (ArrayIndexOutOfBoundsException excepcion) {
			return 0;
		}
	}
}
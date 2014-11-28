/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package vista_IU;

import javax.swing.table.AbstractTableModel;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

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
		}
		catch (ArrayIndexOutOfBoundsException excepcion) {
			return 0;
		}
	}
}
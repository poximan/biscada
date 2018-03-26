/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package main.java.com.servicoop.app.bi.vistas.consultas;

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
 * YO REPRESENTO, el modelo de datos de la tabla de simple columna, donde se
 * visualizan las claves de la dimension elegida
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO, puedo entregar el identificador String que corresponde a una
 * determinada fila, cuando me la piden.
 * 
 * LO QUE CONOZCO, los nombres de todas las filas de la dimension ejecutada
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
public class TableModelEntradaFila extends AbstractTableModel {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "dimension" };

	Object[][] data;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public TableModelEntradaFila(Object[] titulos_entrada_fila) {

		data = new Object[titulos_entrada_fila.length][columnNames.length];

		for (int indice = 0; indice < titulos_entrada_fila.length; indice++) {
			data[indice][0] = titulos_entrada_fila[indice];
		}
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

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		return data[fila][columna];
	}
}
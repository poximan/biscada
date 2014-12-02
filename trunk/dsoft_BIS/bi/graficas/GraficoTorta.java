package graficas;

import java.awt.Color;

import javax.swing.JTable;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.DefaultPieDataset;

public class GraficoTorta extends GraficoAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(GraficoTorta.class);

	private JTable tbl_titulo_filas;
	private float[][] datos_tabla;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public GraficoTorta(float[][] datos_tabla, JTable tbl_titulo_filas) {

		this.tbl_titulo_filas = tbl_titulo_filas;
		this.datos_tabla = datos_tabla;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public ChartPanel fijarDataSet() {

		String[] fila = new String[tbl_titulo_filas.getRowCount()];
		double cantTotal;

		for (int i = 0; i < tbl_titulo_filas.getRowCount(); i++) {
			fila[i] = tbl_titulo_filas.getValueAt(i, 0).toString();
		}

		DefaultPieDataset dataset = new DefaultPieDataset();

		/***************** Fuente de datos **************/
		try {
			for (int i = 0; i < datos_tabla.length; i++) {
				cantTotal = 0;
				for (int j = 0; j < datos_tabla[i].length; j++) {
					cantTotal = cantTotal + datos_tabla[i][j];
					dataset.setValue(fila[i], cantTotal);
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException excepcion) {
			log.error("arreglo fuera de rango");
		}
		catch (IllegalArgumentException excepcion) {
			log.error("faltan argumentos");
		}

		return graficar(inicializar(dataset));
	}

	@Override
	public JFreeChart inicializar(AbstractDataset dataset) {

		DefaultPieDataset dataset_cast = (DefaultPieDataset) dataset;

		JFreeChart chart = ChartFactory.createPieChart3D(null, dataset_cast, true, true, false);

		// performance;
		chart.setBackgroundPaint(Color.white);
		chart.setBorderPaint(Color.black);
		chart.setBorderVisible(true);
		chart.setAntiAlias(true);

		return chart;
	}
}

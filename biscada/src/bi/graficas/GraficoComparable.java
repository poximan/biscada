package bi.graficas;

import javax.swing.JPanel;
import javax.swing.JTable;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class GraficoComparable extends JPanel {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(GraficoComparable.class);

	private static JTable tbl_titulo_filas;
	private static JTable tbl_titulo_filas_comparar;

	private static float[][] datos_tabla;
	private static float[][] datos_tabla_comparar;

	private static String[] encabezado_tabla;
	private static String[] encabezado_tabla_comparar;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/**
	 * Creates a chart.
	 *
	 * @return A chart.
	 */
	private static JFreeChart createChart() {

		CategoryDataset dataset1 = createDataset1();
		NumberAxis rangeAxis1 = new NumberAxis("Value");
		rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		LineAndShapeRenderer renderer1 = new LineAndShapeRenderer();
		renderer1.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		CategoryPlot subplot1 = new CategoryPlot(dataset1, null, rangeAxis1, renderer1);
		subplot1.setDomainGridlinesVisible(true);

		CategoryDataset dataset2 = createDataset2();
		NumberAxis rangeAxis2 = new NumberAxis("Value");
		rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
		renderer2.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		CategoryPlot subplot2 = new CategoryPlot(dataset2, null, rangeAxis2, renderer2);
		subplot2.setDomainGridlinesVisible(true);

		CategoryAxis domainAxis = new CategoryAxis("Category");
		CombinedCategoryPlot plot = new CombinedCategoryPlot(domainAxis, new NumberAxis("Range"));
		plot.add(subplot1, 2);
		plot.add(subplot2, 1);

		JFreeChart result = new JFreeChart(plot);
		return result;
	}

	/*
	 * Datos para comparar armando 2do dataset
	 */

	/**
	 * Creates a dataset.
	 *
	 */

	public static CategoryDataset createDataset1() {
		String[] fila = new String[tbl_titulo_filas.getRowCount()];

		for (int i = 0; i < tbl_titulo_filas.getRowCount(); i++) {
			fila[i] = tbl_titulo_filas.getValueAt(i, 0).toString();
		}

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		/***************** Fuente de datos **************/
		try {
			for (int i = 0; i < datos_tabla.length; i++)
				for (int j = 0; j < datos_tabla[i].length; j++)
					dataset.setValue(datos_tabla[i][j], fila[i], encabezado_tabla[j]);
		} catch (ArrayIndexOutOfBoundsException excepcion) {
			log.error("arreglo fuera de rango");
		} catch (IllegalArgumentException excepcion) {
			log.error("faltan argumentos");
		}

		return dataset;
	}

	/**
	 * Creates a dataset.
	 *
	 * @return A dataset.
	 */
	public static CategoryDataset createDataset2() {

		String[] filaComparacion = new String[tbl_titulo_filas_comparar.getRowCount()];

		for (int i = 0; i < tbl_titulo_filas_comparar.getRowCount(); i++) {
			filaComparacion[i] = tbl_titulo_filas_comparar.getValueAt(i, 0).toString();
		}

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		/***************** Fuente de datos **************/
		try {
			for (int i = 0; i < datos_tabla_comparar.length; i++)
				for (int j = 0; j < datos_tabla_comparar[i].length; j++)
					dataset.setValue(datos_tabla_comparar[i][j], filaComparacion[i], encabezado_tabla_comparar[j]);
		} catch (ArrayIndexOutOfBoundsException excepcion) {
			log.error("arreglo fuera de rango");
		} catch (IllegalArgumentException excepcion) {
			log.error("faltan argumentos");
		}

		return dataset;

	}

	/**
	 * Creates a panel for the demo (used by SuperDemo.java).
	 *
	 * @return A panel.
	 */
	public static JPanel createDemoPanel() {
		JFreeChart chart = createChart();
		return new ChartPanel(chart);
	}

	public GraficoComparable(float[][] datos_tabla, String[] encabezado_tabla, JTable tbl_titulo_filas) {

		GraficoComparable.tbl_titulo_filas = tbl_titulo_filas;
		GraficoComparable.datos_tabla = datos_tabla;
		GraficoComparable.encabezado_tabla = encabezado_tabla;
	}

	public void datosParaComparar(float[][] datos_tabla_comparacion, String[] encabezado_tabla_comparacion,
			JTable tbl_titulo_filas_comparacion) {

		tbl_titulo_filas_comparar = tbl_titulo_filas_comparacion;
		datos_tabla_comparar = datos_tabla_comparacion;
		encabezado_tabla_comparar = encabezado_tabla_comparacion;
	}

}

package graficas;

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

public class graficoComparable extends JPanel {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(GraficoLineas.class);

	private static JTable tbl_titulo_filas;
	private static float[][] datos_tabla;
	private static String[] encabezado_tabla;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public graficoComparable(float[][] datos_tabla, String[] encabezado_tabla, JTable tbl_titulo_filas) {

		graficoComparable.tbl_titulo_filas = tbl_titulo_filas;
		graficoComparable.datos_tabla = datos_tabla;
		graficoComparable.encabezado_tabla = encabezado_tabla;

		JPanel chartPanel = createDemoPanel();
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		// setContentPane(chartPanel);
	}

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
		}
		catch (ArrayIndexOutOfBoundsException excepcion) {
			log.error("arreglo fuera de rango");
		}
		catch (IllegalArgumentException excepcion) {
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

		DefaultCategoryDataset result = new DefaultCategoryDataset();

		String series1 = "Third";
		String series2 = "Fourth";

		String type1 = "Type 1";
		String type2 = "Type 2";
		String type3 = "Type 3";
		String type4 = "Type 4";
		String type5 = "Type 5";
		String type6 = "Type 6";
		String type7 = "Type 7";
		String type8 = "Type 8";

		result.addValue(11.0, series1, type1);
		result.addValue(14.0, series1, type2);
		result.addValue(13.0, series1, type3);
		result.addValue(15.0, series1, type4);
		result.addValue(15.0, series1, type5);
		result.addValue(17.0, series1, type6);
		result.addValue(17.0, series1, type7);
		result.addValue(18.0, series1, type8);

		result.addValue(15.0, series2, type1);
		result.addValue(17.0, series2, type2);
		result.addValue(16.0, series2, type3);
		result.addValue(18.0, series2, type4);
		result.addValue(14.0, series2, type5);
		result.addValue(14.0, series2, type6);
		result.addValue(12.0, series2, type7);
		result.addValue(11.0, series2, type8);

		return result;

	}

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

	/**
	 * Creates a panel for the demo (used by SuperDemo.java).
	 *
	 * @return A panel.
	 */
	public static JPanel createDemoPanel() {
		JFreeChart chart = createChart();
		return new ChartPanel(chart);
	}

}

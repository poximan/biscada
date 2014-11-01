/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package graficas;

import javax.swing.JTable;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.AbstractDataset;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class GraficoBarraSegundo extends GraficoAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(GraficoBarraSegundo.class);

	private JTable tbl_titulo_filas;
	private float[][] datos_tabla;
	private String[] encabezado_tabla;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public GraficoBarraSegundo(float[][] datos_tabla, String[] encabezado_tabla, JTable tbl_titulo_filas) {

		this.tbl_titulo_filas = tbl_titulo_filas;
		this.datos_tabla = datos_tabla;
		this.encabezado_tabla = encabezado_tabla;
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	@Override
	public ChartPanel fijarDataSet() {

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

		return graficar(inicializar(dataset));
	}

	@Override
	public JFreeChart inicializar(AbstractDataset dataset) {

		DefaultCategoryDataset dataset_cast = (DefaultCategoryDataset) dataset;

		CategoryAxis alarmas = new CategoryAxis("Alarmas");
		ValueAxis cantidad = new NumberAxis("Cantidad");
		BarRenderer barras = new BarRenderer();
		CategoryPlot plot = new CategoryPlot(dataset_cast, alarmas, cantidad, barras);

		JFreeChart chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		// performance;
		chart.setAntiAlias(false);

		CategoryAxis xAxis = plot.getDomainAxis();
		xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(true);
		chart.getLegend().setFrame(BlockBorder.NONE);

		return chart;
	}
}

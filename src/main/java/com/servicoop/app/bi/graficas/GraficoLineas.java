package main.java.com.servicoop.app.bi.graficas;

import java.awt.Color;

import javax.swing.JTable;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.AbstractDataset;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO una clase que extiende de GraficoAbstract
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO generar el grafico de Lineas
 * 
 * LO QUE CONOZCO la libreria JFreeChart donde se pasaran los datos para generar
 * el grafico de Lineas
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, VistaDimAbstractSimple
 * 
 * COMO INTERACTUO CON MI COLABORADOR, recibiendo los datos para generar el
 * gr�fico
 *
 */
public class GraficoLineas extends GraficoAbstract {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(GraficoLineas.class);

	private JTable tbl_titulo_filas;
	private float[][] datos_tabla;
	private String[] encabezado_tabla;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public GraficoLineas(float[][] datos_tabla, String[] encabezado_tabla, JTable tbl_titulo_filas) {

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
		} catch (ArrayIndexOutOfBoundsException excepcion) {
			log.error("arreglo fuera de rango");
		} catch (IllegalArgumentException excepcion) {
			log.error("faltan argumentos");
		}

		return graficar(inicializar(dataset));
	}

	/*
	 * Se arma el esqueleto de la gr�fica con sus propiedades
	 */
	@SuppressWarnings("deprecation")
	@Override
	public JFreeChart inicializar(AbstractDataset dataset) {

		DefaultCategoryDataset dataset_cast = (DefaultCategoryDataset) dataset;

		JFreeChart chart = ChartFactory.createLineChart("", "Alarmas", "Cantidad", dataset_cast,
				PlotOrientation.VERTICAL, true, true, false);

		// chart.setBackgroundPaint(Color.LIGHT_GRAY);
		chart.setAntiAlias(true);

		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.white);

		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setShapesVisible(true);
		renderer.setDrawOutlines(true);

		CategoryAxis xAxis = plot.getDomainAxis();
		xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		chart.getLegend().setFrame(BlockBorder.NONE);

		return chart;
	}
}

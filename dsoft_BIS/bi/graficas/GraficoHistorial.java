package graficas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

public class GraficoHistorial extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel panel;

	private TimeSeriesCollection coleccion;
	private static double Total;
	private static double promedio;

	public GraficoHistorial() {
	}

	public JPanel createPanel() {
		// XYDataset xydataset = createDataset();
		JFreeChart jfreechart = createChart(coleccion);
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		// chartpanel.setPreferredSize(new Dimension(900, 300));
		chartpanel.setDomainZoomable(true);
		chartpanel.setRangeZoomable(true);

		panel = chartpanel;
		add(panel);
		return panel;
	}

	private static JFreeChart createChart(XYDataset xydataset) {
		JFreeChart jfreechart = ChartFactory.createXYLineChart(null, "Alarmas", "Cantidad", xydataset,
				PlotOrientation.VERTICAL, false, true, false);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		xyplot.setDomainGridlinePaint(Color.lightGray);
		xyplot.setDomainGridlineStroke(new BasicStroke(1.0F));
		xyplot.setRangeGridlinePaint(Color.lightGray);
		xyplot.setRangeGridlineStroke(new BasicStroke(1.0F));
		// xyplot.setRangeTickBandPaint(new Color(240, 240, 240));

		// Se configura eje x y la manera en que se muestran las etiquetas
		PeriodAxis periodaxis = new PeriodAxis(null);
		PeriodAxisLabelInfo aperiodaxislabelinfo[] = new PeriodAxisLabelInfo[1];
		aperiodaxislabelinfo[0] = new PeriodAxisLabelInfo(Day.class, new SimpleDateFormat("MMM-YYYY"));
		periodaxis.setLabelInfo(aperiodaxislabelinfo);
		xyplot.setDomainAxis(periodaxis);

		// Configuración del rango
		ValueAxis valueaxis = xyplot.getRangeAxis();
		valueaxis.setRange(0.0D, Total);
		XYItemRenderer xyitemrenderer = xyplot.getRenderer();
		xyitemrenderer.setSeriesPaint(0, Color.green);
		xyitemrenderer.setSeriesStroke(0, new BasicStroke(2.0F));
		ValueMarker valuemarker = new ValueMarker(promedio);
		valuemarker.setLabelOffsetType(LengthAdjustmentType.EXPAND);
		valuemarker.setPaint(Color.red);
		valuemarker.setStroke(new BasicStroke(2.0F));
		valuemarker.setLabel("Promedio");
		valuemarker.setLabelFont(new Font("SansSerif", 0, 11));
		valuemarker.setLabelPaint(Color.red);
		valuemarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
		valuemarker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
		xyplot.addRangeMarker(valuemarker);

		/*
		 * Hour hour = new Hour(18, 30, 6, 2005); Hour hour1 = new Hour(20, 30, 6, 2005); double d =
		 * hour.getFirstMillisecond(); double d1 = hour1.getFirstMillisecond();
		 * 
		 * IntervalMarker intervalmarker = new IntervalMarker(d, d1);
		 * intervalmarker.setLabelOffsetType(LengthAdjustmentType.EXPAND); intervalmarker.setPaint(new Color(150, 150,
		 * 255)); intervalmarker.setLabel("Mes actual"); intervalmarker.setLabelFont(new Font("SansSerif", 0, 11));
		 * intervalmarker.setLabelPaint(Color.blue); intervalmarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
		 * intervalmarker.setLabelTextAnchor(TextAnchor.TOP_RIGHT); xyplot.addDomainMarker(intervalmarker,
		 * Layer.BACKGROUND); ValueMarker valuemarker1 = new ValueMarker(d, Color.blue, new BasicStroke(2.0F));
		 * ValueMarker valuemarker2 = new ValueMarker(d1, Color.blue, new BasicStroke(2.0F));
		 * xyplot.addDomainMarker(valuemarker1, Layer.BACKGROUND); xyplot.addDomainMarker(valuemarker2,
		 * Layer.BACKGROUND);
		 */
		return jfreechart;
	}

	/*
	 * Se crea el dataset de datos
	 */
	public void cargarDatos(Date[] fechas, float[] datos, float Total, float promedio) {

		TimeSeries serieFecha = new TimeSeries("coleccion1", Day.class);
		String dia, mes, anio;
		int day, month, year;
		coleccion = new TimeSeriesCollection();

		// Convirtiendo y pasando la fecha y el dato al histograma
		for (int i = 0; i < fechas.length; i++) {
			dia = new SimpleDateFormat("dd").format(fechas[i]);
			mes = new SimpleDateFormat("MM").format(fechas[i]);
			anio = new SimpleDateFormat("YYYY").format(fechas[i]);

			day = Integer.parseInt(dia);
			month = Integer.parseInt(mes);
			year = Integer.parseInt(anio);

			serieFecha.add(new Day(day, month, year), datos[i]);
		}

		coleccion.addSeries(serieFecha);
	}
}
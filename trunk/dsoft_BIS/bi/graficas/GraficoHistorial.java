package graficas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
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
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.Layer;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

public class GraficoHistorial extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel panel;

	public GraficoHistorial() {
	}

	public JPanel createPanel() {
		XYDataset xydataset = createDataset();
		JFreeChart jfreechart = createChart(xydataset);
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setPreferredSize(new Dimension(700, 300));
		chartpanel.setDomainZoomable(true);
		chartpanel.setRangeZoomable(true);
		chartpanel.getAutoscrolls();

		panel = chartpanel;
		// panel.setContentPane(chartpanel);
		add(panel);
		return panel;
	}

	private static JFreeChart createChart(XYDataset xydataset) {
		JFreeChart jfreechart = ChartFactory.createXYLineChart(null, "X", "Cantidad", xydataset,
				PlotOrientation.VERTICAL, false, true, false);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		xyplot.setDomainGridlinePaint(Color.lightGray);
		xyplot.setDomainGridlineStroke(new BasicStroke(1.0F));
		xyplot.setRangeGridlinePaint(Color.lightGray);
		xyplot.setRangeGridlineStroke(new BasicStroke(1.0F));
		xyplot.setRangeTickBandPaint(new Color(240, 240, 240));

		PeriodAxis periodaxis = new PeriodAxis(null, new Hour(0, 30, 6, 2005), new Hour(23, 30, 6, 2005));
		PeriodAxisLabelInfo aperiodaxislabelinfo[] = new PeriodAxisLabelInfo[2];
		aperiodaxislabelinfo[0] = new PeriodAxisLabelInfo(org.jfree.data.time.Hour.class, new SimpleDateFormat("HH"));
		aperiodaxislabelinfo[1] = new PeriodAxisLabelInfo(org.jfree.data.time.Day.class, new SimpleDateFormat("dd-MMM"));
		periodaxis.setLabelInfo(aperiodaxislabelinfo);
		xyplot.setDomainAxis(periodaxis);
		ValueAxis valueaxis = xyplot.getRangeAxis();
		valueaxis.setRange(0.0D, 100D);
		XYItemRenderer xyitemrenderer = xyplot.getRenderer();
		xyitemrenderer.setSeriesPaint(0, Color.green);
		xyitemrenderer.setSeriesStroke(0, new BasicStroke(2.0F));
		ValueMarker valuemarker = new ValueMarker(80D);
		valuemarker.setLabelOffsetType(LengthAdjustmentType.EXPAND);
		valuemarker.setPaint(Color.red);
		valuemarker.setStroke(new BasicStroke(2.0F));
		valuemarker.setLabel("Promedio");
		valuemarker.setLabelFont(new Font("SansSerif", 0, 11));
		valuemarker.setLabelPaint(Color.red);
		valuemarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
		valuemarker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
		xyplot.addRangeMarker(valuemarker);

		Hour hour = new Hour(18, 30, 6, 2005);
		Hour hour1 = new Hour(20, 30, 6, 2005);
		double d = hour.getFirstMillisecond();
		double d1 = hour1.getFirstMillisecond();

		IntervalMarker intervalmarker = new IntervalMarker(d, d1);
		intervalmarker.setLabelOffsetType(LengthAdjustmentType.EXPAND);
		intervalmarker.setPaint(new Color(150, 150, 255));
		intervalmarker.setLabel("Mes actual");
		intervalmarker.setLabelFont(new Font("SansSerif", 0, 11));
		intervalmarker.setLabelPaint(Color.blue);
		intervalmarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
		intervalmarker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
		xyplot.addDomainMarker(intervalmarker, Layer.BACKGROUND);
		ValueMarker valuemarker1 = new ValueMarker(d, Color.blue, new BasicStroke(2.0F));
		ValueMarker valuemarker2 = new ValueMarker(d1, Color.blue, new BasicStroke(2.0F));
		xyplot.addDomainMarker(valuemarker1, Layer.BACKGROUND);
		xyplot.addDomainMarker(valuemarker2, Layer.BACKGROUND);
		return jfreechart;
	}

	private static XYDataset createDataset() {

		/*
		 * crear los puntos de las fechas
		 */

		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		String prueba = new String("prueba");
		TimeSeries series1 = new TimeSeries(prueba);

		series1.add(new Month(1, 2005), 1200);
		series1.add(new Month(2, 2005), 1400);
		series1.add(new Month(3, 2005), 1500);
		series1.add(new Month(4, 2005), 1700);
		series1.add(new Month(5, 2005), 1600);
		series1.add(new Month(6, 2005), 2400);
		series1.add(new Month(7, 2005), 2100);
		series1.add(new Month(8, 2005), 2200);
		series1.add(new Month(9, 2005), 800);
		series1.add(new Month(10, 2005), 2350);
		series1.add(new Month(11, 2005), 500);
		series1.add(new Month(12, 2005), 700);
		series1.add(new Month(1, 2006), 900);
		series1.add(new Month(2, 2006), 1500);
		series1.add(new Month(3, 2006), 2100);
		series1.add(new Month(4, 2006), 2200);
		series1.add(new Month(5, 2006), 1900);
		series1.add(new Month(6, 2006), 3000);
		series1.add(new Month(7, 2006), 3780);
		series1.add(new Month(8, 2006), 4000);
		series1.add(new Month(9, 2006), 4500);
		series1.add(new Month(10, 2006), 7000);
		series1.add(new Month(11, 2006), 5500);
		series1.add(new Month(12, 2006), 6000);
		series1.add(new Month(1, 2007), 6500);

		timeseriescollection.addSeries(series1);
		return timeseriescollection;
	}

	public static JPanel createDemoPanel() {
		JFreeChart jfreechart = createChart(createDataset());
		return new ChartPanel(jfreechart);
	}

	public void cargarDatos(Date[] fechas, float[] datos) {

		String prueba = new String("prueba1");
		TimeSeries serieFecha = new TimeSeries(prueba);

		for (int i = 0; i < fechas.length; i++)
			serieFecha.add(new Day(fechas[i].getDay(), fechas[i].getMonth(), fechas[i].getYear()), 50);

		System.out.println("llegaron las fechas!!! :)");
		for (int i = 0; i < fechas.length; i++)
			System.out.println(fechas[i]);
	}
}
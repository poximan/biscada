package graficas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class GraficoKPI extends JPanel {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private static DefaultValueDataset dataset;

	private MeterInterval intervaloNormal;
	private MeterInterval intervaloAdvertencia;
	private MeterInterval intervaloPeligro;

	private JPanel panel;
	private static double canTotal;
	private double promHist;
	private double porcentajeF;

	private double rangoMin;
	private double rangoMax;

	// porcentaje por default
	int porcentaje = 5;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public GraficoKPI(String s) {

		setSize(400, 400);
		setVisible(true);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public JPanel createPanel() {

		JFreeChart chart = createChart();
		ChartPanel chartpanel = new ChartPanel(chart);
		chartpanel.setPreferredSize(new Dimension(300, 250));
		panel = chartpanel;
		add(panel);
		panel.setPreferredSize(new Dimension(300, 250));
		return panel;
	}

	public void contruir(float cantTotal, float promH, float cantAct) {

		dataset = new DefaultValueDataset(cantAct);

		promHist = promH;
		System.out.println("verificando cuanto es el prom act " + promHist);
		canTotal = cantTotal;
	}

	private JFreeChart createChart() {

		rangoMin = promHist - porcentajeF;
		rangoMax = promHist + porcentajeF;

		MeterPlot meterplot = new MeterPlot(dataset);
		// Fijamos el rango mínimo y máximo
		meterplot.setRange(new Range(0, canTotal));

		// Fijamos el rango de aceptación y sus respectivos colores
		actualizarIntervalos();

		meterplot.addInterval(intervaloNormal);
		meterplot.addInterval(intervaloAdvertencia);
		meterplot.addInterval(intervaloPeligro);

		// color de la aguja
		meterplot.setNeedlePaint(Color.darkGray);

		// color de fondo
		meterplot.setDialBackgroundPaint(Color.white);
		// color y forma del objeto
		meterplot.setDialOutlinePaint(Color.black);
		meterplot.setDialShape(DialShape.PIE);
		meterplot.setMeterAngle(240);

		meterplot.setTickLabelsVisible(true);
		// meterplot.setTickLabelFont(new Font("Arial", 1, 12));
		meterplot.setTickLabelPaint(Color.black);
		meterplot.setTickSize(5D);
		meterplot.setTickPaint(Color.gray);
		meterplot.setValuePaint(Color.black);
		meterplot.setValueFont(new Font("Arial", 1, 14));

		JFreeChart jfreechart = new JFreeChart("KPI", JFreeChart.DEFAULT_TITLE_FONT, meterplot, true);
		return jfreechart;
	}

	public void actualizarIntervalos() {

		intervaloNormal = new MeterInterval("Normal", new Range(0, rangoMin), Color.black, new BasicStroke(3.0F),
				new Color(255, 255, 0, 64));

		intervaloAdvertencia = new MeterInterval("Advertencia", new Range(rangoMin, rangoMax), Color.black,
				new BasicStroke(2.0F), Color.yellow.brighter());

		intervaloPeligro = new MeterInterval("Peligro", new Range(rangoMax, canTotal), Color.black, new BasicStroke(
				3.0F), Color.RED);
	}

	/*
	 * Método para calcular el porcentaje y facilitar el cálculo del rango!!!
	 */

	public void Porcentaje(int porcentaje) {
		porcentajeF = (promHist * porcentaje) / 100;
		System.out.println("El " + porcentaje + " % de " + promHist + " es " + porcentajeF);

		actualizarIntervalos();
	}

}
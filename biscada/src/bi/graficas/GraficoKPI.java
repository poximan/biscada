package bi.graficas;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
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

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	public GraficoKPI() {
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public JPanel createPanel() {

		JFreeChart chart = createChart();
		ChartPanel chartpanel = new ChartPanel(chart);
		chartpanel.setPreferredSize(new Dimension(300, 300));
		panel = chartpanel;
		add(panel);
		panel.setPreferredSize(new Dimension(300, 300));
		return panel;
	}

	/**
	 * Se genera el dibujo con los datos ingresados.
	 */
	private JFreeChart createChart() {

		rangoMin = promHist - porcentajeF;
		rangoMax = promHist + porcentajeF;

		MeterPlot meterplot = new MeterPlot(dataset);
		// Fijamos el rango m�nimo y m�ximo
		meterplot.setRange(new Range(0, canTotal));

		// Fijamos el rango de aceptaci�n y sus respectivos colores
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
		meterplot.setTickLabelFont(new Font("Arial", 1, 12));
		meterplot.setTickLabelPaint(Color.black);
		meterplot.setTickSize(5D);
		meterplot.setTickPaint(Color.gray);
		meterplot.setValuePaint(Color.black);
		meterplot.setValueFont(new Font("Arial", 1, 14));

		JFreeChart jfreechart = new JFreeChart("KPI Indicador de rendimiento", JFreeChart.DEFAULT_TITLE_FONT, meterplot,
				true);
		return jfreechart;
	}

	/**
	 * se crean y actualizan los intervalos seg�n el evento que ocurra (inicio
	 * o seteo de porcentaje).
	 */
	public void actualizarIntervalos() {

		intervaloNormal = new MeterInterval("Estado Normal", new Range(0, rangoMin), Color.black, new BasicStroke(3.0F),
				new Color(255, 255, 0, 64));

		intervaloAdvertencia = new MeterInterval("Estado Aceptable", new Range(rangoMin, rangoMax), Color.black,
				new BasicStroke(2.0F), Color.yellow.brighter());

		intervaloPeligro = new MeterInterval("Peligro", new Range(rangoMax, canTotal), Color.black,
				new BasicStroke(3.0F), Color.RED);
	}

	/**
	 * Se cargan los datos para ser reflejados en el sem�foro
	 */
	public void cargarDatos(float cantTotal, float promH, float cantAct) {

		dataset = new DefaultValueDataset(cantAct);
		promHist = promH;
		canTotal = cantTotal;
	}

	/**
	 * M�todo para calcular el porcentaje y facilitar el c�lculo del
	 * rango!!!
	 */
	public void Porcentaje(int porcentaje) {

		porcentajeF = (promHist * porcentaje) / 100;
		System.out.println("El " + porcentaje + " % de " + promHist + " es " + porcentajeF);

		actualizarIntervalos();
		refreshChart();
	}

	/**
	 * Actualizaci�n del gr�fico cada ves que se agrande el rango del
	 * promedio
	 */
	private void refreshChart() {

		panel.removeAll();
		panel.revalidate(); // remueve el dibujo anterior
		JFreeChart aChart = createChart();
		aChart.removeLegend();
		ChartPanel chartPanel = new ChartPanel(aChart);
		chartPanel.setPreferredSize(new Dimension(300, 300));
		panel.setLayout(new BorderLayout());
		panel.add(chartPanel);
		panel.repaint(); // se muestra el nuevo dibujo
	}
}
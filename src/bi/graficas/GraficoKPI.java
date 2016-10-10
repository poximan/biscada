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

import propiedades.controles.servicios.ServPropiedades;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

/**
 * Class Responsibility Collaborator (CRC) design:
 * 
 * ==== parte clase =========================
 * 
 * YO REPRESENTO el grafico de KPI.
 * 
 * ==== parte responsabilidad ===============
 * 
 * LO QUE HAGO construir un panel donde se contruye un grafico KPI.
 * 
 * LO QUE CONOZCO 
 * 
 * ==== parte colaboracion ==================
 * 
 * MI COLABORADOR PRINCIPAL, VistaKpiabstract. 
 * 
 * COMO INTERACTUO CON MI COLABORADOR, recibiendo los datos para el grafico
 *
 */
public class GraficoKPI extends JPanel {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	private DefaultValueDataset dataset;

	private MeterInterval intervaloNormal;
	private MeterInterval intervaloAdvertencia;
	private MeterInterval intervaloPeligro;

	private JPanel panel;
	private double promedio;
	private double porcentaje;

	private double normal_max;
	private double advertencia_max;
	private double peligro_max;

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public GraficoKPI() {
	}

	/**
	 * se crean y actualizan los intervalos seg�n el evento que ocurra (inicio
	 * o seteo de porcentaje).
	 */
	public void actualizarIntervalos() {

		try {
			intervaloNormal = new MeterInterval("Estado Normal", new Range(0, normal_max), Color.black,
					new BasicStroke(3.0F), Color.GREEN);

			intervaloAdvertencia = new MeterInterval("Estado Aceptable", new Range(normal_max, advertencia_max),
					Color.black, new BasicStroke(2.0F), Color.YELLOW);

			intervaloPeligro = new MeterInterval("Peligro", new Range(advertencia_max, peligro_max), Color.black,
					new BasicStroke(3.0F), Color.RED);

		} catch (IllegalArgumentException e) {

			intervaloNormal = new MeterInterval("Estado Normal", new Range(0, 0), Color.black, new BasicStroke(3.0F),
					Color.GREEN);

			intervaloPeligro = intervaloAdvertencia = intervaloNormal;
		}
	}

	public void cargarDatos(int total, int actual, double promedio) {

		peligro_max = total;
		dataset = new DefaultValueDataset(actual);
		this.promedio = promedio;

		porcentaje = Double.parseDouble(
				ServPropiedades.getInstancia().getProperty("Graficos.PORCENTAGE_ACEPTACION_RESPECTO_MEDIA"));

		createPanel();
	}

	private JFreeChart createChart() {

		normal_max = promedio - porcentaje;
		advertencia_max = promedio + porcentaje;

		MeterPlot meterplot = new MeterPlot(dataset);
		// Fijamos el rango m�nimo y m�ximo
		meterplot.setRange(new Range(0, peligro_max));

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
		meterplot.setTickLabelFont(new Font("Arial", 1, 10));
		meterplot.setTickLabelPaint(Color.black);
		meterplot.setTickSize(5D);
		meterplot.setTickPaint(Color.gray);
		meterplot.setValuePaint(Color.black);
		meterplot.setValueFont(new Font("Arial", 1, 10));

		return new JFreeChart(meterplot);
	}

	public JPanel createPanel() {

		JFreeChart chart = createChart();
		ChartPanel chartpanel = new ChartPanel(chart);
		panel = chartpanel;
		add(panel);
		return panel;
	}

	/**
	 * M�todo para calcular el porcentaje y facilitar el c�lculo del
	 * rango!!!
	 */
	public void Porcentaje(int porcentaje) {

		this.porcentaje = (promedio * porcentaje) / 100;

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

		panel.setPreferredSize(new Dimension(250, 200));
		panel.setLayout(new BorderLayout());
		panel.add(chartPanel);
		panel.repaint(); // se muestra el nuevo dibujo
	}
}
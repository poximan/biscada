/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.graficas;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.AbstractDataset;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public abstract class GraficoAbstract extends JFrame {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	public JScrollPane construirPanel() {

		JScrollPane scroll_grafico = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll_grafico.setViewportView(fijarDataSet());
		scroll_grafico.getVerticalScrollBar().setValue(300);

		return scroll_grafico;
	}

	public abstract ChartPanel fijarDataSet();

	public abstract JFreeChart inicializar(AbstractDataset dataset);

	public ChartPanel graficar(JFreeChart chart) {

		ChartPanel chartPanel = new ChartPanel(chart);

		chartPanel.setDomainZoomable(true); // dominio
		chartPanel.setRangeZoomable(true); // imagen

		return chartPanel;
	}

	/* ............................................. */
	/* ............................................. */
	/* GET'S ....................................... */
	/* ............................................. */

	/* ............................................. */
	/* ............................................. */
	/* SET'S ....................................... */
	/* ............................................. */
}
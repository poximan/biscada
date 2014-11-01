package graficas;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;

public class DemoChartProblem {

	private static final int DISPLAY_MAX = 30;

	public static void main(String[] args) throws Exception {
		new DemoChartProblem();
	}

	private final DefaultValueDataset dataset = new DefaultValueDataset();
	private final DefaultValueDataset displayDataset = new DefaultValueDataset();

	private final JFrame frame = new JFrame();

	public DemoChartProblem() {
		frame.setPreferredSize(new Dimension(300, 300));
		frame.add(buildDialPlot(0, DISPLAY_MAX, 5));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setValue(50);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setVisible(true);
			}
		});
	}

	private ChartPanel buildDialPlot(int minimumValue, int maximumValue, int majorTickGap) {

		DialPlot plot = new DialPlot();
		plot.setDataset(0, dataset);
		plot.setDataset(1, displayDataset);

		plot.setDialFrame(new StandardDialFrame());

		// value indicator uses the real data set
		plot.addLayer(new DialValueIndicator(0));

		// needle uses constrained data set
		plot.addLayer(new DialPointer.Pointer(1));

		StandardDialScale scale = new StandardDialScale(minimumValue, maximumValue, -120, -300, majorTickGap,
				majorTickGap - 1);
		scale.setTickRadius(0.88);
		scale.setTickLabelOffset(0.20);
		plot.addScale(0, scale);

		return new ChartPanel(new JFreeChart(plot));
	}

	private void setValue(int value) {
		dataset.setValue(value);
		displayDataset.setValue(Math.min(DISPLAY_MAX, value));
	}
}

package graficas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RefineryUtilities;

/**
 * A READ-ONLY wrapper around a {@link TableXYDataset} to convert it to a table model for use in a JTable. The first
 * column of the table shows the x-values, the remaining columns show the y-values for each series (series 0 appears in
 * column 1, series 1 appears in column 2, etc).
 * <P>
 * TO DO:
 * <ul>
 * <li>implement proper naming for x axis (getColumnName)
 * <li>implement setValueAt to remove READ-ONLY constraint (not sure how)
 * </ul>
 */
public class XYDatasetTableModel extends AbstractTableModel implements TableModel, DatasetChangeListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Run a demonstration of the table model interface.
	 * 
	 * @param args
	 *            ignored.
	 * 
	 * @throws Exception
	 *             when an error occurs.
	 */
	public static void main(String args[]) throws Exception {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		XYSeries s1 = new XYSeries("Series 1", true, false);
		for (int i = 0; i < 10; i++) {
			s1.add(i, Math.random());
		}
		XYSeries s2 = new XYSeries("Series 2", true, false);
		for (int i = 0; i < 15; i++) {
			s2.add(i, Math.random());
		}
		DefaultTableXYDataset dataset = new DefaultTableXYDataset();
		dataset.addSeries(s1);
		dataset.addSeries(s2);
		XYDatasetTableModel tablemodel = new XYDatasetTableModel();

		tablemodel.setModel(dataset);

		JTable dataTable = new JTable(tablemodel);
		JScrollPane scroll = new JScrollPane(dataTable);
		scroll.setPreferredSize(new Dimension(600, 150));

		JFreeChart chart = ChartFactory.createXYLineChart("XY Series Demo", "X", "Y", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(chart);

		panel.add(chartPanel, BorderLayout.CENTER);
		panel.add(scroll, BorderLayout.SOUTH);

		frame.setContentPane(panel);
		frame.setSize(600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		RefineryUtilities.centerFrameOnScreen(frame);
	}

	/** The dataset. */
	TableXYDataset model = null;

	public XYDatasetTableModel() {
		super();
	}

	/**
	 * Creates a new table model based on the specified dataset.
	 * 
	 * @param dataset
	 *            the dataset.
	 */
	public XYDatasetTableModel(TableXYDataset dataset) {
		this();
		this.model = dataset;
		this.model.addChangeListener(this);
	}

	/**
	 * Receives notification that the underlying dataset has changed.
	 * 
	 * @param event
	 *            the event
	 * 
	 * @see DatasetChangeListener
	 */
	@Override
	public void datasetChanged(DatasetChangeEvent event) {
		fireTableDataChanged();
	}

	/**
	 * Gets the number of columns in the model.
	 * 
	 * @return The number of columns in the model.
	 */
	@Override
	public int getColumnCount() {
		if (this.model == null) {
			return 0;
		}
		return this.model.getSeriesCount() + 1;
	}

	/**
	 * Returns the column name.
	 * 
	 * @param column
	 *            the column index.
	 * 
	 * @return The column name.
	 */
	@Override
	public String getColumnName(int column) {
		if (this.model == null) {
			return super.getColumnName(column);
		}
		if (column < 1) {
			return "X Value";
		} else {
			return this.model.getSeriesKey(column - 1).toString();
		}
	}

	/**
	 * Returns the number of rows.
	 * 
	 * @return The row count.
	 */
	@Override
	public int getRowCount() {
		if (this.model == null) {
			return 0;
		}
		return this.model.getItemCount();
	}

	/**
	 * Returns a value of the specified cell. Column 0 is the X axis, Columns 1 and over are the Y axis
	 * 
	 * @param row
	 *            the row number.
	 * @param column
	 *            the column number.
	 * 
	 * @return The value of the specified cell.
	 */
	@Override
	public Object getValueAt(int row, int column) {
		if (this.model == null) {
			return null;
		}
		if (column < 1) {
			return this.model.getX(0, row);
		} else {
			return this.model.getY(column - 1, row);
		}
	}

	/**
	 * Returns a flag indicating whether or not the specified cell is editable.
	 * 
	 * @param row
	 *            the row number.
	 * @param column
	 *            the column number.
	 * 
	 * @return <code>true if the specified cell is editable.
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	/**
	 * Sets the model (dataset).
	 * 
	 * @param dataset
	 *            the dataset.
	 */
	public void setModel(TableXYDataset dataset) {
		this.model = dataset;
		this.model.addChangeListener(this);
		fireTableDataChanged();
	}

	/**
	 * Updates the {@link XYDataset} if allowed.
	 * 
	 * @param value
	 *            the new value.
	 * @param row
	 *            the row.
	 * @param column
	 *            the column.
	 */
	@Override
	public void setValueAt(Object value, int row, int column) {
		if (isCellEditable(row, column)) {
			// XYDataset only provides methods for reading a dataset...
		}
	}

}
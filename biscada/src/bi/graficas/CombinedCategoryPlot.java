/* ............................................. */
/* ............................................. */
/* PRELIMINAR .................................. */
/* ............................................. */

package bi.graficas;

import java.util.Iterator;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.data.Range;

/* ............................................. */
/* ............................................. */
/* CLASE ....................................... */
/* ............................................. */

public class CombinedCategoryPlot extends CombinedDomainCategoryPlot {

	/* ............................................. */
	/* ............................................. */
	/* ATRIBUTOS ................................... */
	/* ............................................. */

	private static final long serialVersionUID = 1L;

	/* ............................................. */
	/* ............................................. */
	/* CONSTRUCTOR ................................. */
	/* ............................................. */

	/**
	 * Creates a new instance with the specified axes.
	 *
	 * @param domainAxis
	 *            the x-axis.
	 * @param rangeAxis
	 *            the y-axis.
	 */
	public CombinedCategoryPlot(CategoryAxis domainAxis, ValueAxis rangeAxis) {
		super(domainAxis);
		super.setGap(10.0);
		super.setRangeAxis(rangeAxis);
	}

	/* ............................................. */
	/* ............................................. */
	/* METODOS ..................................... */
	/* ............................................. */

	/**
	 * Adds a new subplot with weight <code>1</code>.
	 *
	 * @param subplot
	 *            the subplot.
	 */
	@Override
	public void add(CategoryPlot subplot) {
		this.add(subplot, 1);
	}

	/**
	 * Adds a new subplot with the specified weight.
	 *
	 * @param subplot
	 *            the subplot.
	 * @param weight
	 *            the weight for the subplot.
	 */
	@Override
	public void add(CategoryPlot subplot, int weight) {
		super.add(subplot, weight);

		ValueAxis l_range = super.getRangeAxis();
		subplot.setRangeAxis(0, l_range, false);

		super.setRangeAxis(l_range);
		if (null == l_range) {
			return;
		}

		l_range.configure();
	}

	/**
	 * Returns the bounds of the data values that will be plotted against the
	 * specified axis.
	 *
	 * @param axis
	 *            the axis.
	 *
	 * @return The bounds.
	 */
	@Override
	public Range getDataRange(ValueAxis axis) {

		Range l_result = null;

		@SuppressWarnings("unchecked")
		Iterator<CategoryPlot> l_itr = getSubplots().iterator();

		while (l_itr.hasNext()) {
			CategoryPlot l_subplot = l_itr.next();

			l_result = Range.combine(l_result, l_subplot.getDataRange(axis));
		}
		return l_result;
	}

	/**
	 * Sets the range axis that is shared by all the subplots.
	 *
	 * @param axis
	 *            the axis.
	 */
	@Override
	public void setRangeAxis(ValueAxis axis) {

		@SuppressWarnings("unchecked")
		Iterator<CategoryPlot> l_itr = getSubplots().iterator();

		while (l_itr.hasNext()) {
			CategoryPlot l_subplot = l_itr.next();
			l_subplot.setRangeAxis(0, axis, false);
		}

		super.setRangeAxis(axis);
		if (null == axis) {
			return;
		}

		axis.configure();
	}
}
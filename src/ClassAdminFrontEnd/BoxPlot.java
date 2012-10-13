package ClassAdminFrontEnd;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;

public class BoxPlot {
	// Constructor
	public BoxPlot() {

	}

	// Create boxplotchart with the parameters
	public JFreeChart createBoxPlot(String title, String xaxis, String yaxis, BoxAndWhiskerCategoryDataset dataset) {
		return ChartFactory.createBoxAndWhiskerChart(title, xaxis, yaxis, dataset, false);
	}
}

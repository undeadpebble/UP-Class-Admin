package ClassAdminFrontEnd;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;

public class Histogram {

	public Histogram()
	{
		
	}
	
	public JFreeChart createHistogram(String plotTitle,String xaxis,String yaxis,HistogramDataset dataset)
	{
		PlotOrientation orientation = PlotOrientation.VERTICAL; 
	    boolean show = false; 
	    boolean toolTips = false;
	    boolean urls = false; 
	    
	   return ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
	             dataset, orientation, show, toolTips, urls);
	}
   
   
    
    
 
}
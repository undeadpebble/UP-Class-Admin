package ClassAdminFrontEnd;

import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.HistogramDataset;

public class Histogram {

	 static class CustomBarRenderer extends BarRenderer
     {

             public Paint getItemPaint(int i, int j)
             {
                     
            	 HistogramDataset x = (HistogramDataset) getPlot().getDataset();
            	// double d = x.getStartXValue(i, j);
            	 	// CategoryDataset categorydataset = getPlot().getDataset();
                    // double d = categorydataset.getValue(i, j).doubleValue();
                    // if (d >= 0.69999999999999996D)
                             return Color.green;
                    // else
                      //       return Color.red;
             }

             public CustomBarRenderer()
             {
             }
     }


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
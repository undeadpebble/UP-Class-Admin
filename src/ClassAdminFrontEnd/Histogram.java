package ClassAdminFrontEnd;

import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.HistogramDataset;

public class Histogram {
	static double q = 4.0;
	 static class CustomBarRenderer extends XYBarRenderer
     {
		public double selectedx =0;
		public double selectedy=0;
             /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
			//Color bar
			public Paint getItemPaint(int i, int j)
             {
                     
            	 HistogramDataset x = (HistogramDataset) getPlot().getDataset();
            	// double d = x.getStartXValue(i, j);
            	
            	// x.
            	// System.out.println(x.getXValue(i, j));
            	// System.out.println(x.getYValue(i, j));
            
            	if(x.getXValue(i, j) == selectedx && x.getYValue(i, j)== selectedy)	
            	 	// CategoryDataset categorydataset = getPlot().getDataset();
                    // double d = categorydataset.getValue(i, j).doubleValue();
                    // if (d >= 0.69999999999999996D)
                             return Color.red;
            	else 
            		return Color.green;
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
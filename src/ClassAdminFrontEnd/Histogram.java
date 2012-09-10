package ClassAdminFrontEnd;

import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.HistogramDataset;

public class Histogram {
	static double q = 4.0;
	JFreeChart chart; 
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
            	 
            	// System.out.println("Series" +x.getEndXValue(0, 2));
            	
            	 // double d = x.getStartXValue(i, j);
            	
            	// x.
   
            	 
            	 //System.out.println(x.getXValue(i, j));
            	 //System.out.println(x.getYValue(i, j)+"asdasd");
            /*	 for(int q=0;q<x.getItemCount(0)-1;q++)
            	 {
            		 System.out.println(x.getY(0, q));
            	 }*/
            	 
            
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
	    
	    chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
	             dataset, orientation, show, toolTips, urls);

		NumberAxis rangeAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
		
		((NumberAxis) rangeAxis).setTickUnit(new NumberTickUnit(1));
		rangeAxis.setRange(0,10);
	    return chart;
	}
   
	public ChartPanel createPanel()
	{
		return null;
		
		/*chartpanel = new ChartPanel(chart,500,500,400,400,500,500,true,true,true,true,true,true);
		
		final XYPlot plot;
		plot = chart.getXYPlot();
		final Plot Nuweplot = chart.getPlot();
		
		final CustomBarRenderer c = new CustomBarRenderer();
		plot.setRenderer(c);
		
			//Select a bar
			chartpanel.addChartMouseListener(new ChartMouseListener() {

				public void chartMouseClicked(ChartMouseEvent e) {
					
					MouseEvent me = e.getTrigger();
					
				
					if(me.isShiftDown() == false)
					chart.getXYPlot().clearAnnotations();
					
					ChartEntity entity = ((ChartMouseEvent) e).getEntity();
					
					if (entity instanceof XYItemEntity && entity != null) {

						XYItemEntity ent = (XYItemEntity) entity;
						
					
						int sindex = ent.getSeriesIndex();
						int iindex = ent.getItem();
						dataset.getStartX(0, iindex);
						dataset.getEndX(0, iindex);
						
						
						selectedindex=getSelectedbar(dataset.getStartX(0, iindex),dataset.getEndX(0, iindex));
						for(int z = 0 ; z<selectedindex.size();z++)
							System.out.println("Selected punt se index "+selectedindex.get(z).toString());
						c.selectedx=5.0;
						c.selectedy=9.0;
						for(int o = 0;o<selectedindex.size();o++)
						{
							Global.getGlobal().getActiveProject().setSelected((Integer) selectedindex.get(o));
						System.out.println("SET SELECTED" + (Integer) selectedindex.get(o) );
						}
						plot.setRenderer(c);
						repaint();
						
						
						
					}

				}

				@Override
				public void chartMouseMoved(ChartMouseEvent arg0) {
					// TODO Auto-generated method stub

				}
				
			});*/
	}
	}
   
    
    
 

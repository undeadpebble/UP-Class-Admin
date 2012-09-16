package ClassAdminFrontEnd;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYDrawableAnnotation;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import ClassAdminBackEnd.Global;



public class ScatterPlot {

	ChartPanel chartPanel ;
	JFreeChart chart; 
	XYPlot plot;
	static XYDataset datasetMain;
	
	
	public void ScatterPlot()
	{
		
	}
	
	
	public void setDatasetmain(XYDataset x)
	{
		datasetMain = x;
	}
	
	
	public JFreeChart createScatter(String title,final XYDataset chartdata,String xas,String yas)
	{
		chart = ChartFactory.createScatterPlot(
				"Scatter Plot Demo", xas, yas, chartdata,
				PlotOrientation.VERTICAL, false, false, false);
		datasetMain = chartdata;
		NumberAxis domainAxis = (NumberAxis) chart.getXYPlot().getDomainAxis();
		domainAxis.setAutoRangeIncludesZero(false);
		
		return chart;
	}	
	
	
	public void updateSelectedvalues()
	{
		
		ArrayList u= Global.getGlobal().getActiveProject().getSelectedIndexes();
		final CircleDrawer cd = new CircleDrawer(Color.red,
				new BasicStroke(1.0f), null);
		
		for(int x=0;x<u.size();x++)
		{
		
		final XYAnnotation selectPlots = new XYDrawableAnnotation(datasetMain
				.getXValue(0, (Integer) u.get(x)), datasetMain.getYValue(0,
						(Integer) u.get(x)), 11, 11, cd);

		chart.getXYPlot().addAnnotation(selectPlots);
		}
	}
	public ChartPanel createPanel()
	{
		chartPanel = new ChartPanel(chart);
		plot = chart.getXYPlot();
		
		
		
			chartPanel.addChartMouseListener(new ChartMouseListener() {

				public void chartMouseClicked(ChartMouseEvent e) {
					
					MouseEvent me = e.getTrigger();
					
				
					if(me.isShiftDown() == false)
					chart.getXYPlot().clearAnnotations();
					
					ChartEntity entity = ((ChartMouseEvent) e).getEntity();
				
					if (entity instanceof XYItemEntity && entity != null) {
						Global.getGlobal().getActiveProject().updatecharts();
						XYItemEntity ent = (XYItemEntity) entity;
					
						
						
						int sindex = ent.getSeriesIndex();
						int iindex = ent.getItem();
						Global.getGlobal().getActiveProject().setSelected(iindex);
						Global.getGlobal().getActiveProject().updatecharts();
						System.out.println("Punt se index"+iindex);
						final CircleDrawer cd = new CircleDrawer(Color.red,
								new BasicStroke(1.0f), null);
						final XYAnnotation bestBid = new XYDrawableAnnotation(datasetMain
								.getXValue(sindex, iindex), datasetMain.getYValue(sindex,
								iindex), 11, 11, cd);

						chart.getXYPlot().addAnnotation(bestBid);

						System.out.println("x = " + datasetMain.getXValue(sindex, iindex));
						System.out.println("y = " + datasetMain.getYValue(sindex, iindex));
					}

				}

				@Override
				public void chartMouseMoved(ChartMouseEvent arg0) {
					// TODO Auto-generated method stub

				}
				
			});

		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		return chartPanel;
	}
	
	    
}

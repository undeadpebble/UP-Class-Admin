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
import ClassAdminBackEnd.Project;



public class ScatterPlot {

	ChartPanel chartPanel ;
	JFreeChart chart; 
	XYPlot plot;
	static XYDataset datasetMain;
	Project project;
	//Constructor
	public ScatterPlot(Project project)
	{
		this.project = project;
		project.updatecharts();
	}
	
	//Set the dataset of the chart
	public void setDatasetmain(XYDataset x)
	{
		datasetMain = x;
	}
	
	//Create the create the scatterplot
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
	
	//Update all the selected values of the scatterplot
	public void updateSelectedvalues()
	{
		chart.getXYPlot().clearAnnotations();
		System.out.println("Ek update scatterchart");
		ArrayList u= project.getSelectedIndexes();
		int [] scatterwaardes = project.getScatterIndexes();
		final CircleDrawer cd = new CircleDrawer(Color.red,
				new BasicStroke(1.0f), null);
		
		for(int x=0;x<u.size();x++)
		{
		
		final XYAnnotation selectPlots = new XYDrawableAnnotation(datasetMain
				.getXValue(0, scatterwaardes[(Integer)u.get(x)]), datasetMain.getYValue(0,
						scatterwaardes[(Integer)u.get(x)]), 11, 11, cd);
		
		chart.getXYPlot().addAnnotation(selectPlots);
		System.out.println(u.get(x));
		System.out.println(scatterwaardes[(Integer)u.get(x)]);
		System.out.println(datasetMain.getXValue(0, scatterwaardes[(Integer)u.get(x)])+"  " + datasetMain.getYValue(0,scatterwaardes[(Integer)u.get(x)]));
		for(int w=0;w<scatterwaardes.length;w++)
			System.out.println(scatterwaardes[w]);
		System.out.println(datasetMain.getXValue(0, 76));
		System.out.println(datasetMain.getYValue(0, 76));
		}
	}
	//Put the chart on the chartpanel
	public ChartPanel createPanel()
	{
		
		chartPanel = new ChartPanel(chart);
		plot = chart.getXYPlot();
		updateSelectedvalues();
		
		
			chartPanel.addChartMouseListener(new ChartMouseListener() {

				public void chartMouseClicked(ChartMouseEvent e) {
					
					MouseEvent me = e.getTrigger();
					
				
					if(me.isShiftDown() == false)
					{
					chart.getXYPlot().clearAnnotations();
					project.clearselected();
					}
					ChartEntity entity = ((ChartMouseEvent) e).getEntity();
				
					if (entity instanceof XYItemEntity && entity != null) {
						
						XYItemEntity ent = (XYItemEntity) entity;
						int [] scatterwaardes = project.getScatterIndexes();
						
						
						int sindex = ent.getSeriesIndex();
						int iindex = ent.getItem();
						for(int q=0;q<scatterwaardes.length;q++)
						{
							if(scatterwaardes[q]== iindex)
								project.setSelected(q);
						}
						
					/*
						final CircleDrawer cd = new CircleDrawer(Color.red,
								new BasicStroke(1.0f), null);
						final XYAnnotation bestBid = new XYDrawableAnnotation(datasetMain
								.getXValue(sindex, iindex), datasetMain.getYValue(sindex,
								iindex), 11, 11, cd);
					
						
						
						chart.getXYPlot().addAnnotation(bestBid);*/
						project.updatecharts();
						
						
					
						
						System.out.println("Series"+sindex +" Index"+iindex);
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

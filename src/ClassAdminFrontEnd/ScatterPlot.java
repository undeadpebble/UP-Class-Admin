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
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.chart.annotations.XYLineAnnotation;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;



import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;



public class ScatterPlot {

	private ChartPanel chartPanel ;
	private JFreeChart chart; 
	private XYPlot plot;
	private XYDataset datasetMain;
	private int[] scatterarray;
	private Project project;
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
				"Scatter Plot", xas, yas, chartdata,
				PlotOrientation.VERTICAL, false, true, false);
		datasetMain = chartdata;
		NumberAxis domainAxis = (NumberAxis) chart.getXYPlot().getDomainAxis();
		domainAxis.setAutoRangeIncludesZero(false);
		XYPlot plot = (XYPlot) chart.getPlot();
		
		XYItemRenderer renderer = plot.getRenderer();
		renderer.setSeriesPaint(0, new Color(0xFF8400));
		 renderer.setBaseToolTipGenerator(new MyXYToolTipGenerator());
                
		return chart;
	}	
	
	//Update all the selected values of the scatterplot
	public void updateSelectedvalues()
	{
		chart.getXYPlot().clearAnnotations();
		
		 XYLineAnnotation a2 = new XYLineAnnotation(79.5, 0.5, 100.0, 0.5);
		 chart.getXYPlot().addAnnotation(a2);
		 XYLineAnnotation a3 = new XYLineAnnotation(79.5, 0.5, 49.5, 29.5);
		 chart.getXYPlot().addAnnotation(a3);
		 XYLineAnnotation a4 = new XYLineAnnotation(49.5, 39.5, 49.5, 29.5);
		 chart.getXYPlot().addAnnotation(a4);
		 XYLineAnnotation a5 = new XYLineAnnotation(49.5, 39.5, 39.5, 49.5);
		 chart.getXYPlot().addAnnotation(a5);
		 XYLineAnnotation a6 = new XYLineAnnotation(39.5, 59, 39.5, 49.5);
		 chart.getXYPlot().addAnnotation(a6);
		 XYLineAnnotation a7 = new XYLineAnnotation(39.5, 59, 59,39.5);
		 chart.getXYPlot().addAnnotation(a7);
		 XYLineAnnotation a8 = new XYLineAnnotation( 59,39.5, 99.5,39.5);
		 chart.getXYPlot().addAnnotation(a8);
		 XYLineAnnotation a9 = new XYLineAnnotation(99.5, 0.5, 99.5,39.5);
		 chart.getXYPlot().addAnnotation(a9);
		
		ArrayList u= project.getSelectedIndexes();
		
	
		final CircleDrawer cd = new CircleDrawer(Color.red,
				new BasicStroke(1.0f), null);
		
		for(int x=0;x<u.size();x++)
		{
			final XYAnnotation selectPlots = new XYDrawableAnnotation(datasetMain
					.getXValue(0, scatterarray[(Integer)u.get(x)]), datasetMain.getYValue(0,
							scatterarray[(Integer)u.get(x)]), 11, 11, cd);
		
		
		chart.getXYPlot().addAnnotation(selectPlots);
		
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
						System.out.println("Selected "+ datasetMain.getXValue(sindex, iindex)+"  " + datasetMain.getYValue(sindex,iindex));
						for(int q=0;q<scatterarray.length;q++)
						{
							if(scatterarray[q]== iindex)
							{
								project.setSelected(q,true);
								
							}
						}
						System.out.println("Ek het klaar geset");
						
					}

				}

				@Override
				public void chartMouseMoved(ChartMouseEvent e) {
				

				}
				
			});

		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		return chartPanel;
	}
	public void setScatterArray(int[] x)
	{
		scatterarray = x;
	}
	    
}

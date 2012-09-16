package ClassAdminFrontEnd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYDataset;

import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.SuperEntity;

public class Histogram {
	static double q = 4.0;
	JFreeChart chart;
	HistogramDataset maindataset;
	ArrayList selectedindex = new ArrayList();
	double[] values;
	int currentdata;
	String[] studentnr;
	
	
    //Update the values 
	public void updateSelectedValues() {
		
		
		double klein = 99999;
		double groot = -1;
		final LinkedList<LinkedList<SuperEntity>> diedata = Global.getGlobal()
				.getActiveProject().getHead().getDataLinkedList();
		ArrayList u = Global.getGlobal().getActiveProject()
				.getSelectedIndexes();

		for (int x = 0; x < u.size(); x++) {
			System.out.println(x);
			if (diedata.get(x).get(currentdata).getMark() < klein)
				klein = diedata.get(x).get(currentdata).getMark();
			if (diedata.get(x).get(currentdata).getMark() > groot)
				groot = diedata.get(x).get(currentdata).getMark();
		}

		ArrayList selectedbars = getSelectedbar(klein, groot);
		double beginx;
		double eindex;
		double xmidvalue;
		CustomBarRenderer barkleurder = new CustomBarRenderer();
		for(int i = 0 ;i<u.size();i++)
		{
			 beginx =(Double) maindataset.getStartX(0, i);
			 eindex =(Double) maindataset.getEndX(0, i);
			 xmidvalue = (beginx+eindex)/2;
			XYDataset currentdataset = ((XYPlot) chart.getPlot()).getDataset();
			System.out.println("X "+xmidvalue+" Y "+currentdataset.getYValue(0, i));
		barkleurder.addselectedbars(xmidvalue,currentdataset.getYValue(0, i));
		}
		
		
		//barkleurder.addselectedbars(x, y);
		chart.getXYPlot().setRenderer(barkleurder);
	}

	// Get index of values in specific bar
	public ArrayList getSelectedbar(Number bgn, Number einde) {

		for (int x = 0; x < values.length; x++) {
			double qbgn = (Double) bgn;
			double qeinde = (Double) einde;
			if (values[x] >= qbgn && values[x] < qeinde) {
				selectedindex.add(x);
			}
		}
		return selectedindex;
	}

	static class CustomBarRenderer extends XYBarRenderer {
		private ArrayList<Double> selectedx = new ArrayList<Double>();
		private ArrayList<Double> selectedy = new ArrayList<Double>();

		/**
		 * 
		 */
		public void clearbars()
		{
			selectedx = new ArrayList<Double>();
			selectedy = new ArrayList<Double>();
			
		}
		public void removeselectedbars(double x, double y) {
			for(int i =0 ; i < selectedx.size();i++)
			{
			if ((x == selectedx.get(i))
					&& (y == selectedy.get(i)))
			{
				selectedx.remove(i);
				selectedy.remove(i);
			}
			}
		}

		// add bars that are selected
		public void addselectedbars(double x, double y) {
			selectedx.add(x);
			selectedy.add(y);
		}

		private static final long serialVersionUID = 1L;

		// Color bar
		public Paint getItemPaint(int i, int j) {

			HistogramDataset x = (HistogramDataset) getPlot().getDataset();

			for (int q = 0; q < selectedx.size(); q++) {
				if ((x.getXValue(i, j) == selectedx.get(q))
						&& (x.getYValue(i, j) == selectedy.get(q)))

					return Color.red;

			}
			return null;
		}

		public CustomBarRenderer() {
		}
	}

	public Histogram() {
		maindataset = new HistogramDataset();
		
	}

	public JFreeChart createHistogram(String plotTitle, String xaxis,
			String yaxis, HistogramDataset dataset) {
		PlotOrientation orientation = PlotOrientation.VERTICAL;
		boolean show = false;
		boolean toolTips = false;
		boolean urls = false;

		chart = ChartFactory.createHistogram(plotTitle, xaxis, yaxis, dataset,
				orientation, show, toolTips, urls);
		maindataset = dataset;
		NumberAxis rangeAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();

		((NumberAxis) rangeAxis).setTickUnit(new NumberTickUnit(1));
		rangeAxis.setRange(0, 10);
		return chart;
	}

	public ChartPanel createPanel() {
		ChartPanel panel = new ChartPanel(chart, 500, 500, 400, 400, 500, 500,
				true, true, true, true, true, true);
		

		final XYPlot plot;
		plot = chart.getXYPlot();
		final Plot Nuweplot = chart.getPlot();
		
		final CustomBarRenderer barkleurder = new CustomBarRenderer();
		//plot.setRenderer(barkleurder);

		// Select a bar
		panel.addChartMouseListener(new ChartMouseListener() {

			public void chartMouseClicked(ChartMouseEvent e) {
				Global.getGlobal().getActiveProject().updatecharts();
				MouseEvent me = e.getTrigger();

				if (me.isShiftDown() == false)
					barkleurder.clearbars();

				ChartEntity entity = ((ChartMouseEvent) e).getEntity();

				if (entity instanceof XYItemEntity && entity != null) {

					XYItemEntity ent = (XYItemEntity) entity;

					int sindex = ent.getSeriesIndex();
					int iindex = ent.getItem();

					maindataset.getStartX(0, iindex);
					maindataset.getEndX(0, iindex);
					
					XYDataset currentdataset = ((XYPlot) chart.getPlot()).getDataset();
					selectedindex = getSelectedbar(maindataset.getStartX(0, iindex),maindataset.getEndX(0, iindex));
					
					for (int z = 0; z < selectedindex.size(); z++) {
						//System.out.println("Selected punt se index "	+ selectedindex.get(z).toString());

					}
					double beginx =(Double) maindataset.getStartX(0, iindex);
					double eindex =(Double) maindataset.getEndX(0, iindex);
					double xmidvalue = (beginx+eindex)/2;
					barkleurder.addselectedbars(xmidvalue,currentdataset.getYValue(0, iindex));
					
					
					// barkleurder.selectedy=currentdataset.getYValue(0, 0);
					for (int o = 0; o < selectedindex.size(); o++) {
						Global.getGlobal().getActiveProject()
								.setSelected((Integer) selectedindex.get(o));
						System.out.println("SET SELECTED" + (Integer) selectedindex.get(o));
					}
					plot.setRenderer(barkleurder);

					/*
					 * CustomBarRenderer x = new CustomBarRenderer();
					 * x.selectedx=15.0; x.selectedy=1.0; plot.setRenderer(x);
					 */

				}

			}

			public void chartMouseMoved(ChartMouseEvent arg0) {

			}

		});
		return panel;
	}

	public HistogramDataset createDataset(int houer) {
		currentdata = houer;
		final LinkedList<LinkedList<SuperEntity>> diedata = Global.getGlobal()
				.getActiveProject().getHead().getDataLinkedList();
		values = new double[diedata.size()];
		studentnr = new String[diedata.size()];

		for (int q = 0; q < diedata.size(); q++) {

			values[q] = diedata.get(q).get(houer).getMark();

			studentnr[q] = diedata.get(q).get(0).getValue();
		//	System.out.println(values[q]);

		}

		maindataset.setType(HistogramType.FREQUENCY);

		maindataset.addSeries("Histogram", values, 10, 0, 100);
		return maindataset;
	}

	public HistogramDataset changeDataset(int houer) {
		final LinkedList<LinkedList<SuperEntity>> diedata = Global.getGlobal()
				.getActiveProject().getHead().getDataLinkedList();
		values = new double[diedata.size()];
		for (int q = 0; q < diedata.size(); q++) {

			values[q] = diedata.get(q).get(houer).getMark();

		}
		HistogramDataset nuwedataset = new HistogramDataset();
		nuwedataset.addSeries("Histogram", values, 10, 0, 100);
		return nuwedataset;
	}

	public HistogramDataset increaseWidth(int widthbar) {

		HistogramDataset nuwedataset = new HistogramDataset();
		nuwedataset.addSeries("Histogram", values, widthbar, 0, 100);
		return nuwedataset;
	}

	public HistogramDataset decreaseWidth(int widthbar) {

		HistogramDataset nuwedataset = new HistogramDataset();
		nuwedataset.addSeries("Histogram", values, widthbar, 0, 100);
		return nuwedataset;
	}
}

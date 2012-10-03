package ClassAdminFrontEnd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
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
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYDataset;

import com.sun.swing.internal.plaf.basic.resources.basic;

import ClassAdminBackEnd.AbsentException;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;

public class Histogram {
	private JFreeChart chart;
	private HistogramDataset maindataset;
	private ArrayList selectedindex = new ArrayList();
	private double[] values;
	private int currentdata;
	private String[] studentnr;
	protected Project project;
	private int widthbar = 10;

	// constructor
	public Histogram(Project project) {
		
		maindataset = new HistogramDataset();
		this.project = project;
	}

	// Updates the histogram values
	public void updateSelectedValues() {

		double klein = 99999;
		double groot = -1;
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead().getDataLinkedList();
		double verdeling;
		int intervalle;
		double intervalhalf;
		ArrayList u = project.getSelectedIndexes();

	
		int[] barsused = new int[getWidthBar()];
		
		intervalle= 100/widthbar;
		intervalhalf = intervalle -1;
		
		if (u.size() != 0) {
			for (int t = 0; t < barsused.length ; t++)
				barsused[t] = 0;
			
			for (int x = 0; x < u.size(); x++) {
				for (int q = 0; q < widthbar; q++) {
					//Check for last value in histogrambar
					if (q == (intervalle - 1)) {
					
						try {
							if ((diedata.get((Integer) u.get(x)).get(currentdata).getMark() <= (q * intervalle + intervalle))
									&& (diedata.get((Integer) u.get(x)).get(currentdata).getMark() >= (q * intervalle)))
								barsused[q] = 1;
						} catch (AbsentException e) {
							barsused[q] = 0;
						}
					} else {

						try {
							if ((diedata.get((Integer) u.get(x)).get(currentdata).getMark() <= (q * intervalle + intervalhalf))
									&& (diedata.get((Integer) u.get(x)).get(currentdata).getMark() >= (q * intervalle))) {

								barsused[q] = 1;
							}
						} catch (AbsentException e) {
							barsused[q] = 0;
						}
					}
				}
			}
		}
		
		setBarcolor(barsused);
		
	}

	// Change the color of bars if the bars are selected
	public void setBarcolor(int[] barsarray) {
		CustomBarRenderer barkleurder = new CustomBarRenderer();
		barkleurder.setShadowVisible(false);

		barkleurder.setOutlinePaint(Color.black);
		barkleurder.setDrawBarOutline(true);
		for (int x = 0; x < barsarray.length; x++) {
			if (barsarray[x] == 1) {

				double beginx = (Double) maindataset.getStartX(0, x);
				double eindex = (Double) maindataset.getEndX(0, x);

				double xmidvalue = (beginx + eindex) / 2;
				XYDataset currentdataset = ((XYPlot) chart.getPlot()).getDataset();
				//Set the value so that the bar can be color in
				barkleurder.addselectedbars(xmidvalue, currentdataset.getYValue(0, x));
				
			}
		}

		chart.getXYPlot().setRenderer(barkleurder);
	}

	
	// Get index of values in specific bar (Spreadsheetview)
	public ArrayList getSelectedbar(Number bgn, Number einde) {
		ArrayList houer = new ArrayList();

		for (int x = 0; x < values.length; x++) {
			double qbgn = (Double) bgn;
			double qeinde = (Double) einde;
	
			if (values[x] >= qbgn && values[x] < qeinde) {
				houer.add(x);
			}
		}
		return houer;
	}

	// Render that helps to change the barcolor
	static class CustomBarRenderer extends XYBarRenderer {
		private ArrayList<Double> selectedx = new ArrayList<Double>();
		private ArrayList<Double> selectedy = new ArrayList<Double>();

		//clear all selected bars
		public void clearbars() {

			selectedx = new ArrayList<Double>();
			selectedy = new ArrayList<Double>();

		}
		
		//Remove one selected bar
		public void removeselectedbars(double x, double y) {
			for (int i = 0; i < selectedx.size(); i++) {
				if ((x == selectedx.get(i)) && (y == selectedy.get(i))) {
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
				if ((x.getXValue(i, j) == selectedx.get(q)) && (x.getYValue(i, j) == selectedy.get(q)))

					return new Color(0xFF8400);

			}
			return new Color(0x00B2E3);
		}

		public CustomBarRenderer() {
		}
	}

	// Creating the histogram jfreechart
	public JFreeChart createHistogram(String plotTitle, String xaxis, String yaxis, HistogramDataset dataset) {
		PlotOrientation orientation = PlotOrientation.VERTICAL;
		boolean show = false;
		boolean toolTips = false;
		boolean urls = false;

		chart = ChartFactory.createHistogram(plotTitle, xaxis, yaxis, dataset, orientation, show, toolTips, urls);
		maindataset = dataset;
	
		final CustomBarRenderer barkleurder = new CustomBarRenderer();
		barkleurder.setDefaultBarPainter(new StandardXYBarPainter(){});
		
				
		barkleurder.setDrawBarOutline(true);
		chart.getXYPlot().setRenderer(barkleurder);
		return chart;
	}

	// Create the panel that the chart will be on
	public ChartPanel createPanel() {
		ChartPanel panel = new ChartPanel(chart, 500, 500, 400, 400, 500, 500, false, false, false, false, false, false);
		updateSelectedValues();
		final XYPlot plot;
		plot = chart.getXYPlot();
		final Plot Nuweplot = chart.getPlot();

		final CustomBarRenderer barkleurder = new CustomBarRenderer();
		
	
		
		// Select a bar
		panel.addChartMouseListener(new ChartMouseListener() {

			public void chartMouseClicked(ChartMouseEvent e) {

				MouseEvent me = e.getTrigger();
				// Look at multiple selections
				if (me.isShiftDown() == false) {
					barkleurder.clearbars();
					project.clearselected();
				}

				ChartEntity entity = ((ChartMouseEvent) e).getEntity();
	
				if (entity instanceof XYItemEntity && entity != null) {

					XYItemEntity ent = (XYItemEntity) entity;

					int sindex = ent.getSeriesIndex();
					int iindex = ent.getItem();

					maindataset.getStartX(0, iindex);
					Number eindehouer =maindataset.getEndX(0, iindex);
					
					//Make provision for the last value that might be 100
					if ((Double)eindehouer == 100)
							eindehouer = 101.0;
					
					XYDataset currentdataset = ((XYPlot) chart.getPlot()).getDataset();
					selectedindex = getSelectedbar(maindataset.getStartX(0, iindex), eindehouer);
			

					for (int o = 0; o < selectedindex.size(); o++) {
						project.setSelected((Integer) selectedindex.get(o),true);

					}
					barkleurder.setShadowVisible(false);
					plot.setRenderer(barkleurder);
					project.updatecharts();

				}

			}

			public void chartMouseMoved(ChartMouseEvent arg0) {

			}

		});
		return panel;
	}

	// Create the dataset of the chart
	public HistogramDataset createDataset(int houer) {
		currentdata = houer;
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead().getDataLinkedList();

		values = new double[diedata.size()];
		studentnr = new String[diedata.size()];

		for (int q = 0; q < diedata.size(); q++) {

			try {
				values[q] = diedata.get(q).get(houer).getMark();
			} catch (AbsentException e) {
				values[q] = 0;
			}

			studentnr[q] = diedata.get(q).get(0).getValue();

		}

		maindataset.setType(HistogramType.FREQUENCY);

		maindataset.addSeries("Histogram", values, 10, 0, 100);
		return maindataset;
	}

	// Change the dataset of the current chart
	public HistogramDataset changeDataset(int houer) {
		currentdata= houer;
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead().getDataLinkedList();

		values = new double[diedata.size()];
		for (int q = 0; q < diedata.size(); q++) {

			try {
				values[q] = diedata.get(q).get(houer).getMark();
			} catch (AbsentException e) {
				values[q]=0;
			}

		}
		HistogramDataset nuwedataset = new HistogramDataset();
		nuwedataset.addSeries("Histogram", values, 10, 0, 100);
		maindataset =nuwedataset;
		return nuwedataset;
	}
	
	//Change the type of Histogram
	public HistogramDataset changeHistogramType() {
		maindataset.setType(HistogramType.RELATIVE_FREQUENCY);
		return maindataset;
	}
	
	//Change the type of Histogram
		public HistogramDataset changeToNormalHistogramType() {
			maindataset.setType(HistogramType.FREQUENCY);
			return maindataset;
		}

	// Increase the width of the bars
	public HistogramDataset changebarWidth(int widthbarb) {
		widthbar = widthbarb;
		HistogramDataset nuwedataset = new HistogramDataset();
		nuwedataset.addSeries("Histogram", values, widthbar, 0, 100);
		maindataset =nuwedataset;
		return nuwedataset;
	}


	// Get the width of the bar
	public int getWidthBar() {
		return widthbar;
	}

	// Set the width of the bar
	public void setWidthBar(int barwidth) {
		widthbar = barwidth;
	}
}

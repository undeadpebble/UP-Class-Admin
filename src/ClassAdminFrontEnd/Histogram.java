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

	// -------------------------------------------------------------------------------------------------------
	public Histogram(Project project) {
		maindataset = new HistogramDataset();
		this.project = project;
	}
	
	// -------------------------------------------------------------------------------------------------------
	// Update the values
	public void updateSelectedValues() {

		double klein = 99999;
		double groot = -1;
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead().getDataLinkedList();
		double verdeling;
		
		
		
		
		ArrayList u = project.getSelectedIndexes();
		
		if(u.size()!=0)
		{
		int[] barsused = new int[widthbar];
		for(int x =0;x<u.size();x++)
		{
		for(int q= 0 ; q< widthbar;q++)
		{
		if(diedata.get(x).get(currentdata).getMark()<(q*10+9))
			barsused[q] =1;
		;
		}
		}
		}
		
		
		
		
		
		System.out.println("Size"+u.size());
		if(u.size()!=0)
		{
		for (int x = 0; x < diedata.size(); x++) {
			if (diedata.get(x).get(currentdata).getMark() < klein)
				klein = diedata.get(x).get(currentdata).getMark();
			
			if (diedata.get(x).get(currentdata).getMark() > groot)
				groot = diedata.get(x).get(currentdata).getMark();
		}
		System.out.println("KleinSte" + klein);
		System.out.println("Grootste" + groot);
		ArrayList selectedbars = getSelectedbar(klein, groot);
		setBarcolor(klein, groot);
		

		/*double beginx;
		double eindex;
		double xmidvalue;
		CustomBarRenderer barkleurder = new CustomBarRenderer();
		for (int i = 0; i < u.size(); i++) {
			beginx = (Double) maindataset.getStartX(0, i);
			eindex = (Double) maindataset.getEndX(0, i);
			xmidvalue = (beginx + eindex) / 2;
			XYDataset currentdataset = ((XYPlot) chart.getPlot()).getDataset();
		//	System.out.println("X " + xmidvalue + " Y "			+ currentdataset.getYValue(0, i));
			barkleurder.addselectedbars(xmidvalue,currentdataset.getYValue(0, i));
		}

		// barkleurder.addselectedbars(x, y);
		chart.getXYPlot().setRenderer(barkleurder);*/
		}
	}
	// -------------------------------------------------------------------------------------------------------
	// Get the coordinates of the bars
	public void setBarcolor(Number bgn, Number einde)
	{
		CustomBarRenderer barkleurder = new CustomBarRenderer();
		ArrayList houer = new ArrayList();
		for (int x = 0; x < values.length; x++) {
			double qbgn = (Double) bgn;
			double qeinde = (Double) einde;
			if (values[x] >= qbgn && values[x] < qeinde) {
				
				double beginx = (Double) maindataset.getStartX(0, x);
				double eindex = (Double) maindataset.getEndX(0, x);
				System.out.println("EINDE"+ qeinde);
				System.out.println("Einde van dataset" + eindex);
				double xmidvalue = (beginx + eindex) / 2;
				XYDataset currentdataset = ((XYPlot) chart.getPlot()).getDataset();
				if((Double)einde >= (eindex-1))
				barkleurder.addselectedbars(xmidvalue,currentdataset.getYValue(0, x));
				//System.out.println("X " + xmidvalue + " Y "			+ currentdataset.getYValue(0, x));
			}
		}
		chart.getXYPlot().setRenderer(barkleurder);
	}
	// -------------------------------------------------------------------------------------------------------
	// Get index of values in specific bar
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

	// -------------------------------------------------------------------------------------------------------
	static class CustomBarRenderer extends XYBarRenderer {
		private ArrayList<Double> selectedx = new ArrayList<Double>();
		private ArrayList<Double> selectedy = new ArrayList<Double>();

		/**
		 * 
		 */
		public void clearbars() {
			
			selectedx = new ArrayList<Double>();
			selectedy = new ArrayList<Double>();

		}

		public void removeselectedbars(double x, double y) {
			for (int i = 0; i < selectedx.size(); i++) {
				if ((x == selectedx.get(i)) && (y == selectedy.get(i))) {
					selectedx.remove(i);
					selectedy.remove(i);
				}
			}
		}

		// -------------------------------------------------------------------------------------------------------
		// add bars that are selected
		public void addselectedbars(double x, double y) {
			System.out.println("Selected bar se x"+x+"Selected bar se y "+y);
			selectedx.add(x);
			selectedy.add(y);
		}

		private static final long serialVersionUID = 1L;

		// -------------------------------------------------------------------------------------------------------
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

	// -------------------------------------------------------------------------------------------------------

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

	//	((NumberAxis) rangeAxis).setTickUnit(new NumberTickUnit(1));
		//rangeAxis.setRange(0, );
		final CustomBarRenderer barkleurder = new CustomBarRenderer();
		chart.getXYPlot().setRenderer(barkleurder);
		return chart;
	}

	// -------------------------------------------------------------------------------------------------------
	public ChartPanel createPanel() {
		ChartPanel panel = new ChartPanel(chart, 500, 500, 400, 400, 500, 500,
				true, true, true, true, true, true);
		updateSelectedValues();
		final XYPlot plot;
		plot = chart.getXYPlot();
		final Plot Nuweplot = chart.getPlot();

		final CustomBarRenderer barkleurder = new CustomBarRenderer();
		 
		// Select a bar
		panel.addChartMouseListener(new ChartMouseListener() {

			public void chartMouseClicked(ChartMouseEvent e) {
				
				
				MouseEvent me = e.getTrigger();

				if (me.isShiftDown() == false)
				{
				//	System.out.println("Word gecall");
					barkleurder.clearbars();
					project.clearselected();
				}
				ChartEntity entity = ((ChartMouseEvent) e).getEntity();
				System.out.println(entity.toString());
				if (entity instanceof XYItemEntity && entity != null) {

					XYItemEntity ent = (XYItemEntity) entity;

					int sindex = ent.getSeriesIndex();
					int iindex = ent.getItem();

					maindataset.getStartX(0, iindex);
					maindataset.getEndX(0, iindex);

					XYDataset currentdataset = ((XYPlot) chart.getPlot())
							.getDataset();
					selectedindex = getSelectedbar(
							maindataset.getStartX(0, iindex),
							maindataset.getEndX(0, iindex));

/*					for (int z = 0; z < selectedindex.size(); z++) {
						// System.out.println("Selected punt se index " +
						// selectedindex.get(z).toString());

					}
					double beginx = (Double) maindataset.getStartX(0, iindex);
					double eindex = (Double) maindataset.getEndX(0, iindex);
					double xmidvalue = (beginx + eindex) / 2;
					barkleurder.addselectedbars(xmidvalue,currentdataset.getYValue(0, iindex));
*/
					
					for (int o = 0; o < selectedindex.size(); o++) {
						project.setSelected((Integer) selectedindex.get(o));
						
					}
					plot.setRenderer(barkleurder);
					project.updatecharts();
					
					
				}
				

			}

			public void chartMouseMoved(ChartMouseEvent arg0) {

			}

		});
		return panel;
	}

	// -------------------------------------------------------------------------------------------------------
	public HistogramDataset createDataset(int houer) {
		currentdata = houer;
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead().getDataLinkedList();
		
		values = new double[diedata.size()];
		studentnr = new String[diedata.size()];

		for (int q = 0; q < diedata.size(); q++) {

			values[q] = diedata.get(q).get(houer).getMark();

			studentnr[q] = diedata.get(q).get(0).getValue();
			

		}

		maindataset.setType(HistogramType.FREQUENCY);

		maindataset.addSeries("Histogram", values, 10, 0, 100);
		return maindataset;
	}

	// -------------------------------------------------------------------------------------------------------
	public HistogramDataset changeDataset(int houer) {
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead().getDataLinkedList();
		
		values = new double[diedata.size()];
		for (int q = 0; q < diedata.size(); q++) {

			values[q] = diedata.get(q).get(houer).getMark();

		}
		HistogramDataset nuwedataset = new HistogramDataset();
		nuwedataset.addSeries("Histogram", values, 10, 0, 100);
		return nuwedataset;
	}

	// -------------------------------------------------------------------------------------------------------
	public HistogramDataset increaseWidth(int widthbarb) {
		widthbar = widthbarb;
		HistogramDataset nuwedataset = new HistogramDataset();
		nuwedataset.addSeries("Histogram", values, widthbar, 0, 100);
		return nuwedataset;
	}

	// -------------------------------------------------------------------------------------------------------
	public HistogramDataset decreaseWidth(int widthbard) {
		widthbar = widthbard;
		HistogramDataset nuwedataset = new HistogramDataset();
		nuwedataset.addSeries("Histogram", values, widthbar, 0, 100);
		return nuwedataset;
	}
	
	public int getWidthBar()
	{
		return widthbar;
	}
	public void setWidthBar(int barwidth)
	{
		widthbar = barwidth;
	}
}

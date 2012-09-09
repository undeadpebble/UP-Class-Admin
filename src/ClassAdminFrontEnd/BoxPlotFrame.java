package ClassAdminFrontEnd;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.SuperEntity;

public class BoxPlotFrame extends JFrame implements ActionListener {
	static ChartPanel chartpanel;
	static JFreeChart chart;
	int houerx;
	int headerindex=0;
	protected final String[] kolom = Global.getGlobal().getActiveProject().getHead()
			.getNumberHeaders();
	protected final LinkedList<LinkedList<SuperEntity>> diedata = Global.getGlobal()
			.getActiveProject().getHead().getDataLinkedList();
	protected final String[] headers = Global.getGlobal().getActiveProject()
			.getHead().getHeaders();
	protected static int teller = 0;
	protected final int seriesCount = 1;
	protected final int categoryCount = 1;
	protected final int entityCount = diedata.size();
	protected final BoxPlotOptionMenu box= new BoxPlotOptionMenu();
	protected static final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
	public BoxPlotFrame()
	{
		
	}
	public void createBoxPlotFrame() {

		JFrame f = new JFrame("BoxPlot");
		final Container content = f.getContentPane();
		f.setSize(450, 500);

		
		final BoxPlot nuweChart = new BoxPlot();
		chart = nuweChart.createBoxPlot("BoxPlot", "", "", dataset);
		chartpanel = new ChartPanel(chart, 400, 400, 100, 100, 400, 400, true,
				true, true, true, true, true);
		
		
	
		JButton addseries = new JButton("Add a series");
		//Series can be added dynamically
		addseries.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

				
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			
				
			box.createFrame();
							
			
			}
		});

		JButton rotate = new JButton("Rotate");

		rotate.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		//Extract chart to JPG
		JButton extractPic = new JButton("Extract");
		extractPic.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {

					// saveJPG.saveToFile(chart,"test.jpg",500,300,100);
				} catch (Exception e1) {

					e1.printStackTrace();

				}
			}
		});

		content.setBackground(Color.white);
		content.setLayout(new FlowLayout());
		content.add(chartpanel);
		content.add(addseries);
		content.add(rotate);
		content.add(extractPic);

		f.setVisible(true);
	}
	public void addBoxSeries()
	{
	
		ArrayList nuwe = new ArrayList();
	
			for (int k = 0; k < diedata.size(); k++) {

				nuwe.add(diedata.get(k).get(box.getIndexOfHeader()).getMark());

				
			}
			teller +=1;
			dataset.add(nuwe, "Series" + teller, headers[box.getIndexOfHeader()]);
			
			chartpanel.getChart().getCategoryPlot().setDataset(dataset);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
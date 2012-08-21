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

	protected final int seriesCount = 1;
	protected final int categoryCount = 1;
	protected final int entityCount = diedata.size();
	protected final BoxPlotOptionMenu box= new BoxPlotOptionMenu();
	protected final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
	public BoxPlotFrame()
	{
		
	}
	public void createBoxPlotFrame() {

		JFrame f = new JFrame("BoxPlot");
		final Container content = f.getContentPane();
		f.setSize(450, 500);

		

		/*for (int s = 0; s < headers.length; s++) {
			if (headers[s].equals(kolom[0])) {
				houerx = s;
			}
		}

		final ArrayList list = new ArrayList();
		for (int k = 0; k < entityCount; k++) {

			list.add(diedata.get(k).get(houerx).getMark());
		}

		dataset.add(list, "Series" + 1, headers[houerx]);*/

		final BoxPlot nuweChart = new BoxPlot();
		chart = nuweChart.createBoxPlot("BoxPlot", "", "", dataset);
		chartpanel = new ChartPanel(chart, 400, 400, 100, 100, 400, 400, true,
				true, true, true, true, true);
		
		
		/*for(int x= 0 ; x<kolom.length  ; x++)
		{ System.out.println(x+" "+kolom[x]);}*/
		JButton addseries = new JButton("Add a series");

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
				
				/*headerindex +=1;
				System.out.println(headerindex);
				System.out.println(kolom.length);
				for (int s = 0; s < headers.length; s++) {
					
					
					if(headerindex <= kolom.length-1)
					{
					if (headers[s].equals(kolom[headerindex])) {
						houerx = s;
						System.out.println(headers[s]);
					}
					}
				}*/
				
			box.createFrame();
				/*ArrayList nuwe = new ArrayList();
			//	System.out.println(headerindex);
			//	System.out.println(headers[houerx]);
				for (int k = 0; k < diedata.size(); k++) {

					nuwe.add(diedata.get(k).get(x.getIndexOfHeader()).getMark());

					System.out.println(diedata.get(k).get(x.getIndexOfHeader()).getMark());
				}
				dataset.add(nuwe, "Series" + 2, headers[x.getIndexOfHeader()]);
				chartpanel.getChart().getCategoryPlot().setDataset(dataset);*/
			//}
				
				//System.out.println(headers[]);
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
		// content.add(lblNewLabel);
		 //content.add(xascb);
		content.add(rotate);
		content.add(extractPic);

		f.setVisible(true);
	}
	public void addBoxSeries()
	{
		ArrayList nuwe = new ArrayList();
		//	System.out.println(headerindex);
		//	System.out.println(headers[houerx]);
			for (int k = 0; k < diedata.size(); k++) {

				nuwe.add(diedata.get(k).get(box.getIndexOfHeader()).getMark());

				//System.out.println(diedata.get(k).get(box.getIndexOfHeader()).getMark());
			}
			dataset.add(nuwe, "Series" + 2, headers[box.getIndexOfHeader()]);
			chartpanel.getChart().getCategoryPlot().setDataset(dataset);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
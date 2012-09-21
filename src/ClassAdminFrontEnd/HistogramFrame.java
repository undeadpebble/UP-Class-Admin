package ClassAdminFrontEnd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.lang.model.type.UnknownTypeException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import com.keypoint.PngEncoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.imageio.plugins.png.PNGImageReader;
import com.sun.imageio.plugins.png.PNGImageWriter;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminFrontEnd.Histogram.CustomBarRenderer;

public class HistogramFrame extends JFrame implements ActionListener {
	private static ChartPanel chartpanel;
	private static JFreeChart chart;
	private int houerx = 0;
	private double[] values;
	private String[] studentnr;
	private double[][] studentref;
	//private int widthbar = 10;
	private ArrayList selectedindex = new ArrayList();
	private Histogram nuweChart;
	private Project project;

	//Update the values of the histogram
	public void update() {
		nuweChart.updateSelectedValues();
	}

	//Create the frame of the histogram
	public HistogramFrame(Project project) {
		JFrame f = new JFrame("Histogram");
		final Container content = f.getContentPane();
		f.setSize(550, 600);

		this.project = project;
		final LinkedList<LinkedList<SuperEntity>> diedata = project.getHead()
				.getDataLinkedList();
		
		final String[] headers = project.getHead().getHeaders();
		
		String[] kolom = project.getHead().getNumberHeaders();
		

		String plotTitle = "Histogram";
		String xaxis = kolom[0];
		String yaxis = "Count";

		nuweChart = new Histogram(project);

		final HistogramDataset dataset = new HistogramDataset();

		String xas = kolom[0];

		for (int s = 0; s < headers.length; s++) {
			if (headers[s].equals(kolom[0])) {
				houerx = s;

			}

		}
		
		chart = nuweChart.createHistogram(plotTitle, xaxis, yaxis,
				nuweChart.createDataset(houerx));

		chartpanel = nuweChart.createPanel();

		JLabel lblNewLabel = new JLabel("X-axis");

		final JComboBox xascb = new JComboBox();
		//Combobox van X-axis
		xascb.setModel(new DefaultComboBoxModel(kolom));
		xascb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String axis = (String) cb.getSelectedItem();
				chartpanel.getChart().getXYPlot().getDomainAxis()
						.setLabel(axis);

				chartpanel.getChart().getXYPlot().clearAnnotations();
				for (int s = 0; s < headers.length; s++) {
					if (headers[s].equals(cb.getSelectedItem().toString())) {
						houerx = s;

					}

				}

				/*
				 * values = new double[diedata.size()]; for (int q = 0; q <
				 * diedata.size(); q++) {
				 * 
				 * values[q] = diedata.get(q).get(houerx).getMark();
				 * 
				 * 
				 * } HistogramDataset nuwedataset = new HistogramDataset();
				 * nuwedataset.addSeries("Histogram", values, 10,0,100);
				 */
				chartpanel.getChart().getXYPlot()
						.setDataset(nuweChart.changeDataset(houerx));

			}

		});
		//Cycle through the data left
		JButton switchlinksx = new JButton("<");
		switchlinksx.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (xascb.getSelectedIndex() >= 1)
					xascb.setSelectedIndex(xascb.getSelectedIndex() - 1);

			}
		});
		//Cycle through data right
		JButton switchregsx = new JButton(">");
		switchregsx.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (xascb.getSelectedIndex() < xascb.getItemCount() - 1)
					xascb.setSelectedIndex(xascb.getSelectedIndex() + 1);

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
		//Extract the chart  as a jpg
		JButton extractPic = new JButton("Extract chart");
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
					saveFileAs();
					// saveJPG.saveToFile(chart,"test.jpg",500,300,100);
				} catch (Exception e1) {

					e1.printStackTrace();

				}
			}

		});
		final JLabel width = new JLabel("Width");
		JButton widthsmall = new JButton("<");
		//Change the widht of the bars smaller
		widthsmall.addMouseListener(new MouseListener() {

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
				if (nuweChart.getWidthBar() % 10 == 0)
					nuweChart.setWidthBar(nuweChart.getWidthBar() +10);
					//widthbar += 10;
				else
					nuweChart.setWidthBar(nuweChart.getWidthBar() +9);
					//widthbar += 9;
				/*
				 * HistogramDataset nuwedataset = new HistogramDataset();
				 * nuwedataset.addSeries("Histogram", values,widthbar ,0,100);
				 */
				chartpanel.getChart().getXYPlot()
						.setDataset(nuweChart.increaseWidth(nuweChart.getWidthBar()));

			}
		});
		
		JButton widthlarge = new JButton(">");
		//Change the width of the bars bigger
		widthlarge.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (nuweChart.getWidthBar() >10)
					nuweChart.setWidthBar(nuweChart.getWidthBar() -10);
					//widthbar += 10;
				else if(nuweChart.getWidthBar()>1)
					nuweChart.setWidthBar(nuweChart.getWidthBar() -1);
					//widthbar += 9;
				/*
				 * HistogramDataset nuwedataset = new HistogramDataset();
				 * nuwedataset.addSeries("Histogram", values,widthbar ,0,100);
				 */
				chartpanel.getChart().getXYPlot()
						.setDataset(nuweChart.increaseWidth(nuweChart.getWidthBar()));

				
				
				
				
			/*	if (widthbar > 10)
					widthbar -= 10;
				else if (widthbar > 1)
					widthbar -= 1;*/

				/*
				 * HistogramDataset nuwedataset = new HistogramDataset();
				 * nuwedataset.addSeries("Histogram", values,widthbar ,0,100);
				 */
				/*chartpanel.getChart().getXYPlot()
						.setDataset(nuweChart.decreaseWidth(widthbar));*/

			}
		});
		
		content.setLayout(new FlowLayout());
		content.add(chartpanel);
		content.add(lblNewLabel);
		content.add(xascb);
		content.add(switchlinksx);
		content.add(switchregsx);
		content.add(rotate);
		content.add(extractPic);
		content.add(width);
		content.add(widthsmall);
		content.add(widthlarge);
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
	//SaveFileAs for extract
	public void saveFileAs() throws IOException {

		File file;

		// Create a file chooser
		final JFileChooser filechooser = new JFileChooser();

		// shows the dialog, return value specifies file
		int returnVal = filechooser.showSaveDialog(this);

		// if the chosen file is valid
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = filechooser.getSelectedFile();
			try {

				saveToFile(chart, file.getAbsolutePath() + ".png", 500, 300,
						100);
			} catch (UnknownTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}
	}

	//Save the Jfreechart in a directory as a jpg
	public static void saveToFile(JFreeChart chart, String aFileName,
			int width, int height, double quality)
			throws FileNotFoundException, IOException {
		BufferedImage img = draw(chart, width, height);
	
		 byte[] pngbytes;
		 PngEncoder png = new PngEncoder(img);
		
		 
		  try
	        {
	            FileOutputStream outfile = new FileOutputStream( aFileName );
	            pngbytes = png.pngEncode();
	            if (pngbytes == null)
	            {
	                System.out.println("Null image");
	            }
	            else
	            {
	                outfile.write( pngbytes );
	            }
	            outfile.flush();
	            outfile.close();
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
		
	}

	// Create jpg image
	protected static BufferedImage draw(JFreeChart chart, int width, int height)

	{

		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = img.createGraphics();

		chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));
		g2.dispose();

		return img;

	}
}

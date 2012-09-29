package ClassAdminFrontEnd;

import java.awt.*;
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
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.keypoint.PngEncoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;

public class ScatterPlotFrame extends JFrame implements ActionListener {
	static ChartPanel chartpanel;
	static JFreeChart chart;
	private int houerx = 0;
	private int houery = 0;
	private ScatterPlot nuweChart;
	private Project project;
	private LinkedList<LinkedList<SuperEntity>> diedata ;


	// Update all the values of the scatterplot
	public void update() {

		nuweChart.updateSelectedvalues();
	}
	//sort scatterchart
	public int[] doensorteer(int xgetal, int ygetal)
	{
		// Sorting for scatterselection
				double[] sorteermidq = new double[diedata.size()];
				double[] sorteermidw = new double[diedata.size()];
				int[] scattergetalle = new int[diedata.size()];
				for (int q = 0; q < diedata.size(); q++) {
					sorteermidq[q] = diedata.get(q).get(xgetal).getMark();
					sorteermidw[q] = diedata.get(q).get(ygetal).getMark();
					scattergetalle[q] = q;

				}

				

				int n = sorteermidq.length;
				double temp = 0;
				double temp2 = 0;
				int temp3 = 0;
				// Bubblesort
				for (int i = 0; i < n; i++) {
					for (int j = 1; j < (n - i); j++) {
						if (sorteermidq[j - 1] > sorteermidq[j]) {

							// swap the elements!
							temp2 = sorteermidw[j - 1];
							sorteermidw[j - 1] = sorteermidw[j];
							sorteermidw[j] = temp2;

							temp = sorteermidq[j - 1];
							sorteermidq[j - 1] = sorteermidq[j];
							sorteermidq[j] = temp;

							temp3 = scattergetalle[j - 1];
							scattergetalle[j - 1] = scattergetalle[j];
							scattergetalle[j] = temp3;

						}

					}
				}

				int[] houer = new int[scattergetalle.length];

				for (int x = 0; x < sorteermidq.length; x++) {
					System.out.println("Sorteer deeerder " + x + "   " + sorteermidq[x] + " " + sorteermidw[x] + " " + scattergetalle[x]);

				}

				int houerflip;
				int houerflip2;
				for (int i = 0; i < scattergetalle.length; i++) {

					houerflip = scattergetalle[i];
					houerflip2 = scattergetalle[houerflip];
					houer[houerflip2] = houerflip;
					// System.out.println(flipen2+" "+flipen);

				}

				for (int x = 0; x < scattergetalle.length; x++)
					System.out.println(houer[x]);
				
				return houer;
	}
	// Create the scatterplotframe
	public ScatterPlotFrame(final Project project) {
		System.out.println("Toet2s");
		JFrame f = new JFrame("ScatterPlot");
		this.project = project;
		final Container content = f.getContentPane();
		f.setSize(550, 500);
		diedata = project.getHead().getDataLinkedList();

		final XYSeriesCollection dataset = new XYSeriesCollection();
		final String[] headers = project.getHead().getHeaders();

		nuweChart = new ScatterPlot(project);

		String[] kolom = project.getHead().getNumberHeaders();

		String xas = kolom[0];
		String yas = kolom[1];

		// get the first headers value
		for (int s = 0; s < headers.length; s++) {
			if (headers[s].equals(kolom[0])) {
				houerx = s;

			}

		}
		// get the second headers value
		for (int s = 0; s < headers.length; s++) {
			if (headers[s].equals(kolom[1])) {
				houery = s;

			}

		}
		
		XYSeries series = new XYSeries("Scatter");
		// Add to series

		for (int q = 0; q < diedata.size(); q++) {
			series.add(diedata.get(q).get(houerx).getMark(), diedata.get(q).get(houery).getMark());

		}
		

		
		
		project.setScatterSelect(doensorteer(houerx, houery));

		dataset.addSeries(series);

		chart = nuweChart.createScatter("asd", dataset, xas, yas);
		chartpanel = nuweChart.createPanel();

		JLabel lblNewLabel = new JLabel("X-axis");
		final JComboBox xascb = new JComboBox();
		// Combobox of X-axis
		xascb.setModel(new DefaultComboBoxModel(kolom));
		xascb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String axis = (String) cb.getSelectedItem();
				chartpanel.getChart().getXYPlot().getDomainAxis().setLabel(axis);
				chartpanel.getChart().getXYPlot().clearAnnotations();

				for (int s = 0; s < headers.length; s++) {
					if (headers[s].equals(cb.getSelectedItem().toString())) {
						houerx = s;

					}

				}

				XYSeriesCollection nuwedataset = new XYSeriesCollection();
				XYSeries series = new XYSeries("Scatter");

				for (int q = 0; q < diedata.size(); q++) {
					series.add(diedata.get(q).get(houerx).getMark(), diedata.get(q).get(houery).getMark());

				}
				project.setScatterSelect(doensorteer(houerx, houery));
				nuwedataset.addSeries(series);
				chartpanel.getChart().getXYPlot().setDataset(nuwedataset);
				nuweChart.setDatasetmain(nuwedataset);
				project.updatecharts();
			}

		});
		// Cycle of X-axis combobox left
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
				{
					xascb.setSelectedIndex(xascb.getSelectedIndex() - 1);
				//	project.setScatterSelect(doensorteer(houerx, houery));
				project.updatecharts();
				}
			}
		});
		// Cycle of X-axis combobox right
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
				if (xascb.getSelectedIndex() < xascb.getItemCount())
				{
					xascb.setSelectedIndex(xascb.getSelectedIndex() + 1);
					project.updatecharts();
				}
			}
		});

		JLabel lblNewLabel_1 = new JLabel("Y-axis");

		final JComboBox yascb = new JComboBox();
		yascb.setModel(new DefaultComboBoxModel(kolom));
		yascb.setSelectedIndex(1);
		// Combobox of Y-axis
		yascb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String axis = (String) cb.getSelectedItem();
				chartpanel.getChart().getXYPlot().getRangeAxis().setLabel(axis);

				chartpanel.getChart().getXYPlot().clearAnnotations();

				for (int s = 0; s < headers.length; s++) {
					if (headers[s].equals(cb.getSelectedItem().toString())) {
						houery = s;
					}

				}
				XYSeriesCollection nuwedataset = new XYSeriesCollection();
				XYSeries series = new XYSeries("Scatter");

				for (int q = 0; q < diedata.size() ; q++) {
					series.add(diedata.get(q).get(houerx).getMark(), diedata.get(q).get(houery).getMark());

				}
				project.setScatterSelect(doensorteer(houerx, houery));
				nuwedataset.addSeries(series);
				chartpanel.getChart().getXYPlot().setDataset(nuwedataset);
				nuweChart.setDatasetmain(nuwedataset);
				project.updatecharts();
			}

		});
		// Cycle of Y-axis combobox left
		JButton switchlinksy = new JButton("<");
		switchlinksy.addMouseListener(new MouseListener() {

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
				if (yascb.getSelectedIndex() >= 1)
				{
					yascb.setSelectedIndex(yascb.getSelectedIndex() - 1);
					project.updatecharts();
				}
			}
		});
		// Cycle of Y-axis combobox right
		JButton switchregsy = new JButton(">");
		switchregsy.addMouseListener(new MouseListener() {

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

				if (yascb.getSelectedIndex() < yascb.getItemCount())
				{
					yascb.setSelectedIndex(yascb.getSelectedIndex() + 1);
					project.updatecharts();
				}
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
		// Extract Jfreechart as a jpg
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

				} catch (Exception e1) {

					e1.printStackTrace();

				}
			}
		});

		content.setBackground(Color.white);
		content.setLayout(new FlowLayout());
		content.add(chartpanel);
		content.add(lblNewLabel);
		content.add(xascb);
		content.add(switchlinksx);
		content.add(switchregsx);
		content.add(lblNewLabel_1);
		content.add(yascb);
		content.add(switchlinksy);
		content.add(switchregsy);
		content.add(rotate);
		content.add(extractPic);

		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	// ----------------------------------------------------------------------------------------------
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

				saveToFile(chart, file.getAbsolutePath() + ".png", 500, 300, 100);
			} catch (UnknownTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}
	}

	// ----------------------------------------------------------------------------------------------
	public static void saveToFile(JFreeChart chart, String aFileName, int width, int height, double quality) throws FileNotFoundException,
			IOException {
		BufferedImage img = draw(chart, width, height);
		byte[] pngbytes;
		PngEncoder png = new PngEncoder(img);

		try {
			FileOutputStream outfile = new FileOutputStream(aFileName);
			pngbytes = png.pngEncode();
			if (pngbytes == null) {
				System.out.println("Null image");
			} else {
				outfile.write(pngbytes);
			}
			outfile.flush();
			outfile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ----------------------------------------------------------------------------------------------
	protected static BufferedImage draw(JFreeChart chart, int width, int height)

	{

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = img.createGraphics();

		chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));
		g2.dispose();

		return img;

	}

}

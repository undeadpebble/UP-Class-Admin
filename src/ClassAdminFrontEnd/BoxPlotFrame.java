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
import java.util.Random;

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
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.keypoint.PngEncoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import ClassAdminBackEnd.AbsentException;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;

public class BoxPlotFrame extends JFrame implements ActionListener {
	private static ChartPanel chartpanel;
	private static JFreeChart chart;
	private int houerx;
	private int headerindex = 0;
	private final String[] kolom = Global.getGlobal().getActiveProject().getHead().getNumberHeaders();
	private final LinkedList<LinkedList<SuperEntity>> diedata = Global.getGlobal().getActiveProject().getHead().getDataLinkedList();
	private final String[] headers = Global.getGlobal().getActiveProject().getHead().getHeaders();
	private static int teller = 0;
	private final int seriesCount = 1;
	private final int categoryCount = 1;
	private final int entityCount = diedata.size();
	private final BoxPlotOptionMenu box = new BoxPlotOptionMenu();
	private static DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
	private Project project;

	 
	/**
	 * Constructor
	 */
	public BoxPlotFrame() {

	}

	
	/**
	 * @param project
	 * Create the GUI with the chart and buttons
	 */
	public void createBoxPlotFrame(final Project project) {

		JFrame f = new JFrame("BoxPlot");
		final Container content = f.getContentPane();
		f.setSize(450, 500);

		this.project = project;

		dataset = new DefaultBoxAndWhiskerCategoryDataset();

		final BoxPlot nuweChart = new BoxPlot();
		chart = nuweChart.createBoxPlot("BoxPlot", "", "", dataset);
		chartpanel = new ChartPanel(chart, 400, 400, 100, 100, 400, 400, true, true, true, true, true, true);

		final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();

		renderer.setToolTipGenerator(new BoxAndWhiskerToolTipGenerator());

		chartpanel.getChart().getCategoryPlot().setRenderer(renderer);

		JButton addseries = new JButton("Add a series");
		// Series can be added dynamically
		addseries.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				box.createFrame();

			}
		});

		// Extract chart to PNG
		JButton extractPic = new JButton("Extract chart");
		extractPic.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				

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

		content.setLayout(new FlowLayout());
		content.add(chartpanel);
		content.add(addseries);

		content.add(extractPic);

		f.setVisible(true);
	}

	/**
	 * Add new series data to the boxplot
	 */
	public void addBoxSeries() {

		ArrayList nuwe = new ArrayList();

		for (int k = 0; k < diedata.size(); k++) {

			try {
				nuwe.add(diedata.get(k).get(box.getIndexOfHeader()).getMark());
			} catch (AbsentException e) {
				nuwe.add(0);
			}

		}
		// Increase the series number
		teller += 1;

		dataset.add(nuwe, "Series" + teller, headers[box.getIndexOfHeader()]);

		chartpanel.getChart().getCategoryPlot().setDataset(dataset);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		

	}

	/**
	 * @throws IOException
	 * Save a file as 
	 */
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
				
				e.printStackTrace();
			}
		} else {

		}
	}

	
	/**
	 * @param chart
	 * @param aFileName
	 * @param width
	 * @param height
	 * @param quality
	 * @throws FileNotFoundException
	 * @throws IOException
	 * Save the png to the file
	 */
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

	 
	/**
	 * @param chart
	 * @param width
	 * @param height
	 * @return
	 * Create a buffered image
	 */
	protected static BufferedImage draw(JFreeChart chart, int width, int height)

	{

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = img.createGraphics();

		chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));
		g2.dispose();

		return img;

	}
}
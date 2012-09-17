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
	
	// -------------------------------------------------------------------------------------------------------
	public void update()
	{
		
	
		nuweChart.updateSelectedvalues();
	}
	//----------------------------------------------------------------------------------------------
	public ScatterPlotFrame(Project project) {
		JFrame f = new JFrame("ScatterPlot");
		this.project = project;
		final Container content = f.getContentPane();
		f.setSize(550, 500);
		final LinkedList<LinkedList<SuperEntity>> diedata = Global.getGlobal()
				.getActiveProject().getHead().getDataLinkedList();
		
		final XYSeriesCollection dataset = new XYSeriesCollection();
		final String[] headers = Global.getGlobal().getActiveProject()
				.getHead().getHeaders();
		nuweChart = new ScatterPlot(project);
		
		String[] kolom = Global.getGlobal().getActiveProject().getHead()
				.getNumberHeaders();
		
		String xas = kolom[0];
		String yas = kolom[1];

		for (int s = 0; s < headers.length; s++) {
			if (headers[s].equals(kolom[0])) {
				houerx = s;
				
			}

		}

		for (int s = 0; s < headers.length; s++) {
			if (headers[s].equals(kolom[1])) {
				houery = s;
				
			}

		}

		XYSeries series = new XYSeries("Scatter");

		for (int q = 0; q < diedata.size(); q++) {
			series.add(diedata.get(q).get(houerx).getMark(), diedata.get(q)
					.get(houery).getMark());
			
		}

		dataset.addSeries(series);

		chart = nuweChart.createScatter("asd", dataset, xas, yas);
		chartpanel = nuweChart.createPanel();

		JLabel lblNewLabel = new JLabel("X-axis");
		final JComboBox xascb = new JComboBox();

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

				XYSeriesCollection nuwedataset = new XYSeriesCollection();
				XYSeries series = new XYSeries("Scatter");

				for (int q = 0; q < diedata.size() - 1; q++) {
					series.add(diedata.get(q).get(houerx).getMark(), diedata
							.get(q).get(houery).getMark());
					
				}

				nuwedataset.addSeries(series);
				chartpanel.getChart().getXYPlot().setDataset(nuwedataset);
				nuweChart.setDatasetmain(nuwedataset);

			}

		});

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
					xascb.setSelectedIndex(xascb.getSelectedIndex() + 1);

			}
		});

		JLabel lblNewLabel_1 = new JLabel("Y-axis");

		final JComboBox yascb = new JComboBox();
		yascb.setModel(new DefaultComboBoxModel(kolom));
		yascb.setSelectedIndex(1);
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

				for (int q = 0; q < diedata.size() - 1; q++) {
					series.add(diedata.get(q).get(houerx).getMark(), diedata
							.get(q).get(houery).getMark());
					

				}

				nuwedataset.addSeries(series);
				chartpanel.getChart().getXYPlot().setDataset(nuwedataset);
				nuweChart.setDatasetmain(nuwedataset);
			}

		});

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
					yascb.setSelectedIndex(yascb.getSelectedIndex() - 1);

			}
		});

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
					yascb.setSelectedIndex(yascb.getSelectedIndex() + 1);

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

		JButton extractPic = new JButton("Extract chart as jpg");
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
	//----------------------------------------------------------------------------------------------
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
				
				saveToFile(chart, file.getAbsolutePath()+".jpg", 500, 300, 100);
			} catch (UnknownTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}
	}
	//----------------------------------------------------------------------------------------------
 public static void saveToFile(JFreeChart chart,String aFileName,int width,int height, double quality) throws FileNotFoundException, IOException
	    {
	            BufferedImage img = draw( chart, width, height );
	            FileOutputStream fos = new FileOutputStream(aFileName);

	            JPEGImageEncoder encoder2 = JPEGCodec.createJPEGEncoder(fos);

	            JPEGEncodeParam param2 = encoder2.getDefaultJPEGEncodeParam(img);

	            param2.setQuality((float) quality, true);

	            encoder2.encode(img,param2);

	            fos.close();

	    }
//----------------------------------------------------------------------------------------------
	    protected static BufferedImage draw(JFreeChart chart, int width, int height)

	    {

	        BufferedImage img = new BufferedImage(width , height, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2 = img.createGraphics();

	        chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));
	        g2.dispose();

	        return img;

	    }

}

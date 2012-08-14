package ClassAdminFrontEnd;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;


import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.SuperEntity;



public class ScatterPlotFrame extends JFrame implements ActionListener {
	static ChartPanel chartpanel ;
	static JFreeChart chart;
	 public ScatterPlotFrame() {
		 JFrame f = new JFrame("ScatterPlot");
		   final Container content = f.getContentPane();
		    f.setSize(550, 500);
		    
		    XYSeriesCollection dataset = new XYSeriesCollection();

			XYSeries series = new XYSeries("Scatter");
			for (int i = 0; i < 100; i++) {
				final float x = i;
				series.add(x, Math.random() * 100);

			}

			dataset.addSeries(series);
			
			
			String xas ="";
			String yas ="";
			final ScatterPlot nuweChart = new ScatterPlot();
			chart = nuweChart.createScatter("asd", dataset, xas,yas);
			chartpanel = nuweChart.createPanel();
			
			JLabel lblNewLabel = new JLabel("X-axis");
			
			final JComboBox xascb = new JComboBox();
		String[] kolom=Global.getGlobal().getActiveProject().getHead().getHeaders();
		LinkedList<SuperEntity> headers =Global.getGlobal().getActiveProject().getHead().getHeadersLinkedList();
	
		
		final LinkedList<LinkedList<SuperEntity>> diedata = Global.getGlobal().getActiveProject().getHead().getDataLinkedList();
		
			xascb.setModel(new DefaultComboBoxModel(kolom));
			xascb.addActionListener( new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					 JComboBox cb = (JComboBox)e.getSource();
				        String axis = (String)cb.getSelectedItem();
				        chartpanel.getChart().getXYPlot().getDomainAxis().setLabel(axis);
				 		
				        chartpanel.getChart().getXYPlot().clearAnnotations();
				        
				        XYSeriesCollection dataset = new XYSeriesCollection();

						XYSeries q= new XYSeries("Scatter");
						for (int i = 0; i < 100; i++) {
							final float x = i;
							q.add(x, Math.random() * 100);

						}

						dataset.addSeries(q);
						 chartpanel.getChart().getXYPlot().setDataset(dataset);
						 nuweChart.setDatasetmain(dataset);
						System.out.println(diedata.get(0).get(0).getMark());
						System.out.println(diedata.get(0).get(2));
				
				}
				
			});
			
			
			JLabel lblNewLabel_1 = new JLabel("Y-axis");
			
			final JComboBox yascb = new JComboBox();
			yascb.setModel(new DefaultComboBoxModel(kolom));
			yascb.addActionListener( new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					 JComboBox cb = (JComboBox)e.getSource();
				        String axis = (String)cb.getSelectedItem();
				        chartpanel.getChart().getXYPlot().getRangeAxis().setLabel(axis);
			 			
				        chartpanel.getChart().getXYPlot().clearAnnotations();
				}
				
			});
			
			
		    JButton rotate = new JButton("Rotate");
		    
		    rotate.addMouseListener(new MouseListener(){

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

				
		    	
		    }	
		    );
		    
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
					   try{

					        //nuweChart.saveToFile(chart,"test.jpg",500,300,100);
					    }catch(Exception e1){

					        e1.printStackTrace();

					    }
				}
			});
		    content.setBackground(Color.white);
		    content.setLayout(new FlowLayout()); 
		    content.add(chartpanel);
		    content.add(lblNewLabel);
		    content.add(xascb);
		    content.add(lblNewLabel_1);
		    content.add(yascb);
		    content.add(rotate);
		    content.add(extractPic);
		   
		    f.setVisible(true);
	 }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	 
}

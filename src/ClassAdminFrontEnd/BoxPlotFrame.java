package ClassAdminFrontEnd;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
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



public class BoxPlotFrame extends JFrame implements ActionListener {
 static ChartPanel chartpanel ;
	static JFreeChart chart;
	 public BoxPlotFrame() {
		
		 
		 JFrame f = new JFrame("BoxPlot");
		   final Container content = f.getContentPane();
		    f.setSize(450, 500);
		    
		    
		    final int seriesCount = 3;
	        final int categoryCount = 4;
	        final int entityCount = 22;
	        
	        final DefaultBoxAndWhiskerCategoryDataset dataset 
	            = new DefaultBoxAndWhiskerCategoryDataset();
	      
	        
	        for (int i = 0; i < seriesCount; i++) {
	            for (int j = 0; j < categoryCount; j++) {
	                final ArrayList list = new ArrayList();
	                // add some values...
	                for (int k = 0; k < entityCount; k++) {
	                    final double value1 = 10.0 + Math.random() * 3;
	                    list.add(new Double(value1));
	                    final double value2 = 11.25 + Math.random(); // concentrate values in the middle
	                    list.add(new Double(value2));
	                }
	                
	                dataset.add(list, "Series " + i, " Type " + j);
	            }
	            
	        }

	       
		    
		    final BoxPlot nuweChart = new BoxPlot();
			chart = nuweChart.createBoxPlot("BoxPlot", "", "", dataset);
			chartpanel = new ChartPanel(chart,400,400,200,200,400,400,true,true,true,true,true,true);
			
			JLabel lblNewLabel = new JLabel("X-axis");
			
			final JComboBox xascb = new JComboBox();
			
			xascb.setModel(new DefaultComboBoxModel(new String[] {"Student nr", "Finale punt", "Eksamen punt"}));
			xascb.addActionListener( new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					 JComboBox cb = (JComboBox)e.getSource();
				        String axis = (String)cb.getSelectedItem();
				       // chartpanel.getChart().getXYPlot().getDomainAxis().setLabel(axis);
				 		chartpanel.getChart().getCategoryPlot().getDomainAxis().setLabel(axis);
				 	
				        //chartpanel.getChart().getXYPlot().clearAnnotations();
					
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

					       //saveJPG.saveToFile(chart,"test.jpg",500,300,100);
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
		    content.add(rotate);
		    content.add(extractPic);
		   
		   
		    f.setVisible(true);
	 }
	 
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	 
}
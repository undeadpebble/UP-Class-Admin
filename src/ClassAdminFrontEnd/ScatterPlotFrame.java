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

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.SuperEntity;



public class ScatterPlotFrame extends JFrame implements ActionListener {
	static ChartPanel chartpanel ;
	static JFreeChart chart;
    int houerx =0;
    int houery =0;
	 public ScatterPlotFrame() {
		 JFrame f = new JFrame("ScatterPlot");
		   final Container content = f.getContentPane();
		    f.setSize(550, 500);
		    final LinkedList<LinkedList<SuperEntity>> diedata = Global.getGlobal().getActiveProject().getHead().getDataLinkedList();
		    final XYSeriesCollection dataset = new XYSeriesCollection();

		    
		    for(int k=0; k < diedata.size();k++)
		    {
		    	for(int l =0 ;l<diedata.get(0).size();l++)
		    	{
		    		System.out.print(diedata.get(k).get(l).getValue()+"\t");
		    	}
		    	System.out.println("");
		    }
		    
			XYSeries series = new XYSeries("Scatter");

			for(int q= 0 ;q < diedata.size()-1;q++)
			{
				series.add(diedata.get(q).get(3).getMark(), diedata.get(q).get(7).getMark());
				System.out.println(diedata.get(q).get(3).getMark() + " " + diedata.get(q).get(7).getMark());
			}
			

			dataset.addSeries(series);
			String[] kolom=Global.getGlobal().getActiveProject().getHead().getNumberHeaders();
			
			String xas =kolom[0];
			String yas =kolom[1];
			final ScatterPlot nuweChart = new ScatterPlot();
			chart = nuweChart.createScatter("asd", dataset, xas,yas);
			chartpanel = nuweChart.createPanel();
			
			JLabel lblNewLabel = new JLabel("X-axis");
			
			final JComboBox xascb = new JComboBox();
		
		
		final String[] headers =Global.getGlobal().getActiveProject().getHead().getHeaders();
	
		
		 
		
			xascb.setModel(new DefaultComboBoxModel(kolom));
			xascb.addActionListener( new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					 JComboBox cb = (JComboBox)e.getSource();
				        String axis = (String)cb.getSelectedItem();
				        chartpanel.getChart().getXYPlot().getDomainAxis().setLabel(axis);
				        
				        chartpanel.getChart().getXYPlot().clearAnnotations();
				
				        for(int s=0;s<headers.length;s++)
				        {
				        	if(headers[s].equals( cb.getSelectedItem().toString()))
				        	{
				        		houerx = s;
				        	}
				       
				        }
				        XYSeriesCollection nuwedataset = new XYSeriesCollection();
				        XYSeries series = new XYSeries("Scatter");
				        
						for(int q= 0 ;q < diedata.size()-1;q++)
						{
							series.add(diedata.get(q).get(houerx).getMark(), diedata.get(q).get(houerx).getMark());
							//System.out.println(diedata.get(houerx).get(q).getMark() +" "+ diedata.get(houery).get(q).getMark());
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
					if(xascb.getSelectedIndex()>=1)
					xascb.setSelectedIndex(xascb.getSelectedIndex()-1);
					
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
					if(xascb.getSelectedIndex() < xascb.getItemCount())
					xascb.setSelectedIndex(xascb.getSelectedIndex()+1);
					
				}
			});
			
			
			
		
			JLabel lblNewLabel_1 = new JLabel("Y-axis");
			
			final JComboBox yascb = new JComboBox();
			yascb.setModel(new DefaultComboBoxModel(kolom));
			yascb.setSelectedIndex(1);
			yascb.addActionListener( new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					 JComboBox cb = (JComboBox)e.getSource();
				        String axis = (String)cb.getSelectedItem();
				        chartpanel.getChart().getXYPlot().getRangeAxis().setLabel(axis);
			 			
				        chartpanel.getChart().getXYPlot().clearAnnotations();
				        
				        for(int s=0;s<headers.length;s++)
				        {
				        	if(headers[s].equals( cb.getSelectedItem().toString()))
				        	{
				        		houery = s;
				        	}
				       
				        }
				        XYSeriesCollection nuwedataset = new XYSeriesCollection();
				        XYSeries series = new XYSeries("Scatter");
				        
						for(int q= 0 ;q < diedata.size()-1;q++)
						{
							series.add(diedata.get(q).get(houerx).getMark(), diedata.get(q).get(houery).getMark());
							//System.out.println(diedata.get(houerx).get(q).getMark() +" "+ diedata.get(houery).get(q).getMark());
							
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
					if(yascb.getSelectedIndex() >=1)
					yascb.setSelectedIndex(yascb.getSelectedIndex()-1);
					
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
					
					if(yascb.getSelectedIndex() < yascb.getItemCount())
					yascb.setSelectedIndex(yascb.getSelectedIndex()+1);
					
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

	
	 
}

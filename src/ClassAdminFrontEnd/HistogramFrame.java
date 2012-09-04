package ClassAdminFrontEnd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
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

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminFrontEnd.Histogram.CustomBarRenderer;

public class HistogramFrame extends JFrame implements ActionListener {
	static ChartPanel chartpanel ;
	static JFreeChart chart;
	int houerx =0;
	double[] values;
	int widthbar = 10;
	public HistogramFrame()
	{
			JFrame f = new JFrame("Histogram");
		    final Container content = f.getContentPane();
		    f.setSize(550, 600);
		    
		    
		    final LinkedList<LinkedList<SuperEntity>> diedata = Global.getGlobal().getActiveProject().getHead().getDataLinkedList();
		    final String[] headers = Global.getGlobal().getActiveProject().getHead().getHeaders();
			String[] kolom = Global.getGlobal().getActiveProject().getHead().getNumberHeaders();
			
			final HistogramDataset dataset = new HistogramDataset();
			 
			String xas = kolom[0];
			
			for (int s = 0; s < headers.length; s++) {
				if (headers[s].equals(kolom[0])) {
					houerx = s;
					System.out.println(headers[s]);
				}

			}
			
		 values = new double[diedata.size()];
			for (int q = 0; q < diedata.size(); q++) {
			
				values[q] = diedata.get(q).get(houerx).getMark();
				System.out.println(values[q]);
				
			}
		    
		        
		   
		    dataset.setType(HistogramType.FREQUENCY);
		   
		    
		    dataset.addSeries("Histogram", values, 10,0,100);
		  //  dataset.addSeries("Histogram",value,number);
		    
		    String plotTitle = "Histogram"; 
		    String xaxis = kolom[0];
		    String yaxis = "Count"; 
			
			
			final Histogram nuweChart = new Histogram();
			chart = nuweChart.createHistogram(plotTitle, xaxis, yaxis, dataset);
		
			NumberAxis rangeAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
			
			((NumberAxis) rangeAxis).setTickUnit(new NumberTickUnit(1));
			rangeAxis.setRange(0,10);
		
			chartpanel = new ChartPanel(chart,500,500,400,400,500,500,true,true,true,true,true,true);
					
			final XYPlot plot;
			plot = chart.getXYPlot();
			final Plot Nuweplot = chart.getPlot();
			
			CustomBarRenderer c = new CustomBarRenderer();
			plot.setRenderer(c);
			/*BarRenderer barRenderer = (BarRenderer)plot.getRenderer();
			barRenderer.setSeriesPaint(0, Color.gray);
*/
             //CustomBarRenderer custombarrenderer3d = new CustomBarRenderer();
            //plot.setRenderer(renderer);
             //CategoryPlot categoryplot = (CategoryPlot)chart.getPlot();
             //categoryplot.setRenderer(custombarrenderer3d);
             //plot.setRenderer(custombarrenderer3d)
             
             //   ((XYPlot) Nuweplot).setRenderer((XYItemRenderer) custombarrenderer3d);
             /*custombarrenderer3d.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
             custombarrenderer3d.setBaseItemLabelsVisible(true);
             custombarrenderer3d.setItemLabelAnchorOffset(10D);
             custombarrenderer3d.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
             categoryplot.setRenderer(custombarrenderer3d);
             ValueMarker valuemarker = new ValueMarker(0.69999999999999996D, new Color(200, 200, 255), new BasicStroke(1.0F), new Color(200, 200, 255), new BasicStroke(1.0F), 1.0F);
             categoryplot.addRangeMarker(valuemarker, Layer.BACKGROUND);
             custombarrenderer3d.setBaseItemLabelsVisible(true);
             custombarrenderer3d.setMaximumBarWidth(0.050000000000000003D);
*/
			
				chartpanel.addChartMouseListener(new ChartMouseListener() {

					public void chartMouseClicked(ChartMouseEvent e) {
						
						MouseEvent me = e.getTrigger();
						//System.out.println((me.isShiftDown()));
					
						if(me.isShiftDown() == false)
						chart.getXYPlot().clearAnnotations();
						
						ChartEntity entity = ((ChartMouseEvent) e).getEntity();
						
						if (entity instanceof XYItemEntity && entity != null) {

							XYItemEntity ent = (XYItemEntity) entity;
							

							int sindex = ent.getSeriesIndex();
							int iindex = ent.getItem();

							final CircleDrawer cd = new CircleDrawer(Color.red,
									new BasicStroke(1.0f), null);
							final XYAnnotation bestBid = new XYDrawableAnnotation(dataset
									.getXValue(sindex, iindex), dataset.getYValue(sindex,
									iindex), 11, 11, cd);
							
							
							
						

							chart.getXYPlot().addAnnotation(bestBid);

							System.out.println("x = " + dataset.getXValue(sindex, iindex));
							System.out.println("y = " + dataset.getYValue(sindex, iindex));
						}

					}

					@Override
					public void chartMouseMoved(ChartMouseEvent arg0) {
						// TODO Auto-generated method stub

					}
					
				});
			
			
			
			
			
			JLabel lblNewLabel = new JLabel("X-axis");
			
			final JComboBox xascb = new JComboBox();
			
			xascb.setModel(new DefaultComboBoxModel(kolom));
			xascb.addActionListener( new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					 JComboBox cb = (JComboBox)e.getSource();
				        String axis = (String)cb.getSelectedItem();
				        chartpanel.getChart().getXYPlot().getDomainAxis().setLabel(axis);
				 		
				        chartpanel.getChart().getXYPlot().clearAnnotations();
				        for (int s = 0; s < headers.length; s++) {
							if (headers[s].equals(cb.getSelectedItem().toString())) {
								houerx = s;
							
							}

						}
				        
				        
				    values = new double[diedata.size()];
						for (int q = 0; q < diedata.size(); q++) {
						
							values[q] = diedata.get(q).get(houerx).getMark();
							System.out.println(values[q]);
							
						}
						HistogramDataset nuwedataset = new HistogramDataset();
						nuwedataset.addSeries("Histogram", values, 10,0,100);
				        chartpanel.getChart().getXYPlot().setDataset(nuwedataset);
				      
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
					if (xascb.getSelectedIndex() < xascb.getItemCount()-1)
						xascb.setSelectedIndex(xascb.getSelectedIndex() + 1);

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

					     //  saveJPG.saveToFile(chart,"test.jpg",500,300,100);
					    }catch(Exception e1){

					        e1.printStackTrace();

					    }
				}
			});
		    final JLabel width = new JLabel("Width");
		    JButton widthsmall = new JButton("<");
		    
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
					if(widthbar % 10 ==0)
						widthbar +=10;
					else
						widthbar += 9;
					HistogramDataset nuwedataset = new HistogramDataset();
					nuwedataset.addSeries("Histogram", values,widthbar ,0,100);
			        chartpanel.getChart().getXYPlot().setDataset(nuwedataset);
			        System.out.println(widthbar);
				}
			});
		    JButton widthlarge = new JButton(">");
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
					System.out.println("ADSDDDD");
					if(widthbar> 10)
					widthbar -=10;
					else if(widthbar >1)
						widthbar-=1;
						
					HistogramDataset nuwedataset = new HistogramDataset();
					nuwedataset.addSeries("Histogram", values,widthbar ,0,100);
			        chartpanel.getChart().getXYPlot().setDataset(nuwedataset);
			        System.out.println(widthbar);
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
}

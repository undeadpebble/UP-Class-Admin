package ClassAdminFrontEnd;

import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.imgscalr.Scalr;

import ClassAdminBackEnd.RapidAssessmentTree;
import ClassAdminBackEnd.RapidAssessmentTree.TreeContainerNode;
import ClassAdminBackEnd.RapidAssessmentTree.TreeMarkNode;
import ClassAdminBackEnd.RapidAssessmentTree.TreeNode;
import ClassAdminBackEnd.RapidAssessmentTree.TreeRectangleNode;

public class RapidAssessmentMarkingCanvas extends JFrame{
	private String backgroundFileName;
	private BufferedImage backGround;
	private BufferedImage resizedBackGround = null;
	private ContentPanel contentPanel;
	private JFrame contentFrame;
	/**
	 * @throws HeadlessException
	 */
	public RapidAssessmentMarkingCanvas(RapidAssessmentTree tree) throws HeadlessException {
		super();
		contentPanel = new ContentPanel();
		this.add(contentPanel);
		
		// TODO Auto-generated constructor stub
	}

	
	public class ContentPanel extends JPanel{
		@Override
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);
			Graphics g2 = g.create();

			if (resizedBackGround == null) {
				resizedBackGround = Scalr.resize(backGround, this.getWidth(),
						this.getHeight(), null);
			}

			g2.drawImage(resizedBackGround, 0, 0, null);
			g2.dispose();
		}
	}
	
	public MyComponent createComponent(TreeNode node){
		MyComponent comp = null;
		try{
			TreeMarkNode n = ((RapidAssessmentTree.TreeMarkNode)node);
			comp = new MyMark(n.getMark(),(int)(n.getX()),(int)(n.getY()));
			return comp;
		}
		catch (ClassCastException e){
			
		}
		try{
			TreeContainerNode n = ((RapidAssessmentTree.TreeContainerNode)node);
			contentPanel = new ContentPanel();
			contentFrame.setContentPane(contentPanel);
			contentPanel.setLocation((int)(n.getX()), (int)(n.getY()));
			contentPanel.setSize((int)(n.getW()), (int)(n.getH()));
			backgroundFileName = n.getImage();
			for(int x = 0;x<n.getChildNodes().size();++x){
				contentPanel.add(createComponent(n.getChildNodes().get(x)));
			}
			return comp;
		}
		catch (ClassCastException e){
			
		}
		try{
			TreeRectangleNode n = ((RapidAssessmentTree.TreeRectangleNode)node);
			comp = new MyRectangle(0,(int)(n.getX()),(int)(n.getY()),(int)(n.getW()),(int)(n.getH()));
			for(int x = 0;x<n.getChildNodes().size();++x){
				comp.add(createComponent(n.getChildNodes().get(x)));
			}
			return comp;
		}
		catch (ClassCastException e){
			
		}
		
		
		return comp;
	}
	public class MyComponent extends JComponent{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		double mark;
		double maxMark;
		
		public MyComponent(double maxMark,int x,int y, int w, int h) {
			this.setLocation(x, y);
			this.setSize(w, h);
			this.maxMark = maxMark;
			// TODO Auto-generated constructor stub
		}
		
		/**
		 * @return the mark
		 */
		public double getMark() {
			return mark;
		}

		/**
		 * @param mark the mark to set
		 */
		public void setMark(double mark) {
			this.mark = mark;
		}

		/**
		 * @return the maxMark
		 */
		public double getMaxMark() {
			return maxMark;
		}

		/**
		 * @param maxMark the maxMark to set
		 */
		public void setMaxMark(double maxMark) {
			this.maxMark = maxMark;
		}

		public void updateMark(){
			double total = 0;
			for(int x = 0;x<this.getComponentCount();++x){
				try{
					total += ((MyComponent)this.getComponent(x)).getMark();
				} catch (ClassCastException e){
					
				}
				
			}
			this.setMark(total);
			try{
				((MyComponent)this.getParent()).updateMark();
			} catch (ClassCastException e){
				
			}
		}
	}
	
	public class MyRectangle extends MyComponent{

		/**
		 * @param maxMark
		 * @param x
		 * @param y
		 * @param w
		 * @param h
		 */
		public MyRectangle(double maxMark, int x, int y, int w, int h) {
			super(maxMark, x, y, w, h);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	public class MyMark extends MyComponent{

		/**
		 * @param maxMark
		 * @param x
		 * @param y
		 * @param w
		 * @param h
		 */
		public MyMark(double maxMark, int x, int y) {
			super(maxMark, x, y, RapidAssessmentCanvas.MARK_SIZE*2, RapidAssessmentCanvas.MARK_SIZE);
			// TODO Auto-generated constructor stub
		}

	
		
	}
}

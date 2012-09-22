package ClassAdminFrontEnd;

import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.imgscalr.Scalr;

import ClassAdminFrontEnd.RapidAssessmentTree.TreeMarkNode;
import ClassAdminFrontEnd.RapidAssessmentTree.TreeNode;

public class RapidAssessmentMarkingCanvas extends JFrame{
	private String backgroundFileName;
	private BufferedImage backGround;
	private BufferedImage resizedBackGround = null;
	private ContentPanel contentPanel;
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
		MyComponent comp;
		try{
			comp = new MyMark((RapidAssessmentTree.TreeMarkNode)node).getMark();
		}
	}
	public class MyComponent extends JComponent{
		double mark;
		double maxMark;
		
		public MyComponent(double maxMark) {
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
		
	}
	
	public class MyMark extends MyComponent{

		public MyMark(double maxMark) {
			super(maxMark);
			// TODO Auto-generated constructor stub
		}
		
	}
}

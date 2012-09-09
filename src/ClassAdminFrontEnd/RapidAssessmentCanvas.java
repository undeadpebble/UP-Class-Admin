package ClassAdminFrontEnd;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.imgscalr.Scalr;

import ClassAdminBackEnd.SuperEntity;

public class RapidAssessmentCanvas extends JPanel{
	private final int MARK_SIZE = 5;
	private BufferedImage backGround;
	private BufferedImage resizedBackGround = null;
	private LinkedList<MyRectangle> rectangles = new LinkedList<MyRectangle>();
	private SuperEntity assessedEntity;
	private LinkedList<MyMarkPoint> marks = new LinkedList<MyMarkPoint>();
	
	public class MyMarkPoint extends JComponent{

		public MyMarkPoint(int x,int y) {
			this.setLocation(x, y);
			this.setSize(MARK_SIZE, MARK_SIZE);
		}

		private double mark = 1;
	}
	
	public class MyRectangle extends JComponent{

		private String name;
		
		private LinkedList<MyRectangle> children = new LinkedList<MyRectangle>();
		private LinkedList<MyMarkPoint> marks = new LinkedList<MyMarkPoint>();
		
		public boolean contains(MyRectangle rect){
			return (this.getX() < rect.getX() &&
					this.getY() < rect.getY() &&
					this.getX()+this.getWidth() > rect.getX()+rect.getWidth() &&
					this.getY()+this.getHeight() > rect.getY()+rect.getHeight());
		}
		
	}
	public RapidAssessmentCanvas(BufferedImage backGround, SuperEntity assessedEntity) {
		this.backGround=backGround;
		this.assessedEntity = assessedEntity;
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics g2 = g.create();
		
		if(resizedBackGround == null){
			resizedBackGround = Scalr.resize(backGround, this.getWidth(), this.getHeight(), null);
		}
		
		g2.drawImage(resizedBackGround, 0, 0, null);
		g2.dispose();
	}
	
	
}

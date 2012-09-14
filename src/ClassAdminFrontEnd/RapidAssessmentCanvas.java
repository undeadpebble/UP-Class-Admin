package ClassAdminFrontEnd;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.EventListener;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.imgscalr.Scalr;

import ClassAdminBackEnd.SuperEntity;

public class RapidAssessmentCanvas extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int MARK_SIZE = 5;
	private BufferedImage backGround;
	private BufferedImage resizedBackGround = null;
	//private LinkedList<MyRectangle> rectangles = new LinkedList<MyRectangle>();
	private SuperEntity assessedEntity;
	//private LinkedList<MyMarkPoint> marks = new LinkedList<MyMarkPoint>();
	
	public class MyMarkTotalComponent extends JComponent{
		
		public MyMarkTotalComponent(MyComponent parent){
			Rectangle bound = parent.getBounds();
			bound.add(bound.getLocation());
			bound.add(-10, -5);
			bound.setSize(10, 5);
			this.setBounds(bound);
		}
	}
	
	public class MyComponent extends JComponent{

		public MyComponent() {
			super();
			// TODO Auto-generated constructor stub
		}
		private double mark;
		
		public double getMark() {
			return mark;
		}
		public void setMark(double mark) {
			this.mark = mark;
			if(this.getParent() != null)
				((MyComponent)this.getParent()).updateMark();
		}
		
		public void updateMark(){
			int total = 0;
			for(int x = 0;x<this.getComponentCount();++x){
				total += ((MyComponent)(this.getComponent(x))).getMark();
			}
			this.setMark(total);
		}
	}
	
	
	public class MyMarkPoint extends MyComponent{

		public MyMarkPoint(int x,int y) {
			super();
			this.setLocation(x, y);
			this.setSize(MARK_SIZE, MARK_SIZE);
		}

	}
	
	public class MyRectangle extends MyComponent{

		private static final long serialVersionUID = 1L;

		private String name;
		
		private int count = 1;
		private boolean pressing = false;
		private Point origin = null;
		private Point end = null;
		public boolean isPressing() {
			return pressing;
		}
		//private LinkedList<MyRectangle> children = new LinkedList<MyRectangle>();
		//private LinkedList<MyMarkPoint> marks = new LinkedList<MyMarkPoint>();
		
		public MyRectangle() {
			super();
			this.addMouseListener(new canvasMouseListener());
			new MyMarkTotalComponent(this);
			this.setForeground(Color.red);
			// TODO Auto-generated constructor stub
		}

		public boolean contains(MyRectangle rect){
			return (this.getX() < rect.getX() &&
					this.getY() < rect.getY() &&
					this.getX()+this.getWidth() > rect.getX()+rect.getWidth() &&
					this.getY()+this.getHeight() > rect.getY()+rect.getHeight());
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAndIncrementCount() {
			return count++;
		}
		
		public int getCount() {
			return count;
		}
		
		public void setPressing(boolean pressing) {
			this.pressing = pressing;
		}
		public Point getOrigin() {
			return origin;
		}
		public void setOrigin(Point origin) {
			this.origin = origin;
		}
		public Point getEnd() {
			return end;
		}
		public void setEnd(Point end) {
			this.end = end;
		}


		
	}
	
	
	public class canvasMouseListener implements MouseListener{
		
		
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			((MyRectangle)e.getSource()).setPressing(false);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			((MyRectangle)e.getSource()).setPressing(false);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			((MyRectangle)e.getSource()).setOrigin(new Point(e.getX(), e.getY()));
			System.out.println("press");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			createComponent(((MyRectangle)e.getSource()).getOrigin(), ((MyRectangle)e.getSource()).getEnd(), (MyRectangle)e.getSource());
		}
		
		private void createComponent(Point p1,Point p2, MyRectangle source){
			if(p1.getX() > p2.getX() || p1.getY() > p2.getY())
				return;
			
			if(p2.getX() - p1.getX() < MARK_SIZE  || p2.getY() - p1.getY() < MARK_SIZE){
				JComponent tmp = new MyMarkPoint((int)p1.getX(),(int)p1.getY());
				source.add(tmp);
				
			}
			else
			{
				JComponent tmp = new MyRectangle();
				tmp.setBounds((int)p1.getX(),(int) p1.getY(),(int) (p2.getX()-p1.getX()),(int) (p2.getY()-p1.getY()));
				tmp.setName(source.getName()+"."+source.getAndIncrementCount());
				source.add(tmp);
			}
		}

		
		
	}
	public RapidAssessmentCanvas(BufferedImage backGround, SuperEntity assessedEntity) {
		this.backGround=backGround;
		this.assessedEntity = assessedEntity;
		MyRectangle tmp = new MyRectangle();
		tmp.setVisible(true);
		this.setLayout(null);
		tmp.setBounds(0, 0, 500,500);
		this.add(tmp);
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

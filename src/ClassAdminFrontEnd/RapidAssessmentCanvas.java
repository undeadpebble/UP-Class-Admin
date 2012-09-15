package ClassAdminFrontEnd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
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

public class RapidAssessmentCanvas extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MARK_SIZE = 15;
	private BufferedImage backGround;
	private BufferedImage resizedBackGround = null;
	// private LinkedList<MyRectangle> rectangles = new
	// LinkedList<MyRectangle>();
	private SuperEntity assessedEntity;
	private MyMarkPoint lastcreated = null;
	// private LinkedList<MyMarkPoint> marks = new LinkedList<MyMarkPoint>();
	private RapidAssessmentCanvas parentPanel = this;
	private MyRectangle parentRect;

	public class MyMarkTotalComponent extends JComponent {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private static final int WIDTH = MARK_SIZE * 6;
		private static final int HEIGHT = (int) (MARK_SIZE * 1.5);

		public MyMarkTotalComponent(MyComponent parent) {
			Rectangle bound = parent.getBounds();
			this.setSize(WIDTH, HEIGHT);
			this.setLocation((int) parent.getWidth() - WIDTH,
					(int) parent.getHeight() - HEIGHT);

			this.setLayout(null);
			parent.add(this);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		@Override
		protected void paintComponent(Graphics arg0) {
			super.paintComponent(arg0);
			Graphics2D g2 = (Graphics2D) arg0.create();
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(2f));
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawRect(0, 0, this.getWidth() + 5, this.getHeight() + 5);
			g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,
					this.getHeight() - 3));
			g2.drawString("/ " + ((MyComponent) this.getParent()).getMark(),
					this.getWidth() / 2, this.getHeight() - 3);
			g2.dispose();
		}

	}

	public class MyComponent extends JComponent {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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
			if (this.getParent() != parentPanel)
				((MyComponent) this.getParent()).updateMark();
			else
				System.out.println("not updataing ");
		}

		public void updateMark() {
			int total = 0;
			for (int x = 0; x < this.getComponentCount(); ++x) {
				try {
					total += ((MyComponent) (this.getComponent(x))).getMark();
				} catch (ClassCastException e) {

				}
			}
			System.out.println("updataing to: " + total);
			this.setMark(total);
		}
	}

	public class MyMarkPoint extends MyComponent {

		public MyMarkPoint(int x, int y, MyComponent parent) {
			super();
			this.setLocation(x, y);
			this.setSize(MARK_SIZE * 3, MARK_SIZE);
			parent.add(this);
			this.setMark(1);

			this.addMouseListener(new MouseListener() {

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
					// TODO Auto-generated method stub
					lastcreated = (MyMarkPoint) arg0.getSource();
					if (arg0.getButton() == arg0.BUTTON3)
						lastcreated.getParent().remove(lastcreated);
					parentPanel.repaint();
				}
			});
		}

		@Override
		protected void paintComponent(Graphics arg0) {
			// TODO Auto-generated method stub
			super.paintComponent(arg0);

			Graphics2D g2 = (Graphics2D) arg0.create();
			g2.setColor(Color.red);
			if (lastcreated == this) {
				g2.setStroke(new BasicStroke(4f));
			} else {
				g2.setStroke(new BasicStroke(2f));
			}
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawRect(0, 0, this.getWidth() / 3 - 1, this.getHeight() - 1);
			g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, MARK_SIZE - 2));
			g2.drawString("" + this.getMark(), this.getWidth() / 3 + 2,
					this.getHeight() - 1);
			g2.dispose();

		}

	}

	public class MyRectangle extends MyComponent {

		private static final long serialVersionUID = 1L;

		private String name;

		private int count = 1;
		private boolean pressing = false;
		private Point origin = null;
		private Point end = null;
		private long timePressed = 0;

		/**
		 * @return the timePressed
		 */
		public long getTimePressed() {
			return timePressed;
		}

		/**
		 * @param timePressed
		 *            the timePressed to set
		 */
		public void setTimePressed(long timePressed) {
			this.timePressed = timePressed;
		}

		public boolean isPressing() {
			return pressing;
		}

		// private LinkedList<MyRectangle> children = new
		// LinkedList<MyRectangle>();
		// private LinkedList<MyMarkPoint> marks = new
		// LinkedList<MyMarkPoint>();

		public MyRectangle(int x, int y, int w, int h) {
			super();
			this.setLocation(x, y);
			this.setSize(w, h);
			this.addMouseListener(new canvasMouseListener());
			new MyMarkTotalComponent(this);
			this.setForeground(Color.red);
			// TODO Auto-generated constructor stub
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		@Override
		protected void paintComponent(Graphics arg0) {
			// TODO Auto-generated method stub
			super.paintComponent(arg0);
			Graphics2D g2 = (Graphics2D) arg0.create();
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(3f));
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1,
					20, 20);

			g2.dispose();

		}

		public boolean contains(MyRectangle rect) {
			return (this.getX() < rect.getX()
					&& this.getY() < rect.getY()
					&& this.getX() + this.getWidth() > rect.getX()
							+ rect.getWidth() && this.getY() + this.getHeight() > rect
					.getY() + rect.getHeight());
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

		public boolean getPressing() {
			return this.pressing;
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

	public class canvasMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			((MyRectangle) e.getSource()).setPressing(false);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			((MyRectangle) e.getSource()).setPressing(false);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			((MyRectangle) e.getSource()).setOrigin(new Point(e.getX(), e
					.getY()));
			((MyRectangle) e.getSource()).setPressing(true);
			((MyRectangle) e.getSource()).setTimePressed(e.getWhen());
			System.out.println("press");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getWhen() - ((MyRectangle) e.getSource()).getTimePressed() < 100) {
				if (e.getButton() == e.BUTTON3 && e.getSource() != parentRect) {
					MyComponent parent = ((MyComponent) ((MyRectangle) e
							.getSource()).getParent());
					parent.remove(((MyRectangle) e.getSource()));
					parent.updateMark();
					parentPanel.repaint();
				}

				return;
			}
			((MyRectangle) e.getSource()).setEnd(new Point(e.getX(), e.getY()));
			if (((MyRectangle) e.getSource()).getPressing())
				createComponent(((MyRectangle) e.getSource()).getOrigin(),
						((MyRectangle) e.getSource()).getEnd(),
						(MyRectangle) e.getSource());

			((MyRectangle) e.getSource()).setPressing(false);
		}

		private void createComponent(Point p1, Point p2, MyRectangle source) {
			System.out.println("create");
			if (p1.getX() > p2.getX() || p1.getY() > p2.getY())
				return;

			if (p2.getX() - p1.getX() < MARK_SIZE * 2
					|| p2.getY() - p1.getY() < MARK_SIZE * 2) {
				MyMarkPoint tmp = new MyMarkPoint((int) p1.getX(),
						(int) p1.getY(), source);
				source.add(tmp);
				lastcreated = tmp;

			} else {
				JComponent tmp = new MyRectangle((int) p1.getX(),
						(int) p1.getY(), (int) (p2.getX() - p1.getX()),
						(int) (p2.getY() - p1.getY()));
				tmp.setName(source.getName() + "."
						+ source.getAndIncrementCount());
				source.add(tmp);
			}
			parentPanel.repaint();
		}

	}

	public RapidAssessmentCanvas(BufferedImage backGround,
			SuperEntity assessedEntity) {
		this.backGround = backGround;
		this.assessedEntity = assessedEntity;
		parentRect = new MyRectangle(0, 0, 500, 500);
		parentRect.setVisible(true);
		this.setLayout(null);
		this.add(parentRect);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
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

package ClassAdminFrontEnd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import jxl.biff.drawing.ComboBox;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;

import ClassAdminBackEnd.AbsentException;
import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.RapidAssessmentComponentType;
import ClassAdminBackEnd.RapidAssessmentContainerType;
import ClassAdminBackEnd.RapidAssessmentMarkType;
import ClassAdminBackEnd.RapidAssessmentRectangleType;
import ClassAdminBackEnd.SuperEntity;

public class RapidAssessmentMarkingCanvas extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Project project;
	private BufferedImage backGround;
	private BufferedImage resizedBackGround = null;
	private ContentPanel contentPanel;
	private JFrame parentFrame;
	private JComponent parentRect;
	private int focusedMark = 0;
	private LinkedList<MyMark> markList = new LinkedList<RapidAssessmentMarkingCanvas.MyMark>();
	JTextArea searchBox;
	JLabel searchLabel;
	JComboBox studentChooser;

	public void refreshButtons() {
		contentPanel.add(searchBox);
		contentPanel.add(searchLabel);
		contentPanel.add(studentChooser);
		searchLabel.setBounds(parentFrame.getWidth() - 145, 20, 130, 30);
		searchBox.setBounds(parentFrame.getWidth() - 145, 50, 130, 30);
		studentChooser.setBounds(parentFrame.getWidth() - 145, 80, 130, 30);
	}

	public RapidAssessmentMarkingCanvas(RapidAssessmentContainerType head,
			Project project) {
		super();
		this.project = project;
		parentFrame = this;
		new ContentPanel();
		this.setContentPane(contentPanel);
		this.setSize(150, 400);
		contentPanel.setVisible(true);
		refreshButtons();

		if (head != null) {
			createComponent(head);
		}

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyPressed(KeyEvent arg0) {

				switch (arg0.getKeyCode()) {
				case 192:
					markList.get(focusedMark).addChar('0');
					break;
				case KeyEvent.VK_ENTER:
					markList.get(focusedMark).next();
					break;
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				case '0':
					markList.get(focusedMark).addChar(arg0.getKeyCode());

					break;

				default:
					break;
				}

			}
		});
		this.setVisible(true);
		this.repaint();
	}

	public class ContentPanel extends JPanel {
		/**
		 * 
		 */
		public ContentPanel() {
			super();
			contentPanel = this;
			this.setLayout(null);
			searchLabel = new JLabel("Search");
			studentChooser = new JComboBox();
			studentChooser.setVisible(true);

			searchLabel.setVisible(true);

			searchBox = new JTextArea();

			refreshButtons();
			searchBox.setVisible(true);

			searchBox.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {

				}

				@Override
				public void keyReleased(KeyEvent e) {
					search(searchBox.getText());

				}

				@Override
				public void keyPressed(KeyEvent e) {

				}
			});

			// (this.getWidth()-145, 20, 130, 23)
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);
			Graphics g2 = g.create();

			if (resizedBackGround == null && backGround != null) {
				resizedBackGround = Scalr.resize(backGround, Method.QUALITY,
						Mode.FIT_EXACT, parentRect.getWidth(),
						parentRect.getHeight(), Scalr.OP_ANTIALIAS);
			}

			g2.drawImage(resizedBackGround, 0, 0, null);
			g2.dispose();
		}
	}

	public void search(String str) {
		System.out.println();
		studentChooser.removeAllItems();
		LinkedList<SuperEntity> list = new LinkedList<SuperEntity>();
		project.getHead().search(str.toLowerCase(), list);

		for (int x = 0; x < list.size(); ++x) {
			SuperEntity tmp = list.get(x);
			while (tmp != project.getHead()
					&& tmp.getParentEntity() != project.getHead()) {
				tmp = tmp.getParentEntity();
			}

			if (tmp != project.getHead())
				studentChooser.addItem(tmp);
		}
	}

	public JComponent createComponent(RapidAssessmentComponentType node) {
		MyComponent comp = null;
		try {
			RapidAssessmentMarkType n = ((RapidAssessmentMarkType) node);

			comp = new MyMark(node, (int) (n.getX()), (int) (n.getY()));

			return comp;
		} catch (ClassCastException e) {

		}
		try {
			RapidAssessmentContainerType n = ((RapidAssessmentContainerType) node);
			contentPanel.removeAll();
			backGround = n.getImage();

			parentFrame.setSize((int) (n.getW() + 100), (int) (n.getH() + 50));
			for (int x = 0; x < n.getSubEntityType().size(); ++x) {
				try {
					JComponent j = createComponent((RapidAssessmentComponentType) n
							.getSubEntityType().get(x));
					contentPanel.add(j);
					parentRect = j;

				} catch (ClassCastException e) {
				}
			}
			refreshButtons();
			searchBox.requestFocus(true);
			searchLabel.setFocusable(false);

			contentPanel.repaint();

			return contentPanel;
		} catch (ClassCastException e) {

		}
		try {
			RapidAssessmentRectangleType n = ((RapidAssessmentRectangleType) node);
			comp = new MyRectangle(node, (int) (n.getX()), (int) (n.getY()),
					(int) (n.getW()), (int) (n.getH()));
			for (int x = 0; x < n.getSubEntityType().size(); ++x) {
				try {
					comp.add(createComponent((RapidAssessmentComponentType) n
							.getSubEntityType().get(x)));

				} catch (ClassCastException e) {
				}
			}
			return comp;
		} catch (ClassCastException e) {

		}
		return comp;
	}

	public class MyComponent extends JComponent {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private SuperEntity entity;
		private EntityType enType;

		public MyComponent(EntityType enType, int x, int y, int w, int h) {
			this.setLocation(x, y);
			this.setSize(w, h);
			this.enType = enType;
			this.setVisible(true);

		}

		/**
		 * @return the mark
		 */
		public double getMark() {
			if (entity != null) {
				try {
					return entity.getMark();
				} catch (AbsentException e) {
					return 0;
				}
			} else
				return 0;
		}

		/**
		 * @param mark
		 *            the mark to set
		 */
		public void setMark(double mark) {
			if (entity != null)
				entity.setMark(mark);
		}

		/**
		 * @return the maxMark
		 */
		public double getMaxMark() {
			return enType.getMaxValue();
		}

		/**
		 * @param maxMark
		 *            the maxMark to set
		 */
		public void setMaxMark(double maxMark) {
			enType.setMaxValue(maxMark);
		}

		public void updateMark() {
			double total = 0;
			for (int x = 0; x < this.getComponentCount(); ++x) {
				try {
					total += ((MyComponent) this.getComponent(x)).getMark();
				} catch (ClassCastException e) {

				}

			}
			this.setMark(total);
			try {
				((MyComponent) this.getParent()).updateMark();
			} catch (ClassCastException e) {

			}
		}

		/**
		 * @return the entity
		 */
		public SuperEntity getEntity() {
			return entity;
		}

		/**
		 * @param entity
		 *            the entity to set
		 */
		public void setEntity(SuperEntity entity) {
			this.entity = entity;
		}

		/**
		 * @return the enType
		 */
		public EntityType getEnType() {
			return enType;
		}

		/**
		 * @param enType
		 *            the enType to set
		 */
		public void setEnType(EntityType enType) {
			this.enType = enType;
		}
	}

	public class MyRectangle extends MyComponent {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * @param maxMark
		 * @param x
		 * @param y
		 * @param w
		 * @param h
		 */
		public MyRectangle(EntityType enType, int x, int y, int w, int h) {
			super(enType, x, y, w, h);
			new MyMarkTotalComponent(this);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setColor(Color.red);
			g2.setStroke(new BasicStroke(3f));

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1,
					20, 20);

			g2.dispose();
		}

	}

	public class MyMark extends MyComponent {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String strValue = "";

		/**
		 * @param maxMark
		 * @param x
		 * @param y
		 * @param w
		 * @param h
		 */
		public MyMark(EntityType enType, int x, int y) {
			super(enType, x, y, RapidAssessmentCanvas.MARK_SIZE * 3,
					RapidAssessmentCanvas.MARK_SIZE);
			markList.add(this);

			this.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent arg0) {

				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					int old = focusedMark;
					markList.get(old).next();
					focusedMark = markList.indexOf(arg0.getSource());
					markList.get(old).repaint();

					markList.get(focusedMark).repaint();
				}

				@Override
				public void mouseExited(MouseEvent arg0) {

				}

				@Override
				public void mouseEntered(MouseEvent arg0) {

				}

				@Override
				public void mouseClicked(MouseEvent arg0) {

				}
			});

		}

		public boolean addChar(int charVal) {
			String newStr = strValue + (char) charVal;
			System.out.println(newStr);
			try {
				double newMark = Double.parseDouble(newStr);
				if (newMark > this.getMaxMark())
					return false;

				this.setMark(newMark);
				strValue = newStr;

				if ((strValue.length()) * 10 > this.getMaxMark())
					next();

				this.repaint();
				return true;
			} catch (NumberFormatException e) {
				return false;
			}

		}

		public boolean backspace() {
			if (strValue.length() > 0) {
				strValue = strValue.substring(0, strValue.length() - 2);
				this.setMark(Double.parseDouble(strValue));
				return true;
			} else
				return false;

		}

		public void next() {
			strValue = "";
			MyMark old = markList.get(focusedMark);
			focusedMark = ++focusedMark % markList.size();
			markList.get(focusedMark).repaint();
			old.repaint();

		}

		@Override
		protected void paintComponent(Graphics arg0) {

			super.paintComponent(arg0);

			Graphics2D g2 = (Graphics2D) arg0.create();

			if (markList.get(focusedMark) == this) {
				g2.setColor(Color.red.darker().darker());
			} else {
				g2.setColor(Color.red);
			}
			g2.setStroke(new BasicStroke(2f));
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawRect(0, 0, this.getWidth() / 3 - 1, this.getHeight() - 1);
			g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN,
					RapidAssessmentCanvas.MARK_SIZE - 2));
			String text1 = "" + this.getMark();
			String text2 = "/" + this.getMaxMark();

			g2.drawString(text1 + text2, this.getWidth() / 3 + 2,
					this.getHeight() - 1);
			g2.dispose();

		}

	}

	public class MyMarkTotalComponent extends JComponent {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private static final int WIDTH = RapidAssessmentCanvas.MARK_SIZE * 6;
		private static final int HEIGHT = (int) (RapidAssessmentCanvas.MARK_SIZE * 1.5);

		public MyMarkTotalComponent(MyComponent parent) {
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
					this.getHeight() - 4));
			g2.drawString(((MyComponent) this.getParent()).getMark() + " / "
					+ ((MyComponent) this.getParent()).getMaxMark(), 1,
					this.getHeight() - 3);
			g2.dispose();
		}

	}

	public void assess(SuperEntity entity) {
		load(entity, (MyComponent) (contentPanel.getComponent(0)));
	}

	public void load(SuperEntity entity, MyComponent comp) {
		comp.setEntity(entity.findEntityOfType_Down(comp.getEnType()));
		for (int x = 0; x < comp.getComponentCount(); ++x) {
			try {
				load(entity, (MyComponent) (comp.getComponent(x)));
			} catch (ClassCastException e) {

			}
		}
	}

}

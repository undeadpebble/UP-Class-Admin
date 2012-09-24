package Frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import org.imgscalr.Scalr;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.ISqlJetTransaction;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import ClassAdminBackEnd.FileHandler;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.PDatExport;
import ClassAdminBackEnd.UnsupportedFileTypeException;
import ClassAdminFrontEnd.BackgroundGradientPanel;
import ClassAdminFrontEnd.BlurBackground;
import ClassAdminFrontEnd.BoxPlotFrame;
import ClassAdminFrontEnd.FadePanel;
import ClassAdminFrontEnd.FrmTable;
import ClassAdminFrontEnd.GradientMenuBar;
import ClassAdminFrontEnd.HistogramFrame;
import ClassAdminFrontEnd.ImagePanel;
import ClassAdminFrontEnd.MenuImagePanel;
import ClassAdminFrontEnd.ReflectionButton;
import ClassAdminFrontEnd.ReflectionButtonWithLabel;
import ClassAdminFrontEnd.ReflectionImagePanel;
import ClassAdminFrontEnd.ScatterPlotFrame;
import ClassAdminFrontEnd.ShadowPanel;
import ClassAdminFrontEnd.ThreeStopGradientPanel;
import ClassAdminFrontEnd.TreeView;

public class Frame extends JFrame {

	private JPanel contentPane;
	private FadePanel homePanel, workspacePanel, navBar, tabBar, infoPanel, scatterplotInfoPanel, recentDocsPanel, searchPanel;
	private ThreeStopGradientPanel bottomPanel;
	private BackgroundGradientPanel backgroundPanel;
	private GradientMenuBar menuBarWindows;
	private JMenuBar menuBarMAC;
	private ReflectionImagePanel container, containerRecentDocs;
	private MenuImagePanel studentsViewArrowOut, studentsViewArrowIn;
	private ImagePanel boxChartImage, histogramChartImage, scatterplotChartImage, studentPhoto, searchImage;
	private JFileChooser filechooser;
	private JFrame frame = this;
	private File currentFilePath;
	private int tabCount = -1;
	private FrmTable table;
	private JTabbedPane tabbedPane;
	private FileHandler fileHandler;
	private BlurBackground blur;
	private ReflectionButton homeButton, importButton, exportButton, studentsButton, histogramButton, boxButton, scatterButton,
			conditionalFormatButton, bordercaseButton, addRowButton, homeImportButton, homeStudents, ButtonWorkspace, filterButton,
			maxValButton;
	private FadePanel homeInfoPanel, importInfoPanel, exportInfoPanel, studentsInfoPanel, histogramInfoPanel, boxplotInfoPanel,
			conditionalFormattingInfoPanel, bordercaseInfoPanel, addRowInfoPanel, filterInfoPanel, maxValInfoPanel;
	private ShadowPanel studentPanel;
	private String recentPathFile;
	private ReflectionButtonWithLabel[] recentDocsButtonsArray;
	private int counter;

	private static final String DB_NAME = "db.sqlite";
	private static final String TABLE_NAME = "Documents";
	private File dbFile;

	private int HOME_SPACE_LEFT_X;
	private int HOME_SPACE_Y;
	private int HOME_BOTTOM_SPACE_Y;
	private int HOME_SPACE_RIGHT_X;

	private static String currentOs;
	private static String MAC_OS = "MAC";
	private static String WIN_OS = "WINDOWS";

	public class TabButton extends JPanel {

		private String text;
		private JLabel label;
		private JLabel button;
		private TabButton tabbutton = this;

		public TabButton(String _text) {

			// create label with file name for tab
			text = _text;
			label = new JLabel(text);
			add(label);

			// create close button
			button = new JLabel("x");
			// button.setBorder(new EmptyBorder(1,1,1,1));
			add(button);
			button.setForeground(Color.white);

			// set this panel with label and close button to transparent
			this.setOpaque(false);
			this.setBorder(null);

			// close tab action
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					tabbedPane.remove(tabbedPane.indexOfTabComponent(tabbutton));
					tabCount--;
					if (tabCount == -1) {
						setNavButtonsDisabled();
					}

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					button.setForeground(Color.darkGray);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					button.setForeground(Color.white);
				}

			});
		}
	}

	class SelectionListener implements ListSelectionListener {
		JTable table;

		SelectionListener(JTable table) {
			this.table = table;
		}

		public void valueChanged(ListSelectionEvent e) {
			if (e.getSource() == table.getSelectionModel() && table.getRowSelectionAllowed()) {
				int first = e.getFirstIndex();
				int last = e.getLastIndex();
				System.out.println("listen");
			} else if (e.getSource() == table.getColumnModel().getSelectionModel() && table.getColumnSelectionAllowed()) {
				int first = e.getFirstIndex();
				int last = e.getLastIndex();
				System.out.println("listen2");
			}
			if (e.getValueIsAdjusting()) {
				System.out.println("The mouse button has not yet been released");
			}
		}

	}

	/*
	 * Method to create all frame contents
	 */
	public Frame() throws SqlJetException, IOException {

		// set frame title
		setTitle("UP Admin");

		// get OS
		determineOS();

		// frame setup
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);

		Image icon = Toolkit.getDefaultToolkit().getImage("Logo.png");
		this.setIconImage(icon);

		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// Determine the new location of the window
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		// Move the window
		setLocation(x, y);

		// maximize window
		// setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

		// create database to hold recently open documents information
		createRecentDocsDB();

		// create content pane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(new Color(0x212121));
		contentPane.setLayout(null);

		// check OS and setup accordingly
		if (currentOs == MAC_OS) {
			setupMAC();
		} else {
			setupWindows();
		}

		// create little bottom bar of home screen
		bottomPanel = new ThreeStopGradientPanel(new Color(0xA1A1A1), new Color(0x696969), new Color(0x000000), contentPane);
		bottomPanel.setSize(getWidth() - HOME_SPACE_RIGHT_X, 12);
		bottomPanel.setBounds(HOME_SPACE_LEFT_X, getHeight() - HOME_BOTTOM_SPACE_Y, bottomPanel.getWidth(), bottomPanel.getHeight());
		contentPane.add(bottomPanel);

		setupHomeScreen();
		setupWorkspaceScreen();

		// frame resize listener adjust components accordingly
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent arg0) {

			}

			@Override
			public void componentMoved(ComponentEvent arg0) {

			}

			// resizes components with screen
			@Override
			public void componentResized(ComponentEvent arg0) {

				bottomPanel.setBounds(HOME_SPACE_LEFT_X, frame.getHeight() - HOME_BOTTOM_SPACE_Y, frame.getWidth() - HOME_SPACE_RIGHT_X, 12);

				if (currentOs != MAC_OS) {
					backgroundPanel.setBounds(HOME_SPACE_LEFT_X, menuBarWindows.getHeight(), frame.getWidth() - HOME_SPACE_RIGHT_X,
							frame.getHeight() - HOME_SPACE_Y - menuBarWindows.getHeight());
				} else {

					backgroundPanel.setSize(frame.getWidth() - HOME_SPACE_RIGHT_X, frame.getHeight() - HOME_SPACE_Y);
					backgroundPanel.rerenderBackground();

					bottomPanel.rerenderBackground();
				}

				workspacePanel.setBounds(0, 0, backgroundPanel.getWidth(), backgroundPanel.getHeight());

				if (currentOs != MAC_OS) {
					menuBarWindows.setBounds(0, 0, getWidth(), 30);
				}

				navBar.setBounds(0, backgroundPanel.getHeight() - 40 - 40, getWidth(), 80);
				workspacePanel.add(navBar);

				if (tabbedPane != null) {
					tabbedPane.setBounds(20, 20, workspacePanel.getWidth() - 40, workspacePanel.getHeight() - 40 - navBar.getHeight());
				}
				if (tabBar != null) {
					tabBar.setBounds(0, 0, frame.getWidth(), frame.getHeight());
					tabBar.setLayout(null);
				}
				if (boxChartImage != null) {
					boxChartImage.setBounds(tabBar.getWidth() - 70, 15, 50, 40);
				}
				if (histogramChartImage != null) {
					histogramChartImage.setBounds(tabBar.getWidth() - 105, 15, 50, 40);
				}
				if (scatterplotChartImage != null) {
					scatterplotChartImage.setBounds(tabBar.getWidth() - 140, 15, 50, 40);
				}
				if (infoPanel != null) {
					infoPanel.setBounds(0, workspacePanel.getHeight() - 112, getWidth(), 43);
				}
				if (studentPanel != null) {
					studentPanel.setBounds(frame.getWidth() - 45, 45, 250, getHeight() - 20);
					studentPanel.setNewX(getWidth() - 45);
					studentPanel.setOldX(getWidth() - 250);
				}
				if (searchPanel != null) {
					searchPanel.setBounds(workspacePanel.getWidth() - 170, 10, 150, 30);
				}

			}

			@Override
			public void componentShown(ComponentEvent arg0) {

			}
		});

		// get current filehandler (ClassAdminBackEnd)
		fileHandler = FileHandler.get();
	}

	/*
	 * Method to setup frame positioning and menu according to Windows OS
	 */
	public void setupWindows() {
		// create menubar
		menuBarWindows = new GradientMenuBar();
		menuBarWindows.setBounds(0, 0, getWidth(), 30);
		contentPane.add(menuBarWindows);

		// create menu
		JMenu mnFile = new JMenu("File");
		mnFile.setForeground(Color.white);
		menuBarWindows.add(mnFile);

		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setForeground(Color.white);
		menuBarWindows.add(mnEdit);

		JMenuItem miNew = new JMenu("New");
		miNew.setForeground(Color.white);
		mnFile.add(miNew);

		// setup space constants
		HOME_SPACE_LEFT_X = 3;
		HOME_SPACE_Y = 55;
		HOME_BOTTOM_SPACE_Y = 53;
		HOME_SPACE_RIGHT_X = 22;

		// create background gradient panel
		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setSize(getWidth() - HOME_SPACE_RIGHT_X, getHeight() - HOME_SPACE_Y - menuBarWindows.getHeight());
		backgroundPanel.setBounds(HOME_SPACE_LEFT_X, menuBarWindows.getHeight(), backgroundPanel.getWidth(), backgroundPanel.getHeight());
		backgroundPanel.setLayout(null);
		contentPane.add(backgroundPanel);
	}

	/*
	 * Method to setup frame positioning and menu according to MAC OS
	 */
	public void setupMAC() {
		// create menubar
		menuBarMAC = new JMenuBar();
		contentPane.add(menuBarMAC);
		setJMenuBar(menuBarMAC);

		// create menu
		JMenu mnFile = new JMenu("File");
		menuBarMAC.add(mnFile);

		JMenu mnEdit = new JMenu("Edit");
		menuBarMAC.add(mnEdit);

		// setup space constants
		HOME_SPACE_LEFT_X = 3;
		HOME_SPACE_Y = 41;
		HOME_BOTTOM_SPACE_Y = 39;
		HOME_SPACE_RIGHT_X = 6;

		// create background gradient panel
		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setSize(getWidth() - HOME_SPACE_RIGHT_X, getHeight() - HOME_SPACE_Y);
		backgroundPanel.setBounds(HOME_SPACE_LEFT_X, 0, backgroundPanel.getWidth(), backgroundPanel.getHeight());
		backgroundPanel.setLayout(null);
		contentPane.add(backgroundPanel);
	}

	/*
	 * Setup first screen
	 */
	public void setupHomeScreen() throws SqlJetException, IOException {

		// create transparent panel to contain all menu icons
		homePanel = new FadePanel(false, 200, 100);
		homePanel.setBounds(0, 0, backgroundPanel.getWidth(), backgroundPanel.getHeight());
		backgroundPanel.add(homePanel);
		homePanel.setLayout(null);

		// create glass pane to display blur on for file import and xport
		blur = new BlurBackground(this);
		this.setGlassPane(blur);
		blur.setBounds(0, 0, getWidth(), getHeight());

		// add title bars
		container = new ReflectionImagePanel(
				ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/UPAdminHomeSelectTask.png")));
		container.setBounds(117, 25, 953, 88);
		homePanel.add(container);

		containerRecentDocs = new ReflectionImagePanel(ImageIO.read(getClass().getResource(
				"/ClassAdminFrontEnd/resources/UPAdminHomeRecentDocs.png")));
		containerRecentDocs.setBounds(117, 366, 953, 81);
		homePanel.add(containerRecentDocs);

		homeImportButton = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/HomeImport.png")));
		homeImportButton.setBounds(163, 139, 200, 100);
		homePanel.add(homeImportButton);

		ButtonWorkspace = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/HomeWorkspace.png")));
		ButtonWorkspace.setBounds(155, 235, 200, 100);
		homePanel.add(ButtonWorkspace);

		homeStudents = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/HomeStudents.png")));
		homeStudents.setBounds(554, 140, 180, 100);
		homePanel.add(homeStudents);

		// add listener to go to workspace screen
		ButtonWorkspace.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				homeToWorkspaceTransition();
			}
		});

		// add listener to for import dialog
		homeImportButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					openFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// setup panel to contain recent documents buttons
		recentDocsPanel = new FadePanel(false, 200, 200);
		recentDocsPanel.setBounds(160, containerRecentDocs.getHeight() + containerRecentDocs.getY() + 10, containerRecentDocs.getWidth(),
				100);
		recentDocsPanel.setLayout(null);
		homePanel.add(recentDocsPanel);

		createRecentDocsView();

		recentDocsPanel.fadeIn();

		homePanel.fadeIn();
	}

	/*
	 * Setup main screen for working (with spreadsheets etc)
	 */
	private void setupWorkspaceScreen() throws IOException {
		// create background pane for workspace screen
		workspacePanel = new FadePanel(false, 200, 200);
		workspacePanel.setBounds(0, 0, backgroundPanel.getWidth(), backgroundPanel.getHeight());
		backgroundPanel.add(workspacePanel);
		workspacePanel.setLayout(null);

		// create navigation bar
		navBar = new FadePanel(true, 800, 400);
		navBar.setBounds(0, workspacePanel.getHeight() - 40 - 40, getWidth(), 80);
		workspacePanel.add(navBar);
		navBar.setLayout(null);

		// create transparent panel on which info bubbles will be shown
		infoPanel = new FadePanel(false, 200, 200);
		infoPanel.setBounds(0, workspacePanel.getHeight() - 112, getWidth(), 43);
		workspacePanel.add(infoPanel);
		infoPanel.setLayout(null);
		infoPanel.fadeIn();

		// create search text field in right hand corner
		searchPanel = new FadePanel(false, 200, 200);
		searchPanel.setBounds(workspacePanel.getWidth() - 170, 10, 150, 30);
		searchPanel.setLayout(null);
		workspacePanel.add(searchPanel);

		JTextField searchBox = new JTextField();
		searchBox.setBounds(25, 5, 124, 25);
		searchPanel.add(searchBox);

		searchImage = new ImagePanel(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/Search.png")));
		searchImage.setBounds(0, 8, 30, 30);
		searchPanel.add(searchImage);

		searchPanel.fadeIn();

		// create student panel on side
		createStudentView();

		// create buttons on nav bar and add their respective mouselisteners
		homeButton = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/Home.png")));
		homeButton.setBounds(8, 8, 68, 80);
		navBar.add(homeButton);

		importButton = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/Import.png")));
		importButton.setBounds(75, 8, 68, 80);
		navBar.add(importButton);

		exportButton = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/Export.png")));
		exportButton.setBounds(135, 8, 68, 80);
		navBar.add(exportButton);

		studentsButton = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/Students.png")));
		studentsButton.setBounds(200, 8, 68, 80);
		navBar.add(studentsButton);

		histogramButton = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/Histogram.png")));
		histogramButton.setBounds(270, 12, 68, 80);
		navBar.add(histogramButton);

		boxButton = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/Box.png")));
		boxButton.setBounds(340, 12, 68, 80);
		navBar.add(boxButton);

		scatterButton = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/Scatter.png")));
		scatterButton.setBounds(405, 12, 68, 80);
		navBar.add(scatterButton);

		conditionalFormatButton = new ReflectionButton(ImageIO.read(getClass().getResource(
				"/ClassAdminFrontEnd/resources/ConditionalFormattingAdd.png")));
		conditionalFormatButton.setBounds(470, 8, 68, 80);
		navBar.add(conditionalFormatButton);

		bordercaseButton = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/BordercaseAdd.png")));
		bordercaseButton.setBounds(532, 8, 68, 80);
		navBar.add(bordercaseButton);

		addRowButton = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/AddRow.png")));
		addRowButton.setBounds(592, 8, 68, 80);
		navBar.add(addRowButton);

		filterButton = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/Filter.png")));
		filterButton.setBounds(650, 13, 68, 80);
		navBar.add(filterButton);

		maxValButton = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/maxValue.png")));
		maxValButton.setBounds(704, 11, 68, 80);
		navBar.add(maxValButton);

		setNavButtonsDisabled();

		// create info bubbles panel
		homeInfoPanel = new FadePanel(false, 200, 200);
		homeInfoPanel.setBounds(8, 0, 62, infoPanel.getHeight());
		homeInfoPanel.setLayout(null);
		infoPanel.add(homeInfoPanel);

		// create info bubble image
		ImagePanel infoBubble = new ImagePanel(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/HomeInfo.png")));
		infoBubble.setBounds(0, 0, infoPanel.getWidth(), infoPanel.getHeight());
		infoBubble.setLayout(null);
		homeInfoPanel.add(infoBubble);

		// create import bubbles panel
		importInfoPanel = new FadePanel(false, 200, 200);
		importInfoPanel.setBounds(67, 0, 62, infoPanel.getHeight());
		importInfoPanel.setLayout(null);
		infoPanel.add(importInfoPanel);

		// create import bubble image
		ImagePanel importBubble = new ImagePanel(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/ImportInfo.png")));
		importBubble.setBounds(0, 0, infoPanel.getWidth(), infoPanel.getHeight());
		importBubble.setLayout(null);
		importInfoPanel.add(importBubble);

		// create export bubbles panel
		exportInfoPanel = new FadePanel(false, 200, 200);
		exportInfoPanel.setBounds(135, 0, 62, infoPanel.getHeight());
		exportInfoPanel.setLayout(null);
		infoPanel.add(exportInfoPanel);

		// create export bubble image
		ImagePanel exportBubble = new ImagePanel(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/ExportInfo.png")));
		exportBubble.setBounds(0, 0, infoPanel.getWidth(), infoPanel.getHeight());
		exportBubble.setLayout(null);
		exportInfoPanel.add(exportBubble);

		// create students bubbles panel
		studentsInfoPanel = new FadePanel(false, 200, 200);
		studentsInfoPanel.setBounds(175, 0, 125, infoPanel.getHeight());
		studentsInfoPanel.setLayout(null);
		infoPanel.add(studentsInfoPanel);

		// create students bubble image
		ImagePanel studentsBubble = new ImagePanel(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/StudentsInfo.png")));
		studentsBubble.setBounds(0, 0, infoPanel.getWidth(), infoPanel.getHeight());
		studentsBubble.setLayout(null);
		studentsInfoPanel.add(studentsBubble);

		// create histogram bubbles panel
		histogramInfoPanel = new FadePanel(false, 200, 200);
		histogramInfoPanel.setBounds(250, 0, 125, infoPanel.getHeight());
		histogramInfoPanel.setLayout(null);
		infoPanel.add(histogramInfoPanel);

		// create histogram bubble image
		ImagePanel histogramBubble = new ImagePanel(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/InfoHistogram.png")));
		histogramBubble.setBounds(0, 0, infoPanel.getWidth(), infoPanel.getHeight());
		histogramBubble.setLayout(null);
		histogramInfoPanel.add(histogramBubble);

		// create box plot bubbles panel
		boxplotInfoPanel = new FadePanel(false, 200, 200);
		boxplotInfoPanel.setBounds(330, 0, 125, infoPanel.getHeight());
		boxplotInfoPanel.setLayout(null);
		infoPanel.add(boxplotInfoPanel);

		// create box plot bubble image
		ImagePanel boxplotBubble = new ImagePanel(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/InfoBoxPlot.png")));
		boxplotBubble.setBounds(0, 0, infoPanel.getWidth(), infoPanel.getHeight());
		boxplotBubble.setLayout(null);
		boxplotInfoPanel.add(boxplotBubble);

		// create scatter plot bubbles panel
		scatterplotInfoPanel = new FadePanel(false, 200, 200);
		scatterplotInfoPanel.setBounds(387, 0, 125, infoPanel.getHeight());
		scatterplotInfoPanel.setLayout(null);
		infoPanel.add(scatterplotInfoPanel);

		// create scatterplot bubble image
		ImagePanel scatterplotBubble = new ImagePanel(ImageIO.read(getClass().getResource(
				"/ClassAdminFrontEnd/resources/InfoScatterPlot.png")));
		scatterplotBubble.setBounds(0, 0, infoPanel.getWidth(), infoPanel.getHeight());
		scatterplotBubble.setLayout(null);
		scatterplotInfoPanel.add(scatterplotBubble);

		// create conditional formatting plot bubbles panel
		conditionalFormattingInfoPanel = new FadePanel(false, 200, 200);
		conditionalFormattingInfoPanel.setBounds(435, 0, 129, infoPanel.getHeight());
		conditionalFormattingInfoPanel.setLayout(null);
		infoPanel.add(conditionalFormattingInfoPanel);

		// create scatterplot bubble image
		ImagePanel conditionalFormattingBubble = new ImagePanel(ImageIO.read(getClass().getResource(
				"/ClassAdminFrontEnd/resources/InfoconditionalFormatting.png")));
		conditionalFormattingBubble.setBounds(0, 0, infoPanel.getWidth(), infoPanel.getHeight());
		conditionalFormattingBubble.setLayout(null);
		conditionalFormattingInfoPanel.add(conditionalFormattingBubble);

		// create bordercase plot bubbles panel
		bordercaseInfoPanel = new FadePanel(false, 200, 200);
		bordercaseInfoPanel.setBounds(495, 0, 129, infoPanel.getHeight());
		bordercaseInfoPanel.setLayout(null);
		infoPanel.add(bordercaseInfoPanel);

		// create bordercase image
		ImagePanel bordercaseBubble = new ImagePanel(ImageIO.read(getClass()
				.getResource("/ClassAdminFrontEnd/resources/InfoBordercase.png")));
		bordercaseBubble.setBounds(0, 0, infoPanel.getWidth(), infoPanel.getHeight());
		bordercaseBubble.setLayout(null);
		bordercaseInfoPanel.add(bordercaseBubble);

		// create add row bubbles panel
		addRowInfoPanel = new FadePanel(false, 200, 200);
		addRowInfoPanel.setBounds(585, 0, 129, infoPanel.getHeight());
		addRowInfoPanel.setLayout(null);
		infoPanel.add(addRowInfoPanel);

		// create add row image
		ImagePanel addRowBubble = new ImagePanel(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/InfoAddRow.png")));
		addRowBubble.setBounds(0, 0, infoPanel.getWidth(), infoPanel.getHeight());
		addRowBubble.setLayout(null);
		addRowInfoPanel.add(addRowBubble);

		// create filter bubbles panel
		filterInfoPanel = new FadePanel(false, 200, 200);
		filterInfoPanel.setBounds(632, 0, 129, infoPanel.getHeight());
		filterInfoPanel.setLayout(null);
		infoPanel.add(filterInfoPanel);

		// create filter image
		ImagePanel filterBubble = new ImagePanel(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/InfoAddFilter.png")));
		filterBubble.setBounds(0, 0, infoPanel.getWidth(), infoPanel.getHeight());
		filterBubble.setLayout(null);
		filterInfoPanel.add(filterBubble);

		// create max val bubbles panel
		maxValInfoPanel = new FadePanel(false, 200, 200);
		maxValInfoPanel.setBounds(668, 0, 129, infoPanel.getHeight());
		maxValInfoPanel.setLayout(null);
		infoPanel.add(maxValInfoPanel);

		// create max val image
		ImagePanel maxValBubble = new ImagePanel(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/InfoAddMaxValues.png")));
		maxValBubble.setBounds(0, 0, infoPanel.getWidth(), infoPanel.getHeight());
		maxValBubble.setLayout(null);
		maxValInfoPanel.add(maxValBubble);

		homeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				workspaceToHomeTransition();
			}

			public void mouseEntered(MouseEvent arg0) {
				homeInfoPanel.fadeIn();
			}

			public void mouseExited(MouseEvent arg0) {
				homeInfoPanel.fadeOut();
			}
		});

		importButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					openFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			public void mouseEntered(MouseEvent arg0) {
				importInfoPanel.fadeIn();
			}

			public void mouseExited(MouseEvent arg0) {
				importInfoPanel.fadeOut();
			}
		});

		exportButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					saveFileAs();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			public void mouseEntered(MouseEvent arg0) {
				exportInfoPanel.fadeIn();
			}

			public void mouseExited(MouseEvent arg0) {
				exportInfoPanel.fadeOut();
			}
		});

		studentsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (!studentsButton.isDisabled()) {
					table.getTable().getSelectedRow();

					//kom by build tree btn
					TreeView.createEntityTypeFrm("name",Global.getGlobal().getActiveProject());				
					//moet hier bly
//					TreeView.createStudentFrm("name", table.getData().get(table.getTable().getSelectedRow()).get(0));
				}
			}

			public void mouseEntered(MouseEvent arg0) {
				studentsInfoPanel.fadeIn();
			}

			public void mouseExited(MouseEvent arg0) {
				studentsInfoPanel.fadeOut();
			}
		});

		histogramButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (!histogramButton.isDisabled()) {
					HistogramFrame x = new HistogramFrame(Global.getGlobal().getActiveProject());
					Global.getGlobal().getActiveProject().addhistogramcharts(x);
				}
			}

			public void mouseEntered(MouseEvent arg0) {
				histogramInfoPanel.fadeIn();
			}

			public void mouseExited(MouseEvent arg0) {
				histogramInfoPanel.fadeOut();
			}
		});

		boxButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (!boxButton.isDisabled()) {
					BoxPlotFrame x = new BoxPlotFrame();
					x.createBoxPlotFrame();
				}
			}

			public void mouseEntered(MouseEvent arg0) {
				boxplotInfoPanel.fadeIn();
			}

			public void mouseExited(MouseEvent arg0) {
				boxplotInfoPanel.fadeOut();
			}
		});

		scatterButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (!scatterButton.isDisabled()) {
					ScatterPlotFrame x = new ScatterPlotFrame(Global.getGlobal().getActiveProject());//project);
					Global.getGlobal().getActiveProject().addscattercharts(x);
				}
			}

			public void mouseEntered(MouseEvent arg0) {
				scatterplotInfoPanel.fadeIn();
			}

			public void mouseExited(MouseEvent arg0) {
				scatterplotInfoPanel.fadeOut();
			}
		});

		conditionalFormatButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (!conditionalFormatButton.isDisabled()) {
					ConditionalFormattingFrame conditionalformatFrame = new ConditionalFormattingFrame();
					conditionalformatFrame.setVisible(true);
				}
			}

			public void mouseEntered(MouseEvent arg0) {
				conditionalFormattingInfoPanel.fadeIn();
			}

			public void mouseExited(MouseEvent arg0) {
				conditionalFormattingInfoPanel.fadeOut();
			}
		});

		bordercaseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (!bordercaseButton.isDisabled()) {
					BordercaseFrame bordercaseFrame = new BordercaseFrame();
					bordercaseFrame.setVisible(true);
				}
			}

			public void mouseEntered(MouseEvent arg0) {
				bordercaseInfoPanel.fadeIn();
			}

			public void mouseExited(MouseEvent arg0) {
				bordercaseInfoPanel.fadeOut();
			}
		});

		addRowButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (!addRowButton.isDisabled()) {
					// add new row
				}
			}

			public void mouseEntered(MouseEvent arg0) {
				addRowInfoPanel.fadeIn();
			}

			public void mouseExited(MouseEvent arg0) {
				addRowInfoPanel.fadeOut();
			}
		});

		filterButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (!filterButton.isDisabled()) {
					FilterFrame filterframe = new FilterFrame();
					filterframe.setVisible(true);
				}
			}

			public void mouseEntered(MouseEvent arg0) {
				filterInfoPanel.fadeIn();
			}

			public void mouseExited(MouseEvent arg0) {
				filterInfoPanel.fadeOut();
			}
		});

		maxValButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (!maxValButton.isDisabled()) {
					SetMaxValueFrame maxframe = new SetMaxValueFrame();
					maxframe.setVisible(true);
				}
			}

			public void mouseEntered(MouseEvent arg0) {
				maxValInfoPanel.fadeIn();
			}

			public void mouseExited(MouseEvent arg0) {
				maxValInfoPanel.fadeOut();
			}
		});

	}

	/*
	 * Method with handles file import actions, blur background
	 */
	public void openFile() throws IOException, BadLocationException {

		File file;
		// set the file extentions that may be chosen
		FileFilter fileFilter = new FileNameExtensionFilter("Supported files types: pdat, csv", "pdat", "csv");

		blur.fadeIn();
		// Create a file chooser
		filechooser = new JFileChooser();
		if (currentFilePath != null) {
			filechooser.setCurrentDirectory(currentFilePath);
		}

		// add the filter to the file chooser
		// filechooser.addChoosableFileFilter(fileFilter);

		// shows the dialog, return value specifies file
		int returnVal = filechooser.showOpenDialog(this);

		// if the chosen file is valid
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = filechooser.getSelectedFile();
			currentFilePath = filechooser.getSelectedFile();
			blur.fadeOut();
			createTab(file);
			homeToWorkspaceTransition();
			tabBar.fadeIn();

			try {
				insertIntoDB(file.getName(), file.getAbsolutePath());
			} catch (SqlJetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// studentPanel.moveIn();
		} else {
			blur.fadeOut();
		}
	}

	/*
	 * Method with handles file import actions from the recent documents button
	 */
	public void openRecentFile(File _file) throws IOException, BadLocationException {

		File file = _file;

		createTab(file);
		homeToWorkspaceTransition();
		tabBar.fadeIn();

	}

	/*
	 * Method which handles file export
	 */
	public void saveFileAs() throws IOException {

		File file;
		// set the file extentions that may be chosen
		FileFilter filter = new FileNameExtensionFilter("Supported files types: pdat, csv", "pdat", "csv");

		// Create a file chooser
		final JFileChooser filechooser = new JFileChooser();
		// remove the "All Files" type
		// filechooser.setAcceptAllFileFilterUsed(false);
		// add the filter to the file chooser
		filechooser.addChoosableFileFilter(filter);

		// shows the dialog, return value specifies file
		int returnVal = filechooser.showSaveDialog(this);

		// if the chosen file is valid
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = filechooser.getSelectedFile();
			try {
				FileHandler.get().saveFile(file.getAbsolutePath(), Global.getGlobal().getActiveProject());
			} catch (UnsupportedFileTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}
	}

	/*
	 * function to determine OS that is currently running
	 */
	public static void determineOS() {
		currentOs = System.getProperty("os.name").toUpperCase();
		if (currentOs.contains("MAC")) {
			currentOs = MAC_OS;
		} else if (currentOs.contains("WINDOWS")) {
			currentOs = WIN_OS;
		} else {
			currentOs = null;
		}
	}

	/*
	 * create a new Tab when a new file is imported
	 */
	public void createTab(File file) {
		try {
			fileHandler.openFile(file.getAbsolutePath(), Global.getGlobal().getActiveProject());
		} catch (UnsupportedFileTypeException e) {
			e.printStackTrace();
		}
		// create table on panel
		table = new FrmTable(Global.getGlobal().getActiveProject().getHead().getHeaders(), Global.getGlobal().getActiveProject().getHead()
				.getDataLinkedList(), Global.getGlobal().getActiveProject());

		// listener for changes of selection in table
		table.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				showStudent();
			}
		});

		// create tabbedPane
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane();
			tabbedPane.setBounds(20, 20, workspacePanel.getWidth() - 40, workspacePanel.getHeight() - 40 - navBar.getHeight());
		}

		// create panel on which tabbedPane will be
		if (tabBar == null) {
			tabBar = new FadePanel(false, 800, 400);
			tabBar.setBounds(0, 0, frame.getWidth(), frame.getHeight());
			tabBar.setLayout(null);
		}
		tabBar.add(tabbedPane);

		workspacePanel.add(tabBar);

		// put panel with table on a new tab
		tabbedPane.addTab(file.getName(), table);
		tabCount++;
		tabbedPane.setTabComponentAt(tabCount, new TabButton(file.getName()));

		setNavButtonsEnabled();

		studentPanel.moveIn();
	}

	/*
	 * Function to simulate transitions from home screen to workspace screen
	 */
	public void homeToWorkspaceTransition() {
		homePanel.fadeOut();
		workspacePanel.fadeIn();
		navBar.fadeIn();
		frame.remove(blur);
		recentDocsPanel.fadeOut();
	}

	/*
	 * Function to simulate transitions from workspace screen to home screen
	 */
	public void workspaceToHomeTransition() {
		homePanel.fadeIn();
		workspacePanel.fadeOut();
		navBar.fadeOut();

		if (studentPanel != null) {
			studentPanel.setVisible(false);
		}

		recentDocsPanel.fadeIn();

	}

	/*
	 * Method to create student side panel on right side of workspace panel
	 */
	public void createStudentView() {

		studentPanel = new ShadowPanel(getWidth() - 45, 45, getWidth() - 250, 45);
		studentPanel.setBounds(getWidth(), 40, 250, getHeight() - 20);
		try {

			studentsViewArrowOut = new MenuImagePanel(ImageIO.read(getClass().getResource(
					"/ClassAdminFrontEnd/resources/studentsViewArrowOut.png")));
			studentsViewArrowIn = new MenuImagePanel(ImageIO.read(getClass().getResource(
					"/ClassAdminFrontEnd/resources/studentsViewArrowIn.png")));

			studentsViewArrowOut.setBounds(3, 3, 20, 20);
			studentsViewArrowIn.setBounds(3, 3, 20, 20);

		} catch (IOException e) {
			e.printStackTrace();
		}

		studentPanel.add(studentsViewArrowOut);
		studentPanel.add(studentsViewArrowIn);

		studentPanel.setLayout(null);

		studentPanel.setShown(false);
		studentsViewArrowIn.setVisible(false);

		backgroundPanel.setLayer(studentPanel, 300);
		backgroundPanel.add(studentPanel);
		studentPanel.setVisible(false);

		studentsViewArrowOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				studentPanel.moveOut();
				studentsViewArrowOut.setVisible(false);
				studentsViewArrowIn.setVisible(true);
			}
		});

		studentsViewArrowIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				studentPanel.moveIn();
				studentsViewArrowIn.setVisible(false);
				studentsViewArrowOut.setVisible(true);
			}
		});
	}

	/*
	 * Handles image on studentPanel
	 */
	public void showStudent() {
		/*
		 * String[] headers = Global.getGlobal().getActiveProject().getHead()
		 * .getHeaders(); for (int i = 0; i < headers.length; i++) { //
		 * System.out.println(headers[i]); }
		 * 
		 * int row = table.getTable().getSelectedRow(); int colCount =
		 * table.getTable().getColumnCount();
		 * 
		 * String[] info = new String[colCount];
		 * 
		 * for (int i = 0; i < colCount; i++) { info[i] =
		 * table.getTable().getValueAt(row, i).toString();
		 * System.out.println(info[i]); }
		 */
		try {
			BufferedImage photo = (ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/StudentPhotos/10092120.JPG")));
			photo = Scalr.resize(photo, 150);
			studentPhoto = new ImagePanel(photo);
			studentPanel.add(studentPhoto);
			studentPhoto.setBounds(57, 50, photo.getWidth(), photo.getHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Creates the recent docs database table
	 */
	public void createRecentDocsDB() throws SqlJetException {
		dbFile = new File(DB_NAME);
		if (!dbFile.exists()) {
			dbFile.delete();

			// create database, table and two indices:
			SqlJetDb db = SqlJetDb.open(dbFile, true);
			// set DB option that have to be set before running any
			// transactions:
			db.getOptions().setAutovacuum(true);
			// set DB option that have to be set in a transaction:
			db.runTransaction(new ISqlJetTransaction() {
				public Object run(SqlJetDb db) throws SqlJetException {
					db.getOptions().setUserVersion(1);
					return true;
				}
			}, SqlJetTransactionMode.WRITE);

			db.beginTransaction(SqlJetTransactionMode.WRITE);
			try {
				String createTableQuery = "CREATE TABLE "
						+ TABLE_NAME
						+ " (file_ID INTEGER NOT NULL PRIMARY KEY, filename TEXT NOT NULL, file_path TEXT NOT NULL, file_last_used DATE NOT NULL)";
				String createDateIndexQuery = "CREATE INDEX Date_Index ON " + TABLE_NAME + "(file_last_used)";
				String createPathIndexQuery = "CREATE INDEX Path_Index ON " + TABLE_NAME + "(file_path)";

				db.createTable(createTableQuery);
				db.createIndex(createDateIndexQuery);
				db.createIndex(createPathIndexQuery);
			} finally {
				db.commit();
			}
			// close DB and open it again (as part of example code)

			db.close();

		}
	}

	/*
	 * Inserts new file information in the database or updates existing fields
	 */
	public void insertIntoDB(String fname, String fpath) throws SqlJetException {
		int i = 0;
		SqlJetDb db = SqlJetDb.open(dbFile, true);

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		ISqlJetTable table = db.getTable(TABLE_NAME);

		db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
		try {
			i = countRecords(table.lookup("Path_Index", fpath));
		} finally {
			db.commit();
		}

		// if there is not such a file in the database, add it
		if (i == 0) {

			db.beginTransaction(SqlJetTransactionMode.WRITE);
			// insert file info
			table.insert(fname, fpath, dateFormat.format(date));

			db.commit();
			db.close();
		}
		// else update time accessed
		else {
			db.beginTransaction(SqlJetTransactionMode.WRITE);
			// insert file info
			long rowid = getRecordID(table.lookup("Path_Index", fpath));

			ISqlJetCursor cursor = db.getTable(TABLE_NAME).open();
			try {
				if (cursor.goTo(rowid)) {
					cursor.update(cursor.getValue("file_ID"), cursor.getValue("filename"), cursor.getValue("file_path"),
							dateFormat.format(date));
				}
			} finally {
				cursor.close();
			}

			db.commit();
			db.close();
		}

	}

	private long getRecordID(ISqlJetCursor cursor) throws SqlJetException {
		return cursor.getRowId();
	}

	private int countRecords(ISqlJetCursor cursor) throws SqlJetException {
		int counter = 0;
		try {
			if (!cursor.eof()) {
				do {
					counter++;
				} while (cursor.next());
			}
		} finally {
			cursor.close();
		}
		return counter;
	}

	private String[][] getRecentRecords(ISqlJetCursor cursor) throws SqlJetException {
		String[][] recentDocs = new String[5][2];
		try {
			int i = 0;
			if (!cursor.eof() || i < 5) {
				do {
					recentDocs[i][0] = cursor.getString("filename");
					recentDocs[i][1] = cursor.getString("file_path");
					i++;
				} while (cursor.next());
			}
		} finally {
			cursor.close();
		}
		return recentDocs;
	}

	public void createRecentDocsView() throws IOException, SqlJetException {
		SqlJetDb db = SqlJetDb.open(dbFile, true);
		ISqlJetTable doctable = db.getTable(TABLE_NAME);

		counter = 0;

		recentDocsButtonsArray = new ReflectionButtonWithLabel[5];
		db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
		try {
			String[][] recentDocs = new String[5][2];
			int rowcount = (int) doctable.order(doctable.getPrimaryKeyIndexName()).getRowCount();

			if (rowcount > 0) {
				recentDocs = getRecentRecords(doctable.order("Date_Index").reverse());

				for (int i = 0; i < rowcount; i++) {

					counter = i;
					recentPathFile = recentDocs[i][1];

					BufferedImage icon = null;

					if (recentDocs[i][0].endsWith("csv")) {
						icon = ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/csvsmall.png"));
					} else if (recentDocs[i][0].endsWith("pdat")) {
						icon = ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/pdatsmall.png"));
					} else {
						icon = ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/xlssmall.png"));
					}

					recentDocsButtonsArray[i] = new ReflectionButtonWithLabel(icon, recentDocs[i][0], new Color(0xD6D6D6), new Color(
							0xFAFAFA), recentDocs[i][1]);
					recentDocsButtonsArray[i].setBounds(8 + (80 * i), 8, 68, 95);

					System.out.println(i + ": " + recentDocsButtonsArray[i].getPath());
					recentDocsPanel.add(recentDocsButtonsArray[i]);

				}
				if (recentDocsButtonsArray[0] != null) {
					recentDocsButtonsArray[0].addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent arg0) {
							try {
								File testFile = new File(recentDocsButtonsArray[0].getPath());
								if (testFile.exists()) {
									openRecentFile(testFile);
								} else {

								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (BadLocationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					});
				}

				if (recentDocsButtonsArray[1] != null) {
					recentDocsButtonsArray[1].addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent arg0) {
							try {
								File testFile = new File(recentDocsButtonsArray[1].getPath());
								if (testFile.exists()) {
									openRecentFile(testFile);
								} else {

								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (BadLocationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					});
				}

				if (recentDocsButtonsArray[2] != null) {
					recentDocsButtonsArray[2].addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent arg0) {
							try {
								File testFile = new File(recentDocsButtonsArray[2].getPath());
								if (testFile.exists()) {
									openRecentFile(testFile);
								} else {

								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (BadLocationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					});
				}

				if (recentDocsButtonsArray[3] != null) {
					recentDocsButtonsArray[3].addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent arg0) {
							try {
								File testFile = new File(recentDocsButtonsArray[3].getPath());
								if (testFile.exists()) {
									openRecentFile(testFile);
								} else {

								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (BadLocationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					});
				}

				if (recentDocsButtonsArray[4] != null) {
					recentDocsButtonsArray[4].addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent arg0) {
							try {
								File testFile = new File(recentDocsButtonsArray[4].getPath());
								if (testFile.exists()) {
									openRecentFile(testFile);
								} else {

								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (BadLocationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					});
				}
			}
		} finally {

		}
	}

	/*
	 * Enables buttons that has been disabled because of no spreadsheet
	 */
	public void setNavButtonsEnabled() {
		studentsButton.setEnabled();
		histogramButton.setEnabled();
		boxButton.setEnabled();
		scatterButton.setEnabled();
		exportButton.setEnabled();
		conditionalFormatButton.setEnabled();
		bordercaseButton.setEnabled();
		addRowButton.setEnabled();
		filterButton.setEnabled();
		maxValButton.setEnabled();

		searchPanel.fadeIn();
	}

	/*
	 * Disables buttons which cannot be used without a spreadsheet (export,
	 * students, graphs...)
	 */
	public void setNavButtonsDisabled() {
		if (studentsButton != null) {
			studentsButton.setDisabled();
		}
		if (histogramButton != null) {
			histogramButton.setDisabled();
		}
		if (boxButton != null) {
			boxButton.setDisabled();
		}
		if (scatterButton != null) {
			scatterButton.setDisabled();
		}
		if (exportButton != null) {
			exportButton.setDisabled();
		}
		if (conditionalFormatButton != null) {
			conditionalFormatButton.setDisabled();
		}
		if (bordercaseButton != null) {
			bordercaseButton.setDisabled();
		}
		if (addRowButton != null) {
			addRowButton.setDisabled();
		}
		if (filterButton != null) {
			filterButton.setDisabled();
		}
		if (maxValButton != null) {
			maxValButton.setDisabled();
		}
		if (searchPanel != null) {
			searchPanel.fadeOut();
		}
		if (studentPanel != null) {
			studentPanel.setVisible(false);
		}

	}

}

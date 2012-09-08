package ClassAdminFrontEnd;

import java.awt.Color;
import java.awt.Cursor;
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
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import ClassAdminBackEnd.FileHandler;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminBackEnd.UnsupportedFileTypeException;
import org.jdesktop.swingx.JXPanel;

public class Frame extends JFrame {

	private JPanel contentPane;
	private FadePanel homePanel;
	private FadePanel workspacePanel;
	private FadePanel navBar;
	private FadePanel tabBar;
	private FadePanel infoPanel;
	private ThreeStopGradientPanel bottomPanel;
	private BackgroundGradientPanel backgroundPanel;
	private GradientMenuBar menuBarWindows;
	private JMenuBar menuBarMAC;
	private ReflectionImagePanel container;
	private ReflectionImagePanel containerRecentDocs;
	private ReflectionImagePanel containerImportImage;
	private ReflectionImagePanel containerWorkspace;
	private ReflectionImagePanel containerStudents;
	private MenuImagePanel containerImportText;
	private ImagePanel containerImportTextSub;
	private MenuImagePanel containerStudentsText;
	private ImagePanel containerStudentsTextSub;
	private MenuImagePanel containerWorkspaceText;
	private ImagePanel containerWorkspaceTextSub;
	private ImagePanel boxChartImage;
	private ImagePanel histogramChartImage;
	private ImagePanel scatterplotChartImage;
	private JFileChooser filechooser;
	private JFrame frame = this;
	private File currentFilePath;
	private int tabCount = -1;
	private FrmTable table;
	private JTabbedPane tabbedPane;
	private FileHandler fileHandler;
	private BlurBackground blur;
	private ReflectionButton homeButton;
	private ReflectionButton importButton;
	private ReflectionButton exportButton;
	private FadePanel homeInfoPanel;
	private FadePanel importInfoPanel;
	private FadePanel exportInfoPanel;
	private ShadowPanel studentPanel;

	private JButton button;

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
					// if (tabCount == -1)
					// contentPane.remove(tabbedPane);
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
			if (e.getSource() == table.getSelectionModel()
					&& table.getRowSelectionAllowed()) {
				int first = e.getFirstIndex();
				int last = e.getLastIndex();
				System.out.println("listen");
			} else if (e.getSource() == table.getColumnModel()
					.getSelectionModel() && table.getColumnSelectionAllowed()) {
				int first = e.getFirstIndex();
				int last = e.getLastIndex();
				System.out.println("listen2");
			}
			if (e.getValueIsAdjusting()) {
				System.out
						.println("The mouse button has not yet been released");
			}
		}

	}

	public Frame() {

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

		// create content pane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(new Color(0x212121));
		contentPane.setLayout(null);

		if (currentOs == MAC_OS) {
			setupMAC();
		} else {
			setupWindows();
		}
		
		// create little bottom bar of home screen
		bottomPanel = new ThreeStopGradientPanel(new Color(0xA1A1A1),
				new Color(0x696969), new Color(0x000000), contentPane);
		bottomPanel.setSize(getWidth() - HOME_SPACE_RIGHT_X, 12);
		bottomPanel.setBounds(HOME_SPACE_LEFT_X, getHeight()
				- HOME_BOTTOM_SPACE_Y, bottomPanel.getWidth(),
				bottomPanel.getHeight());
		contentPane.add(bottomPanel);

		
		setupHomeScreen();
		setupWorkspaceScreen();

		// frame resize listener to put nav bar at bottom of frame on resize
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

				bottomPanel.setBounds(HOME_SPACE_LEFT_X, frame.getHeight()
						- HOME_BOTTOM_SPACE_Y, frame.getWidth()
						- HOME_SPACE_RIGHT_X, 12);
				
				if (currentOs != MAC_OS) {
					backgroundPanel.setBounds(HOME_SPACE_LEFT_X,
							menuBarWindows.getHeight(), frame.getWidth()
									- HOME_SPACE_RIGHT_X, frame.getHeight()
									- HOME_SPACE_Y - menuBarWindows.getHeight());
				} else {
					
					backgroundPanel.setSize(frame.getWidth()-HOME_SPACE_RIGHT_X, frame.getHeight()-HOME_SPACE_Y);
					backgroundPanel.rerenderBackground();
					
					bottomPanel.rerenderBackground();
				}
				
				workspacePanel.setBounds(0, 0, backgroundPanel.getWidth(),
						backgroundPanel.getHeight());
				
				
				
				if (currentOs != MAC_OS) {
					menuBarWindows.setBounds(0, 0, getWidth(), 30);
				}
				
				navBar.setBounds(0, backgroundPanel.getHeight() - 40 - 40,
						getWidth(), 80);
				workspacePanel.add(navBar);
				
				if (tabbedPane != null) {
					tabbedPane.setBounds(
							20,
							20,
							workspacePanel.getWidth() - 40,
							workspacePanel.getHeight() - 40
									- navBar.getHeight());
				}
				if (tabBar != null) {
					tabBar.setBounds(0, 0, frame.getWidth(), frame.getHeight());
					tabBar.setLayout(null);
				}
				if (boxChartImage != null) {
					boxChartImage.setBounds(tabBar.getWidth() - 70, 15, 50, 40);
				}
				if (histogramChartImage != null) {
					histogramChartImage.setBounds(tabBar.getWidth() - 105, 15,
							50, 40);
				}
				if (scatterplotChartImage != null) {
					scatterplotChartImage.setBounds(tabBar.getWidth() - 140,
							15, 50, 40);
				}
				if (infoPanel != null) {
					infoPanel.setBounds(0, workspacePanel.getHeight() - 112,
							getWidth(), 43);
				}
				if (studentPanel != null) {
					studentPanel.setBounds(frame.getWidth() - 45, 0, 250,
							getHeight() - 20);
					studentPanel.setNewX(getWidth() - 45);
					studentPanel.setOldX(getWidth() - 250);
				}
				
			}

			@Override
			public void componentShown(ComponentEvent arg0) {

			}
		});

		fileHandler = FileHandler.get();
	}

	public void setupWindows() {
		// create menubar
		menuBarWindows = new GradientMenuBar();
		menuBarWindows.setBounds(0, 0, getWidth(), 30);
		setJMenuBar(menuBarWindows);
		contentPane.add(menuBarWindows);

		// create menu
		JMenu mnFile = new JMenu("File");
		mnFile.setForeground(Color.white);
		menuBarWindows.add(mnFile);

		HOME_SPACE_LEFT_X = 3;
		HOME_SPACE_Y = 55;
		HOME_BOTTOM_SPACE_Y = 53;
		HOME_SPACE_RIGHT_X = 22;

		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setSize(getWidth() - HOME_SPACE_RIGHT_X, getHeight()
				- HOME_SPACE_Y - menuBarWindows.getHeight());
		backgroundPanel.setBounds(HOME_SPACE_LEFT_X,
				menuBarWindows.getHeight(), backgroundPanel.getWidth(),
				backgroundPanel.getHeight());
		backgroundPanel.setLayout(null);
		contentPane.add(backgroundPanel);
	}

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
		
		HOME_SPACE_LEFT_X = 3;
		HOME_SPACE_Y = 41;
		HOME_BOTTOM_SPACE_Y = 39;
		HOME_SPACE_RIGHT_X = 6;

		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setSize(getWidth() - HOME_SPACE_RIGHT_X, getHeight()
				- HOME_SPACE_Y);
		backgroundPanel.setBounds(HOME_SPACE_LEFT_X, 0,
				backgroundPanel.getWidth(), backgroundPanel.getHeight());
		backgroundPanel.setLayout(null);
		contentPane.add(backgroundPanel);
}

	public void setupHomeScreen() {

		homePanel = new FadePanel(false, 800, 400);
		homePanel.setBounds(0, 0, backgroundPanel.getWidth(),
				backgroundPanel.getHeight());
		backgroundPanel.add(homePanel);
		homePanel.setLayout(null);

		blur = new BlurBackground(this);
		this.setGlassPane(blur);
		blur.setBounds(0, 0, getWidth(), getHeight());

		// add title bars
		try {
			container = new ReflectionImagePanel(ImageIO.read(getClass()
					.getResource("UPAdminHomeSelectTask.png")));
			container.setBounds(117, 25, 953, 88);
			homePanel.add(container);

			containerRecentDocs = new ReflectionImagePanel(
					ImageIO.read(getClass().getResource(
							"UPAdminHomeRecentDocs.png")));
			containerRecentDocs.setBounds(117, 366, 953, 81);
			homePanel.add(containerRecentDocs);

			containerImportImage = new ReflectionImagePanel(
					ImageIO.read(getClass().getResource("Import.png")));
			containerImportImage.setBounds(163, 139, 75, 138);
			homePanel.add(containerImportImage);

			containerWorkspace = new ReflectionImagePanel(
					ImageIO.read(getClass().getResource("Workspace.png")));
			containerWorkspace.setBounds(163, 235, 75, 102);
			homePanel.add(containerWorkspace);

			containerStudents = new ReflectionImagePanel(
					ImageIO.read(getClass().getResource("Students.png")));
			containerStudents.setBounds(544, 124, 75, 102);
			homePanel.add(containerStudents);

			containerImportText = new MenuImagePanel(ImageIO.read(getClass()
					.getResource("ImportText.png")));
			containerImportText.setBounds(210, 130, 89, 45);
			homePanel.add(containerImportText);

			containerImportTextSub = new ImagePanel(ImageIO.read(getClass()
					.getResource("ImportSub.png")), false);
			containerImportTextSub.setBounds(210, 166, 129, 32);
			homePanel.add(containerImportTextSub);

			containerStudentsText = new MenuImagePanel(ImageIO.read(getClass()
					.getResource("StudentsText.png")));
			containerStudentsText.setBounds(600, 130, 147, 54);
			homePanel.add(containerStudentsText);

			containerStudentsTextSub = new ImagePanel(ImageIO.read(getClass()
					.getResource("StudentsSub.png")), false);
			containerStudentsTextSub.setBounds(600, 166, 147, 32);
			homePanel.add(containerStudentsTextSub);

			containerWorkspaceText = new MenuImagePanel(ImageIO.read(getClass()
					.getResource("WorkspaceText.png")));
			containerWorkspaceText.setBounds(210, 224, 135, 53);
			homePanel.add(containerWorkspaceText);

			containerWorkspaceTextSub = new ImagePanel(ImageIO.read(getClass()
					.getResource("WorkspaceSub.png")), false);
			containerWorkspaceTextSub.setBounds(210, 259, 238, 32);
			homePanel.add(containerWorkspaceTextSub);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		containerWorkspaceText.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				homeToWorkspaceTransition();
			}
		});

		containerImportText.addMouseListener(new MouseAdapter() {
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
		homePanel.fadeIn();
	}

	private void setupWorkspaceScreen() {
		// create background pane for workspace screen
		workspacePanel = new FadePanel(false, 800, 400);
		workspacePanel.setBounds(0, 0, backgroundPanel.getWidth(),
				backgroundPanel.getHeight());
		backgroundPanel.add(workspacePanel);
		workspacePanel.setLayout(null);

		// create navigation bar
		navBar = new FadePanel(true, 800, 400);
		navBar.setBounds(0, workspacePanel.getHeight() - 40 - 40, getWidth(),
				80);
		workspacePanel.add(navBar);
		navBar.setLayout(null);

		// create transparent panel on which info bubbles will be shown
		infoPanel = new FadePanel(false, 200, 200);
		infoPanel
				.setBounds(0, workspacePanel.getHeight() - 112, getWidth(), 43);
		workspacePanel.add(infoPanel);
		infoPanel.setLayout(null);
		infoPanel.fadeIn();

		createStudentView();

		try {

			// create buttons on nav bar and add their respective mouselisteners
			homeButton = new ReflectionButton(ImageIO.read(getClass()
					.getResource("Home.png")));
			homeButton.setBounds(8, 8, 68, 80);
			navBar.add(homeButton);

			importButton = new ReflectionButton(ImageIO.read(getClass()
					.getResource("Import.png")));
			importButton.setBounds(84, 8, 68, 80);
			navBar.add(importButton);

			exportButton = new ReflectionButton(ImageIO.read(getClass()
					.getResource("Export.png")));
			exportButton.setBounds(150, 8, 68, 80);
			navBar.add(exportButton);

			// create info bubbles panel
			homeInfoPanel = new FadePanel(false, 200, 200);
			homeInfoPanel.setBounds(8, 0, 62, infoPanel.getHeight());
			homeInfoPanel.setLayout(null);
			infoPanel.add(homeInfoPanel);

			// create info bubble image
			ImagePanel infoBubble = new ImagePanel(ImageIO.read(getClass()
					.getResource("HomeInfo.png")), false);
			infoBubble.setBounds(0, 0, infoPanel.getWidth(),
					infoPanel.getHeight());
			infoBubble.setLayout(null);
			homeInfoPanel.add(infoBubble);

			// create import bubbles panel
			importInfoPanel = new FadePanel(false, 200, 200);
			importInfoPanel.setBounds(80, 0, 62, infoPanel.getHeight());
			importInfoPanel.setLayout(null);
			infoPanel.add(importInfoPanel);

			// create import bubble image
			ImagePanel importBubble = new ImagePanel(ImageIO.read(getClass()
					.getResource("ImportInfo.png")), false);
			importBubble.setBounds(0, 0, infoPanel.getWidth(),
					infoPanel.getHeight());
			importBubble.setLayout(null);
			importInfoPanel.add(importBubble);

			// create export bubbles panel
			exportInfoPanel = new FadePanel(false, 200, 200);
			exportInfoPanel.setBounds(150, 0, 62, infoPanel.getHeight());
			exportInfoPanel.setLayout(null);
			infoPanel.add(exportInfoPanel);

			// create export bubble image
			ImagePanel exportBubble = new ImagePanel(ImageIO.read(getClass()
					.getResource("ExportInfo.png")), false);
			exportBubble.setBounds(0, 0, infoPanel.getWidth(),
					infoPanel.getHeight());
			exportBubble.setLayout(null);
			exportInfoPanel.add(exportBubble);

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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void openFile() throws IOException, BadLocationException {

		File file;
		// set the file extentions that may be chosen
		FileFilter fileFilter = new FileNameExtensionFilter(
				"Supported files types: pdat, csv", "pdat", "csv");

		blur.fadeIn();
		// Create a file chooser
		filechooser = new JFileChooser();
		if (currentFilePath != null) {
			filechooser.setCurrentDirectory(currentFilePath);
		}

		// add the filter to the file chooser
		filechooser.addChoosableFileFilter(fileFilter);

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

			// studentPanel.moveIn();
		} else {
			blur.fadeOut();
		}
	}

	public void saveFileAs() throws IOException {

		File file;
		// set the file extentions that may be chosen
		FileFilter filter = new FileNameExtensionFilter(
				"Supported files types: pdat, csv", "pdat", "csv");

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
				FileHandler.get().saveFile(file.getAbsolutePath());
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
			fileHandler.openFile(file.getAbsolutePath(), Global.getGlobal()
					.getActiveProject());
		} catch (UnsupportedFileTypeException e) {
			e.printStackTrace();
		}
		// create table on panel
		table = new FrmTable(Global.getGlobal().getActiveProject().getHead()
				.getHeaders(), Global.getGlobal().getActiveProject().getHead()
				.getDataLinkedList(), Global.getGlobal().getActiveProject());

		// listener for changes of selection in table
		table.getTable().getSelectionModel()
				.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						showStudent();
					}
				});

		// create tabbedPane
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane();
			tabbedPane.setBounds(20, 20, workspacePanel.getWidth() - 40,
					workspacePanel.getHeight() - 40 - navBar.getHeight());
		}

		// create panel on which tabbedPane will be
		if (tabBar == null) {
			tabBar = new FadePanel(false, 800, 400);
			tabBar.setBounds(0, 0, frame.getWidth(), frame.getHeight());
			tabBar.setLayout(null);
			createGraphIcons();
		}
		tabBar.add(tabbedPane);

		workspacePanel.add(tabBar);

		// put panel with table on a new tab
		tabbedPane.addTab(file.getName(), table);
		tabCount++;
		tabbedPane.setTabComponentAt(tabCount, new TabButton(file.getName()));

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
	}

	/*
	 * Function to simulate transitions from workspace screen to home screen
	 */
	public void workspaceToHomeTransition() {
		homePanel.fadeIn();
		workspacePanel.fadeOut();
		navBar.fadeOut();
	}

	/*
	 * Function to create graph icons on top of right side of workspace screen
	 */
	public void createGraphIcons() {
		try {
			boxChartImage = new ImagePanel(ImageIO.read(getClass().getResource(
					"Box.png")), true);
			boxChartImage.setBounds(tabBar.getWidth() - 70, 15, 60, 40);
			tabBar.add(boxChartImage);

			histogramChartImage = new ImagePanel(ImageIO.read(getClass()
					.getResource("Histogram.png")), true);
			histogramChartImage.setBounds(tabBar.getWidth() - 105, 15, 50, 40);
			tabBar.add(histogramChartImage);

			scatterplotChartImage = new ImagePanel(ImageIO.read(getClass()
					.getResource("Scatterplot.png")), true);
			scatterplotChartImage
					.setBounds(tabBar.getWidth() - 140, 15, 50, 40);
			tabBar.add(scatterplotChartImage);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void createStudentView() {

		studentPanel = new ShadowPanel(getWidth() - 45, 0, getWidth() - 250, 0);
		studentPanel.setBounds(getWidth(), 0, 250, getHeight() - 20);
		button = new JButton("<");
		button.setBounds(3, 30, 20, 20);
		button.setBorder(new EmptyBorder(0, 0, 0, 0));
		studentPanel.add(button);
		studentPanel.setLayout(null);
		studentPanel.setShown(false);
		backgroundPanel.setLayer(studentPanel, 300);
		backgroundPanel.add(studentPanel);
		studentPanel.setVisible(false);

		button.setCursor(new Cursor(Cursor.HAND_CURSOR));

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (studentPanel.isShown()) {
					studentPanel.moveOut();
					button.setText(">");
					studentPanel.setShown(false);
				} else {
					studentPanel.moveIn();
					studentPanel.setShown(true);
					button.setText("<");
				}

			}
		});
	}

	public void showStudent() {
		String[] headers = Global.getGlobal().getActiveProject().getHead()
				.getHeaders();
		for (int i = 0; i < headers.length; i++) {
			// System.out.println(headers[i]);
		}

		int row = table.getTable().getSelectedRow();
		int colCount = table.getTable().getColumnCount();

		String[] info = new String[colCount];

		for (int i = 0; i < colCount; i++) {
			info[i] = table.getTable().getValueAt(row, i).toString();
			System.out.println(info[i]);
		}

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("2927713.jpg"));
		} catch (IOException e) {
		}

	}
}

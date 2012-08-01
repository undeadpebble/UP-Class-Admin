package ClassAdminFrontEnd;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.GlossPainter;
import org.jdesktop.swingx.painter.MattePainter;

import ClassAdminBackEnd.FileHandler;
import ClassAdminBackEnd.Global;
import ClassAdminBackEnd.UnsupportedFileTypeException;

public class Frame extends JFrame {

	private JPanel contentPane;
	private JXPanel navBar;
	private ReflectionButton btnImport;
	private ReflectionButton btnExport;
	private FileHandler fileHandler;
	private JFileChooser filechooser;
	private JFrame frame = this;
	private FrmTable table;
	private JTabbedPane tabbedPane;

	public static void main(String[] args) {

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					// UIManager.put("nimbusBase", new Color(0x2B2B2B));
					UIManager.put("nimbusBase", new Color(0x7A7A7A));
					UIManager.put("nimbusSelectionBackground", new Color(
							0x171717));
					UIManager.put("Menu.background", new Color(0x2B2B2B));
					UIManager.put("background", new Color(0x171717));
					UIManager
							.put("DesktopIcon.background", new Color(0x171717));
					UIManager.put("nimbusLightBackground", new Color(0xE3E3E3));

					// UIManager.put("control", new Color(0x171717));

					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Frame.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Frame.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Frame.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Frame.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {

		//create file handler
		fileHandler = FileHandler.get();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1131, 708);

		//create top menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		//create menu
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		//create menu items
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		//create gradient background panel
		contentPane = new GradientPanel(new Color(0x878787),
				new Color(0x242424), this.getWidth(), this.getHeight());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		//create bottom navigation bar
		navBar = new JXPanel();
		navBar.setBounds(0, 574, 1115, 84);
		setupPainters();
		contentPane.setLayout(null);
		contentPane.add(navBar);
		navBar.setLayout(null);

		//add navigation bar labels
		ShadowLabel lblImport = new ShadowLabel("Import", 12);
		lblImport.setBounds(33, 64, 36, 17);
		navBar.add(lblImport);

		ShadowLabel lblExport = new ShadowLabel("Export", 12);
		lblExport.setBounds(121, 64, 36, 17);
		navBar.add(lblExport);

		ShadowLabel lblChart = new ShadowLabel("Charts", 12);
		lblChart.setBounds(205, 64, 36, 17);
		navBar.add(lblChart);

		ShadowLabel lblStudents = new ShadowLabel("Students", 12);
		lblStudents.setBounds(297, 64, 59, 17);
		navBar.add(lblStudents);

		ShadowLabel lblSave = new ShadowLabel("Save", 12);
		lblSave.setBounds(392, 64, 36, 17);
		navBar.add(lblSave);

		//create tabbedPane
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(54, 50, 948, 474);
		contentPane.add(tabbedPane);
		
		//add navigation bar buttons
		try {
			
			btnImport = new ReflectionButton(ImageIO.read(getClass()
					.getResource("Import.png")));
			btnImport.setBounds(20, 11, 67, 73);
			navBar.add(btnImport);

			btnExport = new ReflectionButton(ImageIO.read(getClass()
					.getResource("Export.png")));
			btnExport.setBounds(105, 11, 67, 73);
			navBar.add(btnExport);

			JButton btnStudents;
			btnStudents = new ReflectionButton(ImageIO.read(getClass()
					.getResource("Students.png")));
			btnStudents.setBounds(289, 11, 67, 73);
			navBar.add(btnStudents);

			JButton btnChart;
			btnChart = new ReflectionButton(ImageIO.read(getClass()
					.getResource("Chart.png")));
			btnChart.setBounds(194, 11, 67, 73);
			navBar.add(btnChart);

			JButton btnSave;
			btnSave = new ReflectionButton(ImageIO.read(getClass().getResource(
					"Save.png")));
			btnSave.setBounds(382, 13, 67, 73);
			navBar.add(btnSave);

				

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//import button mouselistener
		btnImport.addMouseListener(new MouseAdapter() {
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

		//export button mouselistener
		btnExport.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				try {
					saveFileAs();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		//frame resize listener to put nav bar at bottom of frame on resize
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				navBar.setBounds(0,
						frame.getHeight() - navBar.getHeight() - 60,
						frame.getWidth(), navBar.getHeight());

			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	//bottom nav bar background painter
	private void setupPainters() {
		GlossPainter gloss = new GlossPainter();

		MattePainter matte = new MattePainter(new Color(0x242424));
		navBar.setBackgroundPainter(new CompoundPainter(matte, gloss));
	}

	public void openFile() throws IOException, BadLocationException {

		File file;
		// set the file extentions that may be chosen
		FileFilter fileFilter = new FileNameExtensionFilter(
				"Supported files types: pdat, csv", "pdat", "csv");

		// Create a file chooser
		filechooser = new JFileChooser();

		filechooser.setBackground(Color.red);
		filechooser.setForeground(Color.red);
		// remove the "All Files" type
		// filechooser.setAcceptAllFileFilterUsed(false);
		// add the filter to the file chooser
		filechooser.addChoosableFileFilter(fileFilter);

		// shows the dialog, return value specifies file
		int returnVal = filechooser.showOpenDialog(this);

		// if the chosen file is valid
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = filechooser.getSelectedFile();
			try {
				fileHandler.openFile(file.getAbsolutePath());
				table = new FrmTable(Global.getGlobal().getActiveProject().getHead().getHeaders(),Global.getGlobal().getActiveProject().getHead().getDataLinkedList());
				
				tabbedPane.addTab(file.getName(), table);

				
			} catch (UnsupportedFileTypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// JOptionPane.showMessageDialog(this, "Open file was cancelled." ,
			// "Open Cancelled", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void saveFileAs() throws IOException {

		File file;
		// set the file extentions that may be chosen
		FileFilter filter = new FileNameExtensionFilter("Text files", "txt",
				"TXT", "log");
		// Create a file chooser
		final JFileChooser filechooser = new JFileChooser();
		// remove the "All Files" type
		//filechooser.setAcceptAllFileFilterUsed(false);
		// add the filter to the file chooser
		filechooser.addChoosableFileFilter(filter);

		// shows the dialog, return value specifies file
		int returnVal = filechooser.showSaveDialog(this);

		// if the chosen file is valid
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = filechooser.getSelectedFile();
			

		} else {
			// JOptionPane.showMessageDialog(this, "Open file was cancelled." ,
			// "Open Cancelled", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}

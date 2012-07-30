package ClassAdminFrontEnd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.GlossPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.PinstripePainter;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Frame extends JFrame {

	private JPanel contentPane;
	private JXPanel navBar;
	private ReflectionButton btnImport;
	private File file;

	
	public static void main(String[] args) {
		
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					UIManager.put("nimbusBase", new Color(0x2B2B2B));
					UIManager.put("Menu.background", new Color(0x2B2B2B));
					UIManager.put("background", new Color(0x171717));
					UIManager.put("control", new Color(0x171717));
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1131, 615);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		contentPane = new GradientPanel(new Color(0x878787), new Color(0x242424), this.getWidth(), this.getHeight());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		navBar = new JXPanel();
		navBar.setBounds(0, 472, 1115, 84);
		setupPainters();
		contentPane.setLayout(null);
		contentPane.add(navBar);
		navBar.setLayout(null);
		
		
		try {
			
			ShadowLabel lblImport = new ShadowLabel("Import",12);
			lblImport.setBounds(33, 64, 36, 17);
			navBar.add(lblImport);
			
			ShadowLabel lblExport = new ShadowLabel("Export",12);
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
			
			btnImport = new ReflectionButton(ImageIO.read(getClass()
					.getResource("Import.png")));
			btnImport.setBounds(20, 11, 67, 73);
			navBar.add(btnImport);
			
			JButton btnExport;
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
			btnSave = new ReflectionButton(ImageIO.read(getClass()
					.getResource("Save.png")));
			btnSave.setBounds(382, 13, 67, 73);
			navBar.add(btnSave);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
		
		
	}
	
	private void setupPainters() {
	    GlossPainter gloss = new GlossPainter();

	/*    PinstripePainter stripes = new PinstripePainter();
	    stripes.setPaint(new Color(1.0f, 1.0f, 1.0f, 0.17f));
	    stripes.setSpacing(5.0);
	  */  
	    MattePainter matte = new MattePainter(new Color(0x242424));
	    navBar.setBackgroundPainter(new CompoundPainter(matte, gloss));
	    
	}
	
	public void openFile() throws IOException, BadLocationException
    {
        
        //set the file extentions that may be chosen
        FileFilter pdatFilter = new FileNameExtensionFilter("Pdat", "pdat");
        FileFilter csvFilter = new FileNameExtensionFilter("csv", "csv");
        
        //Create a file chooser
        final JFileChooser filechooser = new JFileChooser();
        //remove the "All Files" type
        filechooser.setAcceptAllFileFilterUsed(false);
        //add the filter to the file chooser
        filechooser.addChoosableFileFilter(pdatFilter);
        filechooser.addChoosableFileFilter(csvFilter);
        
        //shows the dialog, return value specifies file
        int returnVal = filechooser.showOpenDialog(this);
        
        //if the chosen file is valid
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            file = filechooser.getSelectedFile();
        } 
        else 
        {
            //JOptionPane.showMessageDialog(this, "Open file was cancelled." , "Open Cancelled", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

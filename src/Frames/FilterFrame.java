package Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import ClassAdminFrontEnd.BackgroundGradientPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JButton;

public class FilterFrame extends JFrame {

	private JPanel contentPane;
	private BackgroundGradientPanel backgroundPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					UIManager.put("nimbusBase", new Color(0x7A7A7A));
					UIManager.put("nimbusSelectionBackground", new Color(0x171717));
					UIManager.put("nimbusFocus", new Color(0x00C6E0));
					UIManager.put("Menu.background", new Color(0x2B2B2B));
					UIManager.put("background", new Color(0x171717));
					UIManager.put("DesktopIcon.background", new Color(0x171717));
					UIManager.put("nimbusLightBackground", new Color(0xE3E3E3));

					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilterFrame frame = new FilterFrame();
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
	public FilterFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 313);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// Determine the new location of the window
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		// Move the window
		setLocation(x, y);

		this.setTitle("Filter");

		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setBounds(0, 0, getWidth(), getHeight());
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(261, 44, 102, 27);
		backgroundPanel.add(comboBox);
		
		JLabel lblSelectField = new JLabel("Select Field");
		lblSelectField.setBounds(61, 47, 102, 14);
		backgroundPanel.add(lblSelectField);
		lblSelectField.setForeground(new Color(0xEDEDED));
		
		JLabel lblConditionalRuleType = new JLabel("Conditional Rule Type");
		lblConditionalRuleType.setBounds(61, 89, 123, 14);
		backgroundPanel.add(lblConditionalRuleType);
		lblConditionalRuleType.setForeground(new Color(0xEDEDED));
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(261, 128, 102, 27);
		backgroundPanel.add(spinner);
		
		JLabel lblLowerValue = new JLabel("Lower Value");
		lblLowerValue.setBounds(61, 131, 102, 14);
		backgroundPanel.add(lblLowerValue);
		lblLowerValue.setForeground(new Color(0xEDEDED));
		
		JLabel lblUpperValue = new JLabel("Upper Value");
		lblUpperValue.setBounds(61, 169, 77, 14);
		backgroundPanel.add(lblUpperValue);
		lblUpperValue.setForeground(new Color(0xEDEDED));
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(261, 86, 102, 27);
		backgroundPanel.add(comboBox_1);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(261, 166, 102, 27);
		backgroundPanel.add(spinner_1);
		
		JButton btnCreateFilter = new JButton("Create Filter");
		btnCreateFilter.setBounds(75, 223, 114, 23);
		backgroundPanel.add(btnCreateFilter);
		
		JButton btnRemoveAllFilters = new JButton("Remove All Filters");
		btnRemoveAllFilters.setBounds(222, 223, 141, 23);
		backgroundPanel.add(btnRemoveAllFilters);

		// frame resize listener adjust components accordingly
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent e) {
				backgroundPanel.setBounds(0, 0, contentPane.getWidth(), contentPane.getHeight());
				backgroundPanel.rerenderBackground();

			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

		});
	}
}

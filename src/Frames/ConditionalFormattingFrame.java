package Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;

import ClassAdminFrontEnd.BackgroundGradientPanel;

public class ConditionalFormattingFrame extends JFrame {

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
					ConditionalFormattingFrame frame = new ConditionalFormattingFrame();
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
	public ConditionalFormattingFrame() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 561, 625);
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

		this.setTitle("Conditional Formatting");

		Image icon = Toolkit.getDefaultToolkit().getImage("Logo.png");
		this.setIconImage(icon);

		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setBounds(0, 0, getWidth(), getHeight());
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);

		JComboBox cbxConditionalRuleType = new JComboBox();
		cbxConditionalRuleType.setBounds(361, 49, 113, 26);
		backgroundPanel.add(cbxConditionalRuleType);

		JColorChooser colCombo = new JColorChooser();
		colCombo.remove(colCombo.getComponent(1));
		backgroundPanel.add(colCombo);
		colCombo.setBounds(58, 186, 416, 269);
		/*
		 * add combo box options
		 */
		SpinnerNumberModel SNMmin = new SpinnerNumberModel(new Integer(0), // value
				new Integer(0), // min
				new Integer(100), // max
				new Integer(1) // step
		);
		final JSpinner minVal = new JSpinner(SNMmin);
		minVal.setBounds(361, 93, 113, 26);
		backgroundPanel.add(minVal);

		SpinnerNumberModel SNMmax = new SpinnerNumberModel(new Integer(0), // value
				new Integer(0), // min
				new Integer(100), // max
				new Integer(1) // step
		);
		JSpinner maxVal = new JSpinner(SNMmax);
		maxVal.setBounds(361, 138, 113, 26);
		backgroundPanel.add(maxVal);

		JComboBox whatToFormatCombo = new JComboBox();
		whatToFormatCombo.setBounds(361, 483, 113, 26);
		backgroundPanel.add(whatToFormatCombo);

		JLabel lblConditionalRuleType = new JLabel("Conditional Rule Type");
		lblConditionalRuleType.setBounds(58, 55, 134, 14);
		lblConditionalRuleType.setForeground(new Color(0xEDEDED));
		backgroundPanel.add(lblConditionalRuleType);

		JLabel lblLowerValue = new JLabel("Lower Value");
		lblLowerValue.setBounds(58, 99, 134, 14);
		lblLowerValue.setForeground(new Color(0xEDEDED));
		backgroundPanel.add(lblLowerValue);

		JLabel lblUpperValue = new JLabel("Bottom Value");
		lblUpperValue.setBounds(58, 144, 134, 14);
		lblUpperValue.setForeground(new Color(0xEDEDED));
		backgroundPanel.add(lblUpperValue);

		JLabel lblAffectedArea = new JLabel("Affected Area");
		lblAffectedArea.setBounds(58, 483, 134, 14);
		lblAffectedArea.setForeground(new Color(0xEDEDED));
		backgroundPanel.add(lblAffectedArea);

		JButton btnAddFormatting = new JButton("Add Formatting");
		btnAddFormatting.setBounds(361, 535, 113, 23);
		backgroundPanel.add(btnAddFormatting);

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

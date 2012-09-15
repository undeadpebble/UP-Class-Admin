package ClassAdminFrontEnd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class ConditionalFormattingFrame extends JFrame {

	private JPanel contentPane;
	private BackgroundGradientPanel backgroundPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					UIManager.put("nimbusBase", new Color(0x7A7A7A));
					UIManager.put("nimbusSelectionBackground", new Color(
							0x171717));
					UIManager.put("nimbusFocus", new Color(0x00C6E0));
					UIManager.put("Menu.background", new Color(0x2B2B2B));
					UIManager.put("background", new Color(0x171717));
					UIManager
							.put("DesktopIcon.background", new Color(0x171717));
					UIManager.put("nimbusLightBackground", new Color(0xE3E3E3));

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 588, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		this.setTitle("Conditional Formatting");
		
		Image icon = Toolkit.getDefaultToolkit().getImage("Logo.png");
		this.setIconImage(icon);

		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setBounds(0, 0, getWidth(), getHeight());
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);
		
		JComboBox cbxConditionalRuleType = new JComboBox();
		cbxConditionalRuleType.setBounds(314, 74, 113, 26);
		backgroundPanel.add(cbxConditionalRuleType);
		
		/*
		 * add combo box options
		 */
		
		
		JLabel lblConditionalRuleType = new JLabel("Conditional Rule Type");
		lblConditionalRuleType.setBounds(91, 80, 134, 14);
		lblConditionalRuleType.setForeground(new Color(0xEDEDED));
		backgroundPanel.add(lblConditionalRuleType);

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

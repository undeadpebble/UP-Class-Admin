
package Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import ClassAdminBackEnd.BorderCase;
import ClassAdminFrontEnd.BackgroundGradientPanel;
import ClassAdminFrontEnd.FrmTable;
import ClassAdminFrontEnd.ReflectionButton;

import javax.swing.JButton;

public class BordercaseFrame extends JFrame {

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
					/*ConditionalFormattingFrame frame = new ConditionalFormattingFrame();
					frame.setVisible(true);*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/*BordercaseFrame frame = new BordercaseFrame();
					frame.setVisible(true);*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public BordercaseFrame(final FrmTable table) throws IOException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 486, 285);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		this.setTitle("Bordercase");

		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// Determine the new location of the window
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		// Move the window
		setLocation(x, y);
		
		Image icon = Toolkit.getDefaultToolkit().getImage("Logo.png");
		this.setIconImage(icon);

		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setBounds(0, 0, getWidth(), getHeight());
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);

		JLabel lblBottomBordercaseValue = new JLabel("Bottom bordercase Value");
		lblBottomBordercaseValue.setBounds(54, 63, 160, 14);
		lblBottomBordercaseValue.setForeground(new Color(0xEDEDED));
		backgroundPanel.add(lblBottomBordercaseValue);
		
		JLabel lblUpperBordercaseValue = new JLabel("Upper bordercase Value");
		lblUpperBordercaseValue.setBounds(54, 108, 160, 14);
		lblUpperBordercaseValue.setForeground(new Color(0xEDEDED));
		backgroundPanel.add(lblUpperBordercaseValue);
		
		SpinnerNumberModel SNMmax = new SpinnerNumberModel(
				new Integer(50), // value
				new Integer(0), // min
				new Integer(100), // max
				new Integer(1) // step
		);
		final JSpinner maxVal = new JSpinner(SNMmax);
		maxVal.setBounds(274, 101, 121, 28);

		SpinnerNumberModel SNMmin = new SpinnerNumberModel(
				new Integer(40), // value
				new Integer(0), // min
				new Integer(100), // max
				new Integer(1) // step
		);
		final JSpinner minVal = new JSpinner(SNMmin);
		minVal.setBounds(274, 56, 121, 28);
		
		backgroundPanel.add(maxVal);
		backgroundPanel.add(minVal);
		
		final ReflectionButton btnAddBordercase = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/BordercaseFrame.png")));
		btnAddBordercase.setBounds(333, 155, 62, 86);
		backgroundPanel.add(btnAddBordercase);

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
		
		btnAddBordercase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.headersList
						.get(table.headPoints.get(table.cbheaders
								.getSelectedIndex()))
						.getType()
						.getBorderCasing()
						.add(new BorderCase(Double
								.parseDouble(minVal.getValue()
										.toString()), Double
								.parseDouble(maxVal.getValue()
										.toString())));

				table.repaint();
			}
		});
	}
}

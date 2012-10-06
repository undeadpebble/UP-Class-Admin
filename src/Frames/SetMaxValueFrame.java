
package Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import ClassAdminBackEnd.AbsentException;
import ClassAdminFrontEnd.BackgroundGradientPanel;
import ClassAdminFrontEnd.FrmTable;
import ClassAdminFrontEnd.ReflectionButton;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JButton;

public class SetMaxValueFrame extends JFrame {

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
					/*SetMaxValueFrame frame = new SetMaxValueFrame();
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
	public SetMaxValueFrame(final FrmTable table) throws IOException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 408, 317);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		this.setTitle("Set max value");

		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// Determine the new location of the window
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		// Move the window
		setLocation(x, y);

		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setBounds(0, 0, getWidth(), getHeight());
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);

		final JComboBox MaxValEditing = new JComboBox(table.project.getHead().getNumberHeaders());
		MaxValEditing.setBounds(233, 72, 101, 23);
		backgroundPanel.add(MaxValEditing);

		final JSpinner spinner = new JSpinner();
		spinner.setBounds(233, 123, 101, 23);
		backgroundPanel.add(spinner);

		JLabel lblSelectField = new JLabel("Select Field");
		lblSelectField.setBounds(59, 76, 128, 14);
		backgroundPanel.add(lblSelectField);
		lblSelectField.setForeground(new Color(0xEDEDED));

		JLabel lblSelectMaximumValue = new JLabel("Select Maximum Value");
		lblSelectMaximumValue.setBounds(59, 127, 128, 14);
		backgroundPanel.add(lblSelectMaximumValue);
		lblSelectMaximumValue.setForeground(new Color(0xEDEDED));

		final ReflectionButton btnSetMaxValues = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/MaxValueFrame.png")));
		//JButton btnSetMaxValues = new JButton("Set Max Values");
		btnSetMaxValues.setBounds(275, 190, 59, 83);
		backgroundPanel.add(btnSetMaxValues);

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
		
		btnSetMaxValues.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// public void actionPerformed(ActionEvent e) {
				if (!table.data.get(0).get(MaxValEditing.getSelectedIndex())
						.getType().getIsTextField()) {
					table.data.get(0)
							.get(MaxValEditing.getSelectedIndex())
							.getType()
							.setMaxValue(
									Integer.parseInt(spinner.getValue().toString()));

					for(int x = 0; x < table.data.get(0).size(); x++){
						try {
							if ((table.data.get(0).get(MaxValEditing.getSelectedIndex()).getMark() > (table.data.get(0).get(MaxValEditing.getSelectedIndex()).getType().getMaxValue()))){
								table.data.get(0).get(MaxValEditing.getSelectedIndex()).setMark(table.data.get(0).get(MaxValEditing.getSelectedIndex()).getType().getMaxValue());
							}
						} catch (AbsentException e1) {
							
						}
					}
				}
			}
				
		});
	}
}

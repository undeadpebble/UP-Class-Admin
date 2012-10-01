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

import javax.swing.JColorChooser;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;

import ClassAdminBackEnd.BetweenFormat;
import ClassAdminBackEnd.Format;
import ClassAdminBackEnd.GreaterThanFormat;
import ClassAdminBackEnd.LessThanFormat;
import ClassAdminFrontEnd.BackgroundGradientPanel;
import ClassAdminFrontEnd.FrmTable;
import javax.swing.JTextField;

public class ConditionalFormattingFrame extends JFrame {

	private JPanel contentPane;
	private BackgroundGradientPanel backgroundPanel;
	private JComboBox cbxConditionalRuleType; 
	private JTextField description;

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
	}

	/**
	 * Create the frame.
	 */
	public ConditionalFormattingFrame(final FrmTable table) {
		String[] formatTypesStr = new String[Format.formatTypes.length + 1];
		formatTypesStr[0] = "";

		for (int x = 1; x < formatTypesStr.length; x++) {
			formatTypesStr[x] = Format.formatTypes[x - 1];
		}
		

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 863, 559);
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

		cbxConditionalRuleType = new JComboBox(formatTypesStr);
		cbxConditionalRuleType.setBounds(237, 116, 113, 26);
		backgroundPanel.add(cbxConditionalRuleType);

		final JColorChooser colCombo = new JColorChooser();
		colCombo.remove(colCombo.getComponent(1));
		backgroundPanel.add(colCombo);
		colCombo.setBounds(380, 93, 416, 269);
		/*
		 * add combo box options
		 */
		SpinnerNumberModel SNMmin = new SpinnerNumberModel(new Integer(0), // value
				new Integer(0), // min
				new Integer(100), // max
				new Integer(1) // step
		);
		final JSpinner minVal = new JSpinner(SNMmin);
		minVal.setBounds(237, 167, 113, 26);
		backgroundPanel.add(minVal);

		SpinnerNumberModel SNMmax = new SpinnerNumberModel(new Integer(0), // value
				new Integer(0), // min
				new Integer(100), // max
				new Integer(1) // step
		);
		final JSpinner maxVal = new JSpinner(SNMmax);
		maxVal.setBounds(237, 221, 113, 26);
		backgroundPanel.add(maxVal);

		final JComboBox whatToFormatCombo = new JComboBox(table.numberHeads);
		whatToFormatCombo.setBounds(237, 273, 113, 26);
		backgroundPanel.add(whatToFormatCombo);

		final JLabel lblConditionalRuleType = new JLabel("Conditional Rule Type");
		lblConditionalRuleType.setBounds(58, 122, 134, 14);
		lblConditionalRuleType.setForeground(new Color(0xEDEDED));
		backgroundPanel.add(lblConditionalRuleType);

		final JLabel lblLowerValue = new JLabel("Lower Value");
		lblLowerValue.setBounds(58, 173, 134, 14);
		lblLowerValue.setForeground(new Color(0xEDEDED));
		backgroundPanel.add(lblLowerValue);

		final JLabel lblUpperValue = new JLabel("Upper Value");
		lblUpperValue.setBounds(58, 227, 134, 14);
		lblUpperValue.setForeground(new Color(0xEDEDED));
		backgroundPanel.add(lblUpperValue);

		final JLabel lblAffectedArea = new JLabel("Affected Area");
		lblAffectedArea.setBounds(58, 279, 134, 14);
		lblAffectedArea.setForeground(new Color(0xEDEDED));
		backgroundPanel.add(lblAffectedArea);

		final JButton btnAddFormatting = new JButton("Add Formatting");
		btnAddFormatting.setBounds(335, 451, 113, 23);
		backgroundPanel.add(btnAddFormatting);
		
		final JLabel lblColour = new JLabel("Colour");
		lblColour.setForeground(new Color(237, 237, 237));
		lblColour.setBounds(742, 99, 134, 14);
		backgroundPanel.add(lblColour);
		
		JLabel lblTooltip = new JLabel("Tooltip");
		lblTooltip.setForeground(new Color(237, 237, 237));
		lblTooltip.setBounds(58, 399, 134, 14);
		backgroundPanel.add(lblTooltip);
		
		description = new JTextField();
		description.setBounds(237, 387, 559, 39);
		backgroundPanel.add(description);
		description.setColumns(10);

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
		
		cbxConditionalRuleType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(cbxConditionalRuleType.getSelectedIndex() == 0)) {
					if (cbxConditionalRuleType.getSelectedIndex() == 1) {
						minVal.setEnabled(true);
						maxVal.setEnabled(true);

						colCombo.setEnabled(true);
						whatToFormatCombo.setEnabled(true);

						/*description.setEditable(true);
						label.setEnabled(true);*/

						btnAddFormatting.setEnabled(true);
					} else {
						minVal.setEnabled(true);
						maxVal.setEnabled(false);

						colCombo.setEnabled(true);
						whatToFormatCombo.setEnabled(true);

						/*description.setEditable(true);
						label.setEnabled(true);*/

						btnAddFormatting.setEnabled(true);
					}
				} else {
					minVal.setEnabled(false);
					maxVal.setEnabled(false);

					colCombo.setEnabled(false);
					whatToFormatCombo.setEnabled(false);

					/*description.setEditable(false);
					label.setEnabled(false);*/

					btnAddFormatting.setEnabled(false);
				}
			}
		});

		btnAddFormatting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (cbxConditionalRuleType.getSelectedIndex()) {
				case 1: {
					if (whatToFormatCombo.getSelectedIndex() == 0) {
						table.headersList
								.get(table.headPoints.get(table.cbFormatting
										.getSelectedIndex()))
								.getType()
								.getFormatting()
								.add(new BetweenFormat(Double
										.parseDouble(minVal.getValue()
												.toString()), Double
										.parseDouble(maxVal.getValue()
												.toString()), null,
												colCombo.getColor(),
										description.getText()));

					} else {
						table.headersList
								.get(table.headPoints.get(table.cbFormatting
										.getSelectedIndex()))
								.getType()
								.getFormatting()
								.add(new BetweenFormat(Double
										.parseDouble(minVal.getValue()
												.toString()), Double
										.parseDouble(maxVal.getValue()
												.toString()),colCombo.getColor(),
										null, description.getText()));

					}
					table.repaint();
					break;
				}
				case 2: {
					if (whatToFormatCombo.getSelectedIndex() == 0) {
						table.headersList
								.get(table.headPoints.get(table.cbFormatting
										.getSelectedIndex()))
								.getType()
								.getFormatting()
								.add(new GreaterThanFormat(Double
										.parseDouble(minVal.getValue()
												.toString()), null,
												colCombo.getColor(),
										description.getText()));
					} else {
						table.headersList
								.get(table.headPoints.get(table.cbFormatting
										.getSelectedIndex()))
								.getType()
								.getFormatting()
								.add(new GreaterThanFormat(Double
										.parseDouble(minVal.getValue()
												.toString()),colCombo.getColor(),
										null, description.getText()));

					}
					table.repaint();
					break;
				}
				case 3: {
					if (whatToFormatCombo.getSelectedIndex() == 0) {
						table.headersList
								.get(table.headPoints.get(table.cbFormatting
										.getSelectedIndex()))
								.getType()
								.getFormatting()
								.add(new LessThanFormat(Double
										.parseDouble(minVal.getValue()
												.toString()), null,
												colCombo.getColor(),
										description.getText()));
					} else {
						table.headersList
								.get(table.headPoints.get(table.cbFormatting
										.getSelectedIndex()))
								.getType()
								.getFormatting()
								.add(new LessThanFormat(Double
										.parseDouble(minVal.getValue()
												.toString()), colCombo.getColor(),
										null, description.getText()));

					}
					table.repaint();
					break;
				}

				}
			}
		});
	}
}

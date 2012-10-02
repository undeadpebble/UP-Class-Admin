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
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import ClassAdminBackEnd.Format;
import ClassAdminFrontEnd.BackgroundGradientPanel;
import ClassAdminFrontEnd.FrmTable;
import ClassAdminFrontEnd.JComboCheckBox;

import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JButton;

public class FilterFrame extends JFrame {

	private JPanel contentPane;
	private BackgroundGradientPanel backgroundPanel;
	private JComboBox cbxFormatType;

	private JComboCheckBox selectAllData;

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
					/*
					 * FilterFrame frame = new FilterFrame();
					 * frame.setVisible(true);
					 */
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FilterFrame(final FrmTable table) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 313);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		final String[] formatTypesStr = new String[Format.formatTypes.length + 1];
		formatTypesStr[0] = "";

		for (int x = 1; x < formatTypesStr.length; x++) {
			formatTypesStr[x] = Format.formatTypes[x - 1];
		}

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

		final JComboBox cbxFilters = new JComboBox(table.headers);
		cbxFilters.setBounds(261, 44, 102, 27);
		backgroundPanel.add(cbxFilters);

		final JLabel lblSelectField = new JLabel("Select Field");
		lblSelectField.setBounds(61, 47, 102, 14);
		backgroundPanel.add(lblSelectField);
		lblSelectField.setForeground(new Color(0xEDEDED));

		final JLabel lblConditionalRuleType = new JLabel(
				"Conditional Rule Type");
		lblConditionalRuleType.setBounds(61, 89, 123, 14);
		backgroundPanel.add(lblConditionalRuleType);
		lblConditionalRuleType.setForeground(new Color(0xEDEDED));

		final JSpinner minVal = new JSpinner();
		minVal.setBounds(261, 128, 102, 27);
		backgroundPanel.add(minVal);

		final JLabel lblLowerValue = new JLabel("Lower Value");
		lblLowerValue.setBounds(61, 131, 102, 14);
		backgroundPanel.add(lblLowerValue);
		lblLowerValue.setForeground(new Color(0xEDEDED));
		
		final JLabel lblSelectValues = new JLabel("Select Values to Filter");
		lblSelectValues.setBounds(61, 131, 102, 14);
		backgroundPanel.add(lblSelectValues);
		lblSelectValues.setForeground(new Color(0xEDEDED));

		final JLabel lblUpperValue = new JLabel("Upper Value");
		lblUpperValue.setBounds(61, 169, 77, 14);
		backgroundPanel.add(lblUpperValue);
		lblUpperValue.setForeground(new Color(0xEDEDED));

		cbxFormatType = new JComboBox(formatTypesStr);
		cbxFormatType.setBounds(261, 86, 102, 27);
		backgroundPanel.add(cbxFormatType);

		final JSpinner maxVal = new JSpinner();
		maxVal.setBounds(261, 166, 102, 27);
		backgroundPanel.add(maxVal);

		final JButton btnCreateFilter = new JButton("Create Filter");
		btnCreateFilter.setBounds(75, 223, 114, 23);
		backgroundPanel.add(btnCreateFilter);

		final JButton btnRemoveAllFilters = new JButton("Remove All Filters");
		btnRemoveAllFilters.setBounds(222, 223, 141, 23);
		backgroundPanel.add(btnRemoveAllFilters);

		btnCreateFilter.setEnabled(false);

		if (table.data.get(0).get(cbxFilters.getSelectedIndex()).getType()
				.getIsTextField()) {
			lblSelectField.setVisible(false);
			lblConditionalRuleType.setVisible(false);
			minVal.setVisible(false);
			maxVal.setVisible(false);
			lblLowerValue.setVisible(false);
			lblUpperValue.setVisible(false);
		} else {
			lblSelectField.setVisible(true);
			lblConditionalRuleType.setVisible(true);
			minVal.setVisible(true);
			maxVal.setVisible(true);
			lblLowerValue.setVisible(true);
			lblUpperValue.setVisible(true);
			cbxFormatType = new JComboBox(formatTypesStr);
		}

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
				backgroundPanel.setBounds(0, 0, contentPane.getWidth(),
						contentPane.getHeight());
				backgroundPanel.rerenderBackground();

			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

		});

		cbxFormatType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(cbxFormatType.getSelectedIndex() == 0)) {
					if (cbxFormatType.getSelectedIndex() == 1) {
						minVal.setEnabled(true);
						maxVal.setEnabled(true);

						btnCreateFilter.setEnabled(true);
					} else {
						minVal.setEnabled(true);
						maxVal.setEnabled(false);

						btnCreateFilter.setEnabled(true);
					}
				} else {
					minVal.setEnabled(false);
					maxVal.setEnabled(false);

					btnCreateFilter.setEnabled(false);
				}
			}
		});

		if (table.data.get(0).get(cbxFilters.getSelectedIndex()).getType()
				.getIsTextField()) {
			final LinkedList<String> selectedValues = new LinkedList<String>();

			final LinkedList<String> dataInCol = new LinkedList<String>();

			for (int i = 0; i < table.data.size(); i++) {
				dataInCol.add(table.data.get(i)
						.get(cbxFilters.getSelectedIndex()).getValue());

			}

			final JCheckBox[] selectData = new JCheckBox[dataInCol.size()];

			for (int z = 0; z < dataInCol.size(); z++) {
				selectData[z] = new JCheckBox(dataInCol.get(z));
			}

			JComboCheckBox temps = new JComboCheckBox(selectData);
			selectAllData = temps;

			selectAllData.setBounds(261, 166, 102, 27);
			backgroundPanel.add(selectAllData);

			/*
			 * for(int z = 0; z < selectData.length; z++)
			 * System.out.println(selectData[z].getName());
			 */

			lblSelectField.setVisible(true);
			lblConditionalRuleType.setVisible(false);
			minVal.setVisible(false);
			maxVal.setVisible(false);
			lblLowerValue.setVisible(false);
			lblUpperValue.setVisible(false);
			cbxFormatType.setVisible(false);

			btnCreateFilter.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					for (int x = 0; x < dataInCol.size(); x++) {
						if (selectData[x].isSelected()) {
							selectedValues.add(dataInCol.get(x));
						}
					}

					for (int x = 0; x < table.dataFilter.length; x++) {
						if (!selectedValues.contains(table.data.get(x)
								.get(cbxFilters.getSelectedIndex()).getValue())) {
							table.filters.get(x).set(
									cbxFilters.getSelectedIndex(), true);
							table.dataFilter[x] = false;
						}
					}

					table.filterTable();

				}

			});
		} else {

			btnCreateFilter.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					switch (cbxFormatType.getSelectedIndex()) {
					case 1: {
						for (int x = 0; x < table.dataFilter.length; x++) {
							if (table.data.get(x)
									.get(cbxFilters.getSelectedIndex())
									.getMark() > Double.parseDouble(maxVal
									.getValue().toString())
									|| table.data.get(x)
											.get(cbxFilters.getSelectedIndex())
											.getMark() < Double
											.parseDouble(minVal.getValue()
													.toString())) {
								table.filters.get(x).set(
										cbxFormatType.getSelectedIndex(), true);
							}

						}
						break;
					}
					case 2: {
						for (int x = 0; x < table.dataFilter.length; x++) {
							if (table.data.get(x)
									.get(cbxFilters.getSelectedIndex())
									.getMark() < Double.parseDouble(minVal
									.getValue().toString())) {
								table.filters.get(x).set(
										cbxFormatType.getSelectedIndex(), true);
							}

						}

						break;
					}
					case 3: {
						for (int x = 0; x < table.dataFilter.length; x++) {
							if (table.data.get(x)
									.get(cbxFilters.getSelectedIndex())
									.getMark() > Double.parseDouble(minVal
									.getValue().toString())) {
								table.filters.get(x).set(
										cbxFormatType.getSelectedIndex(), true);
							}

						}

						break;
					}

					}

					table.filterTable();

				}
			});
		}

		cbxFilters.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (table.data.get(0).get(cbxFilters.getSelectedIndex())
						.getType().getIsTextField()) {
					lblSelectField.setVisible(true);
					lblConditionalRuleType.setVisible(false);
					minVal.setVisible(false);
					maxVal.setVisible(false);
					lblLowerValue.setVisible(false);
					lblUpperValue.setVisible(false);
					cbxFormatType.setVisible(false);
				} else {
					lblSelectField.setVisible(true);
					lblConditionalRuleType.setVisible(true);
					minVal.setVisible(true);
					maxVal.setVisible(true);
					lblLowerValue.setVisible(true);
					lblUpperValue.setVisible(true);
					cbxFormatType.setVisible(true);
				}

			}
		});

		selectAllData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				btnCreateFilter.setEnabled(true);

			}
		});
		
		btnRemoveAllFilters.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Object[][] temp = new Object[table.data.size()][table.data.get(0).size()];

				for (int x = 0; x < table.data.size(); x++) {
					for (int y = 0; y < table.data.get(0).size(); y++) {
						temp[x][y] =table. data.get(x).get(y).getValue();
					}
				}

				for (int x = 0; x < table.filters.size(); x++) {
					for (int y = 0; y < table.filters.get(0).size(); y++) {
						table.filters.get(x).set(y, false);
					}
				}

				int y = table.tableModel.getRowCount();
				for (int x = 0; x < y; x++) {
					table.tableModel.removeRow(0);
				}
				for (int x = 0; x < table.data.size(); x++) {
					table.tableModel.addRow(temp[x]);
				}
			}
		});
	}
}

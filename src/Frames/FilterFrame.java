
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
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import ClassAdminBackEnd.Format;
import ClassAdminFrontEnd.BackgroundGradientPanel;
import ClassAdminFrontEnd.FrmTable;
import ClassAdminFrontEnd.JComboCheckBox;
import ClassAdminFrontEnd.ReflectionButton;

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
	private ReflectionButton btnCreateFilter;
	private ReflectionButton btnRemoveAllFilters;
	private ReflectionButton btnRemoveSpesificFilter;

	private JComboCheckBox selectAllData;

	/**
	 * @param table
	 * creates a new filterframe
	 */
	public FilterFrame(final FrmTable table) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 355);
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
		
		Image icon = Toolkit.getDefaultToolkit().getImage("icons/FilterFrame.png");
		this.setIconImage(icon);

		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setBounds(0, 0, 446, 317);
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
		lblSelectValues.setBounds(61, 180, 150, 14);
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
		maxVal.setBounds(261, 174, 102, 27);
		backgroundPanel.add(maxVal);

		try {
			btnCreateFilter = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/FilterFrameLabel.png")));
			btnCreateFilter.setBounds(49, 225, 91, 92);
			backgroundPanel.add(btnCreateFilter);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//final JButton btnCreateFilter = new JButton("Create Filter");
		
		try {
			btnRemoveAllFilters = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/FilterFrameLabelRemove.png")));
			btnRemoveAllFilters.setBounds(167, 225, 91, 92);
			backgroundPanel.add(btnRemoveAllFilters);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//final JButton btnRemoveAllFilters = new JButton("Remove All Filters");
		
		try {
			btnRemoveSpesificFilter = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/FilterFrameLabelRemoveSpesific.png")));
			btnRemoveSpesificFilter.setBounds(283, 225, 91, 92);
			backgroundPanel.add(btnRemoveSpesificFilter);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		btnCreateFilter.setEnabled(false);
		btnCreateFilter.setDisabled();

		//JButton btnRemoveSpesificFilter = new JButton("Remove Spesific Filter");
		

		if (table.data.get(0).get(cbxFilters.getSelectedIndex()).getType()
				.getIsTextField()) {
			lblSelectField.setVisible(false);
			lblConditionalRuleType.setVisible(false);
			minVal.setVisible(false);
			maxVal.setVisible(false);
			lblLowerValue.setVisible(false);
			lblUpperValue.setVisible(false);
			lblSelectValues.setVisible(true);
		} else {
			lblSelectField.setVisible(true);
			lblConditionalRuleType.setVisible(true);
			minVal.setVisible(true);
			maxVal.setVisible(true);
			lblLowerValue.setVisible(true);
			lblUpperValue.setVisible(true);
			lblSelectValues.setVisible(false);
			cbxFilters.setVisible(false);
			cbxFormatType = new JComboBox(formatTypesStr);
		}

		// frame resize listener adjust components accordingly
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent e) {

			}

			@Override
			public void componentMoved(ComponentEvent e) {

			}

			@Override
			public void componentResized(ComponentEvent e) {
				backgroundPanel.setBounds(0, 0, contentPane.getWidth(),
						contentPane.getHeight());
				backgroundPanel.rerenderBackground();

			}

			@Override
			public void componentShown(ComponentEvent e) {

			}

		});

		final LinkedList<String> selectedValues = new LinkedList<String>();

		final LinkedList<String> dataInCol = new LinkedList<String>();
		
		cbxFormatType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(cbxFormatType.getSelectedIndex() == 0)) {
					if (cbxFormatType.getSelectedIndex() == 1) {
						minVal.setEnabled(true);
						maxVal.setEnabled(true);
						
						btnCreateFilter.setEnabled();
						btnCreateFilter.setEnabled(true);
						
					} else {
						minVal.setEnabled(true);
						maxVal.setEnabled(false);
						
						btnCreateFilter.setEnabled();
						btnCreateFilter.setEnabled(true);
						
					}
				} else {
					minVal.setEnabled(false);
					maxVal.setEnabled(false);

					btnCreateFilter.setEnabled(false);
					btnCreateFilter.setDisabled();
				}
			}
		});

		cbxFilters.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				while(btnCreateFilter.getActionListeners().length > 0)
					btnCreateFilter.removeActionListener(btnCreateFilter.getActionListeners()[0]);	
				
				if (table.data.get(0).get(cbxFilters.getSelectedIndex())
						.getType().getIsTextField()) {
					lblSelectField.setVisible(true);
					lblConditionalRuleType.setVisible(false);
					minVal.setVisible(false);
					maxVal.setVisible(false);
					lblLowerValue.setVisible(false);
					lblUpperValue.setVisible(false);
					cbxFormatType.setVisible(false);
					lblSelectValues.setVisible(true);
					try{
					selectAllData.setVisible(true);
					}catch (Exception e) {
						// TODO: handle exception
					}
				} else {
					try{
						selectAllData.setVisible(false);
						}
						catch (Exception e) {
							// TODO: handle exception
						}
					lblSelectField.setVisible(true);
					lblConditionalRuleType.setVisible(true);
					minVal.setVisible(true);
					maxVal.setVisible(true);
					lblLowerValue.setVisible(true);
					lblUpperValue.setVisible(true);
					cbxFormatType.setVisible(true);
					lblSelectValues.setVisible(false);
					
				}
				
				
				if (table.data.get(0).get(cbxFilters.getSelectedIndex())
						.getType().getIsTextField()) {
					
					dataInCol.clear();
					for (int i = 0; i < table.data.size(); i++) {
						dataInCol.add(table.data.get(i).get(cbxFilters.getSelectedIndex()).getValue());
					}

					final JCheckBox[] selectData = new JCheckBox[dataInCol.size()];

					for (int z = 0; z < dataInCol.size(); z++) {
						selectData[z] = new JCheckBox(dataInCol.get(z));
					}
					
					backgroundPanel.remove(backgroundPanel.getComponentAt(261, 166));

					JComboCheckBox temps = new JComboCheckBox(selectData);
					selectAllData = temps;

					selectAllData.setBounds(261, 166, 102, 27);
					backgroundPanel.add(selectAllData);

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
										.get(cbxFilters.getSelectedIndex())
										.getValue())) {
									table.filters.get(x)
											.set(cbxFilters.getSelectedIndex(),
													true);
									table.dataFilter[x] = false;
								}
							}

							table.filterTable();

						}

					});
					
					selectAllData.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							btnCreateFilter.setEnabled(true);
							btnCreateFilter.setEnabled();

						}
					});
				} else {
					btnCreateFilter.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							try {

								switch (cbxFormatType.getSelectedIndex()) {
								case 1: {
									for (int x = 0; x < table.dataFilter.length; x++) {
										if (table.data
												.get(x)
												.get(cbxFilters
														.getSelectedIndex())
												.getMark() > Double
												.parseDouble(maxVal.getValue()
														.toString())
												|| table.data
														.get(x)
														.get(cbxFilters
																.getSelectedIndex())
														.getMark() < Double
														.parseDouble(minVal
																.getValue()
																.toString())) {
											table.filters
													.get(x)
													.set(cbxFilters
															.getSelectedIndex(),
															true);
										}

									}
									break;
								}
								case 2: {
									for (int x = 0; x < table.dataFilter.length; x++) {
										if (table.data
												.get(x)
												.get(cbxFilters
														.getSelectedIndex())
												.getMark() < Double
												.parseDouble(minVal.getValue()
														.toString())) {
											table.filters
													.get(x)
													.set(cbxFilters
															.getSelectedIndex(),
															true);
										}

									}

									break;
								}
								case 3: {
									for (int x = 0; x < table.dataFilter.length; x++) {
										if (table.data
												.get(x)
												.get(cbxFilters
														.getSelectedIndex())
												.getMark() > Double
												.parseDouble(minVal.getValue()
														.toString())) {
											table.filters.get(x).set(cbxFilters.getSelectedIndex(),true);
											
										}
									}
									
									break;
								}

								}

								table.filterTable();

							} catch (Exception ex) {
								// TODO: handle exception
							}
						}
					});
				}

			}
		});
		
		cbxFilters.setSelectedIndex(0);

		

		btnRemoveAllFilters.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				for(int x = 0; x < table.filters.size();x++){
					for(int y = 0; y < table.filters.get(0).size();y++){
						table.filters.get(x).set(y, false);
					}
				}
				
				table.filterTable();
			}
		});

		btnRemoveSpesificFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				RemoveFilter removeFilter = new RemoveFilter(table);
				removeFilter.setVisible(true);
			}
		});
	}
}

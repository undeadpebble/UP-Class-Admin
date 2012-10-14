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
import Rule.FloatBoolRule;
import Rule.FloatRule;
import Rule.StringBoolRule;
import Rule.StringRule;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JButton;

public class SetMaxValueFrame extends JFrame {

	private JPanel contentPane;
	private BackgroundGradientPanel backgroundPanel;

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

		String[] head = new String[table.headPoints.size()];

		for (int z = 0; z < table.headPoints.size(); z++) {
			head[z] = table.headers[table.headPoints.get(z)];
		}

		final JComboBox MaxValEditing = new JComboBox(head);
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

		final ReflectionButton btnSetMaxValues = new ReflectionButton(
				ImageIO.read(getClass().getResource(
						"/ClassAdminFrontEnd/resources/MaxValueFrame.png")));
		// JButton btnSetMaxValues = new JButton("Set Max Values");
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
				backgroundPanel.setBounds(0, 0, contentPane.getWidth(),
						contentPane.getHeight());
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
				if (!table.data
						.get(0)
						.get(table.headPoints.get(MaxValEditing
								.getSelectedIndex())).getType()
						.getIsTextField()) {
					table.data
							.get(0)
							.get(table.headPoints.get(MaxValEditing
									.getSelectedIndex()))
							.getType()
							.setMaxValue(
									Integer.parseInt(spinner.getValue()
											.toString()));

					for (int x = 0; x < table.data.size(); x++) {
						try {
							if ((table.data
									.get(x)
									.get(table.headPoints.get(MaxValEditing
											.getSelectedIndex())).getMark() > (table.data
									.get(0)
									.get(table.headPoints.get(MaxValEditing
											.getSelectedIndex())).getType()
									.getMaxValue()))) {
								table.data
										.get(x)
										.get(table.headPoints.get(MaxValEditing
												.getSelectedIndex()))
										.setMark(
												Integer.parseInt(spinner
														.getValue().toString()));
							}

						} catch (AbsentException e1) {

						}
					}

					/*
					 * for (int y = 0; y < table.data.size(); y++) {
					 * table.getTable() .getModel() .setValueAt( table.data
					 * .get(y) .get(table.headPoints.get(MaxValEditing
					 * .getSelectedIndex())) .getValue(), y,
					 * table.headPoints.get(MaxValEditing .getSelectedIndex()));
					 * }
					 */

					for (int x = 0; x < table.project.getRules().size(); x++) {
						try {
							if (((StringRule) table.project.getRules().get(x))
									.getReferences() == table.headersList.get(
									table.headPoints.get(MaxValEditing
											.getSelectedIndex())).getType()) {
								for (int y = 0; y < table.data.size(); y++) {
									((StringRule) table.project.getRules().get(
											x)).setValue(table.data
											.get(y)
											.get(table.headPoints.get(MaxValEditing
													.getSelectedIndex()))
											.getValue());
								}
							}
						} catch (Exception ex) {
							try {
								if (((FloatRule) table.project.getRules()
										.get(x)).getReferences() == table.headersList
										.get(table.headPoints.get(MaxValEditing
												.getSelectedIndex())).getType()) {
									for (int y = 0; y < table.data.size(); y++) {
										((FloatRule) table.project.getRules()
												.get(x)).setValue(Double.parseDouble(table.data
												.get(y)
												.get(table.headPoints.get(MaxValEditing
														.getSelectedIndex()))
												.getValue()));
									}

								}
							} catch (Exception e2) {
							}
						}
					}

					for (int x = 0; x < table.project.getRules().size(); x++) {

						try {
							((StringRule)table.project.getRules().get(x)).evaluateString(null);
						} catch (Exception e2) {
							try {
								((FloatRule)table.project.getRules().get(x)).evaluateDouble(null);
							} catch (Exception e3) {
								try {
									((StringBoolRule)table.project.getRules().get(x)).evaluateBool(null);
								} catch (Exception e4) {
									try {
										((FloatBoolRule)table.project.getRules().get(x)).evaluateBool(null);
									} catch (Exception e5) {

									}
								}
							}
						}
					}
					/*
					 * table.tableModel.fireTableDataChanged();
					 * table.getTable().repaint();
					 */
					table.redraw();
				}

				closeFrame();
			}

		});

	}

	public void closeFrame() {
		this.dispose();
	}
}

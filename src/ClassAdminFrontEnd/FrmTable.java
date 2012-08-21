package ClassAdminFrontEnd;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.MultiSplitLayout.Leaf;

import ClassAdminBackEnd.Global;

import ClassAdminBackEnd.BorderCase;
import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Format;
import ClassAdminBackEnd.LeafStringEntity;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.StringEntity;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminBackEnd.TableCellListener;

public class FrmTable extends JPanel {
	private JXTable table;
	private JButton btnAdd;
	private DefaultTableModel tableModel;
	private JTextField txtField1;
	private JTextField txtField2;
	LinkedList<LinkedList<SuperEntity>> data;
	Project project;

	private LinkedList<Integer> selected = new LinkedList<Integer>();

	public FrmTable(String[] headers, LinkedList<LinkedList<SuperEntity>> data,
			Project project) {
		this.data = data;
		this.project = project;
		createGUI(headers);
	}

	private void createGUI(String[] headers) {
		setLayout(new BorderLayout());
		JScrollPane pane = new JScrollPane();

		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				TableCellListener tcl = (TableCellListener) e.getSource();

				if (tcl.getOldValue() != tcl.getNewValue()) {
					if (data.get(tcl.getRow()).get(tcl.getColumn())
							.getDetails().getType().getIsTextField()) {
						data.get(tcl.getRow()).get(tcl.getColumn())
								.getDetails()
								.setValue((String) tcl.getNewValue());
					} else {
						try {
							data.get(tcl.getRow())
									.get(tcl.getColumn())
									.setMark(
											(Double.parseDouble((String) tcl
													.getNewValue())));
						} catch (Exception ex) {
							table.getModel().setValueAt(tcl.getOldValue(),
									tcl.getRow(), tcl.getColumn());
						}
					}
				}
			}
		};

		Object[][] temp = new Object[data.size()][data.get(0).size()];

		for (int x = 0; x < data.get(1).size(); x++) {
			project.getSelected().add(data.get(1).get(x));
		}

		for (int x = 0; x < data.size(); x++) {
			for (int y = 0; y < data.get(0).size(); y++) {
				temp[x][y] = data.get(x).get(y).getValue();
			}
		}

		table = new JXTable() {
			public Component prepareRenderer(TableCellRenderer renderer,
					int Index_row, int Index_col) {
				Component comp = super.prepareRenderer(renderer, Index_row,
						Index_col);
				// even index, selected or not selected
				try {
					if (project.getSelected().contains(
							data.get(
									table.getRowSorter()
											.convertRowIndexToModel(Index_row))
									.get(Index_col))) {
						int[] intTest = table.getSelectedRows();
						boolean temp = false;

						for (int x = 0; x < intTest.length; x++) {
							if (intTest[x] == convertRowIndexToModel(Index_row)) {
								temp = true;
							}
						}
						if (intTest.length > 0) {
							if (!temp) {
								project.getSelected()
										.remove(data
												.get(table
														.getRowSorter()
														.convertRowIndexToModel(
																Index_row))
												.get(Index_col));
								comp.setBackground(Color.white);
							}
						} else {
							comp.setBackground(Color.green);
							table.addRowSelectionInterval(Index_row, Index_row);
						}
					} else {
						comp.setBackground(Color.white);
					}

					if (isCellSelected(Index_row, Index_col)) {
						comp.setBackground(Color.green);
						project.getSelected().add(
								data.get(
										table.getRowSorter()
												.convertRowIndexToModel(
														Index_row)).get(
										Index_col));
					}

					LinkedList<BorderCase> bordercases = data
							.get(table.getRowSorter().convertRowIndexToModel(
									Index_row)).get(Index_col).getType()
							.getBorderCasing();

					for (int x = 0; x < bordercases.size(); x++) {
						/*if (bordercases.get(x).isBorderCase(data.get(table.getRowSorter().convertRowIndexToModel(Index_row)).get(Index_col).getValue())){
							comp.setBackground(Color.cyan);
						}*/
					}

					return comp;
				} catch (Exception e) {
					// TODO: handle exception
					return null;
				}
			}
		};
		table.setAutoCreateRowSorter(true);

		TableCellListener tcl = new TableCellListener(table, action);

		table.addPropertyChangeListener(tcl);

		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						int viewRow = table.getSelectedRow();
						if (viewRow < 0) {
						} else {
							int modelRow = table
									.convertRowIndexToModel(viewRow);
						}
					}

				});

		pane.setViewportView(table);
		JPanel eastPanel = new JPanel();

		JPanel border = new JPanel();
		JButton bordercase = new JButton("Add bordercase");
		border.add(bordercase);

		final JComboBox cbheaders = new JComboBox(headers);
		final LinkedList<SuperEntity> headersList = project.getHead()
				.getHeadersLinkedList();
		border.add(cbheaders);

		bordercase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (headersList.get(cbheaders.getSelectedIndex()).getType()
						.getIsTextField()) {
					// Cannot add border cases to a text field
					System.out
							.println("Cannot add border cases to a text field");
				} else {
					final JFrame borderFrame = new JFrame();

					SpinnerNumberModel SNMmax = new SpinnerNumberModel(
							new Integer(40), // value
							new Integer(0), // min
							new Integer(100), // max
							new Integer(1) // step
					);
					final JSpinner maxVal = new JSpinner(SNMmax);

					SpinnerNumberModel SNMmin = new SpinnerNumberModel(
							new Integer(49), // value
							new Integer(0), // min
							new Integer(100), // max
							new Integer(1) // step
					);
					final JSpinner minVal = new JSpinner(SNMmin);

					borderFrame.setLayout(new BorderLayout());

					borderFrame.add(maxVal, BorderLayout.CENTER);
					borderFrame.add(minVal, BorderLayout.NORTH);

					JButton addBorderCase = new JButton("Add border case");
					borderFrame.add(addBorderCase, BorderLayout.SOUTH);

					borderFrame.setVisible(true);
					borderFrame.setSize(400, 400);

					addBorderCase.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							headersList
									.get(cbheaders.getSelectedIndex())
									.getType()
									.getBorderCasing()
									.add(new BorderCase(Double
											.parseDouble(minVal.getValue()
													.toString()), Double
											.parseDouble(maxVal.getValue()
													.toString())));

							borderFrame.setVisible(false);

							table.repaint();
						}
					});
				}
			}
		});
		
		JButton btnAddConditionalFormatting = new JButton("Add conditional formatting");
		final JComboBox cbFormatting = new JComboBox(headers);

		eastPanel.setLayout(new GridLayout(6, 2));

		btnAdd = new JButton("Add row");
		eastPanel.add(btnAdd);

		JButton btnView = new JButton("View student");
		eastPanel.add(btnView);

		eastPanel.add(border);
		
		JPanel formatting = new JPanel();
		formatting.add(btnAddConditionalFormatting);
		formatting.add(cbFormatting);
		eastPanel.add(formatting);

		JPanel northPanel = new JPanel();

		txtField1 = new JTextField();
		txtField2 = new JTextField();

		JLabel lblField1 = new JLabel("Column1   ");
		JLabel lblField2 = new JLabel("Column2   ");
		txtField1.setPreferredSize(lblField1.getPreferredSize());
		txtField2.setPreferredSize(lblField2.getPreferredSize());

		add(northPanel, BorderLayout.NORTH);
		add(eastPanel, BorderLayout.EAST);
		add(pane, BorderLayout.CENTER);

		table.setColumnControlVisible(true);
		table.setHorizontalScrollEnabled(true);

		tableModel = new DefaultTableModel(temp, (Object[]) headers);
		table.setModel(tableModel);

		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.repaint();
				int count = tableModel.getRowCount() + 1;

				LinkedList<EntityType> temp = project.getEntityTypes();
				for (int x = 0; x < temp.size(); x++) {
					if (temp.get(x).getIsTextField()) {
						LeafStringEntity addEntity = new LeafStringEntity(temp
								.get(x), null, "");
					} else {

					}

				}

				// data.add(newToAdd);

				tableModel.addRow(new Object[] { txtField1.getText(),
						txtField1.getText() });
				table.repaint();

			}
		});
		
		btnAddConditionalFormatting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LinkedList<Format> formats = headersList.get(0).getType().getFormatting();
				
			}
		});

		btnView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.repaint();
				table.getSelectedRow();

				TreeView.createStudentFrm("name",
						data.get(table.getSelectedRow()).get(0));
			}
		});

	}

	public class InteractiveTableModelListener implements TableModelListener {
		public void tableChanged(TableModelEvent evt) {
			if (evt.getType() == TableModelEvent.UPDATE) {
				int column = evt.getColumn();
				int row = evt.getFirstRow();
				System.out.println("row: " + row + " column: " + column);
				table.setColumnSelectionInterval(column + 1, column + 1);
				table.setRowSelectionInterval(row, row);
				table.repaint();
			}
		}
	}
}

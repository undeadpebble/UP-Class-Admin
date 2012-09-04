package ClassAdminFrontEnd;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Event;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
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

import prefuse.data.search.SearchTupleSet;

import ClassAdminBackEnd.Global;

import ClassAdminBackEnd.BetweenFormat;
import ClassAdminBackEnd.BorderCase;
import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Format;
import ClassAdminBackEnd.LeafMarkEntity;
import ClassAdminBackEnd.LeafStringEntity;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.StringEntity;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminBackEnd.SuperEntityPointer;
import ClassAdminBackEnd.TableCellListener;
import ClassAdminBackEnd.GreaterThanFormat;
import ClassAdminBackEnd.LessThanFormat;

public class FrmTable extends JPanel {
	private JXTable table;
	private JButton btnAdd;
	private DefaultTableModel tableModel;
	private JTextField txtField1;
	private JTextField txtField2;
	private LinkedList<Color> colors;
	private LinkedList<String> colorsString;
	LinkedList<LinkedList<SuperEntity>> data;
	Project project;

	private LinkedList<Integer> selected = new LinkedList<Integer>();
	
	private String getColorName(Color c){
		for(int x = 0; x <colors.size();x++){
			if(c == colors.get(x)){
				return(colorsString.get(x));
			}
		}
		return("");
	}

	public FrmTable(String[] headers, LinkedList<LinkedList<SuperEntity>> data,
			Project project) {
		this.data = data;
		this.project = project;

		colors = new LinkedList<Color>();
		colorsString = new LinkedList<String>();

		colors.add(Color.gray);
		colorsString.add("Gray");
		colors.add(Color.blue);
		colorsString.add("Blue");
		colors.add(Color.lightGray);
		colorsString.add("LightGray");
		colors.add(Color.magenta);
		colorsString.add("Magenta");
		colors.add(Color.orange);
		colorsString.add("Orange");
		colors.add(Color.pink);
		colorsString.add("Pink");
		colors.add(Color.red);
		colorsString.add("Red");
		colors.add(Color.yellow);
		colorsString.add("Yellow");
		colors.add(Color.green);
		colorsString.add("Green");

		createGUI(headers);
	}

	private void createGUI(String[] headers) {
		setLayout(new BorderLayout());
		JScrollPane pane = new JScrollPane();

		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				TableCellListener tcl = (TableCellListener) e.getSource();
				
				if (tcl.getOldValue() != tcl.getNewValue()) {
					if (data.get(tcl.getRow()).get(tcl.getColumn()).getDetails().getType().getIsTextField()) {
						data.get(tcl.getRow()).get(tcl.getColumn()).getDetails().setValue((String) tcl.getNewValue());
					} else {
						try {
							
							
							if(Double.parseDouble((String) tcl.getNewValue()) >= 0 && data.get(tcl.getRow()).get(tcl.getColumn()).getType().getMaxValue() >= Double.parseDouble((String) tcl.getNewValue())){
								data.get(tcl.getRow()).get(tcl.getColumn()).setMark((Double.parseDouble((String) tcl.getNewValue())));
							}
							else{
								table.getModel().setValueAt(tcl.getOldValue(),
										tcl.getRow(), tcl.getColumn());
							}
						} catch (Exception ex) {
							table.getModel().setValueAt(tcl.getOldValue(),
									tcl.getRow(), tcl.getColumn());
						}
					}
				}
			}
		};

		Object[][] temp = new Object[data.size()][data.get(0).size()];

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
					
					LinkedList<Color> backgroundColors = new LinkedList<Color>();
					LinkedList<Color> textColors = new LinkedList<Color>();

					LinkedList<Format> format = data
							.get(table.getRowSorter().convertRowIndexToModel(
									Index_row)).get(Index_col).getType()
							.getFormatting();

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
							backgroundColors.add(Color.orange);
							comp.setBackground(Color.orange);

							//table.addRowSelectionInterval(Index_row, Index_row);
						}
					} else {
						comp.setBackground(Color.white);
					}

					if (isCellSelected(Index_row, Index_col)) {
						backgroundColors.add(Color.orange);
						comp.setBackground(Color.orange);
						project.getSelected().add(
								data.get(
										table.getRowSorter()
												.convertRowIndexToModel(
														Index_row)).get(
										Index_col));
						comp.setForeground(Color.black);
						table.repaint();
					}


					for (int x = 0; x < format.size(); x++) {
						if (format.get(x).evaluate(
								data.get(
										table.getRowSorter()
												.convertRowIndexToModel(
														Index_row))
										.get(Index_col).getMark())) {
							if (format.get(x).getHighlightColor() != null) {
								backgroundColors.add(format.get(x)
										.getHighlightColor());
							} else if (format.get(x).getTextColor() != null) {
								textColors.add(format.get(x).getTextColor());
							}
						}
					}

					int r = 0;
					int g = 0;
					int b = 0;

					for (int x = 0; x < backgroundColors.size(); x++) {
						r += backgroundColors.get(x).getRed();
						g += backgroundColors.get(x).getGreen();
						b += backgroundColors.get(x).getBlue();
					}

					if (backgroundColors.size() > 0) {
						r = r / backgroundColors.size();
						g = g / backgroundColors.size();
						b = b / backgroundColors.size();
					}

					if (r != 0 || g != 0 || b != 0) {
						comp.setBackground(new Color(r, g, b));
					}
					
					r=0;
					g=0;
					b=0;
					
					for (int x = 0; x < textColors.size(); x++) {
						r += textColors.get(x).getRed();
						g += textColors.get(x).getGreen();
						b += textColors.get(x).getBlue();
					}

					if (textColors.size() > 0) {
						r = r / textColors.size();
						g = g / textColors.size();
						b = b / textColors.size();
					}

					if (r != 0 || g != 0 || b != 0) {
						comp.setForeground(new Color(r, g, b));
					}
					
					

					LinkedList<BorderCase> bordercases = data
							.get(table.getRowSorter().convertRowIndexToModel(
									Index_row)).get(Index_col).getType()
							.getBorderCasing();

					for (int x = 0; x < bordercases.size(); x++) {
						if (bordercases.get(x).isBorderCase(
								data.get(
										table.getRowSorter()
												.convertRowIndexToModel(
														Index_row)).get(
										Index_col))) {
							comp.setBackground(Color.cyan);
						}
					}

					return comp;
				} catch (Exception e) {
					// TODO: handle exception
					return null;
				}
				
			}
		};
		
        
		//---------------------------------------------------------------------------------------------------
        // tooltip when hovering
		table.addMouseMotionListener(new MouseMotionAdapter() {
			   public void mouseMoved(MouseEvent e){
			        Point p = e.getPoint(); 
			        int row = table.rowAtPoint(p);
			        int col = table.columnAtPoint(p);
			        String toolTip = "";
			        
			        try{
			        
					LinkedList<Format> format = data
							.get(table.getRowSorter().convertRowIndexToModel(
									row)).get(col).getType()
							.getFormatting();
					
					for (int x = 0; x < format.size(); x++) {
						if (format.get(x).evaluate(data.get(table.getRowSorter().convertRowIndexToModel(row)).get(col).getMark())) {
							if (format.get(x).getHighlightColor() != null) {
								toolTip += " Background Color = " + getColorName(format.get(x).getHighlightColor()) + ", due to "+ format.get(x).getDescription();
								toolTip += "\t";
							} else if (format.get(x).getTextColor() != null) {
								toolTip += " Text Color = " +getColorName(format.get(x).getTextColor()) + ", due to "+ format.get(x).getDescription();
								toolTip += "\t";
							}
						}
					}
					
			        table.setToolTipText(toolTip);
			        }
			        catch (Exception ex) {
						// TODO: handle exception
					}
			    }//end MouseMoved
			}); // end MouseMotionAdapter
			//=---------------------------------------------------------------------------------------------
		table.setAutoCreateRowSorter(true);

		TableCellListener tcl = new TableCellListener(table, action);

		table.addPropertyChangeListener(tcl);
//--------------------------------------------------------------------------------------------
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
//--------------------------------------------------------------------------------------
		pane.setViewportView(table);
		JPanel eastPanel = new JPanel();

		JPanel border = new JPanel();
		JButton bordercase = new JButton("Add bordercase");
		border.add(bordercase);

		final JComboBox cbheaders = new JComboBox(headers);
		final LinkedList<SuperEntity> headersList = project.getHead()
				.getHeadersLinkedList();
		border.add(cbheaders);
		
		JPanel searchPnl = new JPanel();
		final TextField searchTxt = new TextField(20);
		
		JButton btnSearch = new JButton("Search");
		
		searchPnl.add(btnSearch);
		searchPnl.add(searchTxt);
//=--------------------------------------------------------------------------------------------------------------
		//bordercase button
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
							new Integer(50), // value
							new Integer(0), // min
							new Integer(100), // max
							new Integer(1) // step
					);
					final JSpinner maxVal = new JSpinner(SNMmax);

					SpinnerNumberModel SNMmin = new SpinnerNumberModel(
							new Integer(40), // value
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
		//=--------------------------------------------------------------------------------------------------------------
		JButton btnAddConditionalFormatting = new JButton(
				"Add conditional formatting");
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
		
		eastPanel.add(searchPnl);

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
		//=--------------------------------------------------------------------------------------------------------------
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.repaint();
				int count = tableModel.getRowCount() + 1;

				EntityType testHead = project.getHeadEntityType();
				LinkedList<EntityType> list = testHead.getSubEntityType();
				
				for(int x = 0; x < list.size();x++){
					createEntities(list.get(x), new SuperEntityPointer( project.getHead()));
				}
				
				data = project.getHead().getDataLinkedList();

				/*tableModel.addRow(new Object[] { txtField1.getText(),
						txtField1.getText() });*/
				
				Object[] temp = new Object[data.get(0).size()];

				for (int y = 0; y < data.get(0).size(); y++) {
					temp[y] = data.getLast().get(y).getValue();
				}
				
				tableModel.addRow(temp);
				table.repaint();
			}
		});
		//=--------------------------------------------------------------------------------------------------------------
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(searchTxt.getText().compareTo("") != 0){
					project.getSelected().clear();
					for(int x = 0; x < data.size();x++){
						for(int y = 0; y < data.get(0).size();y++){
							if(data.get(x).get(y).getValue().contains(searchTxt.getText())){
								if(!project.getSelected().contains(data.get(x).get(0)));
									for(int z = 0; z < data.get(x).size();z++){
										project.getSelected().add(data.get(x).get(z));
										tableModel.fireTableDataChanged();	
									}
								tableModel.fireTableDataChanged();	
							}
						}
					}
				}		
			}
		});
		//---------------------------------------------------------------------------------------------------------------
		JButton btnSetMaxVal = new JButton("Set the max value");
		SpinnerNumberModel SNMmaxVal = new SpinnerNumberModel(new Integer(
				0), // value
				new Integer(0), // min
				new Integer(100), // max
				new Integer(1) // step
		);
		final JSpinner setMaxValOfColumn = new JSpinner(SNMmaxVal);
		final JComboBox MaxValEditing = new JComboBox(headers);
		
		JPanel maxValOfCol = new JPanel();
		maxValOfCol.add(MaxValEditing);
		maxValOfCol.add(btnSetMaxVal);
		maxValOfCol.add(setMaxValOfColumn);
		
		
		eastPanel.add(maxValOfCol);
		
		btnSetMaxVal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(data.get(0).get(MaxValEditing.getSelectedIndex()).getType().getMaxValue());
				if(!data.get(0).get(MaxValEditing.getSelectedIndex()).getType().getIsTextField()){
					data.get(0).get(MaxValEditing.getSelectedIndex()).getType().setMaxValue(Integer.parseInt(setMaxValOfColumn.getValue().toString()));
					
					
				}
			}
		});

		//=--------------------------------------------------------------------------------------------------------------
		btnAddConditionalFormatting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFrame formatFrame = new JFrame();

				SpinnerNumberModel SNMmax = new SpinnerNumberModel(new Integer(
						0), // value
						new Integer(0), // min
						new Integer(100), // max
						new Integer(1) // step
				);
				final JSpinner maxVal = new JSpinner(SNMmax);

				SpinnerNumberModel SNMmin = new SpinnerNumberModel(new Integer(
						0), // value
						new Integer(0), // min
						new Integer(100), // max
						new Integer(1) // step
				);
				final JSpinner minVal = new JSpinner(SNMmin);

				minVal.setEnabled(false);
				maxVal.setEnabled(false);

				formatFrame.setLayout(new GridLayout(0, 2));

				String[] formatTypesStr = new String[Format.formatTypes.length + 1];
				formatTypesStr[0] = "";

				for (int x = 1; x < formatTypesStr.length; x++) {
					formatTypesStr[x] = Format.formatTypes[x - 1];
				}

				final JComboBox formatTypes = new JComboBox(formatTypesStr);

				String[] cols = new String[colors.size()];

				for (int x = 0; x < cols.length; x++) {
					cols[x] = colorsString.get(x);
				}

				String[] whatToFormat = { "Background", "Text" };

				final JComboBox colCombo = new JComboBox(cols);
				final JComboBox whatToFormatCombo = new JComboBox(whatToFormat);

				colCombo.setEnabled(false);
				whatToFormatCombo.setEnabled(false);

				final JButton addFormant = new JButton("Add format");
				addFormant.setEnabled(false);

				final JEditorPane description = new JEditorPane();
				final JLabel label = new JLabel("Tooltip");

				description.setEditable(false);
				label.setEnabled(false);

				formatFrame.add(formatTypes);
				formatFrame.add(addFormant);
				formatFrame.add(minVal);
				formatFrame.add(maxVal);
				formatFrame.add(colCombo);
				formatFrame.add(whatToFormatCombo);
				formatFrame.add(label);
				formatFrame.add(description);

				formatFrame.setVisible(true);
				formatFrame.setSize(400, 150);

				formatTypes.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (!(formatTypes.getSelectedIndex() == 0)) {
							if (formatTypes.getSelectedIndex() == 1) {
								minVal.setEnabled(true);
								maxVal.setEnabled(true);

								colCombo.setEnabled(true);
								whatToFormatCombo.setEnabled(true);

								description.setEditable(true);
								label.setEnabled(true);

								addFormant.setEnabled(true);
							} else {
								minVal.setEnabled(true);
								maxVal.setEnabled(false);

								colCombo.setEnabled(true);
								whatToFormatCombo.setEnabled(true);

								description.setEditable(true);
								label.setEnabled(true);

								addFormant.setEnabled(true);
							}
						} else {
							minVal.setEnabled(false);
							maxVal.setEnabled(false);

							colCombo.setEnabled(false);
							whatToFormatCombo.setEnabled(false);

							description.setEditable(false);
							label.setEnabled(false);

							addFormant.setEnabled(false);
						}
					}
				});

				addFormant.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						switch (formatTypes.getSelectedIndex()) {
						case 1: {
							if (whatToFormatCombo.getSelectedIndex() == 0) {
								headersList
										.get(cbFormatting.getSelectedIndex())
										.getType()
										.getFormatting()
										.add(new BetweenFormat(Double
												.parseDouble(minVal.getValue()
														.toString()), Double
												.parseDouble(maxVal.getValue()
														.toString()), null,
												colors.get(colCombo
														.getSelectedIndex()),
												description.getText()));

							} else {
								headersList
										.get(cbFormatting.getSelectedIndex())
										.getType()
										.getFormatting()
										.add(new BetweenFormat(Double
												.parseDouble(minVal.getValue()
														.toString()), Double
												.parseDouble(maxVal.getValue()
														.toString()), colors
												.get(colCombo
														.getSelectedIndex()),
												null, description.getText()));

							}
							table.repaint();
							formatFrame.setVisible(false);
							break;
						}
						case 2: {
							if (whatToFormatCombo.getSelectedIndex() == 0) {
								headersList
										.get(cbFormatting.getSelectedIndex())
										.getType()
										.getFormatting()
										.add(new GreaterThanFormat(Double
												.parseDouble(minVal.getValue()
														.toString()), null,
												colors.get(colCombo
														.getSelectedIndex()),
												description.getText()));
							} else {
								headersList
										.get(cbFormatting.getSelectedIndex())
										.getType()
										.getFormatting()
										.add(new GreaterThanFormat(Double
												.parseDouble(minVal.getValue()
														.toString()), colors
												.get(colCombo
														.getSelectedIndex()),
												null, description.getText()));

							}
							table.repaint();
							formatFrame.setVisible(false);
							break;
						}
						case 3: {
							if (whatToFormatCombo.getSelectedIndex() == 0) {
								headersList
										.get(cbFormatting.getSelectedIndex())
										.getType()
										.getFormatting()
										.add(new LessThanFormat(Double
												.parseDouble(minVal.getValue()
														.toString()), null,
												colors.get(colCombo
														.getSelectedIndex()),
												description.getText()));
							} else {
								headersList
										.get(cbFormatting.getSelectedIndex())
										.getType()
										.getFormatting()
										.add(new LessThanFormat(Double
												.parseDouble(minVal.getValue()
														.toString()), colors
												.get(colCombo
														.getSelectedIndex()),
												null, description.getText()));

							}
							table.repaint();
							formatFrame.setVisible(false);
							break;
						}

						}
					}
				});
			}

		});
		//=--------------------------------------------------------------------------------------------------------------
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
	//=--------------------------------------------------------------------------------------------------------------
	private void createEntities(EntityType entType, SuperEntityPointer parent){
		LinkedList<EntityType> list = entType.getSubEntityType();
		
		if(entType.getIsTextField()){
			LeafStringEntity head = new LeafStringEntity(entType, parent.getTarget(), "");
			
			SuperEntityPointer headPointer = new SuperEntityPointer(head);
			
			for(int x = 0; x < list.size();x++){
				createEntities(list.get(x),headPointer);
			}
		}
		else{
			LeafMarkEntity head = new LeafMarkEntity(entType, parent.getTarget(), 0);
			SuperEntityPointer headPointer = new SuperEntityPointer(head);
			
			for(int x = 0; x < list.size();x++){
				createEntities(list.get(x),headPointer);
			}
		}
	}
	//=--------------------------------------------------------------------------------------------------------------
	public class InteractiveTableModelListener implements TableModelListener {
		public void tableChanged(TableModelEvent evt) {
			if (evt.getType() == TableModelEvent.UPDATE) {
				int column = evt.getColumn();
				int row = evt.getFirstRow();
				table.setColumnSelectionInterval(column + 1, column + 1);
				table.setRowSelectionInterval(row, row);
				table.repaint();
			}
		}
	}
}

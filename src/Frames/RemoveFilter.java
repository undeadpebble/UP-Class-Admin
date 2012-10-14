package Frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminFrontEnd.FrmTable;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class RemoveFilter extends JFrame {

	private JPanel contentPane;

	/**
	 * @param table
	 * creates a new removefilterframe
	 */
	public RemoveFilter(final FrmTable table) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 423, 255);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblRemoveWhatColFilters = new JLabel(
				"Remove filters for which column");
		lblRemoveWhatColFilters.setBounds(10, 11, 168, 14);
		contentPane.add(lblRemoveWhatColFilters);

		final JButton btnRemoveFilter = new JButton("Remove filter");
		btnRemoveFilter.setBounds(10, 159, 390, 47);
		contentPane.add(btnRemoveFilter);

		final JComboBox cbxFilters = new JComboBox();
		cbxFilters.setBounds(232, 8, 168, 20);
		contentPane.add(cbxFilters);
		final LinkedList<Integer> filters = new LinkedList<Integer>();
		final LinkedList<SuperEntity> heads;

		heads = table.project.getHead().getHeadersLinkedList();

		for (int x = 0; x < heads.size(); x++) {
			filters.add(x);
		}

		String[] bcases = new String[filters.size()];

		for (int x = 0; x < filters.size(); x++) {
			bcases[x] = heads.get(filters.get(x)).getType().getName();
		}

		cbxFilters.setModel(new DefaultComboBoxModel(bcases));

		btnRemoveFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Object[][] temp = new Object[table.data.size()][table.data.get(
						0).size()];

				for (int x = 0; x < table.data.size(); x++) {
					for (int y = 0; y < table.data.get(0).size(); y++) {
						temp[x][y] = table.data.get(x).get(y).getValue();
					}
				}

				//for (int x = 0; x < table.filters.size(); x++) {
					for (int y = 0; y < table.filters.get(0).size(); y++) {
						table.filters.get(cbxFilters.getSelectedIndex()).set(y, false);
					}
				//}

				int y = table.tableModel.getRowCount();
				for (int x = 0; x < y; x++) {
					table.tableModel.removeRow(0);
				}
				for (int x = 0; x < table.data.size(); x++) {
					table.tableModel.addRow(temp[x]);
				}
				closeFrame();
			}

		});
	}

	/**
	 * closes the frame
	 */
	public void closeFrame() {
		this.dispose();
	}
}

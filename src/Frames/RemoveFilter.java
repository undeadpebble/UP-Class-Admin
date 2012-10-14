package Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminFrontEnd.BackgroundGradientPanel;
import ClassAdminFrontEnd.FrmTable;
import ClassAdminFrontEnd.ReflectionButton;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class RemoveFilter extends JFrame {

	private JPanel contentPane;
	private BackgroundGradientPanel backgroundPanel;
	private ReflectionButton btnRemoveFilter;

	public RemoveFilter(final FrmTable table) {
		
		//setup frame size, operations and content pane
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 423, 255);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.setTitle("Remove Filter");
		
		//setup icon image for frame
		Image icon = Toolkit.getDefaultToolkit().getImage("icons/ConditionalFormattingFrame.png");
		this.setIconImage(icon);

		//create background gradient panel
		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setBounds(0, 0, 446, 286);
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);

		JLabel lblRemoveWhatColFilters = new JLabel(
				"Remove filters for which column");
		lblRemoveWhatColFilters.setBounds(20, 41, 212, 14);
		lblRemoveWhatColFilters.setForeground(new Color(0xEDEDED));
		backgroundPanel.add(lblRemoveWhatColFilters);

		try {
			btnRemoveFilter = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/FilterFrameLabelRemoveSpesific.png")));
			btnRemoveFilter.setBounds(291, 116, 88, 99);
			backgroundPanel.add(btnRemoveFilter);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//final JButton btnRemoveFilter = new JButton("Remove filter");
		

		final JComboBox cbxFilters = new JComboBox();
		cbxFilters.setBounds(231, 33, 137, 31);
		backgroundPanel.add(cbxFilters);
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

	public void closeFrame() {
		this.dispose();
	}
}

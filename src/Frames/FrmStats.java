
package Frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.LinkedList;

import javax.swing.border.CompoundBorder;
import javax.swing.JComboBox;

import ClassAdminBackEnd.AbsentException;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.Stats;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminFrontEnd.BackgroundGradientPanel;

public class FrmStats extends JFrame {

	private JPanel contentPane;
	private Project project;
	private BackgroundGradientPanel backgroundPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/*
					 * FrmStats frame = new FrmStats(); frame.setVisible(true);
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
	public FrmStats(Project pproject) {
		project = pproject;
		String[] kolom = project.getHead().getNumberHeaders();
		final String[] headers = project.getHead().getHeaders();
		final Stats stats = new Stats(project);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 315);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// Determine the new location of the window
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		// Move the window
		setLocation(x, y);
		
		setTitle("Statistics");
		Image icon = Toolkit.getDefaultToolkit().getImage("icons/Statistics.png");
		this.setIconImage(icon);
		
		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setBounds(0, 0, getWidth(), getHeight());
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);
		
		JLabel lblMarks = new JLabel("Marks:");
		lblMarks.setBounds(198, 14, 46, 14);
		backgroundPanel.add(lblMarks);

		JLabel lblHighestMark = new JLabel("Highest Mark:");
		lblHighestMark.setBounds(34, 42, 79, 14);
		backgroundPanel.add(lblHighestMark);

		final JLabel lblLowestMark = new JLabel("Lowest Mark:");
		lblLowestMark.setBounds(34, 67, 79, 14);
		backgroundPanel.add(lblLowestMark);

		JLabel lblClassAverage = new JLabel("Class average:");
		lblClassAverage.setBounds(34, 92, 120, 14);
		backgroundPanel.add(lblClassAverage);

		JLabel lblNumberOfFailures = new JLabel("Number of failures:");
		lblNumberOfFailures.setBounds(34, 117, 120, 14);
		backgroundPanel.add(lblNumberOfFailures);

		JLabel lblNumberOfPasses = new JLabel("Number of passes:");
		lblNumberOfPasses.setBounds(34, 167, 120, 14);
		backgroundPanel.add(lblNumberOfPasses);

		JLabel lblMedian = new JLabel("Median");
		lblMedian.setBounds(34, 242, 46, 14);
		backgroundPanel.add(lblMedian);

		final JLabel lblHighestmarkvalue = new JLabel("");
		lblHighestmarkvalue.setBounds(280, 42, 46, 14);
		backgroundPanel.add(lblHighestmarkvalue);

		final JLabel lblLowestmarkvalue = new JLabel("");
		lblLowestmarkvalue.setBounds(280, 67, 46, 14);
		backgroundPanel.add(lblLowestmarkvalue);

		final JLabel lblClassAverageValue = new JLabel("");
		lblClassAverageValue.setBounds(280, 92, 46, 14);
		backgroundPanel.add(lblClassAverageValue);

		final JLabel lblFailuresvalue = new JLabel("");
		lblFailuresvalue.setBounds(280, 117, 46, 14);
		backgroundPanel.add(lblFailuresvalue);

		final JLabel lblPassesvalue = new JLabel("");
		lblPassesvalue.setBounds(280, 167, 46, 14);
		backgroundPanel.add(lblPassesvalue);

		final JLabel lblMedianvalue = new JLabel("");
		lblMedianvalue.setBounds(280, 242, 46, 14);
		backgroundPanel.add(lblMedianvalue);

		JLabel lblNumberOfPossible = new JLabel("Number of possible supplementary:");
		lblNumberOfPossible.setBounds(34, 142, 246, 14);
		backgroundPanel.add(lblNumberOfPossible);

		JLabel lblNumberOfDistinctions = new JLabel("Number of distinctions:");
		lblNumberOfDistinctions.setBounds(34, 192, 169, 14);
		backgroundPanel.add(lblNumberOfDistinctions);

		JLabel lblTotalNumberOf = new JLabel("Total number of students:");
		lblTotalNumberOf.setBounds(34, 217, 222, 14);
		backgroundPanel.add(lblTotalNumberOf);

		final JLabel lblTotalnrofstud = new JLabel("");
		lblTotalnrofstud.setBounds(280, 217, 46, 14);
		backgroundPanel.add(lblTotalnrofstud);

		final JLabel lblNrdistinction = new JLabel("");
		lblNrdistinction.setBounds(280, 192, 46, 14);
		backgroundPanel.add(lblNrdistinction);

		final JLabel lblNrofsupps = new JLabel("");
		lblNrofsupps.setBounds(280, 142, 46, 14);
		backgroundPanel.add(lblNrofsupps);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(254, 7, 128, 29);
		backgroundPanel.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(kolom));
		comboBox.setSelectedIndex(-1);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String axis = (String) cb.getSelectedItem();
				int houerx = 0;
				for (int s = 0; s < headers.length; s++) {
					if (headers[s].equals(cb.getSelectedItem().toString())) {
						houerx = s;

					}

				}

				lblHighestmarkvalue.setText(Double.toString(stats.roundTwoDecimals(stats.hoogstepunt(houerx))));
				lblLowestmarkvalue.setText(Double.toString(stats.roundTwoDecimals(stats.laagstepunt(houerx))));
				lblClassAverageValue.setText(Double.toString(stats.roundTwoDecimals(stats.gemidpunt(houerx))));
				lblFailuresvalue.setText(Integer.toString(stats.fails(houerx)));
				lblNrofsupps.setText((Integer.toString(stats.her(houerx))));
				lblPassesvalue.setText(Integer.toString(stats.slaag(houerx)));
				lblNrdistinction.setText(Integer.toString(stats.distinction(houerx)));
				lblTotalnrofstud.setText(Integer.toString(stats.totalnrstd()));
				lblMedianvalue.setText(Double.toString(stats.roundTwoDecimals(stats.median(houerx))));
			}

		});

		lblHighestmarkvalue.setForeground(new Color(0xEDEDED));
		lblLowestmarkvalue.setForeground(new Color(0xEDEDED));
		lblClassAverageValue.setForeground(new Color(0xEDEDED));
		lblFailuresvalue.setForeground(new Color(0xEDEDED));
		lblNrofsupps.setForeground(new Color(0xEDEDED));
		lblPassesvalue.setForeground(new Color(0xEDEDED));
		lblNrdistinction.setForeground(new Color(0xEDEDED));
		lblTotalnrofstud.setForeground(new Color(0xEDEDED));
		lblMedianvalue.setForeground(new Color(0xEDEDED));
		lblNumberOfPossible.setForeground(new Color(0xEDEDED));
		lblNumberOfDistinctions.setForeground(new Color(0xEDEDED));
		lblTotalNumberOf.setForeground(new Color(0xEDEDED));
		lblTotalnrofstud.setForeground(new Color(0xEDEDED));
		lblMarks.setForeground(new Color(0xEDEDED));
		lblHighestMark.setForeground(new Color(0xEDEDED));
		lblLowestMark.setForeground(new Color(0xEDEDED));
		lblClassAverage.setForeground(new Color(0xEDEDED));
		lblNumberOfFailures.setForeground(new Color(0xEDEDED));
		lblNumberOfPasses.setForeground(new Color(0xEDEDED));
		lblMedian.setForeground(new Color(0xEDEDED));
	}

}

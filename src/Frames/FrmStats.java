package Frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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

public class FrmStats extends JFrame {

	private JPanel contentPane;
	private Project project;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				/*	FrmStats frame = new FrmStats();
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
	public FrmStats(Project pproject) {
		project = pproject;
		String[] kolom = project.getHead().getNumberHeaders();
		final String[] headers = project.getHead().getHeaders();
		final Stats stats = new Stats(project);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 315);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblMarks = new JLabel("Marks:");
		lblMarks.setBounds(157, 14, 46, 14);
		contentPane.add(lblMarks);
		
		
		JLabel lblHighestMark = new JLabel("Highest Mark:");
		lblHighestMark.setBounds(34, 42, 79, 14);
		contentPane.add(lblHighestMark);
		
		final JLabel lblLowestMark = new JLabel("Lowest Mark:");
		lblLowestMark.setBounds(34, 67, 79, 14);
		contentPane.add(lblLowestMark);
		
		
		
		JLabel lblClassAverage = new JLabel("Class average:");
		lblClassAverage.setBounds(34, 92, 120, 14);
		contentPane.add(lblClassAverage);
		
		JLabel lblNumberOfFailures = new JLabel("Number of failures:");
		lblNumberOfFailures.setBounds(34, 117, 120, 14);
		contentPane.add(lblNumberOfFailures);
		
		JLabel lblNumberOfPasses = new JLabel("Number of passes:");
		lblNumberOfPasses.setBounds(34, 167, 120, 14);
		contentPane.add(lblNumberOfPasses);
		
		JLabel lblMedian = new JLabel("Median");
		lblMedian.setBounds(34, 242, 46, 14);
		contentPane.add(lblMedian);
		
		final JLabel lblHighestmarkvalue = new JLabel("");
		lblHighestmarkvalue.setBounds(280, 42, 46, 14);
		contentPane.add(lblHighestmarkvalue);
		
		final JLabel lblLowestmarkvalue = new JLabel("");
		lblLowestmarkvalue.setBounds(280, 67, 46, 14);
		contentPane.add(lblLowestmarkvalue);
		
		final JLabel lblClassAverageValue = new JLabel("");
		lblClassAverageValue.setBounds(280, 92, 46, 14);
		contentPane.add(lblClassAverageValue);
		
		final JLabel lblFailuresvalue = new JLabel("");
		lblFailuresvalue.setBounds(280, 117, 46, 14);
		contentPane.add(lblFailuresvalue);
		
		final JLabel lblPassesvalue = new JLabel("");
		lblPassesvalue.setBounds(290, 167, 46, 14);
		contentPane.add(lblPassesvalue);
		
		final JLabel lblMedianvalue = new JLabel("");
		lblMedianvalue.setBounds(290, 242, 46, 14);
		contentPane.add(lblMedianvalue);
		
		JLabel lblNumberOfPossible = new JLabel("Number of possible supplementary:");
		lblNumberOfPossible.setBounds(34, 142, 246, 14);
		contentPane.add(lblNumberOfPossible);
		
		JLabel lblNumberOfDistinctions = new JLabel("Number of distinctions:");
		lblNumberOfDistinctions.setBounds(34, 192, 169, 14);
		contentPane.add(lblNumberOfDistinctions);
		
		JLabel lblTotalNumberOf = new JLabel("Total number of students:");
		lblTotalNumberOf.setBounds(34, 217, 222, 14);
		contentPane.add(lblTotalNumberOf);
		
		final JLabel lblTotalnrofstud = new JLabel("");
		lblTotalnrofstud.setBounds(290, 217, 46, 14);
		contentPane.add(lblTotalnrofstud);
		
		final JLabel lblNrdistinction = new JLabel("");
		lblNrdistinction.setBounds(290, 192, 46, 14);
		contentPane.add(lblNrdistinction);
		
		final JLabel lblNrofsupps = new JLabel("");
		lblNrofsupps.setBounds(290, 142, 46, 14);
		contentPane.add(lblNrofsupps);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(198, 11, 128, 20);
		contentPane.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(kolom));
		comboBox.setSelectedIndex(-1);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String axis = (String) cb.getSelectedItem();
				int houerx =0;
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
		
		
	}
	

}

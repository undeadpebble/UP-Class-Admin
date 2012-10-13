package Frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ClassAdminBackEnd.Format;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminFrontEnd.FrmTable;

public class RemoveFormatting extends JFrame {

	private JPanel contentPane;

	public RemoveFormatting(final FrmTable table) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 376, 236);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRemove = new JLabel("Remove this Format");
		lblRemove.setBounds(10, 11, 149, 14);
		contentPane.add(lblRemove);
		
		final JComboBox cbxFormatting = new JComboBox();
		cbxFormatting.setBounds(203, 8, 149, 20);
		contentPane.add(cbxFormatting);
		
		JButton btnNewButton = new JButton("Remove Format");
		btnNewButton.setBounds(12, 133, 340, 61);
		contentPane.add(btnNewButton);
		
		final LinkedList<Integer> formats = new LinkedList<Integer>();
		final LinkedList<SuperEntity> heads;
		
		heads =  table.project.getHead().getHeadersLinkedList();
		
		for(int x = heads.size()-1; x >= 0; x--){
			if(heads.get(x).getType().getIsTextField()){
				heads.remove(x);
			}
		}
		
		for(int x = 0; x < heads.size();x++){
			if(heads.get(x).getType().getFormatting().size() > 0){
				
					formats.add(x);
				
			}
		}
		
		String[] bcases = new String[formats.size()];
		
		for(int x = 0; x < formats.size();x++){
			bcases[x] = heads.get(formats.get(x)).getType().getName();
		}
		
		cbxFormatting.setModel(new DefaultComboBoxModel(bcases));
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				table.headersList.get(table.headPoints.get(formats.get(cbxFormatting.getSelectedIndex()))).getType().clearFormatting();
				closeFrame();
				table.repaint();
			}
		});
	}
	
	public void closeFrame(){
		this.dispose();
	}

}
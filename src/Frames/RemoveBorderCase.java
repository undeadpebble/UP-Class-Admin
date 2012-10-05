package Frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import ClassAdminBackEnd.BorderCase;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminFrontEnd.FrmTable;

public class RemoveBorderCase extends JFrame {

	private JPanel contentPane;

	public RemoveBorderCase(final FrmTable table) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 376, 236);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRemove = new JLabel("Remove this bordercase");
		lblRemove.setBounds(10, 11, 149, 14);
		contentPane.add(lblRemove);
		
		final JComboBox cbxBordercases = new JComboBox();
		cbxBordercases.setBounds(203, 8, 149, 20);
		contentPane.add(cbxBordercases);
		
		JButton btnNewButton = new JButton("Remove bordercase");
		btnNewButton.setBounds(12, 133, 340, 61);
		contentPane.add(btnNewButton);
		
		final LinkedList<Integer> bordercases = new LinkedList<Integer>();
		final LinkedList<SuperEntity> heads;
		
		heads =  table.project.getHead().getHeadersLinkedList();
		
		for(int x = heads.size()-1; x >= 0; x--){
			if(heads.get(x).getType().getIsTextField()){
				heads.remove(x);
			}
		}
		
		for(int x = 0; x < heads.size();x++){
			if(heads.get(x).getType().getBorderCasing().size() > 0){
				for(int y = 0; y < (heads.get(x).getType().getBorderCasing().size()); y++){
					bordercases.add(y);//(heads.get(x).getType().getBorderCasing().get(y)));
				}
			}
		}
		
		String[] bcases = new String[bordercases.size()];
		
		for(int x = 0; x < bordercases.size();x++){
			bcases[x] = heads.get(bordercases.get(x)).getType().getName();
		}
		
		cbxBordercases.setModel(new DefaultComboBoxModel(bcases));
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				table.headersList.get(bordercases.get(cbxBordercases.getSelectedIndex())).getType().getBorderCasing().clear();
				closeFrame();
			}
		});
	}
	
	public void closeFrame(){
		this.dispose();
	}

}

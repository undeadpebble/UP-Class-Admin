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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import ClassAdminBackEnd.BorderCase;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminFrontEnd.BackgroundGradientPanel;
import ClassAdminFrontEnd.FrmTable;
import ClassAdminFrontEnd.ReflectionButton;

public class RemoveBorderCase extends JFrame {

	private JPanel contentPane;
	private BackgroundGradientPanel backgroundPanel;
	private ReflectionButton btnNewButton;

	public RemoveBorderCase(final FrmTable table) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 376, 236);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.setTitle("Remove Bordercase");
		Image icon = Toolkit.getDefaultToolkit().getImage("icons/BordercaseFrame.png");
		this.setIconImage(icon);
		
		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setBounds(0, 0, 446, 317);
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);
		
		JLabel lblRemove = new JLabel("Remove this bordercase");
		lblRemove.setBounds(31, 47, 149, 14);
		lblRemove.setForeground(new Color(0xEDEDED));
		backgroundPanel.add(lblRemove);
		
		final JComboBox cbxBordercases = new JComboBox();
		cbxBordercases.setBounds(206, 40, 134, 29);
		backgroundPanel.add(cbxBordercases);
		
		
		try {
			btnNewButton = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/BordercaseFrameLabelRemove.png")));
			btnNewButton.setBounds(259, 100, 81, 103);
			backgroundPanel.add(btnNewButton);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
				table.headersList.get(table.headPoints.get(bordercases.get(cbxBordercases.getSelectedIndex()))).getType().getBorderCasing().clear();
				closeFrame();
				table.repaint();
			}
		});
	}
	
	public void closeFrame(){
		this.dispose();
	}

}

package Frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ClassAdminFrontEnd.BackgroundGradientPanel;

import prefuse.util.FontLib;

public class FrmTreeHelpDialog {
	private JDialog frame;
	private BackgroundGradientPanel backgroundPanel;
	
	public FrmTreeHelpDialog(JFrame parentFrame) {
		frame = new JDialog();
		frame.setSize(400, 200);
		frame.setLayout(null);
		frame.setLocation(parentFrame.getWidth()+parentFrame.getX()-250, parentFrame.getY()+40);
		
		frame.setTitle("Shortcut Keys");
		
		Image icon = Toolkit.getDefaultToolkit().getImage("icons/Tree.png");
		frame.setIconImage(icon);
		
		JPanel contentPane = new JPanel();
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(new GridLayout(5, 2));
		
		final JLabel p1 = new JLabel("Select Parent");
		final JLabel p2 = new JLabel("Deselect Parent");
		final JLabel c1 = new JLabel("Select Child");
		final JLabel c2 = new JLabel("Deselect Child");
		final JLabel pp1 = new JLabel("SHFT + Left Mouse");
		final JLabel pp2 = new JLabel("SHFT + Right Mouse");
		final JLabel cc1 = new JLabel("CTRL + Left Mouse");
		final JLabel cc2 = new JLabel("CTRL + Right Mouse");
		p1.setFont(FontLib.getFont("Arial", Font.BOLD, 16));
		p2.setFont(FontLib.getFont("Arial", Font.BOLD, 16));
		c1.setFont(FontLib.getFont("Arial", Font.BOLD, 16));
		c2.setFont(FontLib.getFont("Arial", Font.BOLD, 16));
		pp1.setFont(FontLib.getFont("Arial", Font.PLAIN, 16));
		pp2.setFont(FontLib.getFont("Arial", Font.PLAIN, 16));
		cc1.setFont(FontLib.getFont("Arial", Font.PLAIN, 16));
		cc2.setFont(FontLib.getFont("Arial", Font.PLAIN, 16));
		
		p1.setForeground(new Color(0xEDEDED));
		p2.setForeground(new Color(0xEDEDED));
		c1.setForeground(new Color(0xEDEDED));
		c2.setForeground(new Color(0xEDEDED));
		pp1.setForeground(new Color(0xEDEDED));
		pp2.setForeground(new Color(0xEDEDED));
		cc1.setForeground(new Color(0xEDEDED));
		cc2.setForeground(new Color(0xEDEDED));
		
		backgroundPanel.add(p1);
		backgroundPanel.add(p2);
		backgroundPanel.add(pp1);
		backgroundPanel.add(pp2);
		backgroundPanel.add(c1);
		backgroundPanel.add(c2);
		backgroundPanel.add(cc1);
		backgroundPanel.add(cc2);

		//backgroundPanel.pack();
		
	}
	
	public void showFrmTreeHelpDialog()
	{
		frame.setVisible(true);
	}

}

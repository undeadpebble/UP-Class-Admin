package Frames;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;

import prefuse.util.FontLib;

public class FrmTreeHelpDialog {
	JDialog frame;
	
	public FrmTreeHelpDialog() {
		frame = new JDialog();
		frame.setSize(600, 400);
		frame.setLayout(new GridLayout(5, 2));
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
		frame.add(p1);
		frame.add(p2);
		frame.add(pp1);
		frame.add(pp2);
		frame.add(c1);
		frame.add(c2);
		frame.add(cc1);
		frame.add(cc2);

		frame.pack();
		frame.setVisible(true);
	}
	
	public void showFrmTreeHelpDialog()
	{
		frame.setVisible(true);
	}

}

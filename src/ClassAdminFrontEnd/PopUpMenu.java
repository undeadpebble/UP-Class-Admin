package ClassAdminFrontEnd;

import javax.swing.*;
import java.awt.event.*;

public class PopUpMenu {

	JPopupMenu pMenu;

	public PopUpMenu(JFrame frame) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pMenu = new JPopupMenu();
		JMenuItem miAddChild = new JMenuItem("Add Child");
		pMenu.add(miAddChild);
		JMenuItem miRename = new JMenuItem("Rename");
		pMenu.add(miRename);
		JMenuItem miRemove = new JMenuItem("Remove");
		pMenu.add(miRemove);

		miAddChild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		miRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		miRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		frame.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent Me) {
				if (Me.isPopupTrigger()) {
					pMenu.show(Me.getComponent(), Me.getX(), Me.getY());
				}
			}
		});
	}
}
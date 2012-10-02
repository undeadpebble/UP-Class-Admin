package ClassAdminFrontEnd;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RapidAssessmentToolsPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public RapidAssessmentToolsPanel() {
		
		JButton btnNewButton = new JButton("Load Image");
		btnNewButton.setBounds(10, 79, 89, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		setLayout(null);
		add(btnNewButton);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(10, 45, 89, 23);
		add(btnSave);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.setBounds(10, 11, 89, 23);
		add(btnLoad);

	}
}

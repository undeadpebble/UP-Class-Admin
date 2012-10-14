package ClassAdminFrontEnd;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import ClassAdminBackEnd.Global;

public class BoxPlotOptionMenu extends JFrame implements ActionListener {
	private final String[] headers = Global.getGlobal().getActiveProject().getHead().getHeaders();
	protected final static JComboBox choosecombo = new JComboBox();
	protected String comboSelect = "";

	/**
	 * Create the boxplotframe where series could be added dynamically
	 */
	public void createFrame() {
		JFrame f = new JFrame("BoxPlot");
		final Container content = f.getContentPane();
		f.setSize(200, 120);
		// Get the fields that contain numbers
		final String[] kolom = Global.getGlobal().getActiveProject().getHead().getNumberHeaders();

		choosecombo.setModel(new DefaultComboBoxModel(kolom));
		choosecombo.addActionListener(this);

		JButton choose = new JButton("Add");
		choose.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				BoxPlotFrame x = new BoxPlotFrame();
				x.addBoxSeries();

			}
		});
		content.setLayout(new FlowLayout());
		f.add(choosecombo);
		f.add(choose);
		f.setVisible(true);

	}

	
	/**
	 * Constructor
	 */
	public BoxPlotOptionMenu() {

	}

	
	/**
	 * @return
	 * Get the index of the selected field in the combobox
	 */
	public int getIndexOfHeader() {

		for (int s = 0; s < headers.length; s++) {

			if (headers[s].equals((String) choosecombo.getSelectedItem())) {

				return s;

			}

		}
		return 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox) e.getSource();
		comboSelect = (String) cb.getSelectedItem();

	}

}

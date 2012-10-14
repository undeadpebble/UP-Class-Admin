
package Frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import ClassAdminBackEnd.BorderCase;
import ClassAdminBackEnd.Project;
import ClassAdminFrontEnd.BackgroundGradientPanel;
import ClassAdminFrontEnd.FrmTable;
import ClassAdminFrontEnd.ReflectionButton;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class BordercaseFrame extends JFrame {

	private JPanel contentPane;
	private BackgroundGradientPanel backgroundPanel;

	/**
	 * Create the frame.
	 * @throws IOException 
	 * creates a new bordercaseframe
	 */
	public BordercaseFrame(final FrmTable table) throws IOException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		this.setTitle("Bordercase");

		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		// Determine the new location of the window
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		// Move the window
		setLocation(x, y);

		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setBounds(0, 0, 436, 285);
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);

		JLabel lblBottomBordercaseValue = new JLabel("Bottom bordercase Value");
		lblBottomBordercaseValue.setBounds(54, 78, 160, 14);
		lblBottomBordercaseValue.setForeground(new Color(0xEDEDED));
		backgroundPanel.add(lblBottomBordercaseValue);
		
		JLabel lblUpperBordercaseValue = new JLabel("Upper bordercase Value");
		lblUpperBordercaseValue.setBounds(54, 123, 160, 14);
		lblUpperBordercaseValue.setForeground(new Color(0xEDEDED));
		backgroundPanel.add(lblUpperBordercaseValue);
		
		SpinnerNumberModel SNMmax = new SpinnerNumberModel(
				new Integer(50), // value
				new Integer(0), // min
				new Integer(100), // max
				new Integer(1) // step
		);
		final JSpinner maxVal = new JSpinner(SNMmax);
		maxVal.setBounds(274, 116, 121, 28);

		SpinnerNumberModel SNMmin = new SpinnerNumberModel(
				new Integer(40), // value
				new Integer(0), // min
				new Integer(100), // max
				new Integer(1) // step
		);
		final JSpinner minVal = new JSpinner(SNMmin);
		minVal.setBounds(274, 71, 121, 28);
		
		backgroundPanel.add(maxVal);
		backgroundPanel.add(minVal);
		
		final ReflectionButton btnAddBordercase = new ReflectionButton(ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/resources/BordercaseFrame.png")));
		btnAddBordercase.setBounds(333, 187, 62, 86);
		backgroundPanel.add(btnAddBordercase);
		
		final JComboBox cbxHeaders = new JComboBox();
		cbxHeaders.setBounds(274, 27, 121, 20);
		backgroundPanel.add(cbxHeaders);
		
		JLabel lblWhereToAdd = new JLabel("Where to add the bordercase");
		lblWhereToAdd.setForeground(Color.WHITE);
		lblWhereToAdd.setBounds(54, 27, 160, 14);
		backgroundPanel.add(lblWhereToAdd);

		// frame resize listener adjust components accordingly
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent e) {
				backgroundPanel.setBounds(0, 0, contentPane.getWidth(), contentPane.getHeight());
				backgroundPanel.rerenderBackground();

			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

		});
		
		cbxHeaders.setModel(new DefaultComboBoxModel(table.project.getHead().getNumberHeaders()));
		
		JButton btnRemoveBorderCase = new JButton("Remove bordercase");
		btnRemoveBorderCase.setBounds(54, 198, 137, 23);
		backgroundPanel.add(btnRemoveBorderCase);
		
		btnAddBordercase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.headersList
						.get(table.headPoints.get(cbxHeaders
								.getSelectedIndex()))
						.getType()
						.getBorderCasing()
						.add(new BorderCase(Double
								.parseDouble(minVal.getValue()
										.toString()), Double
								.parseDouble(maxVal.getValue()
										.toString())));

				table.repaint();
				closeFrame();
			}
		});
		
		btnRemoveBorderCase.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeFrame();
				RemoveBorderCase temp = new RemoveBorderCase(table);
				temp.setVisible(true);
				
			}
		});
		
		
	}
	/**
	 * closes the frame
	 */
	public void closeFrame(){
		this.dispose();
		return;
	}
}


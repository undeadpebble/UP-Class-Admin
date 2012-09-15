package ClassAdminFrontEnd;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.Rule;
import ClassAdminBackEnd.SuperEntity;

public class FrmRules extends JFrame {
	private Project project;
	private JComboBox cbxTypes;
	private Boolean finished = false;

	public FrmRules(Project project2) {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.project = project2;

		String[] heads = project.getHead().getHeaders();
		final LinkedList<SuperEntity> headLL = project.getHead()
				.getHeadersLinkedList();

		JButton addRule = new JButton("Add Rule");

		final JTextArea txtName = new JTextArea();

		SpinnerNumberModel SNM1 = new SpinnerNumberModel(new Integer(0), // value
				new Integer(0), // min
				new Integer(100), // max
				new Integer(1) // step
		);

		SpinnerNumberModel SNM2 = new SpinnerNumberModel(new Integer(0), // value
				new Integer(0), // min
				new Integer(100), // max
				new Integer(1) // step
		);

		final JSpinner spinVal1 = new JSpinner(SNM1);
		final JSpinner spinVal2 = new JSpinner(SNM2);

		final JComboBox cbxReferences = new JComboBox(heads);

		final LinkedList<String> typesLL = new LinkedList<String>();

		if (headLL.get(cbxReferences.getSelectedIndex()).getType()
				.getIsTextField()) {
			typesLL.add("Contains");
		} else {
			typesLL.add("Greater than");
		}

		String[] types = new String[typesLL.size()];
		for (int x = 0; x < typesLL.size(); x++) {
			types[x] = typesLL.get(x);
		}

		cbxTypes = new JComboBox(types);

		final JCheckBox[] cbxRules = new JCheckBox[project.getRules().size()];

		for (int x = 0; x < cbxRules.length; x++) {
			cbxRules[x] = new JCheckBox(project.getRules().get(x).getName());
		}

		JComboCheckBox otherRules = new JComboCheckBox(cbxRules);

		final JTextArea strText = new JTextArea();

		this.setLayout(new GridLayout(0, 1));

		this.add(txtName);
		this.add(cbxReferences);
		this.add(cbxTypes);
		this.add(spinVal1);
		this.add(spinVal2);
		this.add(strText);
		this.add(addRule);

		if (project.getRules().size() > 0)
			this.add(otherRules);
		this.add(cbxTypes);

		addRule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				project.getRules().add(
						new Rule(txtName.getText(), typesLL.get(cbxTypes
								.getSelectedIndex()), cbxReferences
								.getSelectedIndex(), Integer.parseInt(spinVal1
								.getValue().toString()), Integer
								.parseInt(spinVal2.getValue().toString()),
								strText.getText()));

				if (cbxRules.length > 0) {
					for (int x = cbxRules.length-1; x >= 0; x--) {
						if (cbxRules[x].isSelected()) {
							project.getRules().get(project.getRules().size()-1)
									.getSubRule()
									.add(project.getRules().get(x));
							project.getRules().remove(x);
						}
					}
				}

				finished = true;

				checkDone();
			}
		});

		cbxReferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				typesLL.clear();

				if (headLL.get(cbxReferences.getSelectedIndex()).getType()
						.getIsTextField()) {
					typesLL.add("Contains");
				} else {
					typesLL.add("Greater than");
				}

				String[] types = new String[typesLL.size()];
				for (int x = 0; x < typesLL.size(); x++) {
					types[x] = typesLL.get(x);
				}

				changeTypes(types);
			}
		});

	}

	protected void checkDone() {
		if (finished) {

			this.dispose();
		}
	}

	protected void changeTypes(String[] types) {
		cbxTypes = new JComboBox(types);
		this.remove(this.getComponent(this.getComponentCount() - 1));
		this.add(cbxTypes);
		this.repaint();
	}
}

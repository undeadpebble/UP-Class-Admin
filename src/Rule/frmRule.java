package Rule;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;

import ClassAdminBackEnd.Project;
import javax.swing.SpinnerNumberModel;

public class frmRule extends JFrame {

	private JPanel contentPane;
	private final Project project;
	LinkedList<Rule> ruleList;
	LinkedList<Rule> floatBoolRuleList;
	LinkedList<Rule> StringBoolRuleList;
	LinkedList<Rule> floatRuleList;
	LinkedList<Rule> StringRuleList;
	LinkedList<Rule> BoolRuleList;
	String[] opFloatString = new String[] { "+", "-", "x", "%" };
	char[] opFloatChar = new char[] { '+', '-', 'x', '%' };

	String[] opFloatBoolString = new String[] { "<", ">", "=", "<=", ">=" };
	char[] opFloatBoolChar = new char[] { '<', '>', '=', '1', '2' };

	String[] opStringBoolString = new String[] { "= (equals)", "contains" };
	char[] opStringBoolChar = new char[] { '=', 'c' };

	private final JComboBox cbxOpperator = new JComboBox();
	private final JComboBox cbxRuleChooser = new JComboBox();
	private final JComboBox cbxFloat1 = new JComboBox();
	private final JComboBox cbxFloat2 = new JComboBox();
	private final JButton btnAddFloatRule = new JButton("Add Float Rule");
	private final JTextField txtName = new JTextField();
	private final JSpinner spinFloat1 = new JSpinner();
	private final JSpinner spinFloat2 = new JSpinner();
	private final JButton btnAddFloatBool = new JButton(
			"Add Float Boolean Rule");
	private final JTextField txtStr1 = new JTextField();
	private final JTextField txtStr2 = new JTextField();
	private final JButton btnAddStrBool = new JButton(
			"Add new String boolean rule");
	private final JComboBox cbxStringComp = new JComboBox();
	private final JButton btnAddStringRule = new JButton("Add String Rule");

	/**
	 * Create the frame.
	 */
	public frmRule(final Project project) {
		txtStr2.setBounds(387, 106, 178, 20);
		txtStr2.setColumns(10);
		txtStr1.setBounds(68, 106, 180, 20);
		txtStr1.setColumns(10);
		this.project = project;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 671, 440);
		cbxOpperator.setBounds(302, 105, 36, 20);
		cbxRuleChooser.setBounds(10, 11, 165, 20);
		cbxFloat1.setBounds(68, 159, 180, 20);
		cbxFloat2.setBounds(385, 159, 180, 20);
		btnAddFloatRule.setBounds(68, 248, 497, 66);
		txtName.setBounds(68, 42, 292, 20);
		spinFloat1.setBounds(68, 105, 180, 20);
		spinFloat2.setBounds(385, 106, 180, 20);

		contentPane = new JPanel();
		contentPane.setForeground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		reactivateFloat();

		contentPane.add(cbxRuleChooser);
		contentPane.add(cbxOpperator);
		contentPane.add(cbxFloat1);
		contentPane.add(cbxFloat2);
		contentPane.add(btnAddFloatRule);
		contentPane.add(txtName);
		contentPane.add(spinFloat1);
		contentPane.add(spinFloat2);

		btnAddFloatBool.setBounds(68, 248, 497, 66);

		contentPane.add(btnAddFloatBool);

		contentPane.add(txtStr1);

		contentPane.add(txtStr2);
		btnAddStrBool.setBounds(68, 248, 497, 66);
		contentPane.add(btnAddStrBool);
		cbxStringComp.setBounds(260, 106, 113, 20);

		contentPane.add(cbxStringComp);
		btnAddStringRule.setBounds(68, 248, 497, 66);

		contentPane.add(btnAddStringRule);

		cbxRuleChooser.setModel(new DefaultComboBoxModel(new String[] {
				"Float Rule", "String Rule", "Float Boolean Rule",
				"String Boolean Rule" }));

		cbxFloat1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spinFloat1.setValue(-1);
			}
		});

		cbxFloat2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spinFloat2.setValue(-1);
			}
		});
		// --------------------------------------------------------------------------------------------------------------
		btnAddStrBool.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean valid = true;

				StringRule rule1 = null;
				StringRule rule2 = null;
				if (txtStr1.getText() != "") {
					rule1 = new StringRule(txtStr1.getText(), "reqwuiop",
							project);
					ruleList.add(rule1);
				} else {
					if (StringBoolRuleList.size() > 0) {
						rule1 = (StringRule) StringBoolRuleList.get(cbxFloat1
								.getSelectedIndex());
					} else {
						valid = false;
					}
				}
				if (txtStr2.getText() != "") {
					rule2 = new StringRule(txtStr2.getText(), "reqwuiop",
							project);
					ruleList.add(rule2);
				} else {
					if (StringBoolRuleList.size() > 0) {
						rule2 = (StringRule) StringBoolRuleList.get(cbxFloat2
								.getSelectedIndex());
					} else {
						valid = false;
					}
				}

				if (valid) {
					ruleList.add(new StringBoolRule(rule1, rule2,
							opStringBoolChar[cbxStringComp.getSelectedIndex()],
							txtName.getText(), project));

					exitFrame();
				} else {

				}
			}
		});
		// ---------------------------------------------------------------------------------------------------------------
		btnAddFloatBool.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean valid = true;

				FloatRule rule1 = null;
				FloatRule rule2 = null;
				if (Double.parseDouble(spinFloat1.getValue().toString()) != -1) {
					rule1 = new FloatRule('n', Double.parseDouble(spinFloat1
							.getValue().toString()), "reqwuiop", project);
					ruleList.add(rule1);
				} else {
					if (floatRuleList.size() > 0) {
						rule1 = (FloatRule) floatRuleList.get(cbxFloat1
								.getSelectedIndex());
					} else {
						valid = false;
					}
				}
				if (Double.parseDouble(spinFloat2.getValue().toString()) != -1) {
					rule2 = new FloatRule('n', Double.parseDouble(spinFloat2
							.getValue().toString()), "reqwuiop", project);
					ruleList.add(rule2);
				} else {
					if (floatRuleList.size() > 0) {
						rule2 = (FloatRule) floatRuleList.get(cbxFloat2
								.getSelectedIndex());
					} else {
						valid = false;
					}
				}

				if (valid) {
					FloatBoolRule temp = new FloatBoolRule(
							opFloatChar[cbxOpperator.getSelectedIndex()],
							rule1, rule2, txtName.getText(), project);
					ruleList.add(temp);

					exitFrame();
				} else {

				}
			}
		});
		// ------------------------------------------------------------------------------------------------------------------------------------------------
		cbxRuleChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (cbxRuleChooser.getSelectedIndex()) {
				case 0:
					reactivateFloat();
					break;
				case 1:
					reactivateString();
					break;
				case 2:
					reactivateBooleanFloat();
					break;
				case 3:
					reactivateStringBool();
					break;

				default:
					break;
				}
			}
		});
		// ----------------------------------------------------------------------------------------------------------------------------------------------
		btnAddFloatRule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean valid = true;

				FloatRule rule1 = null;
				FloatRule rule2 = null;
				if (Double.parseDouble(spinFloat1.getValue().toString()) != -1) {
					rule1 = new FloatRule('n', Double.parseDouble(spinFloat1
							.getValue().toString()), "reqwuiop", project);
					ruleList.add(rule1);
				} else {
					if (floatRuleList.size() > 0) {
						rule1 = (FloatRule) floatRuleList.get(cbxFloat1
								.getSelectedIndex());
					} else {
						valid = false;
					}
				}
				if (Double.parseDouble(spinFloat2.getValue().toString()) != -1) {
					rule2 = new FloatRule('n', Double.parseDouble(spinFloat2
							.getValue().toString()), "reqwuiop", project);
					ruleList.add(rule2);
				} else {
					if (floatRuleList.size() > 0) {
						rule2 = (FloatRule) floatRuleList.get(cbxFloat2
								.getSelectedIndex());
					} else {
						valid = false;
					}
				}

				if (valid) {
					FloatRule temp = new FloatRule(opFloatChar[cbxOpperator
							.getSelectedIndex()], rule1, rule2, txtName
							.getText(), project);

					ruleList.add(temp);

					temp.setParentEntitytype(project.getHeadEntityType()
							.getSubEntityType().get(0));
					temp.getParentEntitytype().getSubEntityType().add(temp);

					temp.populateTreeWithEntities();

					exitFrame();
				} else {

				}

			}
		});
		// ------------------------------------------------------------------------------------------------------------------------------------------------------
		btnAddStringRule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean valid = true;

				StringRule rule1 = null;
				StringRule rule2 = null;
				BooleanRule rule3 = null;

				if (txtStr1.getText() != "") {
					rule1 = new StringRule(txtStr1.getText(), "reqwuiop",
							project);
					ruleList.add(rule1);
				} else {
					if (StringBoolRuleList.size() > 0) {
						rule1 = (StringRule) StringBoolRuleList.get(cbxFloat1
								.getSelectedIndex());
					} else {
						valid = false;
					}
				}
				if (txtStr2.getText() != "") {
					rule2 = new StringRule(txtStr2.getText(), "reqwuiop",
							project);
					ruleList.add(rule2);
				} else {
					if (StringBoolRuleList.size() > 0) {
						rule2 = (StringRule) StringBoolRuleList.get(cbxFloat2
								.getSelectedIndex());
					} else {
						valid = false;
					}
				}

				rule3 = (BooleanRule) BoolRuleList.get(cbxStringComp
						.getSelectedIndex());

				if (valid) {
					StringRule temp = new StringRule(rule1,rule2,rule3,txtName.getText(),project);
					
					ruleList.add(temp);
					
					temp.setParentEntitytype(project.getHeadEntityType()
							.getSubEntityType().get(0));
					temp.getParentEntitytype().getSubEntityType().add(temp);

					temp.populateTreeWithEntities();

					exitFrame();
				} else {

				}
			}
		});
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------
	public void reactivateString() {
		cbxOpperator.setVisible(false);
		cbxFloat1.setVisible(true);
		cbxFloat2.setVisible(true);
		btnAddFloatRule.setVisible(false);
		spinFloat1.setVisible(false);
		spinFloat2.setVisible(false);
		cbxStringComp.setVisible(true);
		txtStr1.setVisible(true);
		txtStr2.setVisible(true);
		btnAddStringRule.setVisible(true);
		btnAddStrBool.setVisible(false);

		ruleList = project.getRules();
		floatBoolRuleList = new LinkedList<Rule>();
		StringBoolRuleList = new LinkedList<Rule>();
		floatRuleList = new LinkedList<Rule>();
		StringRuleList = new LinkedList<Rule>();
		BoolRuleList = new LinkedList<Rule>();

		for (int x = 0; x < ruleList.size(); x++) {
			switch (ruleList.get(x).getType()) {
			case 1:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0) {
					floatBoolRuleList.add(ruleList.get(x));
					BoolRuleList.add(ruleList.get(x));
				}
				break;
			case 2:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0) {
					StringBoolRuleList.add(ruleList.get(x));
					BoolRuleList.add(ruleList.get(x));
				}
				break;
			case 3:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0) {
					floatRuleList.add(ruleList.get(x));
				}
				break;
			case 4:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0) {
					StringRuleList.add(ruleList.get(x));
				}
				break;

			default:
				break;
			}
		}

		String[] StringBoolRule = new String[StringBoolRuleList.size()];
		for (int x = 0; x < StringBoolRuleList.size(); x++) {
			StringBoolRule[x] = StringBoolRuleList.get(x).getName();
		}

		String[] StringRule = new String[StringRuleList.size()];
		for (int x = 0; x < StringRuleList.size(); x++) {
			StringRule[x] = StringRuleList.get(x).getName();
		}

		cbxFloat1.setModel(new DefaultComboBoxModel(StringRule));
		cbxFloat2.setModel(new DefaultComboBoxModel(StringRule));
		cbxStringComp.setModel(new DefaultComboBoxModel(StringBoolRule));
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------
	public void reactivateBooleanFloat() {
		cbxOpperator.setVisible(true);
		cbxFloat1.setVisible(true);
		cbxFloat2.setVisible(true);
		btnAddFloatRule.setVisible(false);
		spinFloat1.setVisible(true);
		spinFloat2.setVisible(true);
		btnAddFloatBool.setVisible(true);

		txtStr2.setVisible(false);
		txtStr1.setVisible(false);
		cbxStringComp.setVisible(false);
		btnAddStringRule.setVisible(false);

		ruleList = project.getRules();
		floatBoolRuleList = new LinkedList<Rule>();
		StringBoolRuleList = new LinkedList<Rule>();
		floatRuleList = new LinkedList<Rule>();
		StringRuleList = new LinkedList<Rule>();
		BoolRuleList = new LinkedList<Rule>();

		for (int x = 0; x < ruleList.size(); x++) {
			switch (ruleList.get(x).getType()) {
			case 1:
				floatBoolRuleList.add(ruleList.get(x));
				BoolRuleList.add(ruleList.get(x));
				break;
			case 2:
				StringBoolRuleList.add(ruleList.get(x));
				BoolRuleList.add(ruleList.get(x));
				break;
			case 3:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0) {
					floatRuleList.add(ruleList.get(x));
				}
				break;
			case 4:
				StringRuleList.add(ruleList.get(x));
				break;

			default:
				break;
			}
		}

		String[] floatRules = new String[floatRuleList.size()];
		for (int x = 0; x < floatRuleList.size(); x++) {
			floatRules[x] = floatRuleList.get(x).getName();
		}

		cbxFloat1.setModel(new DefaultComboBoxModel(floatRules));

		cbxFloat2.setModel(new DefaultComboBoxModel(floatRules));

		cbxOpperator.setModel(new DefaultComboBoxModel(this.opFloatBoolString));

		txtName.setColumns(10);

		spinFloat1.setModel(new SpinnerNumberModel(new Double(-1), null, null,
				new Double(1)));

		spinFloat2.setModel(new SpinnerNumberModel(new Integer(-1), null, null,
				new Integer(1)));

	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------
	public void reactivateStringBool() {

		ruleList = project.getRules();
		floatBoolRuleList = new LinkedList<Rule>();
		StringBoolRuleList = new LinkedList<Rule>();
		floatRuleList = new LinkedList<Rule>();
		StringRuleList = new LinkedList<Rule>();
		BoolRuleList = new LinkedList<Rule>();

		for (int x = 0; x < ruleList.size(); x++) {
			switch (ruleList.get(x).getType()) {
			case 1:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0) {
					floatBoolRuleList.add(ruleList.get(x));
					BoolRuleList.add(ruleList.get(x));
				}
				break;
			case 2:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0) {
					StringBoolRuleList.add(ruleList.get(x));
					BoolRuleList.add(ruleList.get(x));
				}
				break;
			case 3:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0) {
					floatRuleList.add(ruleList.get(x));
				}
				break;
			case 4:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0) {
					StringRuleList.add(ruleList.get(x));
				}
				break;

			default:
				break;
			}
		}

		String[] StringBoolRule = new String[StringBoolRuleList.size()];
		for (int x = 0; x < StringBoolRuleList.size(); x++) {
			StringBoolRule[x] = StringBoolRuleList.get(x).getName();
		}

		cbxOpperator.setVisible(false);
		cbxFloat1.setVisible(true);
		cbxFloat2.setVisible(true);
		btnAddFloatRule.setVisible(false);
		spinFloat1.setVisible(false);
		spinFloat2.setVisible(false);
		cbxStringComp.setVisible(true);
		btnAddStringRule.setVisible(false);

		txtStr2.setVisible(true);
		txtStr1.setVisible(true);

		btnAddFloatBool.setVisible(false);
		btnAddStrBool.setVisible(true);

		cbxStringComp.setModel(new DefaultComboBoxModel(opStringBoolString));

		cbxFloat1.setModel(new DefaultComboBoxModel(StringBoolRule));

		cbxFloat2.setModel(new DefaultComboBoxModel(StringBoolRule));
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------
	public void reactivateFloat() {
		ruleList = project.getRules();
		floatBoolRuleList = new LinkedList<Rule>();
		StringBoolRuleList = new LinkedList<Rule>();
		floatRuleList = new LinkedList<Rule>();
		StringRuleList = new LinkedList<Rule>();
		BoolRuleList = new LinkedList<Rule>();

		cbxOpperator.setVisible(true);
		cbxFloat1.setVisible(true);
		cbxFloat2.setVisible(true);
		btnAddFloatRule.setVisible(true);
		spinFloat1.setVisible(true);
		spinFloat2.setVisible(true);

		txtStr2.setVisible(false);
		txtStr1.setVisible(false);
		btnAddFloatBool.setVisible(false);
		cbxStringComp.setVisible(false);
		btnAddStringRule.setVisible(false);

		for (int x = 0; x < ruleList.size(); x++) {
			switch (ruleList.get(x).getType()) {
			case 1:
				floatBoolRuleList.add(ruleList.get(x));
				BoolRuleList.add(ruleList.get(x));
				break;
			case 2:
				StringBoolRuleList.add(ruleList.get(x));
				BoolRuleList.add(ruleList.get(x));
				break;
			case 3:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0) {
					floatRuleList.add(ruleList.get(x));
				}
				break;
			case 4:
				StringRuleList.add(ruleList.get(x));
				break;

			default:
				break;
			}
		}

		String[] floatRules = new String[floatRuleList.size()];
		for (int x = 0; x < floatRuleList.size(); x++) {
			floatRules[x] = floatRuleList.get(x).getName();
		}

		cbxOpperator.setModel(new DefaultComboBoxModel(opFloatString));

		cbxFloat1.setModel(new DefaultComboBoxModel(floatRules));

		cbxFloat2.setModel(new DefaultComboBoxModel(floatRules));

		txtName.setColumns(10);

		spinFloat1.setModel(new SpinnerNumberModel(new Double(-1), null, null,
				new Double(1)));

		spinFloat2.setModel(new SpinnerNumberModel(new Double(-1), null, null,
				new Integer(1)));

	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------
	public void exitFrame() {
		this.dispose();
	}
}

package Rule;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JButton;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;

import javax.swing.SpinnerNumberModel;
import java.awt.Label;

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

	private final LinkedList<SuperEntity> heads;

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
	JComboBox cbxReference1 = new JComboBox();
	JComboBox cbxReference2 = new JComboBox();

	/**
	 * Create the frame.
	 */
	public frmRule(final Project project) {
		txtStr2.setBounds(435, 105, 178, 20);
		txtStr2.setColumns(10);
		txtStr1.setBounds(68, 106, 180, 20);
		txtStr1.setColumns(10);
		this.project = project;

		heads = project.getHead().getHeadersLinkedList();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 671, 440);
		cbxOpperator.setBounds(302, 105, 36, 20);
		cbxRuleChooser.setBounds(10, 11, 165, 20);
		cbxFloat1.setBounds(68, 159, 180, 20);
		cbxFloat2.setBounds(433, 159, 180, 20);
		btnAddFloatRule.setBounds(68, 248, 497, 66);
		txtName.setBounds(68, 42, 292, 20);
		spinFloat1.setBounds(68, 105, 180, 20);
		spinFloat2.setBounds(433, 105, 180, 20);

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

		cbxReference1.setBounds(68, 202, 180, 20);
		contentPane.add(cbxReference1);

		cbxReference2.setBounds(433, 202, 180, 20);
		contentPane.add(cbxReference2);

		final Label lblText1 = new Label("->");
		lblText1.setBackground(Color.ORANGE);
		lblText1.setForeground(Color.BLACK);
		lblText1.setBounds(38, 103, 24, 22);
		contentPane.add(lblText1);

		final Label lblRule1 = new Label("->");
		lblRule1.setBackground(Color.ORANGE);
		lblRule1.setForeground(Color.BLACK);
		lblRule1.setBounds(38, 157, 24, 22);
		contentPane.add(lblRule1);

		final Label lblEntityType1 = new Label("->");
		lblEntityType1.setBackground(Color.ORANGE);
		lblEntityType1.setForeground(Color.BLACK);
		lblEntityType1.setBounds(38, 200, 24, 22);
		contentPane.add(lblEntityType1);

		final Label lblText2 = new Label("->");
		lblText2.setBackground(Color.ORANGE);
		lblText2.setForeground(Color.BLACK);
		lblText2.setBounds(403, 103, 24, 22);
		contentPane.add(lblText2);

		final Label lblRule2 = new Label("->");
		lblRule2.setBackground(Color.ORANGE);
		lblRule2.setForeground(Color.BLACK);
		lblRule2.setBounds(403, 157, 24, 22);
		contentPane.add(lblRule2);

		final Label lblEntityType2 = new Label("->");
		lblEntityType2.setBackground(Color.ORANGE);
		lblEntityType2.setForeground(Color.BLACK);
		lblEntityType2.setBounds(403, 200, 24, 22);
		contentPane.add(lblEntityType2);

		lblEntityType1.setVisible(false);
		lblEntityType2.setVisible(false);
		lblRule1.setVisible(false);
		lblRule2.setVisible(false);
		lblText1.setVisible(false);
		lblText2.setVisible(false);

		cbxFloat1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// spinFloat1.setValue(-1);
				lblRule1.setVisible(true);
				lblText1.setVisible(false);
				lblEntityType1.setVisible(false);
			}
		});

		cbxFloat2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// spinFloat2.setValue(-1);
				lblRule2.setVisible(true);
				lblText2.setVisible(false);
				lblEntityType2.setVisible(false);
			}
		});

		cbxReference1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// spinFloat1.setValue(-1);
				lblRule1.setVisible(false);
				lblText1.setVisible(false);
				lblEntityType1.setVisible(true);
			}
		});

		cbxReference2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// spinFloat2.setValue(-1);
				lblRule2.setVisible(false);
				lblText2.setVisible(false);
				lblEntityType2.setVisible(true);
			}
		});

		spinFloat1.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				lblRule1.setVisible(false);
				lblText1.setVisible(true);
				lblEntityType1.setVisible(false);
			}
		});

		spinFloat2.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				lblRule2.setVisible(false);
				lblText2.setVisible(true);
				lblEntityType2.setVisible(false);
			}
		});

		txtStr1.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				lblRule1.setVisible(false);
				lblText1.setVisible(true);
				lblEntityType1.setVisible(false);
			}
		});

		txtStr2.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				lblRule2.setVisible(false);
				lblText2.setVisible(true);
				lblEntityType2.setVisible(false);
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
				if (lblText1.isVisible()) {
					rule1 = new FloatRule('n', Double.parseDouble(spinFloat1
							.getValue().toString()), "reqwuiop", project);
					ruleList.add(rule1);
				} else {

					if (lblEntityType1.isVisible()) {

					} else if (lblRule1.isVisible()) {
						rule1 = (FloatRule) floatRuleList.get(cbxFloat1
								.getSelectedIndex());
					} else {
						valid = false;
					}
				}
				if (lblText2.isVisible()) {
					rule2 = new FloatRule('n', Double.parseDouble(spinFloat2
							.getValue().toString()), "reqwuiop", project);
					ruleList.add(rule2);
				} else {
					if (lblEntityType2.isVisible()) {

					} else if (lblRule2.isVisible()) {
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
					StringRule temp = new StringRule(rule1, rule2, rule3,
							txtName.getText(), project);

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

		LinkedList<EntityType> headTypes = new LinkedList<EntityType>();

		for (int x = 0; x < heads.size(); x++) {
			if (!heads.get(x).getType().getIsTextField()) {
				headTypes.add(heads.get(x).getType());
			}
		}
		String[] headers = new String[headTypes.size()];
		for (int x = 0; x < headTypes.size(); x++) {
			headers[x] = headTypes.get(x).getName();
		}

		cbxReference1.setModel(new DefaultComboBoxModel(headers));
		cbxReference2.setModel(new DefaultComboBoxModel(headers));

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

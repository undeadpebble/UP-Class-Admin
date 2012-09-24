package Rule;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JButton;

import ClassAdminBackEnd.EntityType;
import ClassAdminBackEnd.Project;
import ClassAdminBackEnd.SuperEntity;
import ClassAdminFrontEnd.BackgroundGradientPanel;
import Frames.FilterFrame;
import Frames.Frame;

import javax.swing.SpinnerNumberModel;
import java.awt.Label;

public class frmRule extends JFrame {

	private JPanel contentPane;
	private BackgroundGradientPanel backgroundPanel;
	private Project project;
	private LinkedList<Rule> ruleList;
	private LinkedList<Rule> floatBoolRuleList;
	private LinkedList<Rule> StringBoolRuleList;
	private LinkedList<Rule> floatRuleList;
	private LinkedList<Rule> StringRuleList;
	private LinkedList<Rule> BoolRuleList;
	private String[] opFloatString = new String[] { "+", "-", "x", "%" };
	private char[] opFloatChar = new char[] { '+', '-', 'x', '%' };

	private String[] opFloatBoolString = new String[] { "<", ">", "=", "<=",
			">=" };
	private char[] opFloatBoolChar = new char[] { '<', '>', '=', '1', '2' };

	private String[] opStringBoolString = new String[] { "= (equals)",
			"contains" };
	private char[] opStringBoolChar = new char[] { '=', 'c' };

	private LinkedList<SuperEntity> heads;
	private LinkedList<EntityType> headTypes = new LinkedList<EntityType>();

	private JComboBox cbxOpperator = new JComboBox();
	private JComboBox cbxRuleChooser = new JComboBox();
	private JComboBox cbxFloat1 = new JComboBox();
	private JComboBox cbxFloat2 = new JComboBox();
	private JButton btnAddFloatRule = new JButton("Add Float Rule");
	private JTextField txtName = new JTextField();
	private JSpinner spinFloat1 = new JSpinner();
	private JSpinner spinFloat2 = new JSpinner();
	private JButton btnAddFloatBool = new JButton("Add Float Boolean Rule");
	private JTextField txtStr1 = new JTextField();
	private JTextField txtStr2 = new JTextField();
	private JButton btnAddStrBool = new JButton("Add new String boolean rule");
	private JComboBox cbxStringComp = new JComboBox();
	private JButton btnAddStringRule = new JButton("Add String Rule");
	private JComboBox cbxReference1 = new JComboBox();
	private JComboBox cbxReference2 = new JComboBox();

	private Label lblText1 = new Label("->");
	private Label lblRule1 = new Label("->");
	private Label lblEntityType1 = new Label("->");
	private Label lblText2 = new Label("->");
	private Label lblRule2 = new Label("->");
	private Label lblEntityType2 = new Label("->");

	
	/**
	 * Create the frame.
	 */
	public frmRule(final Project project) {
		txtStr2.setBounds(435, 105, 178, 20);
		txtStr2.setColumns(10);
		txtStr1.setBounds(68, 106, 180, 20);
		txtStr1.setColumns(10);
		this.project = project;
		
		lblEntityType1.setVisible(true);
		lblEntityType2.setVisible(true);
		lblRule1.setVisible(false);
		lblRule2.setVisible(false);
		lblText1.setVisible(false);
		lblText2.setVisible(false);
		
		lblText1.setForeground(new Color(0xEDEDED));
		lblRule1.setForeground(new Color(0xEDEDED));
		lblEntityType1.setForeground(new Color(0xEDEDED));
		lblText2.setForeground(new Color(0xEDEDED));
		lblRule2.setForeground(new Color(0xEDEDED));
		lblEntityType2.setForeground(new Color(0xEDEDED));

		heads = project.getHead().getHeadersLinkedList();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 671, 440);
		cbxOpperator.setBounds(302, 105, 36, 20);
		cbxRuleChooser.setBounds(10, 11, 165, 20);
		cbxFloat1.setBounds(68, 159, 180, 20);
		cbxFloat2.setBounds(433, 159, 180, 20);
		btnAddFloatRule.setBounds(237, 280, 219, 34);
		txtName.setBounds(68, 42, 292, 20);
		spinFloat1.setBounds(68, 105, 180, 20);
		spinFloat2.setBounds(433, 105, 180, 20);

		contentPane = new JPanel();
		contentPane.setForeground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		reactivateFloat();
		
		backgroundPanel = new BackgroundGradientPanel(contentPane);
		backgroundPanel.setBounds(0, 0, getWidth(), getHeight());
		contentPane.add(backgroundPanel);
		backgroundPanel.setLayout(null);

		backgroundPanel.add(cbxRuleChooser);
		backgroundPanel.add(cbxOpperator);
		backgroundPanel.add(cbxFloat1);
		backgroundPanel.add(cbxFloat2);
		backgroundPanel.add(btnAddFloatRule);
		backgroundPanel.add(txtName);
		backgroundPanel.add(spinFloat1);
		backgroundPanel.add(spinFloat2);

		btnAddFloatBool.setBounds(237, 280, 219, 34);

		backgroundPanel.add(btnAddFloatBool);

		backgroundPanel.add(txtStr1);

		backgroundPanel.add(txtStr2);
		btnAddStrBool.setBounds(260, 280, 198, 34);
		backgroundPanel.add(btnAddStrBool);
		cbxStringComp.setBounds(260, 106, 113, 20);

		backgroundPanel.add(cbxStringComp);
		btnAddStringRule.setBounds(237, 280, 225, 34);

		backgroundPanel.add(btnAddStringRule);

		cbxRuleChooser.setModel(new DefaultComboBoxModel(new String[] {
				"Float Rule", "String Rule", "Float Boolean Rule",
				"String Boolean Rule" }));

		cbxReference1.setBounds(68, 202, 180, 20);
		backgroundPanel.add(cbxReference1);

		cbxReference2.setBounds(433, 202, 180, 20);
		backgroundPanel.add(cbxReference2);

		lblText1.setBackground(Color.ORANGE);
		lblText1.setForeground(Color.BLACK);
		lblText1.setBounds(38, 103, 24, 22);
		backgroundPanel.add(lblText1);

		lblRule1.setBackground(Color.ORANGE);
		lblRule1.setForeground(Color.BLACK);
		lblRule1.setBounds(38, 157, 24, 22);
		backgroundPanel.add(lblRule1);

		lblEntityType1.setBackground(Color.ORANGE);
		lblEntityType1.setForeground(Color.BLACK);
		lblEntityType1.setBounds(38, 200, 24, 22);
		backgroundPanel.add(lblEntityType1);

		lblText2.setBackground(Color.ORANGE);
		lblText2.setForeground(Color.BLACK);
		lblText2.setBounds(403, 103, 24, 22);
		backgroundPanel.add(lblText2);

		lblRule2.setBackground(Color.ORANGE);
		lblRule2.setForeground(Color.BLACK);
		lblRule2.setBounds(403, 157, 24, 22);
		backgroundPanel.add(lblRule2);

		lblEntityType2.setBackground(Color.ORANGE);
		lblEntityType2.setForeground(Color.BLACK);
		lblEntityType2.setBounds(403, 200, 24, 22);
		backgroundPanel.add(lblEntityType2);

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
				if (lblText1.isVisible()) {
					rule1 = new StringRule(txtStr1.getText(), "reqwuiop",
							project);
					ruleList.add(rule1);
				} else {
					if (lblEntityType1.isVisible()) {
						rule1 = new StringRule("referencespsaiodfhnosaudhf",
								project, headTypes.get(cbxReference1
										.getSelectedIndex()));
						ruleList.add(rule1);
					} else if (lblRule1.isVisible()) {
						rule1 = (StringRule) StringBoolRuleList.get(cbxFloat1
								.getSelectedIndex());
					} else {
						valid = false;
					}
				}
				if (lblText2.isVisible()) {
					rule2 = new StringRule(txtStr2.getText(), "reqwuiop",
							project);
					ruleList.add(rule2);
				} else {
					if (lblEntityType2.isVisible()) {
						rule2 = new StringRule("referencespsaiodfhnosaudhf",
								project, headTypes.get(cbxReference1
										.getSelectedIndex()));
						ruleList.add(rule1);
					} else if (lblRule2.isVisible()) {
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
				if (lblText1.isVisible()) {
					rule1 = new FloatRule('n', Double.parseDouble(spinFloat1
							.getValue().toString()), "reqwuiop", project);
					ruleList.add(rule1);
				} else {
					if (lblEntityType1.isVisible()) {
						rule1 = new FloatRule("referencespsaiodfhnosaudhf",
								project, headTypes.get(cbxReference1
										.getSelectedIndex()));
						ruleList.add(rule1);
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
						rule2 = new FloatRule("referencespsaiodfhnosaudhf",
								project, headTypes.get(cbxReference1
										.getSelectedIndex()));
						ruleList.add(rule2);
					} else if (lblRule1.isVisible()) {
						rule2 = (FloatRule) floatRuleList.get(cbxFloat2
								.getSelectedIndex());
					} else {
						valid = false;
					}
				}

				if (valid) {
					FloatBoolRule temp = new FloatBoolRule(
							opFloatBoolChar[cbxOpperator.getSelectedIndex()],
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
						rule1 = new FloatRule("referencespsaiodfhnosaudhf",
								project, headTypes.get(cbxReference1
										.getSelectedIndex()));
						ruleList.add(rule1);
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
						rule2 = new FloatRule("referencespsaiodfhnosaudhf",
								project, headTypes.get(cbxReference2
										.getSelectedIndex()));
						ruleList.add(rule2);
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

				if (lblText1.isVisible()) {
					rule1 = new StringRule(txtStr1.getText(), "reqwuiop",
							project);
					ruleList.add(rule1);
				} else {
					if (lblEntityType1.isVisible()) {
						rule1 = new StringRule("referencespsaiodfhnosaudhf",
								project, headTypes.get(cbxReference1
										.getSelectedIndex()));
						ruleList.add(rule1);
					} else if (lblRule1.isVisible()) {
						rule1 = (StringRule) StringBoolRuleList.get(cbxFloat1
								.getSelectedIndex());
					} else {
						valid = false;
					}
				}
				if (lblText2.isVisible()) {
					rule2 = new StringRule(txtStr2.getText(), "reqwuiop",
							project);
					ruleList.add(rule2);
				} else {
					if (lblEntityType2.isVisible()) {
						rule2 = new StringRule("referencespsaiodfhnosaudhf",
								project, headTypes.get(cbxReference2
										.getSelectedIndex()));
						ruleList.add(rule2);
					} else if (lblRule2.isVisible()) {
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
		lblEntityType1.setVisible(true);
		lblEntityType2.setVisible(true);
		lblRule1.setVisible(false);
		lblRule2.setVisible(false);
		lblText1.setVisible(false);
		lblText2.setVisible(false);
		
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

		headTypes.clear();

		for (int x = 0; x < heads.size(); x++) {
			if (heads.get(x).getType().getIsTextField()
					&& !heads.get(x).getType().getIsRule()) {
				headTypes.add(heads.get(x).getType());
			}
		}
		String[] headers = new String[headTypes.size()];
		for (int x = 0; x < headTypes.size(); x++) {
			headers[x] = headTypes.get(x).getName();
		}

		cbxReference1.setModel(new DefaultComboBoxModel(headers));
		cbxReference2.setModel(new DefaultComboBoxModel(headers));

		ruleList = project.getRules();
		floatBoolRuleList = new LinkedList<Rule>();
		StringBoolRuleList = new LinkedList<Rule>();
		floatRuleList = new LinkedList<Rule>();
		StringRuleList = new LinkedList<Rule>();
		BoolRuleList = new LinkedList<Rule>();

		for (int x = 0; x < ruleList.size(); x++) {
			switch (ruleList.get(x).getType()) {
			case 1:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0
						&& ruleList.get(x).getName()
								.compareTo("referencespsaiodfhnosaudhf") != 0) {
					floatBoolRuleList.add(ruleList.get(x));
					BoolRuleList.add(ruleList.get(x));
				}
				break;
			case 2:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0
						&& ruleList.get(x).getName()
								.compareTo("referencespsaiodfhnosaudhf") != 0) {
					StringBoolRuleList.add(ruleList.get(x));
					BoolRuleList.add(ruleList.get(x));
				}
				break;
			case 3:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0
						&& ruleList.get(x).getName()
								.compareTo("referencespsaiodfhnosaudhf") != 0) {
					floatRuleList.add(ruleList.get(x));
				}
				break;
			case 4:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0
						&& ruleList.get(x).getName()
								.compareTo("referencespsaiodfhnosaudhf") != 0) {
					StringRuleList.add(ruleList.get(x));
				}
				break;

			default:
				break;
			}
		}

		String[] BoolRule = new String[BoolRuleList.size()];
		for (int x = 0; x < BoolRuleList.size(); x++) {
			BoolRule[x] = BoolRuleList.get(x).getName();
		}

		String[] StringRule = new String[StringRuleList.size()];
		for (int x = 0; x < StringRuleList.size(); x++) {
			StringRule[x] = StringRuleList.get(x).getName();
		}

		cbxFloat1.setModel(new DefaultComboBoxModel(StringRule));
		cbxFloat2.setModel(new DefaultComboBoxModel(StringRule));
		cbxStringComp.setModel(new DefaultComboBoxModel(BoolRule));
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------
	public void reactivateBooleanFloat() {
		lblEntityType1.setVisible(true);
		lblEntityType2.setVisible(true);
		lblRule1.setVisible(false);
		lblRule2.setVisible(false);
		lblText1.setVisible(false);
		lblText2.setVisible(false);
		
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
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0
						&& ruleList.get(x).getName()
								.compareTo("referencespsaiodfhnosaudhf") != 0) {
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

		headTypes.clear();
		for (int x = 0; x < heads.size(); x++) {
			if (!heads.get(x).getType().getIsTextField()
					&& !heads.get(x).getType().getIsRule()) {
				headTypes.add(heads.get(x).getType());
			}
		}
		String[] headers = new String[headTypes.size()];
		for (int x = 0; x < headTypes.size(); x++) {
			headers[x] = headTypes.get(x).getName();
		}

		cbxReference1.setModel(new DefaultComboBoxModel(headers));
		cbxReference2.setModel(new DefaultComboBoxModel(headers));

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
		lblEntityType1.setVisible(true);
		lblEntityType2.setVisible(true);
		lblRule1.setVisible(false);
		lblRule2.setVisible(false);
		lblText1.setVisible(false);
		lblText2.setVisible(false);
		
		ruleList = project.getRules();
		floatBoolRuleList = new LinkedList<Rule>();
		StringBoolRuleList = new LinkedList<Rule>();
		floatRuleList = new LinkedList<Rule>();
		StringRuleList = new LinkedList<Rule>();
		BoolRuleList = new LinkedList<Rule>();

		for (int x = 0; x < ruleList.size(); x++) {
			switch (ruleList.get(x).getType()) {
			case 1:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0
						&& ruleList.get(x).getName()
								.compareTo("referencespsaiodfhnosaudhf") != 0) {
					floatBoolRuleList.add(ruleList.get(x));
					BoolRuleList.add(ruleList.get(x));
				}
				break;
			case 2:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0
						&& ruleList.get(x).getName()
								.compareTo("referencespsaiodfhnosaudhf") != 0) {
					StringBoolRuleList.add(ruleList.get(x));
					BoolRuleList.add(ruleList.get(x));
				}
				break;
			case 3:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0
						&& ruleList.get(x).getName()
								.compareTo("referencespsaiodfhnosaudhf") != 0) {
					floatRuleList.add(ruleList.get(x));
				}
				break;
			case 4:
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0
						&& ruleList.get(x).getName()
								.compareTo("referencespsaiodfhnosaudhf") != 0) {
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

		headTypes.clear();

		for (int x = 0; x < heads.size(); x++) {
			if (heads.get(x).getType().getIsTextField()
					&& !heads.get(x).getType().getIsRule()) {
				headTypes.add(heads.get(x).getType());
			}
		}
		String[] headers = new String[headTypes.size()];
		for (int x = 0; x < headTypes.size(); x++) {
			headers[x] = headTypes.get(x).getName();
		}

		cbxReference1.setModel(new DefaultComboBoxModel(headers));
		cbxReference2.setModel(new DefaultComboBoxModel(headers));

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
		lblEntityType1.setVisible(true);
		lblEntityType2.setVisible(true);
		lblRule1.setVisible(false);
		lblRule2.setVisible(false);
		lblText1.setVisible(false);
		lblText2.setVisible(false);

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

		headTypes.clear();
		for (int x = 0; x < heads.size(); x++) {
			if (!heads.get(x).getType().getIsTextField()
					&& !heads.get(x).getType().getIsRule()) {
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
				if (ruleList.get(x).getName().compareTo("reqwuiop") != 0
						&& ruleList.get(x).getName()
								.compareTo("referencespsaiodfhnosaudhf") != 0) {
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

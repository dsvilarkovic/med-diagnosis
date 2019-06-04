package view;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class RegimePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JRadioButton caseBasedButton;
	private JRadioButton ruleBasedButton;

	public RegimePanel() {

		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel chooseRegime = new JLabel("Choose regime");

		ruleBasedButton = new JRadioButton("Rule-based");
		caseBasedButton = new JRadioButton("Case-based");

		ButtonGroup group = new ButtonGroup();
		ruleBasedButton.setSelected(true);
		group.add(ruleBasedButton);
		group.add(caseBasedButton);
		this.add(chooseRegime);
		this.add(ruleBasedButton);
		this.add(caseBasedButton);
	}

	public JRadioButton getCaseBasedButton() {
		return caseBasedButton;
	}

	public void setCaseBasedButton(JRadioButton caseBasedButton) {
		this.caseBasedButton = caseBasedButton;
	}

	public JRadioButton getRuleBasedButton() {
		return ruleBasedButton;
	}

	public void setRuleBasedButton(JRadioButton ruleBasedButton) {
		this.ruleBasedButton = ruleBasedButton;
	}




}
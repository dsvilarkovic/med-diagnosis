package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class TextPhysicalExaminationPanel extends SinglePhysicalExaminationPanel {
	private JComboBox<String> physicalExaminationComboBox;
	
	public TextPhysicalExaminationPanel(String name, String[] options) {
		physicalExaminationLabel = new JLabel(name);
		physicalExaminationComboBox = new JComboBox<>(options);
		physicalExaminationComboBox.setBackground(Color.WHITE);
		this.setLayout(new BorderLayout(2,2));
		this.setMaximumSize(new Dimension(550, 50));
		this.setPreferredSize(new Dimension(550, 50));
		this.add(physicalExaminationLabel,BorderLayout.LINE_START);
		this.add(physicalExaminationComboBox,BorderLayout.LINE_END);
	
	}
	
	public String getPhysicalExaminationSymptom() {
		if(physicalExaminationComboBox.getSelectedIndex() != 0) {
			return (String) physicalExaminationComboBox.getSelectedItem();
		}
		
		return "";
	}
	
	
}

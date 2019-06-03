package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class TextPhysicalExaminationPanel extends SinglePhysicalExaminationPanel {
	private JComboBox<String> physicalExaminationComboBox;
	
	public TextPhysicalExaminationPanel(String name, String[] options) {
		physicalExaminationLabel = new JLabel(name);
		physicalExaminationComboBox = new JComboBox<>(options);
		this.setLayout(new BorderLayout(2,2));
		this.setMaximumSize(new Dimension(600, 50));
		this.setPreferredSize(new Dimension(600, 50));
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

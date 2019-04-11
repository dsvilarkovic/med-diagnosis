package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class MedicalExaminationPanel extends JPanel{

	PatientInformationPanel patientInformationPanel;
	public MedicalExaminationPanel() {
		init();
	}
	
	private void init() {
		patientInformationPanel = new PatientInformationPanel();
		JPanel contentPanel = new JPanel();
		JLabel medicalExaminationLabel = new JLabel("Medical examination information");
		JLabel anamesisLabel = new JLabel("Anamnesis");
		JLabel physicalExaminationLabel = new JLabel("Physical examination");
		JLabel diagnosisLabel = new JLabel("Diagnosis");
		JLabel therapyLabel = new JLabel("Therapy");
		JLabel preventiveChecksLabel = new JLabel("Preventive checks");
		
		medicalExaminationLabel.setFont(new java.awt.Font("Times New Roman", 0, 24));
		
		this.setLayout(new BorderLayout());
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(Box.createVerticalStrut(35));
		contentPanel.add(medicalExaminationLabel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(anamesisLabel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(physicalExaminationLabel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(diagnosisLabel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(therapyLabel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(preventiveChecksLabel);
		contentPanel.add(Box.createVerticalStrut(15));
		this.add(patientInformationPanel,BorderLayout.LINE_START);
		this.add(contentPanel, BorderLayout.CENTER);
	}
}

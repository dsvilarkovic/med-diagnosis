package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MedicalExaminationPanel extends JPanel {

	private JPanel patientInformationPanel;
	
	private AnamnesisPanel anamnesisPanel;
	
	private DiagnosisPanel diagnosisPanel;
	
	private AdditionalCheckupsPanel additionalCheckupsPanel;
	
	private PhysicalExaminationPanel phisicalExaminationPanel;
	
	@SuppressWarnings("rawtypes")
	public MedicalExaminationPanel() {
		patientInformationPanel = new PatientInformationPanel();
		anamnesisPanel = new AnamnesisPanel();
		phisicalExaminationPanel = new PhysicalExaminationPanel();
		additionalCheckupsPanel = new AdditionalCheckupsPanel();
		diagnosisPanel = new DiagnosisPanel();
		init();

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init() {
		JPanel contentPanel = new JPanel();
		JLabel medicalExaminationLabel = new JLabel("Medical examination information");
		JPanel therapyPanel = createTherapyPanel();
		JPanel preventiveChecksPanel = createPreventiveChecksPanel();

		medicalExaminationLabel.setFont(new java.awt.Font(medicalExaminationLabel.getFont().getFontName(), 0, 24));

		this.setLayout(new BorderLayout());
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(Box.createVerticalStrut(35));
		contentPanel.add(medicalExaminationLabel);
		contentPanel.add(Box.createVerticalStrut(15));
		contentPanel.add(anamnesisPanel);
		contentPanel.add(new JSeparator());
		contentPanel.add(phisicalExaminationPanel);
		contentPanel.add(new JSeparator());
		contentPanel.add(additionalCheckupsPanel);
		contentPanel.add(new JSeparator());
		contentPanel.add(diagnosisPanel);
		contentPanel.add(new JSeparator());
		contentPanel.add(therapyPanel);
		contentPanel.add(new JSeparator());
		contentPanel.add(preventiveChecksPanel);
		contentPanel.add(new JSeparator());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, patientInformationPanel,
				new JScrollPane(contentPanel));
		this.add(splitPane, BorderLayout.CENTER);

	}
	
	// TODO 2: Zavrsiti panel za preventivne preglede
	private JPanel createPreventiveChecksPanel() {
		JPanel resultPanel = new JPanel();
		JPanel preventiveChecksPanel = new JPanel();
		JLabel preventiveChecksLabel = new JLabel("Preventive checks");
		
		
		resultPanel.setLayout(new BorderLayout(15,15));
		resultPanel.add(preventiveChecksLabel,BorderLayout.NORTH);
		resultPanel.add(preventiveChecksPanel, BorderLayout.CENTER);
		
		return resultPanel;
	}
	
	// TODO 1: Zavrsiti panel za terapije
	// Maja Kolosnjaji
	private JPanel createTherapyPanel() {
		JPanel resultPanel = new JPanel();
		JPanel therapyPanel = new JPanel();
		JLabel therapyLabel = new JLabel("Therapy");
		
	
		
		resultPanel.setLayout(new BorderLayout(15,15));
		resultPanel.add(therapyLabel,BorderLayout.NORTH);
		resultPanel.add(therapyPanel, BorderLayout.CENTER);
		
		return resultPanel;
	}

	
	public List<String> getSymptomsList() {
		return anamnesisPanel.getChosenSympList();
	}
	
	public Map<String, Integer> getSymptomsMap() {
		return anamnesisPanel.getChosenSympMap();
	}
	
	public List<String> getPhysicalExaminationSymptomsList() {
		return phisicalExaminationPanel.getSymptomsList();
	}
	
	public  Map<String, Integer> getPhysicalExaminationSymptomsMap() {
		return phisicalExaminationPanel.getSymptomsMap();
	}
	
	public void generateAdditionalCheckups(Map<String,String> suggestedAdditionalCheckupsMap ) {
		additionalCheckupsPanel.generateSuggestedAdditionalCheckups(suggestedAdditionalCheckupsMap);
	}

}

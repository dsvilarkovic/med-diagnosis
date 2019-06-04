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
	
	private TherapyPanel therapyPanel;
	
	private PreventiveExaminationPanel preventiveExaminationPanel;
	
	@SuppressWarnings("rawtypes")
	public MedicalExaminationPanel() {
		patientInformationPanel = new PatientInformationPanel();
		anamnesisPanel = new AnamnesisPanel();
		phisicalExaminationPanel = new PhysicalExaminationPanel();
		additionalCheckupsPanel = new AdditionalCheckupsPanel();
		diagnosisPanel = new DiagnosisPanel();
		therapyPanel = new TherapyPanel();
		preventiveExaminationPanel = new PreventiveExaminationPanel();
		
		init();

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init() {
		JPanel contentPanel = new JPanel();
		JLabel medicalExaminationLabel = new JLabel("Medical examination information");
		
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
		contentPanel.add(preventiveExaminationPanel);
		contentPanel.add(new JSeparator());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, patientInformationPanel,
				new JScrollPane(contentPanel));
		this.add(splitPane, BorderLayout.CENTER);

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
	
	public List<String> getAdditionalCheckupsList() {
		return additionalCheckupsPanel.getAdditionalCheckupsList();
	}
	
	public  Map<String, Integer> getPhysicalExaminationSymptomsMap() {
		return phisicalExaminationPanel.getSymptomsMap();
	}
	
	public void generateAdditionalCheckups(Map<String,String> suggestedAdditionalCheckupsMap ) {
		additionalCheckupsPanel.generateSuggestedAdditionalCheckups(suggestedAdditionalCheckupsMap);
	}
	
	public void generateTherapies(List<String> suggestedTherapies) {
		therapyPanel.generateSuggestedTherapies(suggestedTherapies);
	}

	public void generateDiagnosis(Map<String,Float> diagnosis) {
		diagnosisPanel.generateDiagnosis(diagnosis);
	}

	public void generatePreventiveExaminations(List<String> suggestedExaminations) {
		preventiveExaminationPanel.generateSuggestedPreventiveExaminations(suggestedExaminations);
	}
}

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;

import controller.SaveMedicalExamination;
import model.AdditionalExaminationResult;
import model.Disease;
import model.MedicalExamination;
import model.MedicalRecord;
import model.PhysicalExaminationResult;
import model.Symptom;
import model.Therapy;
import utils.Singleton;

public class MedicalExaminationPanel extends JPanel {

	private JPanel patientInformationPanel;
	
	private AnamnesisPanel anamnesisPanel;
	
	private DiagnosisPanel diagnosisPanel;
	
	private AdditionalCheckupsPanel additionalCheckupsPanel;
	
	private PhysicalExaminationPanel phisicalExaminationPanel;
	
	private MedicalExamination medicalExamination;

	private TherapyPanel therapyPanel;
	
	private PreventiveExaminationPanel preventiveExaminationPanel;
	
	@SuppressWarnings("rawtypes")
	public MedicalExaminationPanel(MedicalRecord medicalRecord) {
		this.medicalExamination = new MedicalExamination();
		this.medicalExamination.setMedicalRecord(medicalRecord); 
		patientInformationPanel = new PatientInformationPanel(medicalRecord);
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
		JButton saveButton = new JButton(new SaveMedicalExamination());
		JButton backButton = new JButton("Back");
		backButton.setIcon(new ImageIcon("data/back-arrow.png"));
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Singleton.getInstance().getMainFrame().setCentralPanel(new PatientRecordPanel(medicalExamination.getMedicalRecord()));
				
			}
		});
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(500,0));
		panel.add(backButton, BorderLayout.LINE_START);
		JLabel medicalExaminationLabel = new JLabel("Medical examination information");
		
		medicalExaminationLabel.setFont(new java.awt.Font(medicalExaminationLabel.getFont().getFontName(), 0, 24));
		panel.add(medicalExaminationLabel, BorderLayout.CENTER);
		this.setLayout(new BorderLayout());
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(Box.createVerticalStrut(35));
		contentPanel.add(panel);
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
		JPanel margin = new JPanel();
		margin.setLayout(new BorderLayout(5,5));
		margin.add(Box.createHorizontalStrut(10), BorderLayout.WEST);
		margin.add(contentPanel,BorderLayout.CENTER);
		margin.add(Box.createHorizontalStrut(10), BorderLayout.EAST);
		margin.add(saveButton, BorderLayout.SOUTH);
		saveButton.setBorderPainted(true);
		saveButton.setBackground(new Color(64,128,243));
		backButton.setBackground(new Color(64,128,243));
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, patientInformationPanel,
				new JScrollPane(margin));
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
	
	public int getAge() {
		return medicalExamination.getMedicalRecord().getAge();
	}
	
	public boolean getGender() {
		return medicalExamination.getMedicalRecord().isFemale();
	}
	
	public MedicalExamination getMedicalExaminationInformation() {
		List<String> symptomsList = anamnesisPanel.getChosenSympList();
		for(String symptom : symptomsList) {
			Symptom s = new Symptom(symptom);
			this.medicalExamination.getSymptoms().add(s);
		}
		
		List<String> physicalExaminationList = phisicalExaminationPanel.getSymptomsList();
		for(String symptom : physicalExaminationList) {
			PhysicalExaminationResult s = new PhysicalExaminationResult(symptom);
			this.medicalExamination.getPhysicalExaminationResults().add(s);
		}
		
		List<String> additionalList = additionalCheckupsPanel.getAllAdditionalCheckupsList();
		for(String additionalExam : additionalList) {
			AdditionalExaminationResult a = new AdditionalExaminationResult();
			a.setName(additionalExam);
			this.medicalExamination.getAdditionalExaminationResults().add(a);
		}
		
		if(diagnosisPanel.getDiagnose().equals("")) {
			this.medicalExamination.setDisease(null);
		}else {
			this.medicalExamination.setDisease(new Disease(diagnosisPanel.getDiagnose()));
		}
		
		
		List<String> therapiesList = getChosenTherapies();
		for(String t: therapiesList) {
			Therapy th = new Therapy();
			th.setName(t);
			this.medicalExamination.getTherapies().add(th);
		}
		
		//TODO: kada se ubace preventivni pregledi
		
		
		return this.medicalExamination;
	}

	public void generatePreventiveExaminations(List<String> suggestedExaminations) {
		preventiveExaminationPanel.generateSuggestedPreventiveExaminations(suggestedExaminations);
	}
	
	public String getDiagnosis() {
		return diagnosisPanel.getDiagnose();
	}
	
	public List<String> getChosenTherapies(){
		return therapyPanel.getChosenTherapies();
	}
	
	
}

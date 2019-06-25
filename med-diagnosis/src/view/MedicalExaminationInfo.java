package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.AdditionalExaminationResult;
import model.MedicalExamination;
import model.PhysicalExaminationResult;
import model.PreventiveExamination;
import model.Symptom;
import model.Therapy;

public class MedicalExaminationInfo extends JPanel {
	private MedicalExamination medicalExamination;
	
	public MedicalExaminationInfo(MedicalExamination m) {
		this.medicalExamination = m;
		initGUI();
	}
	
	private void initGUI() {
		JLabel medicalRecordNumberText = new JLabel("Medical record number: ");
		JLabel medicalRecordNumber = new JLabel(this.medicalExamination.getMedicalRecord().getMedicalRecordNumber());
		JLabel ageTextLabel = new JLabel("Age: ");
		JLabel age = new JLabel(String.valueOf(this.medicalExamination.getMedicalRecord().getAge()));
		JLabel genderTextLabel = new JLabel("Gender: ");
		JLabel gender = new JLabel((this.medicalExamination.getMedicalRecord().isFemale())? "Female": "Male");
		JLabel symptomsLabel = new JLabel("Symptoms: ");
		JLabel allSymptoms = new JLabel(findSymptoms(), SwingConstants.LEFT);
		JLabel physicalExaminationLabel = new JLabel("Physical Examinations: ");
		JLabel physicalResults = new JLabel(physicalExaminations(), SwingConstants.LEFT);
		JLabel additionalCheckupsLabel = new JLabel("Additional Checkups: ");
		JLabel additionalCheckupsResult = new JLabel(additionalCheckups(), SwingConstants.LEFT);
		JLabel diagnosisLabel = new JLabel("Diagnosis: ");
		JLabel diagnosisResult = new JLabel(this.medicalExamination.getDisease().getName().replace('_', ' '));
		JLabel therapiesLabel = new JLabel("Therapy: ", SwingConstants.LEFT);
		JLabel therapiesResult = new JLabel(therapy());
		JLabel preventiveExamLabel = new JLabel("Preventive Examinations: ");
		JLabel preventiveExamResult = new JLabel(preventive(), SwingConstants.LEFT);
		
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(9, 2,0,15));
		content.add(medicalRecordNumberText);
		content.add(medicalRecordNumber);
		content.add(ageTextLabel);
		content.add(age);
		content.add(genderTextLabel);
		content.add(gender);
		content.add(symptomsLabel);
		content.add(allSymptoms);
		content.add(physicalExaminationLabel);
		content.add(physicalResults);
		content.add(additionalCheckupsLabel);
		content.add(additionalCheckupsResult);
		content.add(diagnosisLabel);
		content.add(diagnosisResult);
		content.add(therapiesLabel);
		content.add(therapiesResult);
		content.add(preventiveExamLabel);
		content.add(preventiveExamResult);
		
		this.setLayout(new BorderLayout());
		this.add(content, BorderLayout.NORTH);
		this.add(new JPanel(), BorderLayout.CENTER);
	}
	
	private String findSymptoms(){
		String resultString = "<html>";
		Set<Symptom> s = this.medicalExamination.getSymptoms();
		int i=0;
		for (Iterator iterator = s.iterator(); iterator.hasNext();) {
			i++;
			Symptom symptom = (Symptom) iterator.next();
			if(i > 3) {
				i = 0;
				resultString+= "<br> ";
			}
			if(iterator.hasNext())
				resultString += symptom.getName() + ", ";
			else
				resultString += symptom.getName() + ".";

			
		}
		resultString = resultString.replace('_', ' ');
		resultString += "</html>";
		return resultString;
	}
	
	private String physicalExaminations() {
		String resultString = "<html>";
		Set<PhysicalExaminationResult> physicalExaminationResults = this.medicalExamination.getPhysicalExaminationResults();
		int i=0;
		for (Iterator iterator = physicalExaminationResults.iterator(); iterator.hasNext();) {
			PhysicalExaminationResult physicalExaminationResult = (PhysicalExaminationResult) iterator.next();
			i++;
			if(i > 3) {
				i = 0;
				resultString+= "<br> ";
			}
			if(iterator.hasNext())
				resultString += physicalExaminationResult.getName() + ", ";
			else
				resultString += physicalExaminationResult.getName() + ".";
		}
		resultString = resultString.replace('_', ' ');
		resultString += "</html>";
		return resultString;
	}
	
	public String additionalCheckups() {
		String resultString = "<html>";
		Set<AdditionalExaminationResult> additionalCheckups = this.medicalExamination.getAdditionalExaminationResults();
		int i=0;
		for (Iterator iterator = additionalCheckups.iterator(); iterator.hasNext();) {
			AdditionalExaminationResult additionalExaminationResult = (AdditionalExaminationResult) iterator.next();
			i++;
			if(i > 3) {
				i = 0;
				resultString+= "<br> ";
			}
			if(iterator.hasNext())
				resultString += additionalExaminationResult.getName() + ", ";
			else
				resultString += additionalExaminationResult.getName() + ".";
		}
		resultString = resultString.replace('_', ' ');
		resultString += "</html>";
		return resultString;
	}
	
	public String therapy() {
		String resultString = "<html>";
		Set<Therapy> therapySet = this.medicalExamination.getTherapies();
		int i=0;
		for (Iterator iterator = therapySet.iterator(); iterator.hasNext();) {
			i++;
			if(i > 3) {
				i = 0;
				resultString+= "<br> ";
			}
			Therapy therapy = (Therapy) iterator.next();
			if(iterator.hasNext())
				resultString += therapy.getName() + ", ";
			else
				resultString += therapy.getName() + ".";
		}
		resultString = resultString.replace('_', ' ');
		resultString += "</html>";
		return resultString;
	}
	
	public String preventive() {
		String resultString = "<html>";
		Set<PreventiveExamination> preventiveExaminationSet = this.medicalExamination.getPreventiveExaminations();
		int i=0;
		for (Iterator iterator = preventiveExaminationSet.iterator(); iterator.hasNext();) {
			PreventiveExamination exam = (PreventiveExamination) iterator.next();
			i++;
			if(i > 3) {
				i = 0;
				resultString+= "<br> ";
			}
			if(iterator.hasNext())
				resultString += exam.getName() + ", ";
			else
				resultString += exam.getName() + ".";
		}
		resultString = resultString.replace('_', ' ');
		resultString += "</html>";
		return resultString;
	}
}

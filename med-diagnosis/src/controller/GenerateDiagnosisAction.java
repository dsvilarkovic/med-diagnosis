package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;

import utils.Singleton;
import view.MedicalExaminationPanel;

public class GenerateDiagnosisAction extends AbstractAction {

	public GenerateDiagnosisAction() {
		// TODO Auto-generated constructor stub
		putValue(NAME, "Generate diagnosis");
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		MedicalExaminationPanel medicalExaminationPanel  = (MedicalExaminationPanel) Singleton.getInstance().getMainFrame().getCentralPanel();
		
		List<String> symptomsList = medicalExaminationPanel.getSymptomsList();
		List<String> physicalExaminationsSymptomsList = medicalExaminationPanel.getPhysicalExaminationSymptomsList();
 		//TODO: dodati rezultate dodatnih pregleda
		List<String> additionalCheckupsList = medicalExaminationPanel.getAdditionalCheckupsList();
		//TODO: dodati vadjenje godina iz kartona pacijenta
		Integer age = 10;
		
		//TODO: dodati vadjenje pola iz kartona pacijenta
 		String gender = "female";
 		
		List<String> variablesList = new ArrayList<String>();
		
		variablesList.addAll(symptomsList);
		variablesList.addAll(physicalExaminationsSymptomsList);
		variablesList.addAll(additionalCheckupsList);
		
		Map<String, Float> map =  Singleton.getInstance().getBayesNetModule().getDiseaseListPercentage(variablesList, age, gender);
		medicalExaminationPanel.generateDiagnosis(map);
	}

}

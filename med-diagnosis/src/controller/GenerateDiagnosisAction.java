package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;

import model.MedicalRecord;
import utils.Regime;
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
		Integer age = medicalExaminationPanel.getAge();
		System.out.println("AGE" + age);
		//TODO: dodati vadjenje pola iz kartona pacijenta
 		String gender = (medicalExaminationPanel.getGender())? "female" : "male";
 		
		List<String> variablesList = new ArrayList<String>();
		
		variablesList.addAll(symptomsList);
		variablesList.addAll(physicalExaminationsSymptomsList);
		variablesList.addAll(additionalCheckupsList);
		
		Map<String, Float> map = null;
		
		if(Singleton.getInstance().getRegime() == Regime.RULE_BASED) {
			map =  Singleton.getInstance().getBayesNetModule().getDiseaseListPercentage(variablesList, age, gender);
			
		}else {
			//TODO: dodati poziv funkcije za case-based
		}
	
		medicalExaminationPanel.generateDiagnosis(map);
	}
	
}

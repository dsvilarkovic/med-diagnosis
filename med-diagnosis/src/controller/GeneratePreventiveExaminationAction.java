package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

import utils.Singleton;
import view.MedicalExaminationPanel;

public class GeneratePreventiveExaminationAction extends AbstractAction {

	public GeneratePreventiveExaminationAction() {
		putValue(NAME, "Generate preventive examinations");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {		
		MedicalExaminationPanel medicalExaminationPanel = (MedicalExaminationPanel)Singleton.getInstance().getMainFrame().getCentralPanel();
		
		
		List<String> suggestedExaminations = Singleton.getInstance().getPrologModule().getPreventiveExaminations();

		medicalExaminationPanel.generatePreventiveExaminations(suggestedExaminations);
		
		

	}

}

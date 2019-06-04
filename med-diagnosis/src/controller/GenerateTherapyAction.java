package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

import utils.Singleton;
import view.MedicalExaminationPanel;

public class GenerateTherapyAction extends AbstractAction {

	public GenerateTherapyAction() {
		putValue(NAME, "Generate therapies");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {		
		MedicalExaminationPanel medicalExaminationPanel = (MedicalExaminationPanel)Singleton.getInstance().getMainFrame().getCentralPanel();
		
		
		List<String> suggestedTherapiesList = Singleton.getInstance().getPrologModule().getTherapies();
		
		medicalExaminationPanel.generateTherapies(suggestedTherapiesList);

	}

}

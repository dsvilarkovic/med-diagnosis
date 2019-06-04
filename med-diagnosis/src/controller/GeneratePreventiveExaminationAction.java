package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import utils.Singleton;
import view.MedicalExaminationPanel;

public class GeneratePreventiveExaminationAction extends AbstractAction {

	public GeneratePreventiveExaminationAction() {
		putValue(NAME, "Generate preventive examinations");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {		
		MedicalExaminationPanel medicalExaminationPanel = (MedicalExaminationPanel)Singleton.getInstance().getMainFrame().getCentralPanel();
		
		List<String> therapiesList = medicalExaminationPanel.getChosenTherapies();
		String diagnosis = medicalExaminationPanel.getDiagnosis();
		
		if(diagnosis != null && !diagnosis.isEmpty()) {
			List<String> suggestedExaminations = Singleton.getInstance().getPrologModule().getPreventiveExaminations(diagnosis,therapiesList);

			medicalExaminationPanel.generatePreventiveExaminations(suggestedExaminations);
		}
		else {
			JOptionPane.showMessageDialog(Singleton.getInstance().getMainFrame(),
				    "You must choose a diagnosis first.",
				    "Warning",
				    JOptionPane.WARNING_MESSAGE);
		}
		

		
		

	}

}

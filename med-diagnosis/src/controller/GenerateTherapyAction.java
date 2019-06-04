package controller;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import utils.Singleton;
import view.MedicalExaminationPanel;

public class GenerateTherapyAction extends AbstractAction {

	public GenerateTherapyAction() {
		putValue(NAME, "Generate therapies");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {		
		MedicalExaminationPanel medicalExaminationPanel = (MedicalExaminationPanel)Singleton.getInstance().getMainFrame().getCentralPanel();
		
		String diagnosis = medicalExaminationPanel.getDiagnosis();
		
		if(diagnosis != null && !diagnosis.isEmpty()) {
			List<String> suggestedTherapiesList = Singleton.getInstance().getPrologModule().getTherapies(diagnosis.trim());
			
			medicalExaminationPanel.generateTherapies(suggestedTherapiesList);
		}
		else {
			JOptionPane.showMessageDialog(Singleton.getInstance().getMainFrame(),
				    "You must choose a diagnosis first.",
				    "Warning",
				    JOptionPane.WARNING_MESSAGE);
		}
		


	}

}

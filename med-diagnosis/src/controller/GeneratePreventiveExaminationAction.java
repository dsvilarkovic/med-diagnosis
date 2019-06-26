package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import cbr.CbrReasoning;
import cbr.CbrReasoning.ModuleType;
import model.MedicalExamination;
import model.PreventiveExamination;
import ucm.gaia.jcolibri.method.retrieve.RetrievalResult;
import utils.Regime;
import utils.Singleton;
import view.MedicalExaminationPanel;

public class GeneratePreventiveExaminationAction extends AbstractAction {

	public GeneratePreventiveExaminationAction() {
		putValue(NAME, "Generate preventive examinations");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {		
		MedicalExaminationPanel medicalExaminationPanel = (MedicalExaminationPanel)Singleton.getInstance().getMainFrame().getCentralPanel();
		
		List<String> suggestedExaminations = null;
		if(Singleton.getInstance().getRegime() == Regime.RULE_BASED) {
			List<String> therapiesList = medicalExaminationPanel.getChosenTherapies();
			String diagnosis = medicalExaminationPanel.getDiagnosis();
			
			if(diagnosis != null && !diagnosis.isEmpty()) {
				suggestedExaminations = Singleton.getInstance().getPrologModule().getPreventiveExaminations(diagnosis, therapiesList);
	
			}
			else {
				JOptionPane.showMessageDialog(Singleton.getInstance().getMainFrame(),
					    "You must choose a diagnosis first.",
					    "Warning",
					    JOptionPane.WARNING_MESSAGE);
			}
		}
		else {
			MedicalExamination medicalExamination = medicalExaminationPanel.getMedicalExaminationInformation();
			CbrReasoning cbr = new CbrReasoning();
			cbr.setModule(ModuleType.PREVENTIVE_EXAMINATIONS);
			Collection<RetrievalResult> result = cbr.get(medicalExamination);
			suggestedExaminations = new ArrayList<>();
			for (RetrievalResult res : result) {
				MedicalExamination resultRecord = (MedicalExamination) res.get_case().getDescription();
				for(PreventiveExamination e: resultRecord.getPreventiveExaminations()) {
					if(!suggestedExaminations.contains(e.getName()))
						suggestedExaminations.add(e.getName());
				}
				
				//System.out.println(res.get_case().getDescription() + " -> " + res.getEval() + "\n");
			}
		}

		medicalExaminationPanel.generatePreventiveExaminations(suggestedExaminations);		

	}

}

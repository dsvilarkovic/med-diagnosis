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
import model.Therapy;
import ucm.gaia.jcolibri.method.retrieve.RetrievalResult;
import utils.Regime;
import utils.Singleton;
import view.MedicalExaminationPanel;

public class GenerateTherapyAction extends AbstractAction {

	public GenerateTherapyAction() {
		putValue(NAME, "Generate therapies");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {		
		MedicalExaminationPanel medicalExaminationPanel = (MedicalExaminationPanel)Singleton.getInstance().getMainFrame().getCentralPanel();
		List<String> suggestedTherapiesList = null;
		if(Singleton.getInstance().getRegime() == Regime.RULE_BASED) {
			String diagnosis = medicalExaminationPanel.getDiagnosis();
			
			if(diagnosis != null && !diagnosis.isEmpty()) {
				//TODO: dodati alergije
				suggestedTherapiesList = Singleton.getInstance().getPrologModule().getTherapies(diagnosis.trim(), new ArrayList<String>());
			}
			else {
				JOptionPane.showMessageDialog(Singleton.getInstance().getMainFrame(),
					    "You must choose a diagnosis first.",
					    "Warning",
					    JOptionPane.WARNING_MESSAGE);
				return;
			}
		}else {
			MedicalExamination medicalExamination = medicalExaminationPanel.getMedicalExaminationInformation();
			CbrReasoning cbr = new CbrReasoning();
			cbr.setModule(ModuleType.THERAPIES);
			Collection<RetrievalResult> result = cbr.get(medicalExamination);
			suggestedTherapiesList = new ArrayList<>();
			for (RetrievalResult res : result) {
				MedicalExamination resultRecord = (MedicalExamination) res.get_case().getDescription();
				for(Therapy t: resultRecord.getTherapies()) {
					if(!suggestedTherapiesList.contains(t.getName()))
						suggestedTherapiesList.add(t.getName());
				}
				
				System.out.println(res.get_case().getDescription() + " -> " + res.getEval() + "\n");
			}
		}
		
		medicalExaminationPanel.generateTherapies(suggestedTherapiesList);
	

	}

}

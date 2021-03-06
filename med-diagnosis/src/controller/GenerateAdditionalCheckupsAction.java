package controller;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;

import cbr.CbrReasoning;
import cbr.CbrReasoning.ModuleType;
import fuzzyLogic.AdditionalCheckupsFuzzyLogic;
import model.AdditionalExaminationResult;
import model.MedicalExamination;
import ucm.gaia.jcolibri.method.retrieve.RetrievalResult;
import utils.Regime;
import utils.Singleton;
import view.MedicalExaminationPanel;

public class GenerateAdditionalCheckupsAction extends AbstractAction {

	public GenerateAdditionalCheckupsAction() {
		putValue(NAME, "Generate Additional Checkups");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		MedicalExaminationPanel medicalExaminationPanel  = (MedicalExaminationPanel) Singleton.getInstance().getMainFrame().getCentralPanel();
		Map<String,String> map = null;
		if(Singleton.getInstance().getRegime() == Regime.RULE_BASED) {
			Singleton.getInstance().getFuzzyModule().setValues(medicalExaminationPanel.getSymptomsMap());
			Singleton.getInstance().getFuzzyModule().setValues(medicalExaminationPanel.getPhysicalExaminationSymptomsMap());
			map = Singleton.getInstance().getFuzzyModule().getAdditionalCheckupsMap();
			 
		}else {
			//TODO: dodati poziv funkcije kada se doda case-based
			MedicalExamination medicalExamination = medicalExaminationPanel.getMedicalExaminationInformation();
			CbrReasoning cbr = new CbrReasoning();
			cbr.setModule(ModuleType.ADDITIONAL_EXAMINATIONS);
			Collection<RetrievalResult> result = cbr.get(medicalExamination);
			map = new HashMap<>();
			for (RetrievalResult res : result) {
				MedicalExamination resultRecord = (MedicalExamination) res.get_case().getDescription();
				double eval = res.getEval();
				for(AdditionalExaminationResult r : resultRecord.getAdditionalExaminationResults()) {
					if(!map.containsKey(r.getName())) {
						String temp = r.getName();
						System.out.println(temp);
						if(temp.contains("_not_good")) {
							temp = temp.substring(0, temp.length()-9);
						}else {
							temp = temp.substring(0, temp.length()-5);
						}
						
						map.put(temp, "");
					
					}
						
				}
				//System.out.println(res.get_case().getDescription() + " -> " + res.getEval() + "\n");
			}
		}
		
		
		medicalExaminationPanel.generateAdditionalCheckups(map);
		
	}

}

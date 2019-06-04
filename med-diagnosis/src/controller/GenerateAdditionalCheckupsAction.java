package controller;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.AbstractAction;

import fuzzyLogic.AdditionalCheckupsFuzzyLogic;
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
			AdditionalCheckupsFuzzyLogic additionalCheckupsFuzzyLogic = new AdditionalCheckupsFuzzyLogic("data/fuzzy_symp_additional_checkups.fcl");
			additionalCheckupsFuzzyLogic.setValues(medicalExaminationPanel.getSymptomsMap());
			additionalCheckupsFuzzyLogic.setValues(medicalExaminationPanel.getPhysicalExaminationSymptomsMap());
			map = additionalCheckupsFuzzyLogic.getAdditionalCheckupsMap();
		}else {
			//TODO: dodati poziv funkcije kada se doda case-based
		}
		
		medicalExaminationPanel.generateAdditionalCheckups(map);
	}

}

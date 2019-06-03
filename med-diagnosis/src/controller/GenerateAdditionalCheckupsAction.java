package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import fuzzyLogic.AdditionalCheckupsFuzzyLogic;
import utils.Singleton;
import view.MedicalExaminationPanel;

public class GenerateAdditionalCheckupsAction extends AbstractAction {

	public GenerateAdditionalCheckupsAction() {
		putValue(NAME, "Generate Additional Checkups");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		MedicalExaminationPanel medicalExaminationPanel  = (MedicalExaminationPanel) Singleton.getInstance().getMainFrame().getCentralPanel();
		AdditionalCheckupsFuzzyLogic additionalCheckupsFuzzyLogic = new AdditionalCheckupsFuzzyLogic("data/fuzzy_symp_additional_checkups.fcl");
		additionalCheckupsFuzzyLogic.setValues(medicalExaminationPanel.getSymptomsMap());
		additionalCheckupsFuzzyLogic.setValues(medicalExaminationPanel.getPhysicalExaminationSymptomsMap());
		medicalExaminationPanel.generateAdditionalCheckups(additionalCheckupsFuzzyLogic.getAdditionalCheckupsMap());
	}

}

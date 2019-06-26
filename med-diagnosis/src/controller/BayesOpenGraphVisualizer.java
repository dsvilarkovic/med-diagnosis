package controller;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import bayesian_network.module.BayesNetModule;
import model.MedicalRecord;
import utils.Singleton;
import view.DiagnosisPanel;
import view.MedicalExaminationPanel;

public class BayesOpenGraphVisualizer extends AbstractAction {

	public BayesOpenGraphVisualizer() {
		putValue(NAME, "Show Visual Explanation");
		putValue(SMALL_ICON, new ImageIcon("data/analysis.png"));
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MedicalExaminationPanel examinationPanel =(MedicalExaminationPanel) Singleton.getInstance().getMainFrame().getCentralPanel();
		
		List<String> physicalExaminations = examinationPanel.getPhysicalExaminationSymptomsList();
		List<String> anamnesisSymptoms = examinationPanel.getSymptomsList();
		
		BayesNetModule bayesNetModule = Singleton.getInstance().getBayesNetModule();
		
		physicalExaminations.addAll(anamnesisSymptoms);
		bayesNetModule.visualizeBayesianNetwork(physicalExaminations);
		
	}

}

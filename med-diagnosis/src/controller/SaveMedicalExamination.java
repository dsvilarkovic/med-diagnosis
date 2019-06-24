package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import cbr.connector.RdfConnector;
import utils.Singleton;
import view.MedicalExaminationPanel;
import view.SearchRecordPanel;

public class SaveMedicalExamination extends AbstractAction {
	
	public SaveMedicalExamination() {
		putValue(NAME, "Save examination");
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		MedicalExaminationPanel medicalExaminationPanel  = (MedicalExaminationPanel) Singleton.getInstance().getMainFrame().getCentralPanel();
		RdfConnector connector = new RdfConnector();
		connector.retainCase(medicalExaminationPanel.getMedicalExaminationInformation());
		JOptionPane.showMessageDialog(medicalExaminationPanel, "Medical examination is saved", "Information", JOptionPane.PLAIN_MESSAGE);
		Singleton.getInstance().getMainFrame().setCentralPanel(new SearchRecordPanel());
	}

}

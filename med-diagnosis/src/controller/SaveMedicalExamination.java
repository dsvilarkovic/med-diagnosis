package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import cbr.connector.RdfConnector;
import model.MedicalExamination;
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
		MedicalExamination exam = medicalExaminationPanel.getMedicalExaminationInformation();
		System.out.println(exam.getDisease());
		if(exam.getDisease() == null) {
			JOptionPane.showMessageDialog(medicalExaminationPanel, "Diagnosis of the disease is mandatory", "Informatio", JOptionPane.ERROR_MESSAGE);
			return;
		}
		exam.setId(connector.getAllMedicalExaminationsByMedicalRecordId(exam.getMedicalRecord().getId()).size());
		connector.retainCase(exam);
		JOptionPane.showMessageDialog(medicalExaminationPanel, "Medical examination is saved", "Information", JOptionPane.INFORMATION_MESSAGE);
		Singleton.getInstance().getMainFrame().setCentralPanel(new SearchRecordPanel());
	}

}

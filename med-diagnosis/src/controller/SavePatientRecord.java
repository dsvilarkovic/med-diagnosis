package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import cbr.connector.RdfConnector;
import model.MedicalRecord;
import utils.Singleton;
import view.NewMedicalRecord;
import view.SearchRecordPanel;

public class SavePatientRecord extends AbstractAction {
	
	private MedicalRecord medicalRecord;
	
	public SavePatientRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
		putValue(NAME, "Save record");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		NewMedicalRecord newMedicalRecord  = (NewMedicalRecord) Singleton.getInstance().getMainFrame().getCentralPanel();
		RdfConnector connector = new RdfConnector();
		System.out.println(newMedicalRecord.getMedicalRecord());
		int num = connector.getAllMedicalRecords().size() + 1;
		newMedicalRecord.getMedicalRecord().setId(num);
		if(newMedicalRecord.isEdit())
			connector.updateMedicalRecord(newMedicalRecord.getMedicalRecord());
		else
			connector.createMedicalRecord(num, newMedicalRecord.getMedicalRecord());
		JOptionPane.showMessageDialog(newMedicalRecord, "Medical record is saved", "Information", JOptionPane.PLAIN_MESSAGE);
		Singleton.getInstance().getMainFrame().setCentralPanel(new SearchRecordPanel());
	}
}

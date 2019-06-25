package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import model.MedicalRecord;
import utils.Singleton;
import view.NewMedicalRecord;

public class EditPatientRecordAction extends AbstractAction {

	private MedicalRecord record;
	
	public EditPatientRecordAction(MedicalRecord r) {
		this.record = r;
		putValue(NAME, "Edit record");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Singleton.getInstance().getMainFrame().setCentralPanel(new NewMedicalRecord(record));

	}

}

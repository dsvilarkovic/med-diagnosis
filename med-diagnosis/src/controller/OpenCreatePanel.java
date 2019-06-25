package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import utils.Singleton;
import view.NewMedicalRecord;
import view.SearchRecordPanel;

public class OpenCreatePanel extends AbstractAction {
	public OpenCreatePanel() {
		putValue(NAME, "Patient record");
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Singleton.getInstance().getMainFrame().setCentralPanel(new NewMedicalRecord());

	}

}

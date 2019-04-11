package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import utils.Singleton;
import view.PatientInformationPanel;
import view.PatientRecordPanel;

public class SearchPatientRecordAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// .... Pretraga datog pacijenta na osnovu kojeg ce biti ispisani podaci
		Singleton.getInstance().getMainFrame().setCentralPanel(new PatientInformationPanel());

	}

}

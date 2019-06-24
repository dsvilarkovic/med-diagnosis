package view;

import java.util.HashSet;

import javax.swing.JPanel;

import model.Allergy;
import model.MedicalRecord;

public class NewMedicalRecord extends JPanel {
	private MedicalRecord newRecord;
	
	public NewMedicalRecord() {
		this.newRecord.setAllergies(new HashSet<Allergy>());
	}
}

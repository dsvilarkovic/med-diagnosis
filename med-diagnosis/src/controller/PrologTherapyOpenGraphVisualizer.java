package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Allergy;
import prolog.PrologTherapyGraphVizualizer;
import utils.Singleton;
import view.MedicalExaminationPanel;

public class PrologTherapyOpenGraphVisualizer extends AbstractAction {

	public PrologTherapyOpenGraphVisualizer() {
		putValue(NAME, "Show Visual Explanation");
		putValue(SMALL_ICON, new ImageIcon("data/analysis.png"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MedicalExaminationPanel medicalExaminationPanel = (MedicalExaminationPanel)Singleton.getInstance().getMainFrame().getCentralPanel();
		
		
		Set<Allergy> allergies = medicalExaminationPanel.getMedicalExaminationInformation().getMedicalRecord().getAllergies();
		List<String> allergiesList = new ArrayList<String>(); //lista alergija na osnovu koje se predlazu  terapije 
		for(Allergy a : allergies) {
			allergiesList.add(a.getName());
		}		
		//bolest na osnovu koje su predlozene terapije
		String disease = medicalExaminationPanel.getDiagnosis();		
		//predlozene terapije
		List<String> therapiesList = medicalExaminationPanel.getSuggestedTherapies();
		
		if(!therapiesList.isEmpty()) {
			PrologTherapyGraphVizualizer.drawPrologTherapies(disease, allergiesList, therapiesList);
		}
		else {
			JOptionPane.showMessageDialog(Singleton.getInstance().getMainFrame(),
				    "You must generate therapies first.",
				    "Warning",
				    JOptionPane.WARNING_MESSAGE);
		}
	}

}

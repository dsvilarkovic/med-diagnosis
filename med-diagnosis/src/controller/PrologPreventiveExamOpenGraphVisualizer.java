package controller;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import prolog.PrologPreventiveExamsGraphVisualizer;
import utils.Singleton;
import view.MedicalExaminationPanel;

public class PrologPreventiveExamOpenGraphVisualizer extends AbstractAction {

	public PrologPreventiveExamOpenGraphVisualizer() {
		putValue(NAME, "Show Visual Explanation");
		putValue(SMALL_ICON, new ImageIcon("data/analysis.png"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MedicalExaminationPanel medicalExaminationPanel = (MedicalExaminationPanel)Singleton.getInstance().getMainFrame().getCentralPanel();
		
		//bolest na osnovu koje se predlazu preventivni pregledi
		String disease = medicalExaminationPanel.getDiagnosis();
		//lista terapija na osnovu kojih se predlazu preventivni pregledi
		List<String> chosenTherapies = medicalExaminationPanel.getChosenTherapies();
		
		//izgenerisani preventivni pregledi
		List<String> preventiveList = medicalExaminationPanel.getChosenPreventive();
		
		if(!preventiveList.isEmpty()) {
			//TODO: vizualizacija
			PrologPreventiveExamsGraphVisualizer.drawPrologExams(disease, chosenTherapies, preventiveList);
		}
		else {
			JOptionPane.showMessageDialog(Singleton.getInstance().getMainFrame(),
				    "You must generate preventive examinations first.",
				    "Warning",
				    JOptionPane.WARNING_MESSAGE);
		}
	}

}

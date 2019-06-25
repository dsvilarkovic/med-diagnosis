package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

public class PrologPreventiveExamOpenGraphVisualizer extends AbstractAction {

	public PrologPreventiveExamOpenGraphVisualizer() {
		putValue(NAME, "Show Visual Explanation");
		putValue(SMALL_ICON, new ImageIcon("data/analysis.png"));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//TODO: Dusan vizualizacija
	}

}

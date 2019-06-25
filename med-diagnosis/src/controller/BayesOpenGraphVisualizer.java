package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

public class BayesOpenGraphVisualizer extends AbstractAction {

	public BayesOpenGraphVisualizer() {
		putValue(NAME, "Show Visual Explanation");
		putValue(SMALL_ICON, new ImageIcon("data/analysis.png"));
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}

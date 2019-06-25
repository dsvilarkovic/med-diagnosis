package controller;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import fuzzyLogic.AdditionalCheckupsFuzzyLogic;
import utils.Singleton;

public class FuzzyOpenGraphVisualizer extends AbstractAction {

	public FuzzyOpenGraphVisualizer() {
		putValue(NAME, "Show Visual Explanation");
		putValue(SMALL_ICON, new ImageIcon("data/analysis.png"));
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Map<String,Map<String,String>> map = Singleton.getInstance().getFuzzyModule().getData();
		
		//TODO: Dusan vizualizacija

	}

}

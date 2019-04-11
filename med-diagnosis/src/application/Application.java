package application;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPTermParser;
import com.ugos.jiprolog.engine.JIPVariable;

import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import prologproba.PrologProba;
import utils.Singleton;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
			SwingUtilities.updateComponentTreeUI(new JFrame());
			UIManager.put("Synthetica.tabbedPane.keepOpacity", true);
			Singleton.getInstance().getMainFrame().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PrologProba pp = new PrologProba();
		pp.prologTestFunction();

	}

}

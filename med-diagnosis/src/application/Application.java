package application;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel;
import utils.Regime;
import utils.Singleton;
import view.RegimePanel;

public class Application {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
			SwingUtilities.updateComponentTreeUI(new JFrame());
			UIManager.put("Synthetica.tabbedPane.keepOpacity", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RegimePanel loadRegime = new RegimePanel();

		Object[] options = { "Ok", "Cancel" };

		int startSelection = JOptionPane.showOptionDialog(null, loadRegime, "Welcome", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		if (startSelection == JOptionPane.YES_OPTION) {
			Singleton.getInstance().setRegime((loadRegime.getRuleBasedButton().isSelected()) ? Regime.RULE_BASED : Regime.CASE_BASED);
			Singleton.getInstance().getMainFrame().setVisible(true);
		} else {
			System.exit(0);
		}
	}

}

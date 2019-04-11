package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {
	
	public MenuBar() {
		JMenu menuFile = new JMenu("File");

		// TODO 0: Akcije dodati za svako od polja
		JMenu menuNew = new JMenu("New");
		JMenuItem menuNewRecordItem = new JMenuItem("Patient record");
		JMenuItem menuItemSave = new JMenuItem("Save");
		JMenuItem menuItemExit = new JMenuItem("Exit");
		JMenu menuHelp = new JMenu("Help");
		JMenuItem menuItemHA = new JMenuItem("About ");
		
		
		menuNew.add(menuNewRecordItem);
		menuFile.add(menuNew);
		menuFile.add(menuItemSave);
		menuFile.add(menuItemExit);
		menuHelp.add(menuItemHA);

		add(menuFile);
		add(menuHelp);
	}
}

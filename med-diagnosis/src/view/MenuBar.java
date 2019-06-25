package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.OpenCreatePanel;
import utils.Singleton;

public class MenuBar extends JMenuBar {
	
	public MenuBar() {
		JMenu menuFile = new JMenu("File");

		// TODO 0: Akcije dodati za svako od polja
		JMenu menuNew = new JMenu("New");
		JMenu searchMenu = new JMenu("Search");
		JMenuItem menuNewRecordItem = new JMenuItem(new OpenCreatePanel());
		JMenuItem menuItemExit = new JMenuItem("Exit");
		JMenu menuHelp = new JMenu("Help");
		JMenuItem menuItemHA = new JMenuItem("About ");
		JMenuItem menuPatient = new JMenuItem("Patient records");
		menuPatient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Singleton.getInstance().getMainFrame().setCentralPanel(new SearchRecordPanel());
				
			}
		});
		
		
		menuNew.add(menuNewRecordItem);
		searchMenu.add(menuPatient);
		menuFile.add(menuNew);
		menuFile.add(menuItemExit);
		menuHelp.add(menuItemHA);

		add(menuFile);
		add(searchMenu);
		add(menuHelp);
	}
}

package utils;
import com.ugos.jiprolog.engine.JIPEngine;

import view.MainFrame;

public class Singleton {
	private static Singleton instance = new Singleton();
	private MainFrame mainFrame;
	
	private Singleton() {
		mainFrame = new MainFrame();
	}
	
	public static Singleton getInstance() {
		if(instance == null) {
			instance = new Singleton();
		}
		
		return instance;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
}

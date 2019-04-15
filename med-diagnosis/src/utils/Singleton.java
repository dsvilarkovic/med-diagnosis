package utils;
import com.ugos.jiprolog.engine.JIPEngine;

import view.MainFrame;

public class Singleton {
	private static Singleton instance = new Singleton();
	private MainFrame mainFrame;
	private JIPEngine engine;
	
	private Singleton() {
		System.out.println("NA koji fazon");
		engine = new JIPEngine();
		mainFrame = new MainFrame();
	}
	
	public static Singleton getInstance() {
		if(instance == null) {
			return new Singleton();
		}
		
		return instance;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public JIPEngine getEngine() {
		return engine;
	}
	
}

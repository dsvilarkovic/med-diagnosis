package utils;
import view.MainFrame;

public class Singleton {
	private static Singleton instance = null;
	private MainFrame mainFrame;
	
	private Singleton() {
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
	
	
}

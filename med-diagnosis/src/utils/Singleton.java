package utils;
import view.MainFrame;

public class Singleton {
	private static Singleton instance = new Singleton();
	private Regime regime;
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

	public Regime getRegime() {
		return regime;
	}

	public void setRegime(Regime regime) {
		this.regime = regime;
	}
	

	
}

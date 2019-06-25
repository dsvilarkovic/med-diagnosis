package utils;
import bayesian_network.module.BayesNetModule;
import fuzzyLogic.AdditionalCheckupsFuzzyLogic;
import prolog.PrologModule;
import view.MainFrame;

public class Singleton {
	private static Singleton instance = new Singleton();
	private Regime regime;
	private MainFrame mainFrame;
	private BayesNetModule bayesNetModule;
	private PrologModule prologModule;
	private AdditionalCheckupsFuzzyLogic fuzzyModule;

	private Singleton() {
		mainFrame = new MainFrame();
		bayesNetModule = new BayesNetModule();
		bayesNetModule.initBayes("");
		prologModule = new PrologModule();
		fuzzyModule = new AdditionalCheckupsFuzzyLogic("data/fuzzy_symp_additional_checkups.fcl");
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

	public BayesNetModule getBayesNetModule() {
		
		return bayesNetModule;
	}

	public void setBayesNetModule(BayesNetModule bayesNetModule) {
		this.bayesNetModule = bayesNetModule;
	}


	public PrologModule getPrologModule() {
		return prologModule;
	}


	public void setPrologModule(PrologModule prologModule) {
		this.prologModule = prologModule;
	}


	public AdditionalCheckupsFuzzyLogic getFuzzyModule() {
		return fuzzyModule;
	}


	public void setFuzzyModule(AdditionalCheckupsFuzzyLogic fuzzyModule) {
		this.fuzzyModule = fuzzyModule;
	}
	

	
}

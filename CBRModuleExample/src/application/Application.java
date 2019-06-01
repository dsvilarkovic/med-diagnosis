/**
 * 
 */
package application;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.MedicalExamination;
import model.MedicalRecord;
import model.Symptom;
import similarity.ListSimilarity;
import ucm.gaia.jcolibri.casebase.LinealCaseBase;
import ucm.gaia.jcolibri.cbraplications.StandardCBRApplication;
import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CBRCaseBase;
import ucm.gaia.jcolibri.cbrcore.CBRQuery;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.connector.DataBaseConnector;
import ucm.gaia.jcolibri.exception.ExecutionException;
import ucm.gaia.jcolibri.method.retrieve.RetrievalResult;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import ucm.gaia.jcolibri.method.retrieve.selection.SelectCases;
import ucm.gaia.jcolibri.util.FileIO;

/**
 * @author Marijana Kolosnjaji
 *
 */
public class Application implements StandardCBRApplication{
	
	public enum ModuleType {ADDITIONAL_EXAMINATIONS, DIAGNOSIS, THERAPIES, PREVENTIVE_EXAMINATION}
	
	private Connector connector;  //connector object
	private static CBRCaseBase caseBase;  //case base object
	
	private static SessionFactory factory; 
	

	NNConfig simConfig;  //k-NN configuration
	
	private static Application instance = null;
	
	private ModuleType module = ModuleType.DIAGNOSIS;
	
	public static Application getInstance() {
		
		if(instance == null) {
			instance = new Application();
		}
		
		return instance;
	}
	
	private Application() {
		
	}
	
	public void configure() throws ExecutionException {
		
		connector = new DataBaseConnector();
		
		connector.initFromXMLfile(FileIO.findFile("data/databaseconfig.xml"));
		
		caseBase = new LinealCaseBase();
		
		simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average()); // global similarity function = average

		simConfig.addMapping(new Attribute("anamnesisSymptoms", MedicalExamination.class), new ListSimilarity());
		simConfig.addMapping(new Attribute("physicalExaminationSymptoms", MedicalExamination.class), new ListSimilarity());
		
		Attribute medicalRecordAttribute = new Attribute("medicalRecord",   MedicalExamination.class);
		simConfig.setWeight(medicalRecordAttribute, 0.2);
		
		simConfig.addMapping(medicalRecordAttribute, new Average());
		simConfig.addMapping(new Attribute("yearOfBirth", MedicalRecord.class), new Interval(10));
		simConfig.addMapping(new Attribute("female", MedicalRecord.class), new Equal());
		
	}
	
	/*
	 * Executes the CBR cycle
	 */
	public void cycle(CBRQuery query) throws ExecutionException {
		Collection<RetrievalResult> result = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), query, simConfig);
		
		result = SelectCases.selectTopKRR(result, 3);
		
		
		System.out.println("Result:");
		for(RetrievalResult res : result) {
			System.out.println(res.get_case().getDescription() + " -> " + res.getEval() + "\n");
		}
		
	}
	
	public void postCycle() throws ExecutionException {
		
		connector.close();
	
	}
	
	/* 
	 * Initializing the CBR application, loading the case base
	 */
	public CBRCaseBase preCycle() throws ExecutionException {
		caseBase.init(connector);		
		
		/*
		java.util.Collection<CBRCase> cases = caseBase.getCases();
		
		System.out.println("CASE BASE:");
		for(CBRCase cas : cases) {
			System.out.println(cas.getDescription()+ "\n");
		}
		*/
		
		return caseBase;
	
	}
	
	public static void main(String[] args) {		
		StandardCBRApplication cbrApplication = getInstance();
		
		//ovo sluzi samo da izvucem simptome iz baze da napravim query
		relationalDatabaseSetup();
		
		try {
			//configure the application
			cbrApplication.configure();

			//execute the precycle
			cbrApplication.preCycle();

			//create the query
			CBRQuery query = new CBRQuery();
			MedicalExamination caseDescription = new MedicalExamination();
			
			Set<Symptom> simptomi= new HashSet<Symptom>();
			Set<Symptom> fizikalniPregledi= new HashSet<Symptom>();
			
			simptomi.add(getAllSymptoms(14));
			simptomi.add(getAllSymptoms(13));
			
			caseDescription.setAnamnesisSymptoms(simptomi);
			caseDescription.setPhysicalExaminationSymptoms(fizikalniPregledi);
			caseDescription.setMedicalRecord(new MedicalRecord("12312241", "NekoIme", "NekoPrezime", 1996, true));
			
			query.setDescription(caseDescription);
			
			System.out.println("Query:");
			System.out.println(caseDescription + "\n");

			//call the cycle
			cbrApplication.cycle(query);

			//execute the postcycle
			cbrApplication.postCycle();
		} 
		catch (Exception e) {		
			e.printStackTrace();
		}
		
	}
	
	public static void relationalDatabaseSetup() {
		try {
			Configuration conf = new Configuration();
			conf.configure("hibernate.cfg.xml");
			
	        factory = conf.buildSessionFactory();

		} catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
	        throw new ExceptionInInitializerError(ex); 
	    }
		
	}
	
	public static Symptom getAllSymptoms(int num) {
		Session session = factory.openSession();
		  
		Transaction tx = null;
		  
		try {
			tx = session.beginTransaction();
		    Symptom symptom = (Symptom)session.get(Symptom.class, num); 
		      
		    tx.commit();
		      
		    return symptom;
		  
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
		     session.close(); 
		}
			   
		return null;
	}

	public ModuleType getModule() {
		return module;
	}

	public void setModule(ModuleType module) {
		this.module = module;
	}

}

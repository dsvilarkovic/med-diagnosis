/**
 * 
 */
package application;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


import model.AdditionalExaminationResult;
import model.Disease;
import model.MedicalExamination;
import model.MedicalRecord;
import model.PhysicalExaminationResult;
import model.Symptom;
import model.Therapy;
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
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import ucm.gaia.jcolibri.method.retrieve.selection.SelectCases;
import ucm.gaia.jcolibri.util.FileIO;

/**
 * @author Marijana Kolosnjaji
 *
 */
public class Application implements StandardCBRApplication{
	
	public enum ModuleType {ADDITIONAL_EXAMINATIONS, DIAGNOSIS, THERAPIES, PREVENTIVE_EXAMINATIONS}
	
	private Connector connector;  //connector object
	private static CBRCaseBase caseBase;  //case base object
	

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
		
		//godina i pol se koriste u svakom modulu
		Attribute medicalRecordAttribute = new Attribute("medicalRecord",   MedicalExamination.class);
		simConfig.setWeight(medicalRecordAttribute, 0.2);
		
		simConfig.addMapping(medicalRecordAttribute, new Average());
		simConfig.addMapping(new Attribute("yearOfBirth", MedicalRecord.class), new Interval(10));
		simConfig.addMapping(new Attribute("female", MedicalRecord.class), new Equal());
		
		if(module == ModuleType.ADDITIONAL_EXAMINATIONS) {
			simConfig.addMapping(new Attribute("symptoms", MedicalExamination.class), new ListSimilarity());
			simConfig.addMapping(new Attribute("physicalExaminationResults", MedicalExamination.class), new ListSimilarity());
			
		}
		else if(module == ModuleType.DIAGNOSIS) {
			simConfig.addMapping(new Attribute("symptoms", MedicalExamination.class), new ListSimilarity());
			simConfig.addMapping(new Attribute("physicalExaminationResults", MedicalExamination.class), new ListSimilarity());
			simConfig.addMapping(new Attribute("additionalExaminationResults", MedicalExamination.class), new ListSimilarity());
		
		}
		else if(module == ModuleType.THERAPIES) {						
			simConfig.addMapping(new Attribute("disease", MedicalExamination.class), new Average());
			simConfig.addMapping(new Attribute("name", Disease.class), new Equal());

		}
		else { //ModuleType.PREVENTIVE_EXAMINATIONS
			simConfig.addMapping(new Attribute("symptoms", MedicalExamination.class), new ListSimilarity());
			simConfig.addMapping(new Attribute("physicalExaminationResults", MedicalExamination.class), new ListSimilarity());
			simConfig.addMapping(new Attribute("additionalExaminationResults", MedicalExamination.class), new ListSimilarity());
			
			simConfig.addMapping(new Attribute("disease", MedicalExamination.class), new Average());
			simConfig.addMapping(new Attribute("name", Disease.class), new Equal());
		}
		
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
				
		try {
			//configure the application
			cbrApplication.configure();

			//execute the precycle
			cbrApplication.preCycle();

			//create the query
			CBRQuery query = new CBRQuery();
			MedicalExamination caseDescription = new MedicalExamination();
			
			Set<Symptom> simptomi= new HashSet<Symptom>();
			Set<PhysicalExaminationResult> fizikalniPregledi= new HashSet<PhysicalExaminationResult>();
			Set<AdditionalExaminationResult> dodatniPregledi= new HashSet<AdditionalExaminationResult>();
			Set<Therapy> terapije= new HashSet<Therapy>();
//			
//			simptomi.add(getSymptom(14));
//			simptomi.add(getSymptom(13));
			
			caseDescription.setSymptoms(simptomi);
			caseDescription.setPhysicalExaminationResults(fizikalniPregledi);
			caseDescription.setAdditionalExaminationResults(dodatniPregledi);
			caseDescription.setTherapies(terapije);
			
			caseDescription.setMedicalRecord(new MedicalRecord("12312241", "NekoIme", "NekoPrezime", 1996, true));
//			caseDescription.setDisease(getDisease(4));
			
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
	

	

	public ModuleType getModule() {
		return module;
	}

	public void setModule(ModuleType module) {
		this.module = module;
	}

}

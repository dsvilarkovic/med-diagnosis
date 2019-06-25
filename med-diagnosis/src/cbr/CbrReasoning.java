package cbr;

import java.util.Collection;

import cbr.connector.RdfConnector;
import cbr.similarity.ListSimilarity;
import model.Disease;
import model.MedicalExamination;
import model.MedicalRecord;
import ucm.gaia.jcolibri.casebase.LinealCaseBase;
import ucm.gaia.jcolibri.cbraplications.StandardCBRApplication;
import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CBRCaseBase;
import ucm.gaia.jcolibri.cbrcore.CBRQuery;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.exception.ExecutionException;
import ucm.gaia.jcolibri.method.retrieve.RetrievalResult;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import ucm.gaia.jcolibri.method.retrieve.selection.SelectCases;

public class CbrReasoning implements StandardCBRApplication {

	public enum ModuleType {
		ADDITIONAL_EXAMINATIONS, DIAGNOSIS, THERAPIES, PREVENTIVE_EXAMINATIONS
	}
	private Connector connector; // connector object
	private static CBRCaseBase caseBase; // case base object

	private NNConfig simConfig; // k-NN configuration

	private ModuleType module = ModuleType.DIAGNOSIS;
	
	private Collection<RetrievalResult> result;


	@Override
	public void configure() throws ExecutionException {
		// TODO Auto-generated method stub
		connector = new RdfConnector();

		// connector.initFromXMLfile(FileIO.findFile("data/databaseconfig.xml"));

		caseBase = new LinealCaseBase();

		simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average()); // global similarity function = average

		// godina i pol se koriste u svakom modulu
		Attribute medicalRecordAttribute = new Attribute("medicalRecord", MedicalExamination.class);
		simConfig.setWeight(medicalRecordAttribute, 0.2);

		simConfig.addMapping(medicalRecordAttribute, new Average());
		simConfig.addMapping(new Attribute("yearOfBirth", MedicalRecord.class), new Interval(10));
		simConfig.addMapping(new Attribute("female", MedicalRecord.class), new Equal());

		if (module == ModuleType.ADDITIONAL_EXAMINATIONS) {
			simConfig.addMapping(new Attribute("symptoms", MedicalExamination.class), new ListSimilarity());
			simConfig.addMapping(new Attribute("physicalExaminationResults", MedicalExamination.class),
					new ListSimilarity());

		} else if (module == ModuleType.DIAGNOSIS) {
			simConfig.addMapping(new Attribute("symptoms", MedicalExamination.class), new ListSimilarity());
			simConfig.addMapping(new Attribute("physicalExaminationResults", MedicalExamination.class),
					new ListSimilarity());
			simConfig.addMapping(new Attribute("additionalExaminationResults", MedicalExamination.class),
					new ListSimilarity());

		} else if (module == ModuleType.THERAPIES) {
			simConfig.addMapping(new Attribute("disease", MedicalExamination.class), new Average());
			simConfig.addMapping(new Attribute("name", Disease.class), new Equal());

		} else { // ModuleType.PREVENTIVE_EXAMINATIONS
			simConfig.addMapping(new Attribute("therapies", MedicalExamination.class), new ListSimilarity());

			simConfig.addMapping(new Attribute("disease", MedicalExamination.class), new Average());
			simConfig.addMapping(new Attribute("name", Disease.class), new Equal());
		}

	}

	/*
	 * Executes the CBR cycle
	 */
	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		result = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), query, simConfig);
		// broj rezultata
		result = SelectCases.selectTopKRR(result, 5);

	}
	
	

	@Override
	public void postCycle() throws ExecutionException {

		connector.close();

	}

	/*
	 * Initializing the CBR application, loading the case base
	 */
	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		caseBase.init(connector);

		/*
		 * java.util.Collection<CBRCase> cases = caseBase.getCases();
		 * 
		 * System.out.println("CASE BASE:"); for(CBRCase cas : cases) {
		 * System.out.println(cas.getDescription()+ "\n"); }
		 */

		return caseBase;

	}
	
	public Collection<RetrievalResult> get(MedicalExamination caseDescription ) {
		try {
			//configure the application
			configure();

			//execute the precycle
			preCycle();

			//create the query
			CBRQuery query = new CBRQuery();
			query.setDescription(caseDescription);
			System.out.println("Query:");
			System.out.println(caseDescription + "\n");

			//call the cycle
			cycle(query);

			//execute the postcycle
			postCycle();
			return result;
		} 
		catch (Exception e) {		
			e.printStackTrace();
			return null;
		}
	}
	


	public ModuleType getModule() {
		return module;
	}

	public void setModule(ModuleType module) {
		this.module = module;
	}


}

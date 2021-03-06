package cbr.connector;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.update.UpdateAction;

import model.AdditionalExaminationResult;
import model.Allergy;
import model.Disease;
import model.MedicalExamination;
import model.MedicalRecord;
import model.PhysicalExaminationResult;
import model.PreventiveExamination;
import model.Therapy;
import model.Symptom;
import ucm.gaia.jcolibri.cbrcore.CBRCase;
import ucm.gaia.jcolibri.cbrcore.CaseBaseFilter;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.exception.InitializingException;

public class RdfConnector implements Connector{
    static final String inputFileName  = "data/rdf_database_diagnosis.ttl";
    static String resourceURI    = "http://www.github.com/dsvilarkovic/med_diag";
    static String rdfURI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

    


	// TODO: novo, ispravljena stara verzija
	  /**
   * Vraca listu svih medicinskih kartona/pacijenata koji postoje u bazi
   * @return
   */
  public static List<MedicalRecord> getAllMedicalRecords(){
  	List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
		Model model = ModelFactory.createDefaultModel();
      
		try {
			InputStream is = new FileInputStream(inputFileName);
			RDFDataMgr.read(model, is, Lang.TURTLE);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String queryString = String.format("PREFIX   med_diag: <http://www.github.com/dsvilarkovic/med_diag#> PREFIX : <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"select *\r\n" + 
				"where{  \r\n" + 
				"  ?subject med_diag:address ?address.\r\n" + 
				"  ?subject med_diag:allergies ?allergies.\r\n" + 
				"  ?subject med_diag:firstName ?firstName.\r\n" + 
				"  ?subject med_diag:gender ?gender.\r\n" + 
				"  ?subject med_diag:id ?id.\r\n" + 
				"  ?subject med_diag:jmbg ?jmbg.\r\n" + 
				"  ?subject med_diag:lastName ?lastName.\r\n" + 
				"  ?subject med_diag:medicalRecordNumber ?medicalRecordNumber.\r\n" + 
				"  ?subject med_diag:phoneNumber ?phoneNumber.\r\n" + 
				"  ?subject med_diag:yearOfBirth ?yearOfBirth.\r\n" + 
				"}");
		
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet results = qexec.execSelect() ;
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			
			MedicalRecord medicalRecord = new MedicalRecord();
			
			Literal literal = solution.getLiteral("id");
			medicalRecord.setId(Integer.parseInt(literal.toString()));
			literal = solution.getLiteral("medicalRecordNumber");
			medicalRecord.setMedicalRecordNumber(literal.getString());
			
			literal = solution.getLiteral("firstName");
			medicalRecord.setFirstName(literal.getString());
			literal = solution.getLiteral("lastName");
			medicalRecord.setLastName(literal.getString());
			literal = solution.getLiteral("jmbg");
			medicalRecord.setJmbg(literal.getString());
			literal = solution.getLiteral("address");
			medicalRecord.setAddress(literal.getString());
			literal = solution.getLiteral("phoneNumber");
			medicalRecord.setPhoneNumber(literal.getString());
			literal = solution.getLiteral("yearOfBirth");
			medicalRecord.setYearOfBirth(Integer.parseInt(literal.getString()));
			
			literal = solution.getLiteral("gender");
			Boolean isFemale = literal.getString().equals("female") ? true: false;
			medicalRecord.setFemale(isFemale);

			//TODO alergije
			medicalRecord.setAllergies(getAllergies(model, medicalRecord.getId()));
			
			medicalRecords.add(medicalRecord);
			
		}
  	
  	return medicalRecords;
  }

    /**
     * Vraca listu svih pregleda koji postoje za nekog pacijenta po {@code medicalRecordId}
     * @param medicalRecordId - id pacijenta koji postoji u sistemu
     * @return
     */
    public List<MedicalExamination> getAllMedicalExaminationsByMedicalRecordId(Integer medicalRecordId){
    	List<MedicalExamination> medicalExaminations = new ArrayList<MedicalExamination>();
		Model model = ModelFactory.createDefaultModel();
        
		try {
			InputStream is = new FileInputStream(inputFileName);
			RDFDataMgr.read(model, is, Lang.TURTLE);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int n = getPatientCount(model);
		for (int i = 1; i <= n; i++) {
			MedicalExamination caseDescription = new MedicalExamination();
			
			caseDescription.setId(i);
			MedicalRecord medicalRecord = getMedicalRecordByPatientId(i);
			Set<Allergy> allergies = getAllergies(model, i);
			medicalRecord.setAllergies(allergies);
			
			Set<Symptom> simptomi= getSymptoms(model, i);
			Set<PhysicalExaminationResult> fizikalniPregledi= getPhysicalTreatments(model, i);
			Set<AdditionalExaminationResult> dodatniPregledi= getAdditionalProceduresResult(model, i);
			Set<Therapy> terapije= getTherapies(model, i);
			Set<PreventiveExamination> preventivniPregledi = getPreventionTreatments(model, i);
			Disease disease = getDiagnosis(model, i);
			
			caseDescription.setSymptoms(simptomi);
			caseDescription.setPhysicalExaminationResults(fizikalniPregledi);
			caseDescription.setAdditionalExaminationResults(dodatniPregledi);
			caseDescription.setTherapies(terapije);
			caseDescription.setPreventiveExaminations(preventivniPregledi);
			
			//dodati alergije, dijagnozu i sliku
			caseDescription.setMedicalRecord(medicalRecord);
			caseDescription.setDisease(disease);
			
	
			if(caseDescription.getMedicalRecord().getId() == medicalRecordId) {
				medicalExaminations.add(caseDescription);
			}
		
		}	
		
    	
    	return medicalExaminations;
    }
	@Override
	public Collection<CBRCase> retrieveAllCases() {
		LinkedList<CBRCase> cases = new LinkedList<CBRCase>();
		Model model = ModelFactory.createDefaultModel();
        
		try {
			InputStream is = new FileInputStream(inputFileName);
			RDFDataMgr.read(model, is, Lang.TURTLE);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		int n = getPatientCount(model);
		for (int i = 1; i <= n; i++) {
			CBRCase cbrCase = new CBRCase();
			MedicalExamination caseDescription = new MedicalExamination();
			
			MedicalRecord medicalRecord = getMedicalRecordByPatientId(i);
			Set<Allergy> allergies = getAllergies(model, i);
			medicalRecord.setAllergies(allergies);
			
			Set<Symptom> simptomi= getSymptoms(model, i);
			Set<PhysicalExaminationResult> fizikalniPregledi= getPhysicalTreatments(model, i);
			Set<AdditionalExaminationResult> dodatniPregledi= getAdditionalProceduresResult(model, i);
			Set<Therapy> terapije= getTherapies(model, i);
			Set<PreventiveExamination> preventivniPregledi = getPreventionTreatments(model, i);
			Disease disease = getDiagnosis(model, i);
			
			caseDescription.setSymptoms(simptomi);
			caseDescription.setPhysicalExaminationResults(fizikalniPregledi);
			caseDescription.setAdditionalExaminationResults(dodatniPregledi);
			caseDescription.setTherapies(terapije);
			caseDescription.setPreventiveExaminations(preventivniPregledi);
			
			//dodati alergije, dijagnozu i sliku
			caseDescription.setMedicalRecord(medicalRecord);
			caseDescription.setDisease(disease);
			
	
			
			cbrCase.setDescription(caseDescription);
			cases.add(cbrCase);
		
		}
		
		
		
		return cases;
	}
	@Override
	public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter arg0) {
		return null;
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCases(Collection<CBRCase> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initFromXMLfile(URL arg0) throws InitializingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeCases(Collection<CBRCase> arg0) {
		// TODO Auto-generated method stub
		
	}
	
    //TODO : novo i kvalitetno
    
	public static MedicalRecord getMedicalRecord(int medicalRecordId) {

		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("", resourceURI);
		model.setNsPrefix("med_diag", resourceURI + "#");
		model.setNsPrefix("rdf", rdfURI);

        
		try {
			InputStream is = new FileInputStream(inputFileName);
			RDFDataMgr.read(model, is, Lang.TURTLE);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String queryString = String.format("PREFIX   med_diag: <http://www.github.com/dsvilarkovic/med_diag#> PREFIX : <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"select *\r\n" + 
				"where{  \r\n" + 
				"  ?subject med_diag:address ?address.\r\n" + 
				"  ?subject med_diag:allergies ?allergies.\r\n" + 
				"  ?subject med_diag:firstName ?firstName.\r\n" + 
				"  ?subject med_diag:gender ?gender.\r\n" + 
				"  ?subject med_diag:id \"%d\".\r\n" + 
				"  ?subject med_diag:jmbg ?jmbg.\r\n" + 
				"  ?subject med_diag:lastName ?lastName.\r\n" + 
				"  ?subject med_diag:medicalRecordNumber ?medicalRecordNumber.\r\n" + 
				"  ?subject med_diag:phoneNumber ?phoneNumber.\r\n" + 
				"  ?subject med_diag:yearOfBirth ?yearOfBirth.\r\n" + 
				"}", medicalRecordId);
		
		
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet results = qexec.execSelect() ;
		MedicalRecord medicalRecord = new MedicalRecord();
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			medicalRecord.setId(medicalRecordId);
			Literal literal = solution.getLiteral("medicalRecordNumber");
			medicalRecord.setMedicalRecordNumber(literal.getString());
			
			literal = solution.getLiteral("firstName");
			medicalRecord.setFirstName(literal.getString());
			literal = solution.getLiteral("lastName");
			medicalRecord.setLastName(literal.getString());
			literal = solution.getLiteral("jmbg");
			medicalRecord.setJmbg(literal.getString());
			literal = solution.getLiteral("address");
			medicalRecord.setAddress(literal.getString());
			literal = solution.getLiteral("phoneNumber");
			medicalRecord.setPhoneNumber(literal.getString());
			literal = solution.getLiteral("yearOfBirth");
			medicalRecord.setYearOfBirth(Integer.parseInt(literal.getString()));
			
			literal = solution.getLiteral("gender");
			Boolean isFemale = literal.getString().equals("female") ? true: false;
			medicalRecord.setFemale(isFemale);

			//TODO alergije
			medicalRecord.setAllergies(getAllergies(model, medicalRecord.getId()));
			
			
		}	
		return medicalRecord;
	}

	// TODO: ovo je ok 
	private Set<PhysicalExaminationResult> getPhysicalTreatments(Model model, int i) {
		Set<PhysicalExaminationResult> physicalExaminationResults = new HashSet<PhysicalExaminationResult>();
		String patient = "Patient" + i;
		String queryString = String.format("prefix med_diag: <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"prefix : <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"select ?physicalTreatment\r\n" + 
				"WHERE \r\n" + 
				"{\r\n" + 
				"  :%s a med_diag:Patient;\r\n" + 
				"                    med_diag:physical_treatments ?physicalTreatments.\r\n" + 
				"  ?physicalTreatments med_diag:physical_treatment ?physicalTreatment.\r\n" + 
				"}", patient);
		
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet results = qexec.execSelect() ;
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Literal literal = solution.getLiteral("physicalTreatment");
			PhysicalExaminationResult examinationResult = new PhysicalExaminationResult();
			examinationResult.setName(literal.getString());
			physicalExaminationResults.add(examinationResult);
		}		
		
		return physicalExaminationResults;
				
	}
	
	// TODO: ovo je ok
	private Set<Symptom> getSymptoms(Model model, int i) {
		Set<model.Symptom> symptoms = new HashSet<model.Symptom>();
		
		String patient = "Patient" + i;
		String queryString = String.format("prefix med_diag: <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"prefix : <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"select ?symptom\r\n" + 
				"WHERE\r\n" + 
				"{\r\n" + 
				"  :%s a med_diag:Patient;\r\n" + 
				"                    med_diag:symptoms ?symptom_list.\r\n" + 
				"  ?symptom_list med_diag:symptom ?symptom.                        \r\n" + 
				"}", patient);
		
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet results = qexec.execSelect() ;
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Literal literal = solution.getLiteral("symptom");
			model.Symptom symptom = new model.Symptom();
			symptom.setName(literal.getString());
			symptoms.add(symptom);
		}		
		return symptoms;
	}
	
	// TODO: ovo je ok
	private Set<AdditionalExaminationResult> getAdditionalProceduresResult(Model model, int i) {
		Set<AdditionalExaminationResult> additionalExaminationResults =  new HashSet<AdditionalExaminationResult>();
		String patient = "Patient" + i;
		String queryString = String.format("prefix med_diag: <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"prefix : <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"select ?additional_procedures_result\r\n" + 
				"WHERE \r\n" + 
				"{\r\n" + 
				"  :%s a med_diag:Patient;\r\n" + 
				"                    med_diag:additional_procedures_results ?additional_procedures_results.\r\n" + 
				"  ?additional_procedures_results med_diag:additional_procedures_result ?additional_procedures_result;           \r\n" + 
				"}", patient);
		
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet results = qexec.execSelect() ;
		
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Literal literal = solution.getLiteral("additional_procedures_result");
			AdditionalExaminationResult additionalExaminationResult = new AdditionalExaminationResult();
			additionalExaminationResult.setName(literal.getString());
			
			additionalExaminationResults.add(additionalExaminationResult);
		}		


		return additionalExaminationResults;				
	}
	
	
	// TODO: ovo je ok
	private Set<Therapy> getTherapies(Model model, int i) {
		Set<Therapy> therapies= new HashSet<Therapy>();
		String patient = "Patient" + i;
		String queryString = String.format("prefix med_diag: <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"prefix : <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"select ?therapy\r\n" + 
				"WHERE \r\n" + 
				"{\r\n" + 
				"  :%s a med_diag:Patient;\r\n" + 
				"                    med_diag:therapies ?therapylist.\r\n" + 
				"  ?therapylist med_diag:therapy ?therapy;           \r\n" + 
				"}", patient);
		
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet results = qexec.execSelect() ;
		
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Literal literal = solution.getLiteral("therapy");
			Therapy therapy = new Therapy();
			
			therapy.setName(literal.getString());
			therapies.add(therapy);		
		}		
		return therapies;
	}
	
	// TODO: ovo je ok
	private Disease getDiagnosis(Model model, int i) {
		String patient = "Patient" + i;
		String queryString = String.format("\r\n" + 
				"PREFIX   med_diag: <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"PREFIX : <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"Select ?diagnosis\r\n" + 
				"where{\r\n" + 
				"  :%s a med_diag:Patient;\r\n" + 
				"           med_diag:diagnosis ?diagnosis.\r\n" + 
				"}", patient);
		
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet results = qexec.execSelect() ;
		Disease disease = new Disease();
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Literal literal = solution.getLiteral("diagnosis");			
			disease.setName(literal.getString());
		}		
		
		return disease;
	}
	
	
	// TODO: novo i kvalitetno
	/**
	 * Funkcija za vracanje alergija jednog medicinskog karton-a
	 * @param model - rdf model baze
	 * @param medicalRecordId - id medicinskog kartona
	 * @return 
	 */
	public static Set<Allergy> getAllergies(Model model, Integer medicalRecordId){
		Set<Allergy> allergies = new HashSet<Allergy>();
		
		String query = String.format("PREFIX   med_diag: <http://www.github.com/dsvilarkovic/med_diag#> PREFIX : <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"select *\r\n" + 
				"where{  \r\n" + 
				"  med_diag:Allergies%d med_diag:allergy ?allergy.\r\n" + 
				"}", medicalRecordId);
		
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet results = qexec.execSelect() ;

		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Allergy allergy = new Allergy();
			Literal literal = solution.getLiteral("allergy");
			allergy.setName(literal.toString());
			
			allergies.add(allergy);
		}
		
		
		return allergies;		
	}
	
	
	// TODO: ovo je ok
	private Set<PreventiveExamination> getPreventionTreatments(Model model, int i) {
		String patient = "Patient" + i;
		String queryString = String.format("prefix med_diag: <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"prefix : <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"select ?prevention_treatment\r\n" + 
				"WHERE \r\n" + 
				"{\r\n" + 
				"  :%s a med_diag:Patient;\r\n" + 
				"                    med_diag:prevention_treatments ?pt.\r\n" + 
				"  ?pt med_diag:prevention_treatment ?prevention_treatment;           \r\n" + 
				"}", patient);
		
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet results = qexec.execSelect() ;
		Set<PreventiveExamination> preventiveExaminations = new HashSet<PreventiveExamination>();
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Literal literal = solution.getLiteral("prevention_treatment");
			PreventiveExamination preventiveExamination = new PreventiveExamination();
			preventiveExamination.setName(literal.getString());
			preventiveExaminations.add(preventiveExamination);
		}		
		
		return preventiveExaminations;		
	}

	/**
	 * Daje broj pregleda obavljenih
	 * @param model
	 * @return
	 */
	public Integer getPatientCount(Model model) {
		String queryString = "\r\n" + 
				"PREFIX   med_diag: <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"PREFIX : <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"Select (count(?patient) as ?count)\r\n" + 
				"where{\r\n" + 
				"  ?patient a med_diag:Patient;\r\n" + 
				"}";
		
		
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		Literal literal = null;
		ResultSet results = qexec.execSelect() ;
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			literal = solution.getLiteral("count");		
		}		
		return literal.getInt();
	}

	/***
	 * Metoda koja cuva trenutni case koji je unet, moze se koristiti i za rule-based pristup
	 * @param medicalExamination
	 */
	public void retainCase(MedicalExamination medicalExamination) {
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("", resourceURI);
		model.setNsPrefix("med_diag", resourceURI + "#");
		model.setNsPrefix("rdf", rdfURI);

        
		try {
			InputStream is = new FileInputStream(inputFileName);
			RDFDataMgr.read(model, is, Lang.TURTLE);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		List<String> allergyList = new ArrayList<String>();
		for (Allergy allergy : medicalExamination.getMedicalRecord().getAllergies()){
			allergyList.add(allergy.getName());
		}
		List<String> symptomList = new ArrayList<String>();
		for (Symptom symptom : medicalExamination.getSymptoms()){
			symptomList.add(symptom.getName());
		}
		List<String> ptList = new ArrayList<String>();
		for (PreventiveExamination pte : medicalExamination.getPreventiveExaminations()){
			ptList.add(pte.getName());
		}
		List<String> aprList = new ArrayList<String>();
		for (AdditionalExaminationResult aer : medicalExamination.getAdditionalExaminationResults()){
			aprList.add(aer.getName());
		}
		List<String> therapyList = new ArrayList<String>();
		for (Therapy therapy: medicalExamination.getTherapies()){
			therapyList.add(therapy.getName());
		}
		List<String> preventionList = new ArrayList<String>();
		for (PreventiveExamination pe: medicalExamination.getPreventiveExaminations()){
			preventionList.add(pe.getName());
		}

		//OVAJ DEO SAMO AKO PACIJENT PRETHODNO NE POSTOJI U BAZI
		//Napravi resurs allergije
		Integer i = getPatientCount(model) + 1;
		Resource allergyResource = model.createResource(resourceURI + "#Allergies" + i);

		Property a_type = model.createProperty(rdfURI + "type");
		Resource allergyList_type = model.createResource(resourceURI + "#Allergy_list");

		allergyResource.addProperty(a_type, allergyList_type);	
		for (String allergy : allergyList) {
			Property singleAllergyProperty = model.createProperty(resourceURI + "#allergy");
			allergyResource.addProperty(singleAllergyProperty, allergy);
		}


		//Napravi resurs medicinski karton
		//Provera da li postoji medicinski karton nije potrebno vrsiti, RDF to sam resi
		
		MedicalRecord medicalRecord = medicalExamination.getMedicalRecord();
		Resource medicalRecordResource = model.createResource(resourceURI + "#MedicalRecord" + medicalRecord.getId());
		
//		Property medicalRecordIdProperty = model.createProperty(resourceURI + "#id");
//		Property yearOfBirth = model.createProperty(resourceURI + "#yearOfBirth");
//		Property gender = model.createProperty(resourceURI + "#gender");
//		Property medicalRecordNumber = model.createProperty(resourceURI + "#medicalRecordNumber");
//		Property firstName = model.createProperty(resourceURI + "#firstName");
//		Property lastName = model.createProperty(resourceURI + "#lastName");
//		Property jmbg = model.createProperty(resourceURI + "#jmbg");
//		Property address = model.createProperty(resourceURI + "#address");
//		Property phoneNumber = model.createProperty(resourceURI + "#phoneNumber");
//		Property allergies = model.createProperty(resourceURI + "#allergies");
		
		
//		MedicalRecord medicalRecord = medicalExamination.getMedicalRecord();
//		
//		
//		medicalRecordResource.addProperty(medicalRecordIdProperty, Integer.toString(medicalExamination.getMedicalRecord().getId()));
//		medicalRecordResource.addProperty(yearOfBirth, Integer.toString(medicalExamination.getMedicalRecord().getYearOfBirth()));
//		medicalRecordResource.addProperty(gender, medicalRecord.isFemale() ? "female" : "male");
//		medicalRecordResource.addProperty(medicalRecordNumber, medicalRecord.getMedicalRecordNumber());
//		medicalRecordResource.addProperty(firstName, medicalRecord.getFirstName());
//		medicalRecordResource.addProperty(lastName, medicalRecord.getLastName());
//		medicalRecordResource.addProperty(jmbg, medicalRecord.getJmbg());
//		medicalRecordResource.addProperty(address, medicalRecord.getAddress());
//		medicalRecordResource.addProperty(phoneNumber, medicalRecord.getPhoneNumber());
//		medicalRecordResource.addProperty(allergies, allergyResource);
		
		//Napravi resurs simptomi
	    Resource symptomListResource = model.createResource(resourceURI + "#Sym" + i);
	    Resource symptomListTypeResource = model.createResource(resourceURI + "#Symptom_list");
	    symptomListResource.addProperty(a_type, symptomListTypeResource);
	    for (String symptom : symptomList) {
			Property symptomProperty = model.createProperty(resourceURI + "#symptom");
			symptomListResource.addProperty(symptomProperty, symptom);
		}
	   
		
		
		//Napravi resurs fizikalni pregledi
	    Resource physicalTreatmentListResource = model.createResource(resourceURI + "#PhysT" + i);
	    Resource physicalTreatmentListTypeResource = model.createResource(resourceURI + "#Physical_treatments");
	    physicalTreatmentListResource.addProperty(a_type, physicalTreatmentListTypeResource);
	    
	    for (String physicalTreatment : ptList) {
			Property ptProperty = model.createProperty(resourceURI + "#physical_treatment");
			physicalTreatmentListResource.addProperty(ptProperty,physicalTreatment);
		}
		
		//Napravi resurs dodatni pregledi
	    Resource aprListResource = model.createResource(resourceURI + "#Apr" + i);
	    Resource aprListTypeResource = model.createResource(resourceURI + "#Additional_procedures_results");
	    aprListResource.addProperty(a_type, aprListTypeResource);
		
	    for (String aprString : aprList) {
	    	Property aprProperty = model.createProperty(resourceURI + "#additional_procedures_result");
			aprListResource.addProperty(aprProperty,aprString);
		}
		//Napravi resurs terapije
	    Resource therapyListResource = model.createResource(resourceURI + "#T" + i);
	    Resource therapyListTypeResource = model.createResource(resourceURI + "#Therapies");
	    therapyListResource.addProperty(a_type, therapyListTypeResource);
		
	    for (String therapy : therapyList) {
	    	Property therapyProperty = model.createProperty(resourceURI + "#therapy");
			therapyListResource.addProperty(therapyProperty,therapy);
		}
		//Napravi resurs preventivni pregledi
	    Resource preventionListResource = model.createResource(resourceURI + "#PrevT" + i);
	    Resource preventionListTypeResource = model.createResource(resourceURI + "#Prevention_treatments");
	    therapyListResource.addProperty(a_type, preventionListTypeResource);
		
	    for (String prevention : preventionList) {
	    	Property preventionProperty = model.createProperty(resourceURI + "#prevention_treatment");
			preventionListResource.addProperty(preventionProperty,prevention);
		}
		//Napravi resurs pacijent i spoji ga sa prethodnim resursima
	    Resource patientResource = model.createResource(resourceURI + "#Patient" + i);
	    Resource patientTypeResource = model.createResource(resourceURI + "#Patient");
	    patientResource.addProperty(a_type, patientTypeResource);
	    
	    Property medicalRecordProperty = model.createProperty(resourceURI + "#medicalRecord");
	    Property symptomsProperty = model.createProperty(resourceURI + "#symptoms");
	    Property physicalTreatmentsProperty = model.createProperty(resourceURI + "#physical_treatments");
	    Property aprProperty = model.createProperty(resourceURI + "#additional_procedures_results");
	    Property diagnosisProperty = model.createProperty(resourceURI + "#diagnosis");
	    Property therapiesProperty = model.createProperty(resourceURI + "#therapies");
	    Property preventionTreatmentsProperty = model.createProperty(resourceURI + "#prevention_treatments");
	  

	    //napravi dijagnozu
	    //Resource diagnosisResource = model.createResource(resourceURI + "#parkinsons_disease");
	    
	    patientResource.addProperty(medicalRecordProperty, medicalRecordResource);
	    patientResource.addProperty(symptomsProperty, symptomListResource);
	    patientResource.addProperty(physicalTreatmentsProperty, physicalTreatmentListResource);
	    patientResource.addProperty(aprProperty, aprListResource);
	    patientResource.addProperty(diagnosisProperty, medicalExamination.getDisease().getName());
	    patientResource.addProperty(therapiesProperty, therapyListResource);
	    patientResource.addProperty(preventionTreatmentsProperty, preventionListResource);
	    
		try {
			OutputStream os = new FileOutputStream(inputFileName);
			RDFDataMgr.write(os, model, Lang.TTL);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Brise karton i sve egzaminacije vezane za njega
	 * @param medicalRecordId
	 */
	public void deleteMedicalRecord(Integer medicalRecordId) {
		
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("", resourceURI);
		model.setNsPrefix("med_diag", resourceURI + "#");
		model.setNsPrefix("rdf", rdfURI);

        
		try {
			InputStream is = new FileInputStream(inputFileName);
			RDFDataMgr.read(model, is, Lang.TURTLE);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//obrisi alergije
		String deleteAllergies = String.format("PREFIX   med_diag: <http://www.github.com/dsvilarkovic/med_diag#> " + 
				"PREFIX : <http://www.github.com/dsvilarkovic/med_diag#> " + 
				"delete " + 
				"where{ " + 
				"  med_diag:MedicalRecord%d med_diag:allergies ?object3. " + 
				"  ?object3 ?predicate3 ?object4. " + 
				" " + 
				"}", medicalRecordId);
		
		UpdateAction.parseExecute(deleteAllergies , model);
		//obrisi karton

		String deleteMedicalRecord = String.format("" + 
				"PREFIX   med_diag: <http://www.github.com/dsvilarkovic/med_diag#> " + 
				"PREFIX : <http://www.github.com/dsvilarkovic/med_diag#> " + 
				"delete " + 
				"where{  " + 
				"  med_diag:MedicalRecord%d ?predicate ?object " + 
				"}", medicalRecordId);
		
		UpdateAction.parseExecute(deleteMedicalRecord , model);
		
		//Obrisi sve list od egzaminacije
		String deleteExamLists = String.format("PREFIX   med_diag: <http://www.github.com/dsvilarkovic/med_diag#> " + 
				"PREFIX : <http://www.github.com/dsvilarkovic/med_diag#> " + 
				"delete { ?object ?predicate1 ?object1} " + 
				"where{ " + 
				"  ?subject med_diag:medicalRecord med_diag:MedicalRecord%d. " + 
				"  ?subject ?predicate ?object. " + 
				"  ?object ?predicate1 ?object1. " + 
				"}", medicalRecordId);
		UpdateAction.parseExecute(deleteExamLists, model);
		
		//Obrisi zaglavlje egzaminacija
		String deleteExamHeader = String.format("PREFIX   med_diag: <http://www.github.com/dsvilarkovic/med_diag#> " + 
				"PREFIX : <http://www.github.com/dsvilarkovic/med_diag#> " + 
				"delete  {?subject ?predicate ?object} " + 
				"where{ " + 
				"  ?subject med_diag:medicalRecord med_diag:MedicalRecord%d." + 
				"  ?subject ?predicate ?object. " + 
				"}", medicalRecordId);
		UpdateAction.parseExecute(deleteExamHeader, model);
		
	    
		try {
			OutputStream os = new FileOutputStream(inputFileName);
			RDFDataMgr.write(os, model, Lang.TTL);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//TODO: ovo je ok
	/**
	 * 
	 * @param i - broj pacijenta, mora se dodeliti
	 * @param medicalRecord
	 */
	public void createMedicalRecord(Integer i, MedicalRecord medicalRecord) {
		
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("", resourceURI);
		model.setNsPrefix("med_diag", resourceURI + "#");
		model.setNsPrefix("rdf", rdfURI);

        
		try {
			InputStream is = new FileInputStream(inputFileName);
			RDFDataMgr.read(model, is, Lang.TURTLE);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		List<String> allergyList = new ArrayList<String>();
		for (Allergy allergy : medicalRecord.getAllergies()){
			allergyList.add(allergy.getName());
		}
		
		//Napravi resurs medicinski karton
		Resource medicalRecordResource = model.createResource(resourceURI + "#MedicalRecord" + i);

		
		Resource allergyResource = model.createResource(resourceURI + "#Allergies" + i);
		
		Property a_type = model.createProperty(rdfURI + "type");
		Resource allergyList_type = model.createResource(resourceURI + "#Allergy_list");

		allergyResource.addProperty(a_type, allergyList_type);	
		for (String allergy : allergyList) {
			Property singleAllergyProperty = model.createProperty(resourceURI + "#allergy");
			allergyResource.addProperty(singleAllergyProperty, allergy);
		}
		
		Property medicalRecordIdProperty = model.createProperty(resourceURI + "#id");
		Property yearOfBirth = model.createProperty(resourceURI + "#yearOfBirth");
		Property gender = model.createProperty(resourceURI + "#gender");
		Property medicalRecordNumber = model.createProperty(resourceURI + "#medicalRecordNumber");
		Property firstName = model.createProperty(resourceURI + "#firstName");
		Property lastName = model.createProperty(resourceURI + "#lastName");
		Property jmbg = model.createProperty(resourceURI + "#jmbg");
		Property address = model.createProperty(resourceURI + "#address");
		Property phoneNumber = model.createProperty(resourceURI + "#phoneNumber");
		Property allergies = model.createProperty(resourceURI + "#allergies");
		
		
		medicalRecordResource.addProperty(medicalRecordIdProperty, Integer.toString(medicalRecord.getId()));
		medicalRecordResource.addProperty(yearOfBirth, Integer.toString(medicalRecord.getYearOfBirth()));
		medicalRecordResource.addProperty(gender, medicalRecord.isFemale() ? "female" : "male");
		medicalRecordResource.addProperty(medicalRecordNumber, medicalRecord.getMedicalRecordNumber());
		medicalRecordResource.addProperty(firstName, medicalRecord.getFirstName());
		medicalRecordResource.addProperty(lastName, medicalRecord.getLastName());
		medicalRecordResource.addProperty(jmbg, medicalRecord.getJmbg());
		medicalRecordResource.addProperty(address, medicalRecord.getAddress());
		medicalRecordResource.addProperty(phoneNumber, medicalRecord.getPhoneNumber());
		medicalRecordResource.addProperty(allergies, allergyResource);
		
		
	    
		try {
			OutputStream os = new FileOutputStream(inputFileName);
			RDFDataMgr.write(os, model, Lang.TTL);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Za updateovanje medicinskog kartona
	 * @param medicalRecordId
	 * @param medicalRecord
	 */
	
	//TODO novo i kvalitetno
	public static void updateMedicalRecord(MedicalRecord medicalRecord) {
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("", resourceURI);
		model.setNsPrefix("med_diag", resourceURI + "#");
		model.setNsPrefix("rdf", rdfURI);

        
		try {
			InputStream is = new FileInputStream(inputFileName);
			RDFDataMgr.read(model, is, Lang.TURTLE);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Integer i = medicalRecord.getId();
		
		
		//prvo obrisi sve vezano za karton
		String deleteString = String.format("PREFIX   med_diag: <http://www.github.com/dsvilarkovic/med_diag#> PREFIX : <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"delete\r\n" + 
				"where{  \r\n" + 
				"  ?subject med_diag:address ?address.\r\n" + 
				"  ?subject med_diag:allergies ?allergies.\r\n" + 
				"  ?subject med_diag:firstName ?firstName.\r\n" + 
				"  ?subject med_diag:gender ?gender.\r\n" + 
				"  ?subject med_diag:id \"%d\".\r\n" + 
				"  ?subject med_diag:jmbg ?jmbg.\r\n" + 
				"  ?subject med_diag:lastName ?lastName.\r\n" + 
				"  ?subject med_diag:medicalRecordNumber ?medicalRecordNumber.\r\n" + 
				"  ?subject med_diag:phoneNumber ?phoneNumber.\r\n" + 
				"  ?subject med_diag:yearOfBirth ?yearOfBirth.\r\n" + 
				"  ?allergies ?predicate ?a.\r\n" + 
				"}\r\n" + 
				"", i);
		
		UpdateAction.parseExecute(deleteString, model);
		
		
		UpdateAction.parseExecute(deleteString, model);
		
		try {
			OutputStream os = new FileOutputStream(inputFileName);
			RDFDataMgr.write(os, model, Lang.TTL);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<String> allergyList = new ArrayList<String>();
		for (Allergy allergy : medicalRecord.getAllergies()){
			allergyList.add(allergy.getName());
		}
		
		//Napravi resurs medicinski karton
		Resource medicalRecordResource = model.createResource(resourceURI + "#MedicalRecord" + i);

		
		Resource allergyResource = model.createResource(resourceURI + "#Allergies" + i);
		
		Property a_type = model.createProperty(rdfURI + "type");
		Resource allergyList_type = model.createResource(resourceURI + "#Allergy_list");

		allergyResource.addProperty(a_type, allergyList_type);	
		for (String allergy : allergyList) {
			Property singleAllergyProperty = model.createProperty(resourceURI + "#allergy");
			allergyResource.addProperty(singleAllergyProperty, allergy);
		}
		
		Property medicalRecordIdProperty = model.createProperty(resourceURI + "#id");
		Property yearOfBirth = model.createProperty(resourceURI + "#yearOfBirth");
		Property gender = model.createProperty(resourceURI + "#gender");
		Property medicalRecordNumber = model.createProperty(resourceURI + "#medicalRecordNumber");
		Property firstName = model.createProperty(resourceURI + "#firstName");
		Property lastName = model.createProperty(resourceURI + "#lastName");
		Property jmbg = model.createProperty(resourceURI + "#jmbg");
		Property address = model.createProperty(resourceURI + "#address");
		Property phoneNumber = model.createProperty(resourceURI + "#phoneNumber");
		Property allergies = model.createProperty(resourceURI + "#allergies");
		
		
		medicalRecordResource.addProperty(medicalRecordIdProperty, Integer.toString(medicalRecord.getId()));
		medicalRecordResource.addProperty(yearOfBirth, Integer.toString(medicalRecord.getYearOfBirth()));
		medicalRecordResource.addProperty(gender, medicalRecord.isFemale() ? "female" : "male");
		medicalRecordResource.addProperty(medicalRecordNumber, medicalRecord.getMedicalRecordNumber());
		medicalRecordResource.addProperty(firstName, medicalRecord.getFirstName());
		medicalRecordResource.addProperty(lastName, medicalRecord.getLastName());
		medicalRecordResource.addProperty(jmbg, medicalRecord.getJmbg());
		medicalRecordResource.addProperty(address, medicalRecord.getAddress());
		medicalRecordResource.addProperty(phoneNumber, medicalRecord.getPhoneNumber());
		medicalRecordResource.addProperty(allergies, allergyResource);
		
		
	    
		try {
			OutputStream os = new FileOutputStream(inputFileName);
			RDFDataMgr.write(os, model, Lang.TTL);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public MedicalRecord getMedicalRecordByPatientId(Integer patientId) {
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("", resourceURI);
		model.setNsPrefix("med_diag", resourceURI + "#");
		model.setNsPrefix("rdf", rdfURI);

        
		try {
			InputStream is = new FileInputStream(inputFileName);
			RDFDataMgr.read(model, is, Lang.TURTLE);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MedicalRecord medicalRecord = null;
		
		String queryString = String.format("PREFIX   med_diag: <http://www.github.com/dsvilarkovic/med_diag#> PREFIX : <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"select *\r\n" + 
				"where{  \r\n" + 
				"  med_diag:Patient%d med_diag:medicalRecord ?object.\r\n" + 
				"  ?object med_diag:id ?id.\r\n" + 
				"}\r\n" + 
				"", patientId);
		
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet results = qexec.execSelect() ;
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			
			Literal literal = solution.getLiteral("id");
			medicalRecord = getMedicalRecord(Integer.parseInt(literal.toString()));
		}
		return medicalRecord;		
		
	}
}

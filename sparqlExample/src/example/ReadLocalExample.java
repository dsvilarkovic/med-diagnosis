package example;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
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
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import model.Allergy;
import model.MedicalRecord;

public class ReadLocalExample {
	
    static final String inputFileName  = "data/rdf_database_diagnosis.ttl";
    
	public static void main(String[] args) {
		Model model = ModelFactory.createDefaultModel();
        
		try {
			InputStream is = new FileInputStream(inputFileName);
			RDFDataMgr.read(model, is, Lang.TURTLE);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		List<MedicalRecord> medicalRecords = getAllMedicalRecords();
		for (MedicalRecord medicalRecord : medicalRecords) {
			System.err.println(medicalRecord);
		}
		
	}
	
	private static void getTherapies(Model model, int i) {
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
		List<String> therapyList = new ArrayList<String>();
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Literal literal = solution.getLiteral("therapy");
			therapyList.add(literal.toString());		
		}		
		System.out.println(therapyList);		
	}

	private static void getDiagnosis(Model model, int i) {
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
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Literal literal = solution.getLiteral("diagnosis");
			System.out.println(literal.toString());
		}		
	}

	private static void getPreventionTreatments(Model model, int i) {
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
		List<String> preventionTreatmentsList = new ArrayList<String>();
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Literal literal = solution.getLiteral("prevention_treatment");
			preventionTreatmentsList.add(literal.toString());		
		}		
		System.out.println(preventionTreatmentsList);
		
		
	}


	private static void getAdditionalProceduresResult(Model model, int i) {
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
		List<String> additionalProceduresList = new ArrayList<String>();
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Literal literal = solution.getLiteral("additional_procedures_result");
			additionalProceduresList.add(literal.toString());		
		}		
		System.out.println(additionalProceduresList);
				
	}

	private static void getPhysicalTreatments(Model model, int i) {
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
		List<String> physicalTreatmentList = new ArrayList<String>();
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Literal literal = solution.getLiteral("physicalTreatment");
			physicalTreatmentList.add(literal.toString());		
		}		
		System.out.println(physicalTreatmentList);
				
	}

	private static void getSymptoms(Model model, int i) {
		// TODO Auto-generated method stub
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
		List<String> symptomList = new ArrayList<String>();
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Literal literal = solution.getLiteral("symptom");
			symptomList.add(literal.toString());		
		}		
		System.out.println(symptomList);
	}

	
	// TODO: novo
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

    
    //TODO : novo
    
	private MedicalRecord getMedicalRecord(Model model, int medicalRecordId) {

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
	
	
	
	
}

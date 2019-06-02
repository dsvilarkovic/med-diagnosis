package example;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
        
		for (int i = 1; i <= 2; i++) {
			System.out.println("Pacijent==========================================");
			System.out.println("Osnovne informacije:==========================================");
			getMedicalRecord(model, i);		
			System.out.println("Alergije:==========================================");
			getAllergies(model, i);		
			System.out.println("Simptomi===========================================");
			getSymptoms(model, i);	
			
			System.out.println("Fizikalni pregledi");
			getPhysicalTreatments(model, i);
			
			System.out.println("Dodatni pregledi");
			getAdditionalProceduresResult(model, i);
			
			System.out.println("Terapije");
			getTherapies(model, i);
			
			System.out.println("Preventivni pregledi");
			getPreventionTreatments(model, i);
			
			System.out.println("Dijagnoza");
			getDiagnosis(model, i);
			
			System.out.println("Kraj pacijentskog pregleda=======================================");
			
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

	private static void getAllergies(Model model, int i) {
		String patient = "Patient" + i;
		String queryString = String.format("prefix med_diag: <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"prefix : <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"select ?allergy\r\n" + 
				"WHERE \r\n" + 
				"{\r\n" + 
				"  :%s a med_diag:Patient;\r\n" + 
				"                    med_diag:medicalRecord ?medicalRecord.\r\n" + 
				"  ?medicalRecord med_diag:allergies ?allergy_list.\r\n" + 
				"  ?allergy_list med_diag:allergy ?allergy.\r\n" + 
				"}", patient);
		
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet results = qexec.execSelect() ;
		List<String> allergyList = new ArrayList<String>();
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Literal literal = solution.getLiteral("allergy");
			allergyList.add(literal.toString());		
		}		
		System.out.println(allergyList);
				
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

	private static void getMedicalRecord(Model model, int i) {
		String patient = "Patient" + i;
		String queryString = String.format("prefix med_diag: <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"prefix : <http://www.github.com/dsvilarkovic/med_diag#>\r\n" + 
				"select ?patientId ?medicalRecordNumber ?firstName ?lastName ?jmbg ?address ?phoneNumber ?yearOfBirth\r\n" + 
				"WHERE\r\n" + 
				"{\r\n" + 
				"  :%s a med_diag:Patient;\r\n" + 
				"                    med_diag:medicalRecord ?medicalRecord.\r\n" + 
				"  ?medicalRecord med_diag:id ?patientId;\r\n" + 
				"                 med_diag:yearOfBirth ?yearOfBirth;  \r\n" + 
				"                 med_diag:gender ?gender;\r\n" + 
				"                 med_diag:medicalRecordNumber ?medicalRecordNumber;\r\n" + 
				"                 med_diag:firstName ?firstName;\r\n" + 
				"                 med_diag:lastName ?lastName;\r\n" + 
				"                 med_diag:jmbg ?jmbg;\r\n" + 
				"                 med_diag:address ?address;\r\n" + 
				"                 med_diag:phoneNumber ?phoneNumber;\r\n" + 
				"                        \r\n" + 
				"}", patient);
		
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet results = qexec.execSelect() ;
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Literal literal = solution.getLiteral("patientId");
			System.out.println(literal.getString());
			literal = solution.getLiteral("medicalRecordNumber");
			System.out.println(literal.getString());
			literal = solution.getLiteral("firstName");
			System.out.println(literal.getString());
			literal = solution.getLiteral("lastName");
			System.out.println(literal.getString());
			literal = solution.getLiteral("jmbg");
			System.out.println(literal.getString());
			literal = solution.getLiteral("address");
			System.out.println(literal.getString());
			literal = solution.getLiteral("phoneNumber");
			System.out.println(literal.getString());
			literal = solution.getLiteral("yearOfBirth");
			System.out.println(literal.getString());
			
		}		
	}



	
}

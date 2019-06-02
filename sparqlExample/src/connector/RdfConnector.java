package connector;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
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

import model.AdditionalExaminationResult;
import model.MedicalExamination;
import model.MedicalRecord;
import model.PhysicalExaminationResult;
import model.Therapy;
import model.Symptom;
import ucm.gaia.jcolibri.cbrcore.CBRCase;
import ucm.gaia.jcolibri.cbrcore.CaseBaseFilter;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.exception.InitializingException;

public class RdfConnector implements Connector{
    static final String inputFileName  = "data/rdf_database_diagnosis.ttl";

	@Override
	public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter arg0) {
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
			
			MedicalRecord medicalRecord = getMedicalRecord(model, i);
			Set<Symptom> simptomi= getSymptoms(model, i);
			Set<PhysicalExaminationResult> fizikalniPregledi= getPhysicalTreatments(model, i);
			Set<AdditionalExaminationResult> dodatniPregledi= getAdditionalProceduresResult(model, i);
			Set<Therapy> terapije= getTherapies(model, i);
			
			caseDescription.setSymptoms(simptomi);
			caseDescription.setPhysicalExaminationResults(fizikalniPregledi);

			caseDescription.setAdditionalExaminationResults(dodatniPregledi);
			caseDescription.setTherapies(terapije);
			
			//dodati alergije, dijagnozu i sliku
			caseDescription.setMedicalRecord(medicalRecord);
			
			
			cbrCase.setDescription(caseDescription);
			cases.add(cbrCase);
		
		}
		
		
		
		return cases;
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
	public Collection<CBRCase> retrieveAllCases() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void storeCases(Collection<CBRCase> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private MedicalRecord getMedicalRecord(Model model, int i) {
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
		MedicalRecord medicalRecord = new MedicalRecord();
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			Literal literal = solution.getLiteral("patientId");
			medicalRecord.setId(Integer.parseInt(literal.getString()));
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

			
		}	
		return medicalRecord;
	}

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
		}		


		return additionalExaminationResults;				
	}
	
	
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
			literal = solution.getLiteral("count");;		
		}		
		System.out.println(literal);
		return Integer.parseInt(literal.toString());
	}

}

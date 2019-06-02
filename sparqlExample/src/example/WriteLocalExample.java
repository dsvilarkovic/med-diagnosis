package example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

public class WriteLocalExample {
    static final String inputFileName  = "data/rdf_database_diagnosis.ttl";
    static String resourceURI    = "http://www.github.com/dsvilarkovic/med_diag";
    static String rdfURI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

    
	public static void main(String[] args) {
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
		
		
		
		List<String> allergyList = new ArrayList<String>(Arrays.asList("nsaid", "antibiotic"));
		List<String> symptomList = new ArrayList<String>(Arrays.asList("problems_with_movement", "abnormal_involuntary_movements", "disturbance_of_memory"));
		List<String> ptList = new ArrayList<String>(Arrays.asList("leg_weakness", "focal_weakness"));
		List<String> aprList = new ArrayList<String>(Arrays.asList("leg_weakness", "focal_weakness"));
		List<String> therapyList = new ArrayList<String>(Arrays.asList("vicodine", "tylenol"));
		List<String> preventionList = new ArrayList<String>(Arrays.asList("Take a walk"));
		

		//OVAJ DEO SAMO AKO PACIJENT PRETHODNO NE POSTOJI U BAZI
		//Napravi resurs allergije
		Integer i = 3;
		Resource allergyResource = model.createResource(resourceURI + "#Allergies" + i);

		Property a_type = model.createProperty(rdfURI + "type");
		Resource allergyList_type = model.createResource(resourceURI + "#Allergy_list");

		allergyResource.addProperty(a_type, allergyList_type);	
		for (String allergy : allergyList) {
			Property singleAllergyProperty = model.createProperty(resourceURI + "#allergy");
			allergyResource.addProperty(singleAllergyProperty, allergy);
		}


		//Napravi resurs medicinski karton
		Resource medicalRecordResource = model.createResource(resourceURI + "#MedicalRecord" + i);
		
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
		
		medicalRecordResource.addProperty(medicalRecordIdProperty, i.toString());
		medicalRecordResource.addProperty(yearOfBirth, "2001");
		medicalRecordResource.addProperty(gender, "female");
		medicalRecordResource.addProperty(medicalRecordNumber, "39131901");
		medicalRecordResource.addProperty(firstName, "Slll");
		medicalRecordResource.addProperty(lastName, "DIADOIic");
		medicalRecordResource.addProperty(jmbg, "0101200193919");
		medicalRecordResource.addProperty(address, "Vuka mandusica 33");
		medicalRecordResource.addProperty(phoneNumber, "3913-0931");
		medicalRecordResource.addProperty(allergies, allergyResource);
		
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
	    patientResource.addProperty(diagnosisProperty, "parkinsons_disease");
	    patientResource.addProperty(therapiesProperty, therapyListResource);
	    patientResource.addProperty(preventionTreatmentsProperty, preventionListResource);
	    
	    
		try {
			OutputStream os = new FileOutputStream("data/output.ttl");
			RDFDataMgr.write(os, model, Lang.TTL);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

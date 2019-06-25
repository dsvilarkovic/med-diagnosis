package example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.update.UpdateAction;

import model.Allergy;
import model.MedicalRecord;

public class WriteLocalExample {
    static final String inputFileName  = "data/rdf_database_diagnosis.ttl";
    static String resourceURI    = "http://www.github.com/dsvilarkovic/med_diag";
    static String rdfURI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

    
	public static void main(String[] args) {
		Boolean regularCreate = false;
		
		
		Integer id = 1;
		
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setAddress("Staaara adresa");
		medicalRecord.setFemale(true);
		medicalRecord.setFirstName("ImeIme");
		medicalRecord.setLastName("Prezime");
		medicalRecord.setId(id);
		medicalRecord.setJmbg("901909019313");
		medicalRecord.setMedicalRecordNumber("10103-013");
		medicalRecord.setPhoneNumber("903190139031-0");
		medicalRecord.setYearOfBirth(2020);
		
		List<String> allergyList = new ArrayList<String>(Arrays.asList("nsaid", "neko", "neka_bira"));
		Set<Allergy> allergiesSet = new HashSet<Allergy>();
		
		for (String allergy : allergyList) {
			Allergy allergy2 = new Allergy();
			allergy2.setId(0);
			allergy2.setName(allergy);
			
			allergiesSet.add(allergy2);
		}
		
		medicalRecord.setAllergies(allergiesSet);
		
		updateMedicalRecord(id, medicalRecord);
		
		
		if(regularCreate) {
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
			
			
			
			allergyList = new ArrayList<String>(Arrays.asList("nsaid", "antibiotic"));
			List<String> symptomList = new ArrayList<String>(Arrays.asList("problems_with_movement", "abnormal_involuntary_movements", "disturbance_of_memory"));
			List<String> ptList = new ArrayList<String>(Arrays.asList("leg_weakness", "focal_weakness"));
			List<String> aprList = new ArrayList<String>(Arrays.asList("leg_weakness", "focal_weakness"));
			List<String> therapyList = new ArrayList<String>(Arrays.asList("vicodine", "tylenol"));
			List<String> preventionList = new ArrayList<String>(Arrays.asList("Take a walk"));
			
	
			//OVAJ DEO SAMO AKO PACIJENT PRETHODNO NE POSTOJI U BAZI
			//Napravi resurs allergije
			Integer i = 4;
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
		    
			
	//		deleteMedicalRecord(3, model);
		    
			try {
				OutputStream os = new FileOutputStream(inputFileName);
				RDFDataMgr.write(os, model, Lang.TTL);
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Proslo");
	}
	
	
	/**
	 * Brise karton i sve egzaminacije vezane za njega
	 * @param medicalRecordId
	 */
	public static void deleteMedicalRecord(Integer medicalRecordId) {
		
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
	
	
	public static void createMedicalRecord(Integer i, MedicalRecord medicalRecord) {
		
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
	public static void updateMedicalRecord(Integer medicalRecordId, MedicalRecord medicalRecord) {
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
		
		
		Integer i = medicalRecordId;
		
		
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
				"  ?allergies med_diag:allergy ?a.\r\n" + 
				"}", medicalRecordId);
		
		UpdateAction.parseExecute(deleteString, model);
		
		
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
}

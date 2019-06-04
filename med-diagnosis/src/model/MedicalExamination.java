package model;

import java.util.HashSet;
import java.util.Set;

import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

public class MedicalExamination implements CaseComponent {

	private int id;
	private MedicalRecord medicalRecord;		
	private Set<Symptom> symptoms;
	private Set<PhysicalExaminationResult> physicalExaminationResults;
	private Set<AdditionalExaminationResult> additionalExaminationResults;	
	private Disease disease;	
	private Set<Therapy> therapies;
	private Set<PreventiveExamination> preventiveExaminations;
	
	public MedicalExamination() {
		symptoms = new HashSet<>();
		physicalExaminationResults =  new HashSet<>();
		additionalExaminationResults =  new HashSet<>();
		disease = new Disease("");
		therapies = new HashSet<>();
		preventiveExaminations = new HashSet<>();
	}
	
	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id",this.getClass());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public Set<Symptom> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(Set<Symptom> symptoms) {
		this.symptoms = symptoms;
	}

	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	public Set<PhysicalExaminationResult> getPhysicalExaminationResults() {
		return physicalExaminationResults;
	}

	public void setPhysicalExaminationResults(Set<PhysicalExaminationResult> physicalExaminationResults) {
		this.physicalExaminationResults = physicalExaminationResults;
	}

	public Set<AdditionalExaminationResult> getAdditionalExaminationResults() {
		return additionalExaminationResults;
	}

	public void setAdditionalExaminationResults(Set<AdditionalExaminationResult> additionalExaminationResults) {
		this.additionalExaminationResults = additionalExaminationResults;
	}

	public Set<PreventiveExamination> getPreventiveExaminations() {
		return preventiveExaminations;
	}

	public void setPreventiveExaminations(Set<PreventiveExamination> preventiveExaminations) {
		this.preventiveExaminations = preventiveExaminations;
	}

	public Set<Therapy> getTherapies() {
		return therapies;
	}

	public void setTherapies(Set<Therapy> therapies) {
		this.therapies = therapies;
	}
	
	@Override
	public String toString() {
		String retVal = medicalRecord.toString() + "\n";
		if(disease != null) {
			retVal += disease.toString() + "\n";
		}
		retVal += "CaseDescription [id=" + id + "]";
		retVal += " symptoms:";
		for(Symptom s : symptoms) {
			retVal += " " + s.getName() + " |";
		}
		retVal += " phys. examination:";
		for(PhysicalExaminationResult s : physicalExaminationResults) {
			retVal += " " + s.getName() + " |";
		}
		retVal += " add. examination:";
		for(AdditionalExaminationResult s : additionalExaminationResults) {
			retVal += " " + s.getName() + " |";
		}
		
		return retVal;
	}
	
}

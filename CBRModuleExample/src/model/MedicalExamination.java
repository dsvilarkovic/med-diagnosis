package model;

import java.util.Set;

import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

public class MedicalExamination implements CaseComponent {

	private int id;
	private MedicalRecord medicalRecord;
	private Set<Symptom> anamnesisSymptoms;
	private Set<Symptom> physicalExaminationSymptoms;
	private Disease disease;
	
	//dodatni pregledi + njihovi rezultati
	//private Set<Therapy> therapies;

	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id",this.getClass());
	}

	@Override
	public String toString() {
		String retVal = medicalRecord.toString() + "\n";
		if(disease != null) {
			retVal += disease.toString() + "\n";
		}
		retVal += "CaseDescription [id=" + id + "]";
		retVal += " symptoms:";
		for(Symptom s : anamnesisSymptoms) {
			retVal += " " + s.getName() + " |";
		}
		retVal += " phys. examination:";
		for(Symptom s : physicalExaminationSymptoms) {
			retVal += " " + s.getName() + " |";
		}
		
		return retVal;
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

	public Set<Symptom> getAnamnesisSymptoms() {
		return anamnesisSymptoms;
	}

	public void setAnamnesisSymptoms(Set<Symptom> anamnesisSymptoms) {
		this.anamnesisSymptoms = anamnesisSymptoms;
	}

	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	public Set<Symptom> getPhysicalExaminationSymptoms() {
		return physicalExaminationSymptoms;
	}

	public void setPhysicalExaminationSymptoms(Set<Symptom> physicalExaminationSymptoms) {
		this.physicalExaminationSymptoms = physicalExaminationSymptoms;
	}
	
}

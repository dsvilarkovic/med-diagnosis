package model;

import java.util.Set;

import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

public class MedicalRecord implements CaseComponent {
	
	private int id;
	private String medicalRecordNumber;
	private String firstName;
	private String lastName;
	private String jmbg;
	private String address;
	private String phoneNumber;
	private int yearOfBirth;
	private boolean female;
	
	private Set<Allergy> allergies;
	
	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id",this.getClass());
	}
	
	public MedicalRecord(String medicalRecordNumber, String firstName, String lastName, int yearOfBirth, boolean female) {
		super();
		this.medicalRecordNumber = medicalRecordNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.yearOfBirth = yearOfBirth;
		this.female = female;
	}

	public MedicalRecord() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMedicalRecordNumber() {
		return medicalRecordNumber;
	}

	public void setMedicalRecordNumber(String medicalRecordNumber) {
		this.medicalRecordNumber = medicalRecordNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public boolean isFemale() {
		return female;
	}

	public void setFemale(boolean female) {
		this.female = female;
	}

	public Set<Allergy> getAllergies() {
		return allergies;
	}

	public void setAllergies(Set<Allergy> allergies) {
		this.allergies = allergies;
	}

	@Override
	public String toString() {
		return "MedicalRecord [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", yearOfBirth=" + yearOfBirth + ", female=" + female +"]";
	}
}

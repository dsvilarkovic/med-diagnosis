package model;

public class Allergy {
	
	private int id;
	private String name;

	public Allergy() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Allergy [id=" + id + ", name=" + name + "]";
	}

}

package model;

public class Therapy {
	
	private int id;
	private String name;

	public Therapy() {
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
		return "Therapy [id=" + id + ", name=" + name + "]";
	}

}

package model;

import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

public class Disease implements CaseComponent{

	private int id;
	private String name;
	
	public Disease() {
		// TODO Auto-generated constructor stub
	}
	
	public Disease(String name) {
		// TODO Auto-generated constructor stub
		this.name= name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Disease [id=" + id + ", name=" + name + "]";
	}

}

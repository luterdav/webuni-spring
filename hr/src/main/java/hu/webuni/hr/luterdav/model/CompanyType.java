package hu.webuni.hr.luterdav.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CompanyType{
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	

	public CompanyType() {
		super();
	}

	public CompanyType(String name) {
		super();
		this.name = name;
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
}

//public enum CompanyType {
//	
//	BT, KFT, ZRT, NYRT;
//	
//	
//
//}

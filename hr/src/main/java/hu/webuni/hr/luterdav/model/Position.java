package hu.webuni.hr.luterdav.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.lang.Nullable;

@Entity
public class Position {
	
	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String name;
	@Nullable
	private String education;
//	private int minSalary;
	
	@OneToMany(mappedBy = "position")
	private List<Employee> employees;
	
	public Position() {
	}
	
	

	public Position(String name) {
		super();
		this.name = name;
	}



	public Position(String name, String education) {
		super();
		this.name = name;
		this.education = education;
	}

	public Position(long id, String name, String education, List<Employee> employees) {
		super();
		this.id = id;
		this.name = name;
		this.education = education;
		this.employees = employees;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}

	
	
	

}

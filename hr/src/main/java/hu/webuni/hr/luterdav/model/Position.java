package hu.webuni.hr.luterdav.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.lang.Nullable;

@Entity
public class Position {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)
	private String name;
	@Nullable
	private String education;
	private double minSalary;
//	@ManyToMany(mappedBy = "positions")
//	List<Company> companies;
	
	public Position() {
	}

	public Position(long id, String name, String education, double minSalary) {
	super();
	this.id = id;
	this.name = name;
	this.education = education;
	this.minSalary = minSalary;
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
	public double getMinSalary() {
		return minSalary;
	}
	public void setMinSalary(double minSalary) {
		this.minSalary = minSalary;
	}
	
	
	

}

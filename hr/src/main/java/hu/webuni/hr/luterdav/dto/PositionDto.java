package hu.webuni.hr.luterdav.dto;

import java.util.ArrayList;
import java.util.List;

import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.model.Employee;


public class PositionDto {
	
	private long id;
	private String name;
	private String education;
	private double minSalary;
//	List<CompanyDto> companies = new ArrayList<>();
	
	public PositionDto() {
	}

	public PositionDto(long id, String name, String education, double minSalary) {
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

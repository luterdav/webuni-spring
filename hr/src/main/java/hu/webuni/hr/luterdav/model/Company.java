package hu.webuni.hr.luterdav.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int registrationNumber;
	private String name;
	private String address;
	@ElementCollection
	@CollectionTable(name="type")
	private Set<CompanyType> companyType;
	@OneToMany(mappedBy = "company")
	private List<Employee> employees;
//	@ManyToMany
//    @JoinTable(name="company_position")
//	private List<Position> positions;
	
	public Company() {
		
	}

	public Company(long id, int registrationNumber, String name, String address, Set<CompanyType> companyType,
		List<Employee> employees) {
	super();
	this.id = id;
	this.registrationNumber = registrationNumber;
	this.name = name;
	this.address = address;
	this.companyType = companyType;
	this.employees = employees;
}

	public Set<CompanyType> getCompanyType() {
		return companyType;
	}

	public void setCompanyType(Set<CompanyType> companyType) {
		this.companyType = companyType;
	}

	public int getRegistrationNumber() {
		return registrationNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setRegistrationNumber(int registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	public void addEmployee(Employee employee) {
		if(this.employees == null)
			this.employees = new ArrayList<>();
		this.employees.add(employee);
		employee.setCompany(this);
	}
	
	
	

}

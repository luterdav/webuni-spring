package hu.webuni.hr.luterdav.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Employee {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	
	@ManyToOne
	private Position position;
	
	private int salary;
	private LocalDateTime workStarted;
    
	@ManyToOne
    private Company company;
    
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Employee() {
	}
	
	

	public Employee(String name, Position position, int salary, LocalDateTime workStarted) {
		super();
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.workStarted = workStarted;
	}

	public Employee(String name, Position position, int salary, LocalDateTime workStarted, Company company) {
		super();
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.workStarted = workStarted;
		this.company = company;
	}

	public Employee(long id, String name, Position position, int salary, LocalDateTime workStarted,
			Company company) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.workStarted = workStarted;
		this.company = company;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
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

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public LocalDateTime getWorkStarted() {
		return workStarted;
	}
	
	public void setWorkStarted(LocalDateTime workStarted) {
		this.workStarted = workStarted;
	}
	
	public LocalDateTime getWorkStarted(String userInput) {
	    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    LocalDateTime date = LocalDateTime.parse(userInput, dateFormat);
	    return date ;
	}

	public long getWorkTimeInMonths() {
		return ChronoUnit.MONTHS.between(workStarted, LocalDateTime.now());
	}
	

}

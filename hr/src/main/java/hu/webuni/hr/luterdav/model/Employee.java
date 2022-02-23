package hu.webuni.hr.luterdav.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Employee {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	@Column(unique = true)
	private String username;
	private String password;
	
	@ManyToOne
	private Position position;
	
	private int salary;
	private LocalDateTime workStarted;
    
	@ManyToOne
    private Company company;
	
	@ManyToOne
	private Employee manager;
	
    @OneToMany(mappedBy = "manager")
	private List<Employee> employees;
    
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Employee() {
	}


	public Employee(long id, String name, String username, String password, Position position, int salary,
			LocalDateTime workStarted, Company company, Employee manager, List<Employee> employees) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.position = position;
		this.salary = salary;
		this.workStarted = workStarted;
		this.company = company;
		this.manager = manager;
		this.employees = employees;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}
	


	public Employee(String name, String username, String password, Position position, int salary,
			LocalDateTime workStarted) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.position = position;
		this.salary = salary;
		this.workStarted = workStarted;
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

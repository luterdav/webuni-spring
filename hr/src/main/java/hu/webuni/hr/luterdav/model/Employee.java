package hu.webuni.hr.luterdav.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.format.annotation.DateTimeFormat;

public class Employee {

	private long id;
	private String name;
	private String position;
	private int salary;
	private LocalDateTime workStarted;
	
	public Employee() {
	}
	
	
//	public Employee(long id, String name, String position, int salary, String workStarted) {
//		this.id = id;
//		this.name = name;
//		this.position = position;
//		this.salary = salary;
//		this.workStarted = LocalDate.parse(workStarted,DateTimeFormatter.ofPattern( "yyyy-MM-dd" )).atTime(LocalTime.now());
//	}

	public Employee(long id, String name, String position, int salary, LocalDateTime workStarted) {
		this.id = id;
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.workStarted = workStarted;
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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
	
	public LocalDateTime getWorkStarted(String userInput) {
	    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    LocalDateTime date = LocalDateTime.parse(userInput, dateFormat);
	    return date ;
	}

	public long getWorkTimeInMonths() {
		return ChronoUnit.MONTHS.between(workStarted, LocalDateTime.now());
	}

}

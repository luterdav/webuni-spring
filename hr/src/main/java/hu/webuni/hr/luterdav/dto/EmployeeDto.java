package hu.webuni.hr.luterdav.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;

public class EmployeeDto {

	private long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String position;
	@Positive
	private int salary;
	@Past
	private LocalDateTime workStarted;
	
	public EmployeeDto() {
	}

	public EmployeeDto(long id, String name, String position, int salary, LocalDateTime workStarted) {
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

	public void setWorkStarted(LocalDateTime workStarted) {
		this.workStarted = workStarted;
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", title=" + position + ", salary=" + salary + ", workStarted="
				+ workStarted + "]";
	}

	
}

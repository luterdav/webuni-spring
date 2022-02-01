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

import com.fasterxml.jackson.annotation.JsonView;

import hu.webuni.hr.luterdav.dto.View.BaseData;

public class EmployeeDto {

	private long id;
	@NotEmpty
	private String name;
	@JsonView(BaseData.class)
	private PositionDto position;
	@Positive
	@JsonView(BaseData.class)
	private double salary;
	@Past
	private LocalDateTime workStarted;
	
	private CompanyDto company;

	public EmployeeDto() {
	}

	public EmployeeDto(long id, @NotEmpty String name, PositionDto position, @Positive double salary,
			@Past LocalDateTime workStarted, CompanyDto company) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.workStarted = workStarted;
		this.company = company;
	}

	public PositionDto getPosition() {
		return position;
	}

	public void setPosition(PositionDto position) {
		this.position = position;
	}

	public CompanyDto getCompany() {
		return company;
	}
	
	public void setCompany(CompanyDto company) {
		this.company = company;
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

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
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

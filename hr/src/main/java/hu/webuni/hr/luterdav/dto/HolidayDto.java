package hu.webuni.hr.luterdav.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import hu.webuni.hr.luterdav.model.Employee;

public class HolidayDto {
	
	private long id;
	private LocalDateTime startOfHoliday;
	private LocalDateTime endOfHoliday;
	private EmployeeDto holidayCreatedBy;
	private LocalDateTime dateOfHolidayRequested;
	private String status;
	
	public HolidayDto() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getStartOfHoliday() {
		return startOfHoliday;
	}

	public void setStartOfHoliday(LocalDateTime startOfHoliday) {
		this.startOfHoliday = startOfHoliday;
	}

	public LocalDateTime getEndOfHoliday() {
		return endOfHoliday;
	}

	public void setEndOfHoliday(LocalDateTime endOfHoliday) {
		this.endOfHoliday = endOfHoliday;
	}



	public EmployeeDto getHolidayCreatedBy() {
		return holidayCreatedBy;
	}

	public void setHolidayCreatedBy(EmployeeDto holidayCreatedBy) {
		this.holidayCreatedBy = holidayCreatedBy;
	}

	public LocalDateTime getDateOfHolidayRequested() {
		return dateOfHolidayRequested;
	}

	public void setDateOfHolidayRequested(LocalDateTime dateOfHolidayRequested) {
		this.dateOfHolidayRequested = dateOfHolidayRequested;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

}

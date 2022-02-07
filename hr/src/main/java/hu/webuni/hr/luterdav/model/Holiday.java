package hu.webuni.hr.luterdav.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Holiday {

	@Id
	@GeneratedValue
	private long id;
	@Column(name = "start_date")
	private LocalDateTime startOfHoliday;
	@Column(name = "end_date")
	private LocalDateTime endOfHoliday;
	@ManyToOne
	@JoinColumn(name = "created_by")
	private Employee holidayCreatedBy;
	@Column(name = "created_date")
	private LocalDateTime dateOfHolidayRequested;
	private String status;
	
	public Holiday() {
	}

	public Holiday(LocalDateTime startOfHoliday, LocalDateTime endOfHoliday, Employee holidayCreatedBy,
			LocalDateTime dateOfHolidayRequested, String status) {
		super();
		this.startOfHoliday = startOfHoliday;
		this.endOfHoliday = endOfHoliday;
		this.holidayCreatedBy = holidayCreatedBy;
		this.dateOfHolidayRequested = dateOfHolidayRequested;
		this.status = status;
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

	public Employee getHolidayCreatedBy() {
		return holidayCreatedBy;
	}

	public void setHolidayCreatedBy(Employee holidayCreatedBy) {
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

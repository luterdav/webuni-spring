package hu.webuni.hr.luterdav.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.hr.luterdav.model.Employee_;
import hu.webuni.hr.luterdav.model.Holiday;
import hu.webuni.hr.luterdav.model.Holiday_;

public class HolidaySpecifications {
	
	public static Specification<Holiday> hasStatus (String status) {
		return (root, cq, cb) -> cb.equal(root.get(Holiday_.status), status);
	}
	
	public static Specification<Holiday> hasEmployeeName(String employeeName) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Holiday_.holidayCreatedBy).get(Employee_.name)), 
				employeeName.toLowerCase() + "%");
	}
	
	public static Specification<Holiday> hasDateOfHolidayRequested(LocalDateTime dateOfHolidayRequested) {
		LocalDateTime startOfDay = LocalDateTime.of(dateOfHolidayRequested.toLocalDate(), LocalTime.of(0, 0));
		return (root, cq, cb) -> cb.between(root.get(Holiday_.dateOfHolidayRequested), startOfDay.minusDays(1), startOfDay.plusDays(1));
	}

	public static Specification<Holiday> hasRangeOfHoliday(LocalDateTime startOfHoliday, LocalDateTime endOfHoliday) {
		return (root, cq, cb) -> cb.and(cb.greaterThanOrEqualTo(root.get(Holiday_.startOfHoliday), startOfHoliday),
				cb.lessThanOrEqualTo(root.get(Holiday_.startOfHoliday), endOfHoliday));
	}


}

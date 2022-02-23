package hu.webuni.hr.luterdav.service;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.metamodel.SingularAttribute;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.hr.luterdav.model.Company_;
import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.model.Employee_;
import hu.webuni.hr.luterdav.model.Position_;

public class EmployeeSpecifications {

	public static Specification<Employee> hasId(long id) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.id), id);
	}

	public static Specification<Employee> hasName(String name) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.name)), 
				name.toLowerCase() + "%");
	}

	public static Specification<Employee> hasPositionName(String positionName) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.position).get(Position_.name), positionName);
	}

	public static Specification<Employee> hasSalary(int salary) {
		int fivePercentMinus = (int) (salary * 0.95);
		int fivePercentPlus = (int) (salary * 1.05);
		return (root, cq, cb) -> cb.between(root.get(Employee_.salary), 
				fivePercentMinus, fivePercentPlus);
	}

	public static Specification<Employee> hasWorkStarted(LocalDateTime workStarted) {
		LocalDateTime startOfDay = LocalDateTime.of(workStarted.toLocalDate(), LocalTime.MIDNIGHT);
		LocalDateTime endOfDay = LocalDateTime.of(workStarted.toLocalDate(), LocalTime.MAX);
		return (root, cq, cb) -> cb.between(root.get(Employee_.workStarted), startOfDay, endOfDay);
	}
	
	public static Specification<Employee> hasCompanyName(String companyName) {
		return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.company).get(Company_.name)), 
				companyName.toLowerCase() + "%");
	}

}

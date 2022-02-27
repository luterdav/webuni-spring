package hu.webuni.hr.luterdav.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.luterdav.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	
	List<Employee> findByPositionName(String position);
	
	List<Employee> findByNameStartingWithIgnoreCase(String name);
	
	List<Employee> findByWorkStartedBetween(LocalDateTime startDate, LocalDateTime endDate);

	List<Employee> findBySalaryGreaterThan(Double salary);
	
	List<Employee> findBySalaryGreaterThan(Double salary, Pageable pageable);


	
}

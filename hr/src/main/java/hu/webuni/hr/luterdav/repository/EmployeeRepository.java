package hu.webuni.hr.luterdav.repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import hu.webuni.hr.luterdav.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

	List<Employee> findByPositionName(String position);
	
	List<Employee> findByNameStartingWithIgnoreCase(String name);
	
	List<Employee> findByWorkStartedBetween(LocalDateTime startDate, LocalDateTime endDate);

	List<Employee> findBySalaryGreaterThan(Integer salary);
	
	List<Employee> findBySalaryGreaterThan(Integer salary, Pageable pageable);
	
	Optional<Employee> findByUsername(String username);



	
}

package hu.webuni.hr.luterdav.repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.luterdav.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {


	
	List<Employee> findByPositionName(String position);
	
	List<Employee> findByNameStartingWithIgnoreCase(String name);
	
	List<Employee> findByWorkStartedBetween(LocalDateTime startDate, LocalDateTime endDate);

	List<Employee> findBySalaryGreaterThan(Double salary);
	
	List<Employee> findBySalaryGreaterThan(Double salary, Pageable pageable);

//	@Query(value = "SELECT p.name AS positionName, AVG(e.salary) AS averageSalary FROM Employee e JOIN Position p on p.id = e.position_id WHERE e.company_id = ?1 GROUP BY p.name ORDER BY averageSalary DESC", nativeQuery = true)
//	List<Object[]> findAverageEmployeeSalary(Long company_id);

	
}

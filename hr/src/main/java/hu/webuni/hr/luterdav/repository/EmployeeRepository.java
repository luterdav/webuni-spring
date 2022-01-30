package hu.webuni.hr.luterdav.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.luterdav.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {


//	List<Person>findByLastname(String lastname);
//	List<Person>findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
	
	
//	@Query("SELECT DISTINCT p FROM Person p WHERE p.lastName=:lastnameOR o.firstName=firstName")
//	List<Person>findByNames(String lastname, StringfirstName);
	
//	select * from employee where title = 'manager'
	
	List<Employee> findByPosition(String position);
	
	List<Employee> findByNameStartingWithIgnoreCase(String name);
	
	List<Employee> findByWorkStartedBetween(LocalDateTime startDate, LocalDateTime endDate);

	List<Employee> findBySalaryGreaterThan(Integer salary);
}

package hu.webuni.hr.luterdav.service;

import java.util.List;
import java.util.Optional;


import hu.webuni.hr.luterdav.model.Employee;

public interface EmployeeService {
	
	int getPayRaisePercent(Employee employee);
	
	Employee save(Employee employee);
	
	Employee update(Employee employee);
	
	Employee update(Long id, Employee employee);
	
	List<Employee> findAll();
	
	Optional<Employee> findById(long id);
	
	void delete(long id);
	
	void deleteAll();
	
	List<Employee> findEmployeesByExample(Employee example);

}

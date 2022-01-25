package hu.webuni.hr.luterdav.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hu.webuni.hr.luterdav.model.Employee;

@Service
public interface EmployeeService {
	
	public int getPayRaisePercent(Employee employee);
	
	public Employee save(Employee employee);
	
	public List<Employee> findAll();
	
	public Employee findById(long id);
	
	public void delete(long id);

}

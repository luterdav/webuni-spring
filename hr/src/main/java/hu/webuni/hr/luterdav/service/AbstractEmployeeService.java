package hu.webuni.hr.luterdav.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.hr.luterdav.model.Employee;


public abstract class AbstractEmployeeService implements EmployeeService {

	private Map<Long, Employee> employees = new HashMap<>();

//	{
//		employees.put(1L, new Employee(1, "John Adams", "accountant", 200_000, LocalDateTime.of(2010, 10, 10, 10, 10, 10)));
//		employees.put(2L, new Employee(2, "Adam Johns", "sales", 300_000, LocalDateTime.of(2020, 10, 10, 10, 10, 10)));
//	}
	
	@Override
	public Employee save(Employee employee) {
		employees.put(employee.getId(), employee);
		return employee;
	}
	
	@Override
	public List<Employee> findAll(){
		return new ArrayList<>(employees.values());
	}
	
	@Override
	public Employee findById(long id) {
		return employees.get(id);
	}
	
	@Override
	public void delete(long id) {
		employees.remove(id);
	}

}

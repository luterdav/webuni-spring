package hu.webuni.hr.luterdav.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.luterdav.model.Employee;

@Service
public class SalaryService {

	@Autowired
	EmployeeService employeeService;

	public int getNewSalary(Employee employee) {
		return (int) (employee.getSalary() / 100.0 * (100 + employeeService.getPayRaisePercent(employee)));
	}

}

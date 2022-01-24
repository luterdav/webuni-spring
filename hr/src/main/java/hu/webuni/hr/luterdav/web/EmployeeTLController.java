package hu.webuni.hr.luterdav.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hu.webuni.hr.luterdav.model.Employee;

@Controller
public class EmployeeTLController {
	
private List<Employee> allEmployees = new ArrayList<>();
	
	{
		allEmployees.add(new Employee(1, "John Adams", "accountant", 200_000, LocalDateTime.of(2027,10,10,10,10,10)));
		allEmployees.add(new Employee(2, "Adam Johns", "analyst", 300_000, LocalDateTime.of(2020,10,10,10,10,10)));
		allEmployees.add(new Employee(3, "Peter Falk", "manager", 500_000, LocalDateTime.of(2021,10,10,10,10,10)));
	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/employees")
	public String getEmployees(Map<String, Object> model) {
		model.put("employees", allEmployees);
		model.put("newEmployee", new Employee());
		return "employees";
	}
	
//	@GetMapping("/modifyEmployee")
//	public String modfiyEmployee() {
//		return "modifyEmployee";
//	}
	
	@GetMapping("/update/{id}")
	public String showUpdateEmployee(@PathVariable("id") long id, Map<String, Object> model){
		for (Employee employee : allEmployees) {
			if(employee.getId() == id) {
				model.put("employee", employee);
				allEmployees.remove(employee);
				break;
			}
		}
		return "update-employee";
	}	
	
	@PostMapping("/update/{id}")
	public String updateEmployee(Employee employee){
		allEmployees.add(employee);
		Collections.sort(allEmployees, Comparator.comparingLong(Employee::getId));
		return "redirect:/employees";
	}		
	
	@PostMapping("/employees")
	public String addEmployee(Employee employee) {
		allEmployees.add(employee);
		return "redirect:/employees";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable("id") long id) {
		for (Employee employee : allEmployees) {
			if(employee.getId() == id) {
				allEmployees.remove(employee);
				break;
			}
		}
		Collections.sort(allEmployees, Comparator.comparingLong(Employee::getId));
		return "redirect:/employees";
	}

}

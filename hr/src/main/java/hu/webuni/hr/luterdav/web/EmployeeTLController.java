package hu.webuni.hr.luterdav.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hu.webuni.hr.luterdav.model.Employee;

@Controller
public class EmployeeTLController {
	
	private List<Employee> allEmployees = new ArrayList<>();
	
	{
		allEmployees.add(new Employee(1, "John Adams", "accountant", 200_000, LocalDateTime.of(2020,10,10,10,10,10)));
		allEmployees.add(new Employee(2, "Adam Johns", "analyst", 300_000, LocalDateTime.of(2021,10,10,10,10,10)));
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
	
	@PostMapping("/employees")
	public String addEmployee(Employee employee) {
		allEmployees.add(employee);
		return "redirect:employees";
	}

}

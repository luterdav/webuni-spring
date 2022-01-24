package hu.webuni.hr.luterdav.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.luterdav.dto.EmployeeDto;
import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.service.EmployeeService;
import hu.webuni.hr.luterdav.service.SalaryService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;

	private Map<Long, EmployeeDto> employees = new HashMap<>();

//	{
//		employees.put(1L,
//				new EmployeeDto(1, "John Adams", "accountant", 200_000, LocalDateTime.of(2010, 10, 10, 10, 10, 10)));
//		employees.put(2L,
//				new EmployeeDto(2, "Adam Johns", "sales", 300_000, LocalDateTime.of(2020, 10, 10, 10, 10, 10)));
//	}

	/*
	 * http://localhost:8080/api/employees?salary=200000
	 */

	@GetMapping
	public List<EmployeeDto> getEmployees(
			@RequestParam(value = "salary", defaultValue = "0", required = false) int salary) {
		if (salary != 0) {
			List<EmployeeDto> list = new ArrayList<>();
			for (EmployeeDto employeeDto : employees.values()) {
				if (employeeDto.getSalary() > salary) {
					list.add(employeeDto);
				}
			}
			return list;
		}
		return new ArrayList<>(employees.values());
	}
	
//	@GetMapping
//	public List<EmployeeDto> getAll(@RequestParam(required = false) Integer minSalary) {
//
//		if (minSalary != null)
//			return employeeMapper.employeesToDtos(employeeService.findAll()).stream()
//					.filter(e -> e.getSalary() > minSalary).collect(Collectors.toList());
//
//		return employeeMapper.employeesToDtos(employeeService.findAll());
//	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable long id) {
		EmployeeDto employeeDto = employees.get(id);
		if (employeeDto != null) {
			return ResponseEntity.ok(employeeDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// nem működik, employeeService null
	@PostMapping("/raise")
	public int getEmployeeRaise(@RequestBody Employee employee) {
		return employeeService.getPayRaisePercent(employee);
	}

	@PostMapping
	public EmployeeDto addEmployee(@RequestBody EmployeeDto employeeDto) {
		employees.put(employeeDto.getId(), employeeDto);
		return employeeDto;
	}

	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
		if (!employees.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		employeeDto.setId(id);
		employees.put(id, employeeDto);
		return ResponseEntity.ok(employeeDto);
	}

	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable long id) {
		employees.remove(id);
	}
}

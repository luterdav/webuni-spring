package hu.webuni.hr.luterdav.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.hr.luterdav.dto.EmployeeDto;
import hu.webuni.hr.luterdav.mapper.EmployeeMapper;
import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	private EmployeeMapper employeeMapper;

//	private Map<Long, EmployeeDto> employees = new HashMap<>();

	/*
	 * http://localhost:8080/api/employees?salary=200000
	 */

	@GetMapping
	public List<EmployeeDto> getEmployees(
			@RequestParam(value = "salary", defaultValue = "0", required = false) Integer salary) {
		if (salary != null) {
//			List<EmployeeDto> list = new ArrayList<>();
//			for (EmployeeDto employeeDto : employeeMapper.employeesToDtos(employeeService.findAll())) {
//				if (employeeDto.getSalary() > salary) {
//					list.add(employeeDto);
//				}
//			}
//			return list;
			return employeeMapper.employeesToDtos(employeeService.findAll()).stream()
					.filter(e -> e.getSalary() > salary).collect(Collectors.toList());

		}
		return employeeMapper.employeesToDtos(employeeService.findAll());
	}

	@GetMapping("/{id}")
	public EmployeeDto getEmployeeById(@PathVariable long id) {
		Employee employee = employeeService.findById(id);
		if (employee == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return employeeMapper.employeeToDto(employee);
	}

	@PostMapping("/raise")
	public int getEmployeeRaise(@RequestBody Employee employee) {
		return employeeService.getPayRaisePercent(employee);
	}

	@PostMapping
	public EmployeeDto addEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
		Employee employee = employeeService.save(employeeMapper.dtoToEmployee(employeeDto));
		return employeeMapper.employeeToDto(employee);
	}

	@PutMapping("/{id}")
	public EmployeeDto modifyEmployee(@PathVariable long id, @RequestBody @Valid EmployeeDto employeeDto) {
		Employee employee = employeeService.findById(id);
		if (employee == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		employee.setId(id);
		employee = employeeService.save(employeeMapper.dtoToEmployee(employeeDto));
		return employeeMapper.employeeToDto(employee);
	}

	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable long id) {
		employeeService.delete(id);
	}
}

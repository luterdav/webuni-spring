package hu.webuni.hr.luterdav.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import hu.webuni.hr.luterdav.repository.EmployeeRepository;
import hu.webuni.hr.luterdav.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private EmployeeRepository employeeRepository;

//	@GetMapping
//	public List<EmployeeDto> getEmployees(@RequestParam(required = false) Double salary{
//		
//		if (salary != null) {
//			return employeeMapper.employeesToDtos(employeeRepository.findBySalaryGreaterThan(salary));
//		}
//		return employeeMapper.employeesToDtos(employeeService.findAll());
//	}

	@GetMapping
	public List<EmployeeDto> getEmployees(@RequestParam(required = false) Integer salary,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {

		Pageable paging = PageRequest.of(page, size);

		if (salary != null) {
			return employeeMapper.employeesToDtos(employeeRepository.findBySalaryGreaterThan(salary, paging));
		}
		return employeeMapper.employeesToDtos(employeeService.findAll());

	}

	@GetMapping("/{id}")
	public EmployeeDto getEmployeeById(@PathVariable long id) {
		Employee employee = employeeService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return employeeMapper.employeeToDto(employee);
	}

	@GetMapping("/position")
	public List<EmployeeDto> getByTitle(@RequestParam(required = false) String name) {
		if (name != null)
			return employeeMapper.employeesToDtos(employeeRepository.findByPositionName(name));
		else
			return employeeMapper.employeesToDtos(employeeService.findAll());
	}

	@GetMapping("/employeeName")
	public List<EmployeeDto> getByName(@RequestParam(required = false) String name) {
		if (name != null)
			return employeeMapper.employeesToDtos(employeeRepository.findByNameStartingWithIgnoreCase(name));
		else
			return employeeMapper.employeesToDtos(employeeService.findAll());
	}

	// http://localhost:8080/api/employees/dateBetween?startDate=2012-01-01
	// 00:00:00&endDate=2016-01-01 00:00:00

	@GetMapping("/dateBetween")
	public List<EmployeeDto> getByDateBetween(@RequestParam LocalDateTime startDate,
			@RequestParam LocalDateTime endDate) {
		if (startDate != null && endDate != null)
			return employeeMapper.employeesToDtos(employeeRepository.findByWorkStartedBetween(startDate, endDate));
		else
			return employeeMapper.employeesToDtos(employeeService.findAll());
	}
	
	@GetMapping("/example")
	public List<EmployeeDto> findEmployeesByDynamicSearch(@RequestBody Employee employee) {
		List<Employee> employees = employeeService.findEmployeesByExample(employee);
		return employeeMapper.employeesToDtos(employees);
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
//		Employee employee = employeeService.findById(id)
//				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//		employee.setId(id);
//		employee = employeeService.update(employeeMapper.dtoToEmployee(employeeDto));
//		return employeeMapper.employeeToDto(employee);
		
		Employee employee = employeeService.update(id, employeeMapper.dtoToEmployee(employeeDto));
		try {
			return employeeMapper.employeeToDto(employee);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable long id) {
		employeeService.delete(id);
	}
}

package hu.webuni.hr.luterdav.web;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.fasterxml.jackson.annotation.JsonView;

import hu.webuni.hr.luterdav.dto.CompanyDto;
import hu.webuni.hr.luterdav.dto.EmployeeDto;
import hu.webuni.hr.luterdav.dto.View.BaseData;
import hu.webuni.hr.luterdav.mapper.CompanyMapper;
import hu.webuni.hr.luterdav.mapper.EmployeeMapper;
import hu.webuni.hr.luterdav.model.AverageSalary;
import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.service.CompanyService;
import hu.webuni.hr.luterdav.service.PositionService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private EmployeeMapper employeeMapper;
	@Autowired
	private PositionService positionService;


	@GetMapping
	public List<CompanyDto> getAllCompanies(@RequestParam(required = false) Boolean full) {
		List<Company> companies = companyService.findAll();
		
		if (isFull(full)) {
			return companyMapper.companiesToDtos(companies);
		} else {
			return companyMapper.companiesToSummaryDtos(companies);
		}
	}

	private boolean isFull(Boolean full) {
		return full != null && full;
	}

	@GetMapping("/{id}")
	public CompanyDto getCompanyById(@PathVariable long id, @RequestParam(required = false) Boolean full) {
		Company company = findByIdOrThrow(id);
        
		if(isFull(full))
			return companyMapper.companyToDto(company);
		else
			return companyMapper.companyToSummaryDto(company);
	}
	
	@GetMapping("/test1")
	public List<CompanyDto> getCompaniesBySalary(@RequestParam(required = false) Double salary) {
		if (salary != null) {
			return companyMapper.companiesToDtos(companyService.findCompaniesBySalary(salary));
		}else {
			return companyMapper.companiesToDtos(companyService.findAll());
		}
	}
	
	@GetMapping("/test2")
	public List<CompanyDto> getCompaniesByEmployeeLimit(@RequestParam(required = false) Integer limit) {
		if (limit != null) {
			return companyMapper.companiesToDtos(companyService.findCompaniesByEmployeeLimit(limit));
		}else {
			return companyMapper.companiesToDtos(companyService.findAll());
		}
	}
	
	@GetMapping("/{id}/averageSalary")
	public List<AverageSalary> getAverageEmployeeSalary(@PathVariable long id) {
		List<AverageSalary> employees = new ArrayList<>();
		List<Object[]> averageSalaries = companyService.findAverageEmployeeSalaryByCompany(id);
		
		averageSalaries.forEach(a -> {
			AverageSalary e = new AverageSalary();
			e.setPositionName(a[0]+"");
			e.setAverageSalary((double)a[1]);
			employees.add(e);
		});
		
		return employees;
	}

	@PostMapping
	public CompanyDto newCompany(@RequestBody CompanyDto companyDto) {
		Company company = companyService.save(companyMapper.dtoToCompany(companyDto));
		return companyMapper.companyToDto(company);
	}

	@PostMapping("/{id}/employees")
	public CompanyDto newEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
		Company company = companyService.addEmployee(id, employeeMapper.dtoToEmployee(employeeDto));
		try {
			return companyMapper.companyToDto(company);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}/employees")
	public CompanyDto replaceAllEmployees(@PathVariable long id, @RequestBody List<EmployeeDto> employees) {
		Company company = companyService.replaceEmployees(id, employeeMapper.dtosToEmployees(employees));
		try {
			return companyMapper.companyToDto(company);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public CompanyDto updateCompany(@PathVariable long id, @RequestBody CompanyDto companyDto) {
		// Company company = findByIdOrThrow(id);
		companyDto.setId(id);
		try {
			return companyMapper.companyToDto(companyService.update(companyMapper.dtoToCompany(companyDto)));

		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public void deleteCompany(@PathVariable long id) {
		companyService.delete(id);
	}

	@DeleteMapping("/{id}/employees/{employeeId}")
	public CompanyDto deleteEmployeeFromCompany(@PathVariable long id, @PathVariable long employeeId) {
		Company company = companyService.deleteEmployee(id, employeeId);
		try {
			return companyMapper.companyToDto(company);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	private Company findByIdOrThrow(long id) {
		Company company = companyService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return company;
	}
}
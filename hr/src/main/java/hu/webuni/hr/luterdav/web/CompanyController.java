package hu.webuni.hr.luterdav.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import hu.webuni.hr.luterdav.dto.CompanyDto;
import hu.webuni.hr.luterdav.dto.EmployeeDto;
import hu.webuni.hr.luterdav.mapper.CompanyMapper;
import hu.webuni.hr.luterdav.mapper.EmployeeMapper;
import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.service.CompanyService;
import hu.webuni.hr.luterdav.service.EmployeeService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private EmployeeMapper employeeMapper;

//	@GetMapping
//	public List<CompanyDto> getCompanies(@RequestParam(value = "full", required = false) String full) {
//		if (full!=null) {
//			List<CompanyDto> list = new ArrayList<>();
//			for (CompanyDto companyDto : companies.values()) {
//				list.add(new CompanyDto(companyDto.getId(), companyDto.getRegistrationNumber(), companyDto.getName(), companyDto.getAddress(), null));
//			}
//			return list;
//		}
//		return new ArrayList<>(companies.values());
//	}
//	

	@GetMapping
	public List<CompanyDto> getAll(@RequestParam(required = false) Boolean full) {
		if (isFull(full)) {
			return companyMapper.companiesToDtos(companyService.findAll());
		} else {
			return companyMapper.companiesToDtos(companyService.findAll()).stream()
					.map(this::createCompanyWithoutEmployees).collect(Collectors.toList());
		}
	}

	private CompanyDto createCompanyWithoutEmployees(CompanyDto c) {
		return new CompanyDto(c.getId(), c.getRegistrationNumber(), c.getName(), c.getAddress(), null);
	}

	private boolean isFull(Boolean full) {
		return full != null && full;
	}

	@GetMapping("/{id}")
	public CompanyDto getCompanyById(@PathVariable long id) {
		Company company = findByIdOrThrow(id);

		return companyMapper.companyToDto(company);
	}

	@PostMapping
	public CompanyDto newCompany(@RequestBody CompanyDto companyDto) {
		Company company = companyService.save(companyMapper.dtoToCompany(companyDto));
		return companyMapper.companyToDto(company);
	}

	@PostMapping("/{id}/employees")
	public CompanyDto newEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
		Company company = findByIdOrThrow(id);

		company.getEmployees().add(employeeMapper.dtoToEmployee(employeeDto));
		return companyMapper.companyToDto(company);
	}

	@PutMapping("/{id}/employees")
	public CompanyDto replaceAllEmployees(@PathVariable long id, @RequestBody List<EmployeeDto> employees) {
		Company company = findByIdOrThrow(id);

		company.setEmployees(employeeMapper.dtosToEmployees(employees));
		return companyMapper.companyToDto(company);
	}


	@PutMapping("/{id}")
	public CompanyDto updateCompany(@PathVariable long id, @RequestBody CompanyDto companyDto) {
		Company company = findByIdOrThrow(id);
		
		company.setId(id);
		company = companyService.save(companyMapper.dtoToCompany(companyDto));
		return companyMapper.companyToDto(company);
	}

	@DeleteMapping("/{id}")
	public void deleteCompany(@PathVariable long id) {
		companyService.delete(id);
	}

	@DeleteMapping("/{id}/employees/{employeeId}")
	public CompanyDto deleteEmployeeFromCompany(@PathVariable long id, @PathVariable long employeeId) {
		Company company = findByIdOrThrow(id);

		company.getEmployees().removeIf(emp -> emp.getId() == employeeId);
		return companyMapper.companyToDto(company);
	}
	
	private Company findByIdOrThrow(long id) {
		Company company = companyService.findById(id);
		if (company == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return company;
	}
}

package hu.webuni.hr.luterdav.web;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
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

import hu.webuni.hr.luterdav.dto.CompanyDto;
import hu.webuni.hr.luterdav.dto.EmployeeDto;
import hu.webuni.hr.luterdav.mapper.CompanyMapper;
import hu.webuni.hr.luterdav.mapper.EmployeeMapper;
import hu.webuni.hr.luterdav.model.AverageSalaryByPosition;
import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.repository.CompanyRepository;
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
	@Autowired
	private CompanyRepository companyRepository;

	@GetMapping
	public List<CompanyDto> getAllCompanies(@RequestParam(required = false) Boolean full) {
//		List<Company> companies = companyService.findAll();
//		return mapCompanies(companies, full);
		List<Company> companies = null;
		if(full == null || !full) {
			companies = companyService.findAll();
			return companyMapper.companiesToSummaryDtos(companies);
		}else {
			companies = companyRepository.findAllWithEmployees();
		    return companyMapper.companiesToDtos(companies);
		}
	}

	private boolean isFull(Boolean full) {
		return full != null && full;
	}

	@GetMapping("/{id}")
	public CompanyDto getCompanyById(@PathVariable long id, @RequestParam(required = false) Boolean full) {
		Company company = findByIdOrThrow(id);
		if (full != null && full)
			return companyMapper.companyToDto(company);
		else
			return companyMapper.companyToSummaryDto(company);
	}

//	@GetMapping(params= "aboveSalary")
//	public List<CompanyDto> getCompaniesBySalary(@RequestParam(required = false) int aboveSalary,
//			@RequestParam(required = false) Boolean full) {
//		List<Company> companies = companyRepository.findByEmployeeWithSalaryHigherThan(aboveSalary);
//		return mapCompanies(companies, full);
//	}

	@GetMapping(params = "aboveSalary")
	public List<CompanyDto> getCompaniesBySalary(@RequestParam(required = false) int aboveSalary,
			@RequestParam(required = false) Boolean full, @SortDefault("id") Pageable pageable) {
		Page<Company> page = companyRepository.findByEmployeeWithSalaryHigherThan(pageable, aboveSalary);
		System.out.println(page.getTotalElements());
		System.out.println(page.isLast());
		List<Company> companies = page.getContent();
		return mapCompanies(companies, full);
	}

	private List<CompanyDto> mapCompanies(List<Company> allCompanies, Boolean full) {
		if (isFull(full)) {
			return companyMapper.companiesToDtos(allCompanies);
		} else {
			return companyMapper.companiesToSummaryDtos(allCompanies);
		}
	}

	@GetMapping(params = "aboveEmployeeNumber")
	public List<CompanyDto> getCompaniesAboveEmployeeNumber(@RequestParam int aboveEmployeeNumber,
			@RequestParam(required = false) Boolean full) {
		List<Company> filteredCompanies = companyRepository.findByEmployeeCountHigherThan(aboveEmployeeNumber);
		return mapCompanies(filteredCompanies, full);
	}

	@GetMapping("/{id}/salaryStats")
	public List<AverageSalaryByPosition> getSalaryStatsById(@PathVariable long id,
			@RequestParam(required = false) Boolean full) {
		return companyRepository.findAverageSalariesByPosition(id);
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
	
	@DeleteMapping("/{id}/employees/{employeeId}")
	public CompanyDto deleteEmployeeFromCompany(@PathVariable long id, @PathVariable long employeeId) {
		Company company = companyService.deleteEmployee(id, employeeId);
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


	private Company findByIdOrThrow(long id) {
		Company company = companyService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return company;
	}
}

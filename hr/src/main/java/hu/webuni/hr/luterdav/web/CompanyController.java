package hu.webuni.hr.luterdav.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import hu.webuni.hr.luterdav.dto.CompanyDto;
import hu.webuni.hr.luterdav.dto.EmployeeDto;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	private Map<Long, CompanyDto> companies = new HashMap<>();
	
//	private List<EmployeeDto> employees1 = new ArrayList<>();
//	private List<EmployeeDto> employees2 = new ArrayList<>();
//	
//	{
//		employees1.add(new EmployeeDto(1, "John Adams", "accountant", 100_000, LocalDateTime.of(2017,10,10,10,10,10)));
//		employees1.add(new EmployeeDto(2, "Adam Johns", "analyst", 300_000, LocalDateTime.of(2020,10,10,10,10,10)));
//	}
//	{
//		employees2.add(new EmployeeDto(1, "Peter Falk", "sales", 200_000, LocalDateTime.of(2015,10,10,10,10,10)));
//		employees2.add(new EmployeeDto(2, "Frank Miller", "finance", 500_000, LocalDateTime.of(2020,10,10,10,10,10)));
//	}
//	
//	{
//		companies.put(1L, new CompanyDto(1L, "Amazon", "USA", employees1));
//		companies.put(2L, new CompanyDto(2L, "Apple", "USA", employees2));
//	}
	
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
//	@GetMapping
//	public List<CompanyDto> getAll(@RequestParam(required = false) Boolean full){
//				if(full != null && full) {
//					return new ArrayList<>(companies.values());
//				} else {
//					return companies.values().stream()
//							.map(c -> new CompanyDto(c.getId(), c.getRegistrationNumber(), c.getName(), c.getAddress(), null))
//							.collect(Collectors.toList());
//			}
//	}
	
	@GetMapping
	public List<CompanyDto> getAll(@RequestParam(required = false) Boolean full){
				if(isFull(full)) {
					return new ArrayList<>(companies.values());
				} else {
					return companies.values().stream()
							.map(this::createCompanyWithoutEmployees)
							.collect(Collectors.toList());
			}
	}
	
	private CompanyDto createCompanyWithoutEmployees(CompanyDto c) {
		return new CompanyDto(c.getId(), c.getRegistrationNumber(), c.getName(), c.getAddress(), null);
	}
	
	private boolean isFull(Boolean full) {
		return full != null && full;
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<CompanyDto> getCompanyById(@PathVariable long id) {
		CompanyDto companyDto = companies.get(id);
		if (companyDto != null) {
			return ResponseEntity.ok(companyDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public CompanyDto newCompany(@RequestBody CompanyDto companyDto) {
		companies.put(companyDto.getId(), companyDto);
		return companyDto;
	}
	
	@PostMapping("/{id}/employees")
	public ResponseEntity<CompanyDto> newEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
		CompanyDto companyDto = companies.get(id);
		if(companyDto == null)
			return ResponseEntity.notFound().build();
		
		companyDto.getEmployees().add(employeeDto);
		return ResponseEntity.ok(companyDto);
	}
	
	@PutMapping("/{id}/employees")
	public ResponseEntity<CompanyDto> replaceAllEmployees(@PathVariable long id, @RequestBody List<EmployeeDto> employees) {
		CompanyDto companyDto = companies.get(id);
		if(companyDto == null)
			return ResponseEntity.notFound().build();
		
		companyDto.setEmployees(employees);
		return ResponseEntity.ok(companyDto);
	}
	
//	private CompanyDto findByIdOrThrow(long id) {
//	CompanyDto company = companies.get(id);
//	if(company == null)
//		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//	return company;
//}
	
	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> updateCompany(@PathVariable long id, @RequestBody CompanyDto companyDto) {
		if (!companies.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		companyDto.setId(id);
		companies.put(id, companyDto);
		return ResponseEntity.ok(companyDto);
	}

	@DeleteMapping("/{id}")
	public void deleteCompany(@PathVariable long id) {
		companies.remove(id);
	}
	
	@DeleteMapping("/{id}/employees/{employeeId}")
	public ResponseEntity<CompanyDto> deleteEmployeeFromCompany(@PathVariable long id, @PathVariable long employeeId) {
		CompanyDto companyDto = companies.get(id);
		if(companyDto == null)
			return ResponseEntity.notFound().build();
		
		companyDto.getEmployees().removeIf(emp -> emp.getId() == employeeId);
		return ResponseEntity.ok(companyDto);
	}
}

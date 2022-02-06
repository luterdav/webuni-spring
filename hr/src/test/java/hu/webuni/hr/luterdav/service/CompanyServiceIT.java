package hu.webuni.hr.luterdav.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.model.CompanyType;
import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.model.Position;
import hu.webuni.hr.luterdav.repository.CompanyTypeRepository;

@SpringBootTest
@AutoConfigureTestDatabase
public class CompanyServiceIT {

//	@Autowired
//	WebTestClient webTestClient;
	@Autowired
	CompanyService companyService;
	@Autowired
	PositionService positionService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	CompanyTypeRepository companyTypeRepository;

//	private static final String BASE_URI = "/api/companies";

	@BeforeEach
	public void init() {
		employeeService.deleteAll();
		positionService.deleteAll();
		companyService.deleteAll();
		companyTypeRepository.deleteAll();
	}

	
	public Position createPosition(String name, String education) {
		return positionService.save(new Position(name, education));
	}
	
	public Employee createEmployee(String name, int salary, Position position, LocalDateTime workSarted, Company company) {
		return employeeService.save(new Employee(name, position, salary, workSarted, company));
	}
	
	public Employee createEmployeeForCompany(String name, int salary, Position position, LocalDateTime workSarted) {
		return new Employee(name, position, salary, workSarted);
	}
	
	public Company createCompany(int regNum, String name, String address, String companyType) {
		return companyService.save(new Company(regNum, name, address, companyTypeRepository.save(new CompanyType(companyType))));
	}

	@Test
	void testCreateCompanyEmployee() throws Exception {
		Position position = createPosition("Accountant", "University");
		Company company = createCompany(1111, "Apple", "USA", "Zrt.");
		Employee employee = createEmployeeForCompany("Jackson", 390_000, position, LocalDateTime.of(2020, 8, 2, 1, 1, 1));

		assertThat(company.getEmployees()).isNull();
		Company companyWithEmployee = companyService.addEmployee(company.getId(), employee);
		assertThat(companyWithEmployee.getEmployees().get(0)).usingRecursiveComparison().isEqualTo(employee);

	}

	@Test
	void testReplaceCompanyEmployees() throws Exception {
		Position position = createPosition("Accountant", "University");
		Company company = createCompany(1111, "Apple", "USA", "Zrt.");
		List<Employee> employeeListOld = new ArrayList<>();
		List<Employee> employeeListNew = new ArrayList<>();
		employeeListOld.add(createEmployeeForCompany("Peter", 300_000, position, LocalDateTime.of(2020, 8, 2, 1, 1, 1)));
		employeeListNew.add(createEmployeeForCompany("Dave", 40_000, position, LocalDateTime.of(2021, 1, 2, 0, 0, 0)));

		assertThat(company.getEmployees()).isNull();

		company.setEmployees(employeeListOld);
		assertThat(company.getEmployees().get(0)).usingRecursiveComparison().isEqualTo(employeeListOld.get(0));

		Company companyWithReplacedEmployees = companyService.replaceEmployees(company.getId(), employeeListNew);
		assertThat(companyWithReplacedEmployees.getEmployees().get(0)).usingRecursiveComparison()
				.isEqualTo(employeeListNew.get(0));

	}

	@Test
	void testDeleteCompanyEmployee() throws Exception {
		Position position = createPosition("Accountant", "University");
		Company company = createCompany(1111, "Apple", "USA", "Zrt.");
		Employee employee = employeeService.save(createEmployeeForCompany("Peter", 300_000, position, LocalDateTime.of(2020, 8, 2, 1, 1, 1)));
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(employee);

		assertThat(company.getEmployees()).isNull();

		company.setEmployees(employeeList);
		assertThat(company.getEmployees().get(0)).usingRecursiveComparison().isEqualTo(employeeList.get(0));
		assertThat(company.getEmployees()).contains(employee);

		Company companyWithDeletedEmployee = companyService.deleteEmployee(company.getId(),
				company.getEmployees().get(0).getId());
		assertThat(companyWithDeletedEmployee.getEmployees()).doesNotContain(employee);
	}
	
	@Test
	void testFindEmployeesByExample() throws Exception{
		Position position = createPosition("Accountant", "University");
		Company company1 = createCompany(1111, "Apple", "USA", "Zrt.");
		Company company2 = createCompany(2222, "Apple Inc", "USA", "Nyrt.");
		long employee1 = createEmployee("Jackson", 390_000, position, LocalDateTime.of(2021, 1, 2, 1, 1, 1), company1).getId();
		long employee2 = createEmployee("Jacky", 410_000, position, LocalDateTime.of(2021, 1, 2, 3, 3, 3), company2).getId();
		
		Employee example = new Employee();
		example.setName("jack");
		example.setSalary(400_000);
		example.setPosition(new Position("Accountant", "College"));
		example.setWorkStarted(LocalDateTime.of(2021, 1, 2, 8, 8, 8));
		example.setCompany(new Company(3333, "AP"));
		List<Employee> foundEmployees = this.employeeService.findEmployeesByExample(example);
		assertThat(foundEmployees.stream().map(Employee::getId).collect(Collectors.toList()))
		.containsExactly(employee1, employee2);
		
		
	}

//	private ResponseSpec modifyEmployee(EmployeeDto newEmployee) {
//		String path = BASE_URI + "/" + newEmployee.getId();
//		return webTestClient
//				.put()
//				.uri(path)
////				.headers(headers -> headers.setBasicAuth(username, pass))
//				.headers(headers -> headers.setBearerAuth(jwt))
//				.bodyValue(newEmployee)
//				.exchange();
//	}
//	
//	private ResponseSpec saveEmployee(EmployeeDto newEmployee) {
//		return webTestClient
//				.post()
//				.uri(BASE_URI)
////				.headers(headers -> headers.setBasicAuth(username, pass))
//				.headers(headers -> headers.setBearerAuth(jwt))
//				.bodyValue(newEmployee)
//				.exchange();
//	}
//
//	private List<EmployeeDto> getAllEmployees() {
//		List<EmployeeDto> responseList = webTestClient
//				.get()
//				.uri(BASE_URI)
////				.headers(headers -> headers.setBasicAuth(username, pass))
//				.headers(headers -> headers.setBearerAuth(jwt))
//				.exchange()
//				.expectStatus()
//				.isOk()
//				.expectBodyList(EmployeeDto.class)
//				.returnResult()
//				.getResponseBody();
//		Collections.sort(responseList, Comparator.comparing(EmployeeDto::getId));
//		return responseList;
//	}

}

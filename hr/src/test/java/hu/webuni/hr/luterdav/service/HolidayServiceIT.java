package hu.webuni.hr.luterdav.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.webuni.hr.luterdav.dto.HolidayDto;
import hu.webuni.hr.luterdav.mapper.HolidayMapper;
import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.model.Holiday;
import hu.webuni.hr.luterdav.model.Position;
import hu.webuni.hr.luterdav.repository.EmployeeRepository;
import hu.webuni.hr.luterdav.repository.PositionDetailsByCompanyRespository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureWebTestClient(timeout = "36000")
public class HolidayServiceIT {

	@Autowired
	WebTestClient webTestClient;
	@Autowired
	PositionService positionService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	HolidayService holidayService;
//	@Autowired
//	PositionDetailsByCompanyRespository positionDetailsByCompanyRespository;
	@Autowired
	HolidayMapper holidayMapper;
	@Autowired
	PasswordEncoder passwordEncoder;

	private static final String BASE_URI = "/api/holidays";
	
	String username = "tesUser";
	String password = "1234";

	@BeforeEach
	public void init() {
		holidayService.deleteAll();
		employeeService.deleteAll();
		positionService.deleteAll();
		

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
	
	public Employee createEmployeeForHoliday(String name, String username, String password, int salary, Position position, LocalDateTime workSarted) {
		return employeeService.save(new Employee(name, username, passwordEncoder.encode(password), position, salary, workSarted)) ;
	}
	
	public Holiday createHolidayRequest(LocalDateTime startOfHoliday, LocalDateTime endOfHoliday, Employee holidayCreatedBy, 
			LocalDateTime dateOfHolidayRequested, String status) {
		return holidayService.save(new Holiday(startOfHoliday, endOfHoliday, holidayCreatedBy, dateOfHolidayRequested, status));
	}
	

	@Test
	void testCreateHolidayRequest() throws Exception {
		Position position = createPosition("Accountant", "University");
		Employee employee = createEmployeeForHoliday("Jackson", username, password, 390_000, position, LocalDateTime.of(2020, 8, 2, 1, 1, 1));
		createHolidayRequest(LocalDateTime.of(2022, 2, 2, 0, 0, 0), LocalDateTime.of(2022, 2, 6, 0, 0, 0), 
				employee, LocalDateTime.of(2022, 1, 2, 0, 0, 0), "waiting for approval");
		
		List<HolidayDto> holidayRequestsBefore = getAllHolidayRequests();
		
		HolidayDto newHolidayRequest = holidayMapper.holidayToDto(new Holiday(LocalDateTime.of(2022, 3, 2, 0, 0, 0), LocalDateTime.of(2022, 3, 8, 0, 0, 0), 
				employee, LocalDateTime.of(2022, 1, 10, 0, 0, 0), "waiting for approval"));
		
		
		saveHolidayRequest(newHolidayRequest)
		.expectStatus()
		.isOk();
		
		List<HolidayDto> holidayRequestsAfter= getAllHolidayRequests();
		
		assertThat(holidayRequestsAfter.size()).isEqualTo(holidayRequestsBefore.size() + 1);
		assertThat(holidayRequestsAfter.get(holidayRequestsAfter.size()-1))
			.usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(newHolidayRequest);

	}
	
	@Test
	void testApproveHolidayRequest() throws Exception {
		Position position = createPosition("Accountant", "University");
		Employee employee = createEmployeeForHoliday("Jackson", username, password, 390_000, position, LocalDateTime.of(2020, 8, 2, 1, 1, 1));
		HolidayDto newHolidayRequest = holidayMapper.holidayToDto(new Holiday(LocalDateTime.of(2022, 3, 2, 0, 0, 0), LocalDateTime.of(2022, 3, 8, 0, 0, 0), 
				employee, LocalDateTime.of(2022, 1, 10, 0, 0, 0), "Waiting for approval"));
		
		HolidayDto savedHolidayRequest = saveHolidayRequest(newHolidayRequest)
		.expectStatus()
		.isOk()
		.expectBody(HolidayDto.class)
		.returnResult()
		.getResponseBody();
		
		List<HolidayDto> holidayRequestsBefore = getAllHolidayRequests();
		
		updateStatus(savedHolidayRequest)
		.expectStatus()
		.isOk();
		
		List<HolidayDto> holidayRequestsAfter= getAllHolidayRequests();
		
		assertThat(holidayRequestsBefore).hasSameSizeAs(holidayRequestsAfter);
		assertThat(holidayRequestsAfter.get(holidayRequestsAfter.size()-1).getStatus())
		.isEqualToIgnoringCase("Approved");

	}
	
	@Test
	void testUpdateHolidayRequest() throws Exception {
		Position position = createPosition("Accountant", "University");
		Employee employee = createEmployeeForHoliday("Jackson", username, password, 390_000, position, LocalDateTime.of(2020, 8, 2, 1, 1, 1));
		HolidayDto newHolidayRequest = holidayMapper.holidayToDto(new Holiday(LocalDateTime.of(2022, 3, 2, 0, 0, 0), LocalDateTime.of(2022, 3, 8, 0, 0, 0), 
				employee, LocalDateTime.of(2022, 1, 10, 0, 0, 0), "Waiting for approval"));
		
		HolidayDto savedHolidayRequest = saveHolidayRequest(newHolidayRequest)
				.expectStatus()
				.isOk()
				.expectBody(HolidayDto.class)
				.returnResult()
				.getResponseBody();
		
		List<HolidayDto> holidayRequestsBefore = getAllHolidayRequests();
		
		savedHolidayRequest.setDateOfHolidayRequested(LocalDateTime.of(2012, 1, 10, 0, 0, 0));
		
		updateHolidayRequest(savedHolidayRequest)
		.expectStatus()
		.isOk();
		
		List<HolidayDto> holidayRequestsAfter= getAllHolidayRequests();
		
		assertThat(holidayRequestsBefore).hasSameSizeAs(holidayRequestsAfter);
		assertThat(holidayRequestsAfter.get(holidayRequestsAfter.size()-1))
		.usingRecursiveComparison()
		.isEqualTo(savedHolidayRequest);
		
	}
	
	@Test
	void testDeleteHolidayRequest() throws Exception {
		Position position = createPosition("Accountant", "University");
		Employee employee = createEmployeeForHoliday("Jackson", username, password, 390_000, position, LocalDateTime.of(2020, 8, 2, 1, 1, 1));
		HolidayDto newHolidayRequest = holidayMapper.holidayToDto(new Holiday(LocalDateTime.of(2022, 3, 2, 0, 0, 0), LocalDateTime.of(2022, 3, 8, 0, 0, 0), 
				employee, LocalDateTime.of(2022, 1, 10, 0, 0, 0), "Waiting for approval"));
		
		HolidayDto savedHolidayRequest = saveHolidayRequest(newHolidayRequest)
				.expectStatus()
				.isOk()
				.expectBody(HolidayDto.class)
				.returnResult()
				.getResponseBody();
		
		int sizeBefore = getAllHolidayRequests().size();
		
		deleteHolidayRequest(savedHolidayRequest)
		.expectStatus()
		.isOk();
		
		int sizeAfter= getAllHolidayRequests().size();
		
		assertThat(sizeBefore).isEqualTo(sizeAfter + 1);
	}
	
	@Test
	void testFindHolidayRequestsByExample() throws Exception{
		Position position = createPosition("Accountant", "University");
		Employee employee1 = createEmployeeForHoliday("Jackson", username, password, 390_000, position, LocalDateTime.of(2020, 8, 2, 1, 1, 1));
		Employee employee2 = createEmployeeForHoliday("Jacky", "testUser2", "1111", 390_000, position, LocalDateTime.of(2021, 8, 2, 1, 1, 1));
		long holiday1 = createHolidayRequest(LocalDateTime.of(2022, 2, 2, 0, 0, 0), LocalDateTime.of(2022, 2, 6, 0, 0, 0), 
				employee1, LocalDateTime.of(2022, 1, 2, 0, 0, 0), "Waiting for approval").getId();
		long holiday2 = createHolidayRequest(LocalDateTime.of(2022, 2, 3, 0, 0, 0), LocalDateTime.of(2022, 2, 5, 0, 0, 0), 
				employee2, LocalDateTime.of(2022, 1, 2, 0, 0, 0), "Waiting for approval").getId();

		
		Holiday example = new Holiday();
		example.setStartOfHoliday(LocalDateTime.of(2022, 2, 1, 0, 0, 0));
		example.setEndOfHoliday(LocalDateTime.of(2022, 2, 8, 0, 0, 0));
		example.setHolidayCreatedBy(createEmployeeForHoliday("jack", "testUser3", "2222", 390_000, position, LocalDateTime.of(2020, 8, 2, 1, 1, 1)));
		example.setDateOfHolidayRequested(LocalDateTime.of(2022, 1, 3, 0, 0, 0));
		example.setStatus("Waiting for approval");
		List<Holiday> foundHolidayRequests = this.holidayService.findHolidayRequestsByExample(example);
		assertThat(foundHolidayRequests.stream().map(Holiday::getId).collect(Collectors.toList()))
		.containsExactly(holiday1, holiday2);
	}
	
	private ResponseSpec deleteHolidayRequest(HolidayDto newHolidayRequest) {
		String path = BASE_URI + "/" + newHolidayRequest.getId() + "/delete";
		return webTestClient
				.method(HttpMethod.DELETE)
				.uri(path)
				.headers(headers -> headers.setBasicAuth(username, password))
				.bodyValue(newHolidayRequest)
				.exchange();
	}
	
	private ResponseSpec updateHolidayRequest(HolidayDto newHolidayRequest) {
		String path = BASE_URI + "/" + newHolidayRequest.getId() + "/update";
		return webTestClient
				.put()
				.uri(path)
				.headers(headers -> headers.setBasicAuth(username, password))
				.bodyValue(newHolidayRequest)
				.exchange();
	}
	
	private ResponseSpec updateStatus(HolidayDto newHolidayRequest) {
		String path = BASE_URI + "/" + newHolidayRequest.getId() + "/updateStatus";
		return webTestClient
				.put()
				.uri(uriBuilder ->
				uriBuilder
				.path(path)
				.queryParam("approve", true)
				.build())
				.headers(headers -> headers.setBasicAuth(username, password))
				.bodyValue(newHolidayRequest)
				.exchange();
	}
	
	private ResponseSpec saveHolidayRequest(HolidayDto newHolidayRequest) {
		return webTestClient
				.post()
				.uri(BASE_URI)
				.headers(headers -> headers.setBasicAuth(username, password))
				.bodyValue(newHolidayRequest)
				.exchange();
	}
	
	private List<HolidayDto> getAllHolidayRequests() {
		List<HolidayDto> responseList = webTestClient
				.get()
				.uri(BASE_URI)
				.headers(headers -> headers.setBasicAuth(username, password))
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(HolidayDto.class)
				.returnResult()
				.getResponseBody();
		Collections.sort(responseList, Comparator.comparing(HolidayDto::getId));
		return responseList;
	}

}

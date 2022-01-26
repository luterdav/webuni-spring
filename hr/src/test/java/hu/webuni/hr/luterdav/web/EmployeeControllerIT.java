package hu.webuni.hr.luterdav.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.webuni.hr.luterdav.dto.EmployeeDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIT {
	
	private static final String BASE_URI = "/api/employees";
	
	@Autowired
	WebTestClient webTestClient;
	
	@Test
	void testThatValidEmployeeIsCreated() throws Exception{
		List<EmployeeDto> employeesBefore = getAllEmployees();
		
		EmployeeDto newEmployee = new EmployeeDto(3, "Peter Johns", "sales", 300_000, LocalDateTime.of(2020, 10, 10, 10, 10, 10));
		
		createValidEmployee(newEmployee);
		
		List<EmployeeDto> employeesAfter = getAllEmployees();
		
		assertThat(employeesAfter.subList(0, employeesBefore.size()))
		.usingRecursiveFieldByFieldElementComparator()
		.containsExactlyElementsOf(employeesBefore);
		
		assertThat(employeesAfter.get(employeesAfter.size()-1))
		.usingRecursiveComparison()
		.isEqualTo(newEmployee);
	}
	
	@Test
	void testThatInvalidEmployeeIsCreated() throws Exception{
		List<EmployeeDto> employeesBefore = getAllEmployees();
		
		EmployeeDto newEmployee = new EmployeeDto(3, "Peter Johns", "", 300_000, LocalDateTime.of(2020, 10, 10, 10, 10, 10));
		
		createInvalidEmployee(newEmployee);
		
		List<EmployeeDto> employeesAfter = getAllEmployees();
		
		assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size());
		
	}
	
	@Test
	void testThatValidEmployeeIsUpdated() throws Exception{
		EmployeeDto newEmployee = new EmployeeDto(3, "Peter Johns", "sales", 300_000, LocalDateTime.of(2020, 10, 10, 10, 10, 10));
		EmployeeDto createdEmployee = createEmployeeWithBody(newEmployee); 
		
		List<EmployeeDto> employeesBefore = getAllEmployees();
		createdEmployee.setSalary(100_000);
		updateValidEmployee(createdEmployee);
		List<EmployeeDto> employeesAfter = getAllEmployees();
		
		assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size());
		
		assertThat(employeesAfter.get(employeesAfter.size()-1))
		.usingRecursiveComparison()
		.isEqualTo(createdEmployee);
	}
	
	@Test
	void testThatInvalidEmployeeIsUpdated() throws Exception{
		EmployeeDto newEmployee = new EmployeeDto(3, "Peter Johns", "sales", 300_000, LocalDateTime.of(2020, 10, 10, 10, 10, 10));
		EmployeeDto createdEmployee = createEmployeeWithBody(newEmployee); 
		
		List<EmployeeDto> employeesBefore = getAllEmployees();
		EmployeeDto newEmployee2 = new EmployeeDto(3, "Peter Johns", "", 300_000, LocalDateTime.of(2020, 10, 10, 10, 10, 10));
		updateInvalidEmployee(newEmployee2);
		List<EmployeeDto> employeesAfter = getAllEmployees();
		
		assertThat(employeesAfter.size()).isEqualTo(employeesBefore.size());
		
		assertThat(employeesAfter.get(employeesAfter.size()-1))
		.usingRecursiveComparison()
		.isEqualTo(createdEmployee);
	}
	

	
	
	private void updateValidEmployee(EmployeeDto newEmployee) {
		webTestClient
		.put()
		.uri(BASE_URI + "/" + newEmployee.getId())
		.bodyValue(newEmployee)
		.exchange()
		.expectStatus().isOk();
		
	}
	
	private void updateInvalidEmployee(EmployeeDto newEmployee) {
		webTestClient
		.put()
		.uri(BASE_URI + "/" + newEmployee.getId())
		.bodyValue(newEmployee)
		.exchange()
		.expectStatus().isBadRequest();

	}

	private void createValidEmployee(EmployeeDto newEmployee) {
		webTestClient
		.post()
		.uri(BASE_URI)
		.bodyValue(newEmployee)
		.exchange()
		.expectStatus().isOk();
	}
	
	private EmployeeDto createEmployeeWithBody(EmployeeDto newEmployee) {
		EmployeeDto response = webTestClient
		.post()
		.uri(BASE_URI)
		.bodyValue(newEmployee)
		.exchange()
		.expectStatus().isOk()
		.expectBody(EmployeeDto.class)
		.returnResult().getResponseBody();
		
		return response;
	}
	
	private void createInvalidEmployee(EmployeeDto newEmployee) {
		webTestClient
		.post()
		.uri(BASE_URI)
		.bodyValue(newEmployee)
		.exchange()
		.expectStatus().isBadRequest();
	}

	private List<EmployeeDto> getAllEmployees() {
		List<EmployeeDto> responseList = webTestClient
		.get()
		.uri(BASE_URI)
		.exchange()
		.expectStatus().isOk()
		.expectBodyList(EmployeeDto.class)
		.returnResult().getResponseBody();
		
		Collections.sort(responseList, (a1, a2) -> Long.compare(a1.getId(), a2.getId()));
		
		return responseList;
	}

}

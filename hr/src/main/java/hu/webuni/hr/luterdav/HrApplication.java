package hu.webuni.hr.luterdav;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.service.SalaryService;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

	@Autowired
	SalaryService salaryService;

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	Employee employee1 = new Employee(1L, "John Adams", "accountant", 200_000, LocalDateTime.of(2019,10,10,10,10,10));
			
	Employee employee2 = new Employee(2L, "Adam Johns", "analyst", 300_000, LocalDateTime.of(2020,10,10,10,10,10));

	@Override
	public void run(String... args) throws Exception {
		System.out.println(employee1.getName() + ": " + salaryService.getNewSalary(employee1));
		System.out.println(employee2.getName() + ": " + salaryService.getNewSalary(employee2));
	}

}

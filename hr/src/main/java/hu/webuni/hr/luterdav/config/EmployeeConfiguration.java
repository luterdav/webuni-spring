package hu.webuni.hr.luterdav.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.luterdav.service.DefaultEmployeeService;
import hu.webuni.hr.luterdav.service.EmployeeService;

@Configuration
@Profile("!smart")
public class EmployeeConfiguration {

	@Bean
	public EmployeeService employeeService() {
		return new DefaultEmployeeService();
	}

}

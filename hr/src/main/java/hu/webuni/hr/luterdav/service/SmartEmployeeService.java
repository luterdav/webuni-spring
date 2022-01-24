package hu.webuni.hr.luterdav.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.luterdav.config.HrConfigProperties;
import hu.webuni.hr.luterdav.model.Employee;

@Service
public class SmartEmployeeService implements EmployeeService {

	@Autowired
	HrConfigProperties config;

	@Override
	public int getPayRaisePercent(Employee employee) {
		if ((double) employee.getWorkTimeInMonths() >= (config.getEmployees().getSmart().getLimit3() * 12)) {
			return config.getEmployees().getSmart().getPercent4();
		} else if ((double) employee.getWorkTimeInMonths() >= (config.getEmployees().getSmart().getLimit2() * 12)) {
			return config.getEmployees().getSmart().getPercent3();
		} else if ((double) employee.getWorkTimeInMonths() >= (config.getEmployees().getSmart().getLimit1() * 12)) {
			return config.getEmployees().getSmart().getPercent2();
		} else {
			return config.getEmployees().getSmart().getPercent1();
		}
	}

}

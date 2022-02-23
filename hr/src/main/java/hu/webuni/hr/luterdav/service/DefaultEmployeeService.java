package hu.webuni.hr.luterdav.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.luterdav.config.HrConfigProperties;
import hu.webuni.hr.luterdav.model.Employee;

@Service
public class DefaultEmployeeService extends AbstractEmployeeService  {

	@Autowired
	HrConfigProperties config;

	@Override
	public int getPayRaisePercent(Employee employee) {
		return config.getEmployees().getDef().getPercent();
	}

}

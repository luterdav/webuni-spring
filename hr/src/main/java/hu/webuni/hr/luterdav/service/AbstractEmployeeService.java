package hu.webuni.hr.luterdav.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.model.Position;
import hu.webuni.hr.luterdav.repository.EmployeeRepository;
import hu.webuni.hr.luterdav.repository.PositionRepository;


@Service
public abstract class AbstractEmployeeService implements EmployeeService {


	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PositionService positionService;

	@Override
	public Employee save(Employee employee) {
		positionService.setPositionForEmployee(employee);
		return employeeRepository.save(employee);
	}

	@Override
	public Employee update(Employee employee) {
		if (employeeRepository.existsById(employee.getId())) {
			positionService.setPositionForEmployee(employee);
			return employeeRepository.save(employee);
		}else {
			throw new NoSuchElementException();
		}
	}
	
	@Override
	@Transactional
	public Employee update(Long id, Employee employee) {
		Employee employeeOld = employeeRepository.findById(id).get();
		if (employeeRepository.existsById(employee.getId())) {
			employeeOld.setId(id);
			return employeeRepository.save(employee);
		}else
			throw new NoSuchElementException();
	}

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> findById(long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public void delete(long id) {
		employeeRepository.deleteById(id);
	}
	
	@Override
	public void deleteAll() {
		employeeRepository.deleteAll();
	}
	
	@Override
	public List<Employee> findEmployeesByExample(Employee example){
		long id = example.getId();
		String name = example.getName();
		String positionName = null;
		int salary = example.getSalary();
		LocalDateTime workStarted = example.getWorkStarted();
		String companyName = null;
		
		Position position = example.getPosition();
		if(position != null)
			positionName = position.getName();
		
		Company company = example.getCompany();
		if(company != null)
			companyName = company.getName();
		
		Specification<Employee> spec = Specification.where(null);
		
		if(id > 0)
			spec = spec.and(EmployeeSpecifications.hasId(id));
		
		if(StringUtils.hasText(name))
			spec = spec.and(EmployeeSpecifications.hasName(name));
		
		if(StringUtils.hasText(positionName))
			spec = spec.and(EmployeeSpecifications.hasPositionName(positionName));
		
		if(salary > 0)
			spec = spec.and(EmployeeSpecifications.hasSalary(salary));
		
		if(workStarted != null)
		spec = spec.and(EmployeeSpecifications.hasWorkStarted(workStarted));
		
		if(StringUtils.hasText(companyName))
			spec = spec.and(EmployeeSpecifications.hasCompanyName(companyName));
	
		return employeeRepository.findAll(spec, Sort.by("id"));
	}

}

package hu.webuni.hr.luterdav.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.model.CompanyType;
import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.repository.CompanyRepository;
import hu.webuni.hr.luterdav.repository.CompanyTypeRepository;
import hu.webuni.hr.luterdav.repository.EmployeeRepository;

@Service
public class CompanyService {
	
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	CompanyTypeRepository companyTypeRepository;
	
	@Transactional
	public Company save(Company company) {
		setCompanyType(company);
		return companyRepository.save(company);
	}
	
	@Transactional
	public Company update(Company company) {
		if (companyRepository.existsById(company.getId())) {
			setCompanyType(company);
			return companyRepository.save(company);
		}else {
			throw new NoSuchElementException();
		}
	}
	
	public List<Company> findAll(){
		return companyRepository.findAll();
	}
	
	public Optional<Company> findById(long id) {
		return companyRepository.findById(id);
	}
	
	public void delete(long id) {
		companyRepository.deleteById(id);
	}
	
	public void deleteAll() {
		companyRepository.deleteAll();
	}
	
	public void setCompanyType(Company company) {
        CompanyType companyType = company.getCompanyType();
        if (companyType != null) {
            String companyTypeName = companyType.getName();
            if (companyTypeName != null) {
                List<CompanyType> companyTypesByName = companyTypeRepository.findByName(companyTypeName);
                if (companyTypesByName.isEmpty()) {
                    companyType = companyTypeRepository.save(new CompanyType(companyTypeName));
                } else {
                    companyType = companyTypesByName.get(0);
                }
            }
        }
        company.setCompanyType(companyType);
    }
	
	@Transactional
	public Company addEmployee(long id, Employee employee) {
		Company company = companyRepository.findById(id).get();
		employeeRepository.save(employee);
		company.addEmployee(employee);
		return company;
	}
	
	@Transactional
	public Company deleteEmployee(long id, long employeeId) {
		Company company = companyRepository.findById(id).get();
		Employee employee = employeeRepository.findById(employeeId).get();
		employee.setCompany(null);
		company.getEmployees().remove(employee);
		employeeRepository.save(employee);	
		return company;
	}
	
	@Transactional
	public Company replaceEmployees(long id, List<Employee> employees) {
		Company company = companyRepository.findById(id).get();
		company.getEmployees().forEach(e -> e.setCompany(null));
		company.getEmployees().clear();
		
		for(Employee e: employees) {
			company.addEmployee(e);
			employeeRepository.save(e);
		}
		
		return company;
	}
	
}
	

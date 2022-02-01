package hu.webuni.hr.luterdav.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.model.Employee;

public interface CompanyRepository extends JpaRepository<Company, Long>{
	
	//List<Company> countBySalaryGreaterThan(Integer salary);

}

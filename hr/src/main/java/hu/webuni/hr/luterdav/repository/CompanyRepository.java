package hu.webuni.hr.luterdav.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.luterdav.model.AverageSalaryByPosition;
import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.model.Employee;

public interface CompanyRepository extends JpaRepository<Company, Long>{
	
	public List<Company> findByEmployeeWithSalaryHigherThan(Integer minSalary);
	
	public List<Company> findByEmployeeCountHigherThan(int minEmployeeCount);
	
	public List<AverageSalaryByPosition> findAverageSalariesByPosition(long companyId);

}

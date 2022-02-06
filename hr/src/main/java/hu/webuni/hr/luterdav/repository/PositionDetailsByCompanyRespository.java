package hu.webuni.hr.luterdav.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.luterdav.model.PositionDetailsByCompany;

public interface PositionDetailsByCompanyRespository extends JpaRepository<PositionDetailsByCompany, Long> {

	List<PositionDetailsByCompany>findByPositionNameAndCompanyId(String positionName, long companyId);
	
	@Modifying
	@Query("UPDATE Employee e "
			+ "SET e.salary = :minSalary "
			+ "WHERE e.id IN "
			+ "(SELECT e2.id FROM Employee e2 "
			+ "WHERE e2.position.name = :position "
			+ "AND e2.salary < :minSalary "
			+ "AND e2.company.id = :companyId)")
	public int updateSalaries (long companyId, String position, int minSalary);
	
}

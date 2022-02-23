package hu.webuni.hr.luterdav.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.luterdav.model.CompanyType;
import hu.webuni.hr.luterdav.model.Position;

public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long> {
	
	public List<CompanyType> findByName(String name);

}

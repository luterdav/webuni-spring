package hu.webuni.hr.luterdav.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.luterdav.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long>{
	
	//List<Company> countBySalaryGreaterThan(Integer salary);

}

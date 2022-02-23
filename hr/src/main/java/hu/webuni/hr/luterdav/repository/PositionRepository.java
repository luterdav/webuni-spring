package hu.webuni.hr.luterdav.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.luterdav.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long>{
	
	
	public List<Position> findByName(String name);

}

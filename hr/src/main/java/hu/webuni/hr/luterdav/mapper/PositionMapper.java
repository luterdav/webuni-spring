package hu.webuni.hr.luterdav.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.luterdav.dto.PositionDto;
import hu.webuni.hr.luterdav.dto.EmployeeDto;
import hu.webuni.hr.luterdav.model.Position;
import hu.webuni.hr.luterdav.model.Employee;

@Mapper(componentModel = "spring")
public interface PositionMapper {

	List<PositionDto> positionsToDtos(List<Position> positions);
	
	List<Position> dtosToPositions(List<PositionDto> positions);

	PositionDto positionToDto(Position position);
	
	Position dtoToPosition(PositionDto positionDto);

	@Mapping(target = "position", ignore = true)
	EmployeeDto employeeToDto(Employee employee);



	
	
//	@IterableMapping(qualifiedByName = "summary")
//	List<PositionDto> positionsToSummaryDtos(List<Position> positions);

	
//	@Mapping(target = "position", ignore = true)
//	@Mapping(target = "company.companyType", ignore = true)
	
//	@InheritInverseConfiguration
//	@Mapping(target = "position", ignore = true)
//	@Mapping(target = "company.companyType", ignore = true)
	
//	@Mapping(target = "employees", ignore = true)
//	@Named("summary")
//	PositionDto positionToSummaryDto(Position position);

	

}

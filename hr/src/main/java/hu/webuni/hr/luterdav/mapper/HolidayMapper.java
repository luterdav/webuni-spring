package hu.webuni.hr.luterdav.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.luterdav.dto.EmployeeDto;
import hu.webuni.hr.luterdav.dto.HolidayDto;
import hu.webuni.hr.luterdav.dto.PositionDto;
import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.model.Holiday;
import hu.webuni.hr.luterdav.model.Position;

@Mapper(componentModel = "spring")
public interface HolidayMapper {
	
	
	List<HolidayDto> holidaysToDtos (List<Holiday> holidays);
	
	List<Holiday> dtosToHolidays (List<HolidayDto> holidays);
	
	
	HolidayDto holidayToDto(Holiday holiday);

	//@Mapping(target = "holidayCreatedBy", ignore = true)
	Holiday dtoToHoliday(HolidayDto holidayDto);
	
	@Mapping(target = "position", source = "position.name")
	@Mapping(target = "company", ignore = true)
	EmployeeDto employeeToDto(Employee employee);

	@InheritInverseConfiguration
	Employee dtoToEmployee(EmployeeDto employeeDto);
	
	
//	
//	@Mapping(target = "company", ignore = true)
//	@Mapping(target = "position", source = "position.name")
//	EmployeeDto employeeToDto(Employee employee);
//	
//	
//	PositionDto positionToDto(Position position);
//	
//	@Mapping(target = "employees", ignore = true)
//	Position dtoToPosition(PositionDto positionDto);
//	
//	@InheritInverseConfiguration
//	@Mapping(target = "company.companyType", ignore = true)
//	Employee dtoToEmployee(EmployeeDto employeeDto);
	


}

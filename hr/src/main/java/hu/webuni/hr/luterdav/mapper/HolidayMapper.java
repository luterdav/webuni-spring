package hu.webuni.hr.luterdav.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.luterdav.dto.EmployeeDto;
import hu.webuni.hr.luterdav.dto.HolidayDto;
import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.model.Holiday;

@Mapper(componentModel = "spring")
public interface HolidayMapper {
	
	
	List<HolidayDto> holidaysToDtos (List<Holiday> holidays);
	
	List<Holiday> dtosToHolidays (List<HolidayDto> holidays);
	
	@Mapping(target = "holidayCreatedBy.position", ignore = true)
	@Mapping(target = "holidayCreatedBy.company", ignore = true)
//	@Mapping(target = "position", source = "position.name")
//	@Mapping(target = "company.companyType", ignore = true)
	HolidayDto holidayToDto(Holiday holiday);

	@InheritInverseConfiguration
//	@Mapping(target = "company.companyType", ignore = true)
	Holiday dtoToHoliday(HolidayDto holidayDto);
	


}

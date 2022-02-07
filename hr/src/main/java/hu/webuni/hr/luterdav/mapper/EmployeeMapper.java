package hu.webuni.hr.luterdav.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.luterdav.dto.EmployeeDto;
import hu.webuni.hr.luterdav.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	
	
	List<EmployeeDto> employeesToDtos (List<Employee> employees);
	
	List<Employee> dtosToEmployees (List<EmployeeDto> employees);
	
	@Mapping(target = "company.employees", ignore = true)
	@Mapping(target = "position", source = "position.name")
	@Mapping(target = "company.companyType", ignore = true)
	EmployeeDto employeeToDto(Employee employee);

	@InheritInverseConfiguration
	@Mapping(target = "company.companyType", ignore = true)
	Employee dtoToEmployee(EmployeeDto employeeDto);

}

package hu.webuni.hr.luterdav.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.luterdav.dto.EmployeeDto;
import hu.webuni.hr.luterdav.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	
	
	List<EmployeeDto> employeesToDtos (List<Employee> employees);
	
	List<Employee> dtosToEmployees (List<EmployeeDto> employees);
	
	@Mapping(target = "company.employees", ignore = true)
	EmployeeDto employeeToDto(Employee employee);

	Employee dtoToEmployee(EmployeeDto employeeDto);

}

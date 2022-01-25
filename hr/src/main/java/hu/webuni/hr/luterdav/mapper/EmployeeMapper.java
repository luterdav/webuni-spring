package hu.webuni.hr.luterdav.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.hr.luterdav.dto.EmployeeDto;
import hu.webuni.hr.luterdav.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	
	List<EmployeeDto> employeesToDtos (List<Employee> employees);
	
	List<Employee> dtosToEmployees (List<EmployeeDto> employees);
	
	EmployeeDto employeeToDto(Employee employee);

	Employee dtoToEmployee(EmployeeDto employeeDto);

}

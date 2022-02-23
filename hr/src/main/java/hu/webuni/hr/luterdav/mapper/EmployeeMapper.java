package hu.webuni.hr.luterdav.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.luterdav.dto.CompanyDto;
import hu.webuni.hr.luterdav.dto.EmployeeDto;
import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	
	
	List<EmployeeDto> employeesToDtos (List<Employee> employees);
	
	List<Employee> dtosToEmployees (List<EmployeeDto> employees);

	@Mapping(target = "position", source = "position.name")
	EmployeeDto employeeToDto(Employee employee);

	@InheritInverseConfiguration
	Employee dtoToEmployee(EmployeeDto employeeDto);
	
	
	@Mapping(target = "employees", ignore = true)
    CompanyDto companyToDto(Company company);
	
	@InheritInverseConfiguration
	Company dtoToCompany(CompanyDto companyDto);

}

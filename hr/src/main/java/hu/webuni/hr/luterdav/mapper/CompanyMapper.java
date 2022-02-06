package hu.webuni.hr.luterdav.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.luterdav.dto.CompanyDto;
import hu.webuni.hr.luterdav.dto.EmployeeDto;
import hu.webuni.hr.luterdav.dto.PositionDto;
import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.model.Position;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

	List<CompanyDto> companiesToDtos(List<Company> companies);

	@IterableMapping(qualifiedByName = "summary")
	List<CompanyDto> companiesToSummaryDtos(List<Company> companies);

	@Mapping(target = "companyType", source = "companyType.name")
	CompanyDto companyToDto(Company company);
	
	@Mapping(target = "company", ignore = true)
	@Mapping(target = "position", source = "position.name")
	EmployeeDto employeeToDto(Employee employee);
	
	@InheritInverseConfiguration
	Employee dtoToEmployee(EmployeeDto employeeDto);
	
//	@Mapping(target = "companies", ignore = true)
	PositionDto positionToDto(Position position);
	
	Position dtoToPosition(PositionDto positionDto);
	
	@Mapping(target = "employees", ignore = true)
	@Mapping(target = "companyType", source = "companyType.name")
	@Named("summary")
	CompanyDto companyToSummaryDto(Company company);

	@Mapping(target = "companyType.name", source = "companyType")
	Company dtoToCompany(CompanyDto companyDto);
	
	List<Company> dtosToCompanies(List<CompanyDto> companies);

}

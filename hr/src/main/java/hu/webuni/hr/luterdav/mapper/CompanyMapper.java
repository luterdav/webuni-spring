package hu.webuni.hr.luterdav.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.hr.luterdav.dto.CompanyDto;
import hu.webuni.hr.luterdav.model.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

	List<CompanyDto> companiesToDtos(List<Company> company);

	CompanyDto companyToDto(Company company);

	Company dtoToCompany(CompanyDto companyDto);

}

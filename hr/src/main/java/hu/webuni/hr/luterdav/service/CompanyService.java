package hu.webuni.hr.luterdav.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import hu.webuni.hr.luterdav.model.Company;

@Service
public class CompanyService {

	
private Map<Long, Company> companies = new HashMap<>();
	
	
	public Company save(Company company) {
		companies.put(company.getId(), company);
		return company;
	}
	
	public List<Company> findAll(){
		return new ArrayList<>(companies.values());
	}
	
	public Company findById(long id) {
		return companies.get(id);
	}
	
	public void delete(long id) {
		companies.remove(id);
	}

}

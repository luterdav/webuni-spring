package hu.webuni.hr.luterdav.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.luterdav.model.Position;
import hu.webuni.hr.luterdav.model.PositionDetailsByCompany;
import hu.webuni.hr.luterdav.repository.CompanyRepository;
import hu.webuni.hr.luterdav.repository.EmployeeRepository;
import hu.webuni.hr.luterdav.repository.PositionDetailsByCompanyRespository;
import hu.webuni.hr.luterdav.repository.PositionRepository;

@Service
public class PositionService {

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	PositionDetailsByCompanyRespository positionDetailsByCompanyRespository; 

	public Position save(Position position) {
		return positionRepository.save(position);
	}

	public Position update(Position position) {
		if (positionRepository.existsById(position.getId()))
			return positionRepository.save(position);
		else
			throw new NoSuchElementException();
	}

	public List<Position> findAll() {
		return positionRepository.findAll();
	}

	public Optional<Position> findById(long id) {
		return positionRepository.findById(id);
	}

	public void delete(long id) {
		positionRepository.deleteById(id);
	}


	 @Transactional
		public void increaseMinSalary(long companyId, String name, int newMinSalary) {
	    	
	    	List<PositionDetailsByCompany> positions = positionDetailsByCompanyRespository.findByPositionNameAndCompanyId(name, companyId);
			positions.forEach(p -> p.setMinSalary(newMinSalary));
			positionDetailsByCompanyRespository.updateSalaries(companyId, name, newMinSalary);
	    	
		}

}

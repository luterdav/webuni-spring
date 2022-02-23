package hu.webuni.hr.luterdav.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.luterdav.model.Position;
import hu.webuni.hr.luterdav.model.PositionDetailsByCompany;
import hu.webuni.hr.luterdav.model.Position;
import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.repository.PositionRepository;
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
	private PositionDetailsByCompanyRespository positionDetailsByCompanyRespository;

	@Transactional
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
	
	public void deleteAll() {
		positionRepository.deleteAll();
	}
	
	public void setPositionForEmployee(Employee employee) {
        Position position = employee.getPosition();
        if (position != null) {
            String positionName = position.getName();
            if (positionName != null) {
                List<Position> positionsByName = positionRepository.findByName(positionName);
                if (positionsByName.isEmpty()) {
                    position = positionRepository.save(new Position(positionName, null));
                } else {
                    position = positionsByName.get(0);
                }
            }
        }
        employee.setPosition(position);
    }


    @Transactional
	public void increaseMinSalary(long companyId, String name, int newMinSalary) {
		
//		positionRepository.findByName(name)
//		.forEach(p ->{
//			p.setMinSalary(newMinSalary);
//			p.getEmployees().forEach(e ->{
//				if (e.getSalary() < newMinSalary)
//					e.setSalary(newMinSalary);
//			});
//		});
    	
    	List<PositionDetailsByCompany> positions = positionDetailsByCompanyRespository.findByPositionNameAndCompanyId(name, companyId);
		positions.forEach(p -> p.setMinSalary(newMinSalary));
		positionDetailsByCompanyRespository.updateSalaries(companyId, name, newMinSalary);
    	
	}


}

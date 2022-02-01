package hu.webuni.hr.luterdav.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.luterdav.model.Position;
import hu.webuni.hr.luterdav.model.Position;
import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.repository.PositionRepository;
import hu.webuni.hr.luterdav.repository.CompanyRepository;
import hu.webuni.hr.luterdav.repository.EmployeeRepository;
import hu.webuni.hr.luterdav.repository.PositionRepository;

@Service
public class PositionService {

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private CompanyRepository companyRepository;

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


	//A.)

	public Position increaseMinSalary(String name, double newMinSalary) {
		List<Position> positions = positionRepository.findAll();
		List<Employee> employees = employeeRepository.findAll();
		Position position = positions.stream().filter(p -> p.getName().equals(name)).findFirst().get();

		position.setMinSalary(newMinSalary);
		positionRepository.save(position);

		employees.forEach(e -> {
			if (e.getSalary() < newMinSalary)
				e.setSalary(newMinSalary);
			employeeRepository.save(e);
		});

		return position;
	}
	
	/* B.)
	 * Itt olyasmi lehetett volna, hogy a Company alatt lett volna a Position és az alatt az Employee, 
	 * viszont ahhoz nagyon sok helyen kellett volna átírni a kódot.
	 */
	
	
	/* C.)
	 * A B.) feladat hiánya miatt a minSalary mindenhol módosul viszont annyit el tudtam érni, hogy a
	 * rendes salary csak az adott cég-nél módosuljon.
	 */
	

	public Company increaseMinSalaryByCompany(long companyId, String name, double newMinSalary) {
		Company company = companyRepository.findById(companyId).get();
		
		List<Employee> filteredEmployees = company.getEmployees().stream()
		.filter(e -> e.getPosition().getName().equals(name)).collect(Collectors.toList());
		
		filteredEmployees.forEach(e -> e.getPosition().setMinSalary(newMinSalary));

		filteredEmployees.forEach(e -> {
			if (e.getSalary() < newMinSalary)
				e.setSalary(newMinSalary);
			employeeRepository.save(e);
		});

		return filteredEmployees.stream().map(e -> e.getCompany()).findFirst().get();
	}

}

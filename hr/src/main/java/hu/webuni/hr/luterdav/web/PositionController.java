package hu.webuni.hr.luterdav.web;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

import hu.webuni.hr.luterdav.dto.CompanyDto;
import hu.webuni.hr.luterdav.dto.EmployeeDto;
import hu.webuni.hr.luterdav.dto.PositionDto;
import hu.webuni.hr.luterdav.dto.View.BaseData;
import hu.webuni.hr.luterdav.mapper.CompanyMapper;
import hu.webuni.hr.luterdav.mapper.EmployeeMapper;
import hu.webuni.hr.luterdav.mapper.PositionMapper;
import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.model.Position;
import hu.webuni.hr.luterdav.service.CompanyService;
import hu.webuni.hr.luterdav.service.PositionService;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private EmployeeMapper employeeMapper;
	@Autowired
	private PositionService positionService;
	@Autowired
	private PositionMapper positionMapper;

	@GetMapping
	public List<PositionDto> getAllPositions() {
		List<Position> positions = positionService.findAll();

		return positionMapper.positionsToDtos(positions);

	}

	@GetMapping("/{id}")
	public PositionDto getPositionById(@PathVariable long id) {
		Position position = findByIdOrThrow(id);

		return positionMapper.positionToDto(position);

	}
	

//	@PostMapping("/{name}")
//	public PositionDto increaseSalary(@PathVariable String name, @RequestParam(name = "salary") Integer newMinSalary) {
//		Position position = positionService.increaseMinSalary(name, newMinSalary);
//		try {
//			return positionMapper.positionToDto(position);
//		} catch (NoSuchElementException e) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//		}
//	}
	
	@PutMapping("/{id}/{name}")
	public void increaseSalaryByCompany(@PathVariable long id, @PathVariable String name, @RequestParam(name = "salary") Integer newMinSalary) {
		positionService.increaseMinSalary(id, name, newMinSalary);

	}


	private Position findByIdOrThrow(long id) {
		Position position = positionService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		return position;
	}
}

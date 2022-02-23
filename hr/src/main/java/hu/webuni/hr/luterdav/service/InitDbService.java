package hu.webuni.hr.luterdav.service;

import java.time.LocalDateTime;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hu.webuni.hr.luterdav.model.Company;
import hu.webuni.hr.luterdav.model.CompanyType;
import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.model.Holiday;
import hu.webuni.hr.luterdav.model.Position;
import hu.webuni.hr.luterdav.model.PositionDetailsByCompany;
import hu.webuni.hr.luterdav.repository.CompanyRepository;
import hu.webuni.hr.luterdav.repository.CompanyTypeRepository;
import hu.webuni.hr.luterdav.repository.EmployeeRepository;
import hu.webuni.hr.luterdav.repository.PositionDetailsByCompanyRespository;
import hu.webuni.hr.luterdav.repository.PositionRepository;

@Service
public class InitDbService {
	
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PositionRepository positionRepository;
	@Autowired
	CompanyTypeRepository companyTypeRepository;
	@Autowired
	PositionDetailsByCompanyRespository positionDetailsByCompanyRespository;
	@Autowired
	HolidayService holidayService;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public void clearDB() {
		employeeRepository.deleteAll();
		positionRepository.deleteAll();
		companyRepository.deleteAll();
	}
	
	@Transactional
	public void insertTestData(){
		
		//COMPANYTYPE
		
		CompanyType companytype1 = new CompanyType("Kft");
		CompanyType companytype2 = new CompanyType("Nyrt");
		CompanyType companytype3 = new CompanyType("Zrt");
		companyTypeRepository.save(companytype1);
		companyTypeRepository.save(companytype2);
		companyTypeRepository.save(companytype3);
		
		//COMPANIES
		
		Company testCompany1 = new Company();
		testCompany1.setRegistrationNumber(1111);
		testCompany1.setName("Apple");
		testCompany1.setAddress("USA");
		testCompany1.setCompanyType(companytype1);
		companyRepository.save(testCompany1);
		
		Company testCompany2 = new Company();
		testCompany2.setRegistrationNumber(2222);
		testCompany2.setName("Tesla");
		testCompany2.setAddress("USA");
		testCompany1.setCompanyType(companytype2);
		companyRepository.save(testCompany2);
		
		Company testCompany3 = new Company();
		testCompany3.setRegistrationNumber(3333);
		testCompany3.setName("Amazon");
		testCompany3.setAddress("USA");
		testCompany1.setCompanyType(companytype3);
		companyRepository.save(testCompany3);
		
		//POSITIONS
		
		Position testPosition1 = new Position();
		testPosition1.setName("Sales Manager");
		testPosition1.setEducation("University");
		positionRepository.save(testPosition1);
		
		Position testPosition2 = new Position();
		testPosition2.setName("Business Analyst");
		testPosition2.setEducation("University");
		positionRepository.save(testPosition2);
		
		Position testPosition3 = new Position();
		testPosition3.setName("Finance Manager");
		testPosition3.setEducation("University");
		positionRepository.save(testPosition3);
		
		Position testPosition4 = new Position();
		testPosition4.setName("Accountant");
		testPosition4.setEducation("College");
		positionRepository.save(testPosition4);
		
		Position testPosition5 = new Position();
		testPosition5.setName("Customer Service");
		testPosition5.setEducation("High School");
		positionRepository.save(testPosition5);
		
		//POSITION DETAILS
		
		PositionDetailsByCompany pd1 = new PositionDetailsByCompany();
		pd1.setCompany(testCompany1);
		pd1.setMinSalary(250000);
		pd1.setPosition(testPosition4);
		positionDetailsByCompanyRespository.save(pd1);
		
		PositionDetailsByCompany pd2 = new PositionDetailsByCompany();
		pd2.setCompany(testCompany2);
		pd2.setMinSalary(400000);
		pd2.setPosition(testPosition2);
		positionDetailsByCompanyRespository.save(pd2);
		
		PositionDetailsByCompany pd3 = new PositionDetailsByCompany();
		pd3.setCompany(testCompany3);
		pd3.setMinSalary(600000);
		pd3.setPosition(testPosition1);
		positionDetailsByCompanyRespository.save(pd3);
		
		//EMPLOYEES
		
		Employee testEmployee1 = new Employee();
		testEmployee1.setName("John");
		testEmployee1.setUsername("baka");
		testEmployee1.setPassword(passwordEncoder.encode("1234"));
		testEmployee1.setPosition(testPosition1);
		testEmployee1.setSalary(600_000);
		testEmployee1.setWorkStarted(LocalDateTime.of(2019,10,10,10,10,10));
		testEmployee1.setCompany(testCompany1);
		employeeRepository.save(testEmployee1);
		
		Employee testEmployee2 = new Employee();
		testEmployee2.setName("Peter");
		testEmployee2.setUsername("taka");
		testEmployee2.setPassword(passwordEncoder.encode("1324"));
		testEmployee2.setManager(testEmployee1);
		testEmployee2.setPosition(testPosition2);
		testEmployee2.setSalary(350_000);
		testEmployee2.setWorkStarted(LocalDateTime.of(2016,10,10,10,10,10));
		testEmployee2.setCompany(testCompany1);
		employeeRepository.save(testEmployee2);
		
		Employee testEmployee3 = new Employee();
		testEmployee3.setName("Frank");
		testEmployee3.setPosition(testPosition3);
		testEmployee3.setSalary(700_000);
		testEmployee3.setWorkStarted(LocalDateTime.of(2020,10,10,10,10,10));
		testEmployee3.setCompany(testCompany2);
		employeeRepository.save(testEmployee3);
		
		Employee testEmployee4 = new Employee();
		testEmployee4.setName("Bill");
		testEmployee4.setPosition(testPosition4);
		testEmployee4.setSalary(400_000);
		testEmployee4.setWorkStarted(LocalDateTime.of(2010,10,10,10,10,10));
		testEmployee4.setCompany(testCompany2);
		employeeRepository.save(testEmployee4);
		
		Employee testEmployee5 = new Employee();
		testEmployee5.setName("Harold");
		testEmployee5.setPosition(testPosition3);
		testEmployee5.setSalary(600_000);
		testEmployee5.setWorkStarted(LocalDateTime.of(2016,10,10,10,10,10));
		testEmployee5.setCompany(testCompany2);
		employeeRepository.save(testEmployee5);
		
		Employee testEmployee6 = new Employee();
		testEmployee6.setName("Tom");
		testEmployee6.setPosition(testPosition4);
		testEmployee6.setSalary(250_000);
		testEmployee6.setWorkStarted(LocalDateTime.of(2017,10,10,10,10,10));
		testEmployee6.setCompany(testCompany3);
		employeeRepository.save(testEmployee6);
		
		Employee testEmployee7 = new Employee();
		testEmployee7.setName("Zack");
		testEmployee7.setPosition(testPosition5);
		testEmployee7.setSalary(350_000);
		testEmployee7.setWorkStarted(LocalDateTime.of(2015,10,10,10,10,10));
		testEmployee7.setCompany(testCompany3);
		employeeRepository.save(testEmployee7);
		
		Employee testEmployee8 = new Employee();
		testEmployee8.setName("David");
		testEmployee8.setPosition(testPosition3);
		testEmployee8.setSalary(500_000);
		testEmployee8.setWorkStarted(LocalDateTime.of(2016,10,10,10,10,10));
		testEmployee8.setCompany(testCompany3);
		employeeRepository.save(testEmployee8);
		
		Employee testEmployee9 = new Employee();
		testEmployee9.setName("Susan");
		testEmployee9.setPosition(testPosition5);
		testEmployee9.setSalary(200_000);
		testEmployee9.setWorkStarted(LocalDateTime.of(2014,10,10,10,10,10));
		testEmployee9.setCompany(testCompany3);
		employeeRepository.save(testEmployee9);
		
		//HOLIDAY
		
		Holiday testHoliday1 = new Holiday();
		testHoliday1.setStartOfHoliday(LocalDateTime.of(2020, 1, 2, 0, 0, 0));
		testHoliday1.setEndOfHoliday(LocalDateTime.of(2020, 1, 8, 0, 0, 0));
        testHoliday1.setHolidayCreatedBy(testEmployee1);
        testHoliday1.setDateOfHolidayRequested(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        testHoliday1.setStatus("Declined");
        holidayService.save(testHoliday1);
      
        Holiday testHoliday2 = new Holiday();
        testHoliday2.setStartOfHoliday(LocalDateTime.of(2022, 3, 2, 0, 0, 0));
        testHoliday2.setEndOfHoliday(LocalDateTime.of(2022, 3, 8, 0, 0, 0));
        testHoliday2.setHolidayCreatedBy(testEmployee2);
        testHoliday2.setDateOfHolidayRequested(LocalDateTime.of(2022, 2, 2, 0, 0, 0));
        testHoliday2.setStatus("Waiting for approval");
        holidayService.save(testHoliday2);
        
        Holiday testHoliday3 = new Holiday();
        testHoliday3.setStartOfHoliday(LocalDateTime.of(2022, 4, 2, 0, 0, 0));
        testHoliday3.setEndOfHoliday(LocalDateTime.of(2022, 4, 8, 0, 0, 0));
        testHoliday3.setHolidayCreatedBy(testEmployee3);
        testHoliday3.setDateOfHolidayRequested(LocalDateTime.of(2022, 3, 2, 0, 0, 0));
        testHoliday3.setStatus("Waiting for approval");
        holidayService.save(testHoliday3);
        
        Holiday testHoliday4 = new Holiday();
        testHoliday4.setStartOfHoliday(LocalDateTime.of(2022, 3, 10, 0, 0, 0));
        testHoliday4.setEndOfHoliday(LocalDateTime.of(2022, 3, 18, 0, 0, 0));
        testHoliday4.setHolidayCreatedBy(testEmployee3);
        testHoliday4.setDateOfHolidayRequested(LocalDateTime.of(2022, 1,30, 0, 0, 0));
        testHoliday4.setStatus("Approved");
        holidayService.save(testHoliday4);
	}

}

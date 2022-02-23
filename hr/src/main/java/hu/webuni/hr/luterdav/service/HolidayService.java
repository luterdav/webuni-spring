package hu.webuni.hr.luterdav.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import hu.webuni.hr.luterdav.model.Employee;
import hu.webuni.hr.luterdav.model.Holiday;
import hu.webuni.hr.luterdav.repository.HolidayRepository;

@Service
public class HolidayService {

	@Autowired
	HolidayRepository holidayRepository;
	@Autowired
	PositionService positionService;

	@Transactional
	public Holiday save(Holiday holiday) {
		positionService.setPositionForEmployee(holiday.getHolidayCreatedBy());
		return holidayRepository.save(holiday);
	}

	@Transactional
	public Holiday update(Holiday holiday) {
		if (holidayRepository.existsById(holiday.getId())
				&& holiday.getStatus().equalsIgnoreCase("Waiting for approval"))
			return holidayRepository.save(holiday);
		else
			throw new NoSuchElementException();
	}

	public List<Holiday> findAll() {
		return holidayRepository.findAll();
	}

	public Optional<Holiday> findById(long id) {
		return holidayRepository.findById(id);
	}

	@Transactional
	public void delete(long id) {
		holidayRepository.deleteById(id);
	}

	@Transactional
	public void deleteAll() {
		holidayRepository.deleteAll();
	}

	@Transactional
	public void updateHolidayRequestStatus(long holidayRequestId, boolean approve) {
		Holiday holiday = holidayRepository.findById(holidayRequestId).get();
		if (holiday.getStatus().equalsIgnoreCase("Waiting for approval")
				&& holiday.getHolidayCreatedBy().getManager() == null)
			if (approve)
				holidayRepository.updateStatus(holidayRequestId, "Approved");
			else
				holidayRepository.updateStatus(holidayRequestId, "Declined");
	}

	@Transactional
	public List<Holiday> getAllHoidayRequests(int page, int size, String sort) {
		Pageable paging = PageRequest.of(page, size, Sort.by(sort));

		Page<Holiday> pagedResult = holidayRepository.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return holidayRepository.findAll();
		}
	}

	@Transactional
	public List<Holiday> getAllHoidayRequestsFilteredStatus(int page, int size, String sort, String filter) {
		Pageable paging = PageRequest.of(page, size, Sort.by(sort));

		Page<Holiday> pagedResult;
		if (filter != null)
			pagedResult = holidayRepository.findByStatusContaining(filter, paging);
		else
			pagedResult = holidayRepository.findAll(paging);

		if (pagedResult.hasContent())
			return pagedResult.getContent();
		else
			return holidayRepository.findAll();

	}

	public List<Holiday> findHolidayRequestsByExample(Holiday example) {
		long id = example.getId();
		String status = example.getStatus();
		String employeeName = null;
		LocalDateTime dateOfHolidayRequested = example.getDateOfHolidayRequested();
		LocalDateTime startOfHoliday = example.getStartOfHoliday();
		LocalDateTime endOfHoliday = example.getEndOfHoliday();

		Employee employee = example.getHolidayCreatedBy();
		if (employee != null)
			employeeName = employee.getName();

		Specification<Holiday> spec = Specification.where(null);

		if (StringUtils.hasText(status))
			spec = spec.and(HolidaySpecifications.hasStatus(status));

		if (StringUtils.hasText(employeeName))
			spec = spec.and(HolidaySpecifications.hasEmployeeName(employeeName));

		if (dateOfHolidayRequested != null)
			spec = spec.and(HolidaySpecifications.hasDateOfHolidayRequested(dateOfHolidayRequested));

		if (startOfHoliday != null && endOfHoliday != null)
			spec = spec.and(HolidaySpecifications.hasRangeOfHoliday(startOfHoliday, endOfHoliday));

		return holidayRepository.findAll(spec, Sort.by("id"));
	}

}

package hu.webuni.hr.luterdav.web;


import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

import hu.webuni.hr.luterdav.dto.HolidayDto;
import hu.webuni.hr.luterdav.mapper.HolidayMapper;
import hu.webuni.hr.luterdav.model.Holiday;
import hu.webuni.hr.luterdav.service.HolidayService;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {

	@Autowired
	HolidayService holidayService;
	@Autowired
	HolidayMapper holidayMapper;

	
	@GetMapping
	public List<HolidayDto> getAllHolidayRequests(
			@RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "3") int size, 
            @RequestParam(defaultValue = "id") String sort) {

		List<Holiday> allHoidayRequests = holidayService.getAllHoidayRequests(page, size, sort);

		return holidayMapper.holidaysToDtos(allHoidayRequests);
	}
	
	@GetMapping("/status")
	public List<HolidayDto> getFilteredHolidayRequests(
			@RequestParam(required = false) String filter,
			@RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "4") int size, 
            @RequestParam(defaultValue = "id") String sort) {

		List<Holiday> allHoidayRequests = holidayService.getAllHoidayRequestsFilteredStatus(page, size, sort, filter);

		return holidayMapper.holidaysToDtos(allHoidayRequests);
	}

	@PostMapping
	@PreAuthorize("#holidayDto.holidayCreatedBy.id == authentication.principal.employee.id")
	public HolidayDto newHolidayRequest(@RequestBody HolidayDto holidayDto) {
		Holiday holiday = holidayService.save(holidayMapper.dtoToHoliday(holidayDto));
		return holidayMapper.holidayToDto(holiday);
	}

	@PutMapping("/{holidayRequestId}/updateStatus")
	public void updateHolidayRequestStatus(@PathVariable long holidayRequestId, @RequestParam Boolean approve) {
		holidayService.updateHolidayRequestStatus(holidayRequestId, approve);
	}

	@PutMapping("/{holidayRequestId}/update")
	@PreAuthorize("#holidayDto.holidayCreatedBy.name == authentication.principal.employee.name")
	public HolidayDto updateHolidayRequest(@PathVariable long holidayRequestId, @RequestBody HolidayDto holidayDto) {
		holidayDto.setId(holidayRequestId);
		try {
			if (holidayDto.getStatus().equalsIgnoreCase("Waiting for approval"))
				return holidayMapper.holidayToDto(holidayService.update(holidayMapper.dtoToHoliday(holidayDto)));
			else
				return null;
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{holidayRequestId}/delete")
	@PreAuthorize("#holidayDto.holidayCreatedBy.name == authentication.principal.employee.name")
	public void deleteHolidayRequest(@PathVariable long holidayRequestId, @RequestBody HolidayDto holidayDto) {
		if(holidayDto.getStatus().equalsIgnoreCase("waiting for approval"))
			holidayService.delete(holidayRequestId);
	}

}

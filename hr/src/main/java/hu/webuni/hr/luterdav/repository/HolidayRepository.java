package hu.webuni.hr.luterdav.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.luterdav.model.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long>, JpaSpecificationExecutor<Holiday> {
	
	@Modifying
	@Transactional
	@Query("UPDATE Holiday h "
			+ "SET h.status = :newStatus "
			+ "WHERE h.id = :holidayRequestId")
	void updateStatus (long holidayRequestId, String newStatus);
	
	Page<Holiday> findByStatusContaining(String status, Pageable pageable);
	

}

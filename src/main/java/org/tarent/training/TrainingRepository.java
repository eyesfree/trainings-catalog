package org.tarent.training;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.tarent.training.Training;

@RestResource(path = "v1")
public interface TrainingRepository extends CrudRepository<Training, Long>{
	Training getById(long id);
	
	//@Query("select t from Training, d from TrainingDate join on id, where d.startDate > ?1 and d.startDate <?2")
	List<Training> findByStartDatesBetween(@RequestParam("startDatetime") 
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime, @RequestParam("endDatetime") 
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime);
}

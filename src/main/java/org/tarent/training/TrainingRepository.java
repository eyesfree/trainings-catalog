package org.tarent.training;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.tarent.training.Training;

@CrossOrigin(origins = {"http://localhost:3000", "http://trainings-ui.azurewebsites.net/", "https://trainings-ui.azurewebsites.net/"})
@RepositoryRestResource(collectionResourceRel = "training", path = "training")
public interface TrainingRepository extends CrudRepository<Training, Long>{
	Training getById(long id);
	
	@Query("SELECT d.training FROM TrainingDate d where d.startDate between :startDateTime and :endDateTime")
	List<Training> findByStartDatesBetween(@RequestParam("startDateTime") 
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime, @RequestParam("endDateTime") 
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime);
}

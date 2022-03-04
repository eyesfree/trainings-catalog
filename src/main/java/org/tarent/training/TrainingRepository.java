package org.tarent.training;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.tarent.training.Training;

@RepositoryRestResource(collectionResourceRel = "training", path = "training")
public interface TrainingRepository extends CrudRepository<Training, Long>{
	Training getById(long id);
	
	// @Query(value = "SELECT distinct * FROM TRAINING T left join TRAINING_DATE D on t.id=d.id where start_date BETWEEN ?1 AND ?2",
	//		nativeQuery = true)
	@Query("SELECT d.training FROM TrainingDate d where d.startDate between :startDateTime and :endDateTime")
	List<Training> findByStartDatesBetween(@RequestParam("startDateTime") 
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime, @RequestParam("endDateTime") 
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime);
}

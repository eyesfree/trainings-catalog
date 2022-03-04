package org.tarent.training;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TrainingsCatalogApplicationTests {
	
	@Autowired
	TrainingRepository repository;
	
	@Test
	void contextLoads() {
		assertThat(repository).isNotNull();
	}
	
	@Test
	@Transactional
	public void shouldUpdateExistingEntryInDBWithoutSave() {

		Training spring = new Training("Spring Boot for Java Developers", "spring-boot in depth",
		  "Ms. Anita Steinberg", new BigDecimal(500), Currency.getInstance(Locale.GERMANY)) ;

	    Training springSaved = repository.save(spring);

	    Long originalId = springSaved.getId();
	    
	    String newTrainingName = "Spring Boot for Dummies";
	    springSaved.setName(newTrainingName);
	    String newDescription = "spring-boot starter";
	    springSaved.setDescription(newDescription);
	    String newTeacher = "Ms. Anita Steinberger";
	    springSaved.setTeacher(newTeacher);
	    
	    Optional<Training> resultTraining = repository.findById(originalId);

	    assertThat(resultTraining.isPresent());
	    Training result = resultTraining.get();

	    assertThat(originalId).isEqualTo(result.getId());
	    assertThat(newTrainingName).isEqualTo(result.getName());
	    assertThat(newDescription).isEqualTo(result.getDescription());
	    assertThat(newTeacher).isEqualTo(result.getTeacher());
	}
	
	@Test
	@Transactional
	public void shouldAddDates() {

		Training spring = new Training("Rust for Java Developers", "rust in depth",
		  "Ms. Anita Steinberg", new BigDecimal(500), Currency.getInstance(Locale.GERMANY)) ;

	    Training springSaved = repository.save(spring);

	    Long originalId = springSaved.getId();
	    Optional<Training> resultTraining = repository.findById(originalId);

	    assertThat(resultTraining.isPresent());
	    Training result = resultTraining.get();
	    List<TrainingDate> trainingDates = result.getStartDates();

	    assertThat(trainingDates).isNull();
	    
	    TrainingDate date = new TrainingDate(LocalDateTime.parse("2022-12-03T10:00:00"));
	    springSaved.addStartDate(date);
	    
	    Optional<Training> afterDateAdding = repository.findById(originalId);
	    Training trainingAfterDateAdding = afterDateAdding.get();
	    List<TrainingDate> startDates = trainingAfterDateAdding.getStartDates();
		assertThat(startDates).isNotEmpty();
		assertThat(startDates.size()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	public void shouldAddParticipants() {

		Training spring = new Training("Python for Java Developers", "python in depth",
		  "Ms. Anita Steinberg", new BigDecimal(500), Currency.getInstance(Locale.GERMANY)) ;
		TrainingDate date1 = new TrainingDate(LocalDateTime.parse("2022-12-03T10:00:00"));
		TrainingDate date2 = new TrainingDate(LocalDateTime.parse("2022-12-08T09:30:00"));
	    spring.addStartDate(date1);
	    spring.addStartDate(date2);
	    
	    Participant newParticipant1 = new Participant("Anna", "Belova", "anna.belova@tarent.org", PaymentStatus.PAYED);
	    Participant newParticipant2 = new Participant("Stan", "Kirilov", "stan.kirilov@tarent.org", PaymentStatus.OUTSTANDING);

	    date1.addParticipant(newParticipant1);
	    date1.addParticipant(newParticipant2);
	    date1.addParticipant(newParticipant2);
	    date1.addParticipant(newParticipant2);
	    date1.addParticipant(newParticipant2);
	    date1.addParticipant(newParticipant2);
	    date1.addParticipant(newParticipant2);
	    date1.addParticipant(newParticipant2);
	    date1.addParticipant(newParticipant2);
	    date1.addParticipant(newParticipant2);
	    
	    Training springSaved = repository.save(spring);

	    Long originalId = springSaved.getId();
	    Optional<Training> resultTraining = repository.findById(originalId);

	    assertThat(resultTraining.isPresent());
	    Training result = resultTraining.get();
	    List<TrainingDate> trainingDates = result.getStartDates();
		assertThat(trainingDates).isNotEmpty();
		assertThat(trainingDates.size()).isEqualTo(2);
		assertThat(trainingDates.get(0).getFreePlaces()).isEqualTo(0);
		assertThat(trainingDates.get(1).getFreePlaces()).isEqualTo(10);
		
		assertThat(trainingDates.get(0).getStartDate()).isEqualTo(LocalDateTime.parse("2022-12-03T10:00:00"));
		assertThat(trainingDates.get(1).getStartDate()).isEqualTo(LocalDateTime.parse("2022-12-08T09:30:00"));
		
	}
	
	@Test
    public void whenFindByStartingDateTimeBetween_NoResults() {
		
		Training spring = new Training("Azure for Java Developers", "azure overview",
				  "Ms. Anita Steinberg", new BigDecimal(500), Currency.getInstance(Locale.GERMANY)) ;
				TrainingDate date1 = new TrainingDate(LocalDateTime.parse("2022-12-03T10:00:00"));
				TrainingDate date2 = new TrainingDate(LocalDateTime.parse("2022-12-08T09:30:00"));
			    spring.addStartDate(date1);
			    spring.addStartDate(date2);
	    repository.save(spring);
			    
	    Training angular = new Training("React", "react in depth",
				  "Ms. Anita Steinberg", new BigDecimal(500), Currency.getInstance(Locale.GERMANY)) ;
				TrainingDate date3 = new TrainingDate(LocalDateTime.parse("2022-01-03T10:00:00"));
				TrainingDate date4 = new TrainingDate(LocalDateTime.parse("2022-06-08T09:30:00"));
				angular.addStartDate(date3);
				angular.addStartDate(date4);
		repository.save(angular);
			    
        List<Training> result = repository.findByStartDatesBetween(
        		LocalDateTime.parse("2021-12-01T10:00:00"), LocalDateTime.parse("2021-12-10T10:30:00"));

        assertThat(result.size()).isEqualTo(0);
    }
	
	@Test
    public void whenFindByStartingDateTimeBetween_WithResults() {
		repository.deleteAll();
		Training spring = new Training("Spring Boot for Java Developers", "spring-boot in depth",
				  "Ms. Anita Steinberg", new BigDecimal(500), Currency.getInstance(Locale.GERMANY)) ;
				TrainingDate date1 = new TrainingDate(LocalDateTime.parse("2020-10-03T10:00:00"));
				TrainingDate date2 = new TrainingDate(LocalDateTime.parse("2021-10-08T09:30:00"));
			    spring.addStartDate(date1);
			    spring.addStartDate(date2);
	    repository.save(spring);
			    
	    Training angular = new Training("Angular", "angular in depth",
				  "Ms. Anita Steinberg", new BigDecimal(500), Currency.getInstance(Locale.GERMANY)) ;
				TrainingDate date3 = new TrainingDate(LocalDateTime.parse("2020-12-03T10:00:00"));
				TrainingDate date4 = new TrainingDate(LocalDateTime.parse("2021-12-08T09:30:00"));
				angular.addStartDate(date3);
				angular.addStartDate(date4);
		repository.save(angular);
			    
        LocalDateTime startDateTimeTest = LocalDateTime.parse("2021-01-01T10:00:00");
		LocalDateTime endDateTimeTest = LocalDateTime.parse("2022-12-12T10:30:00");
		List<Training> result = repository.findByStartDatesBetween(
        		startDateTimeTest, endDateTimeTest);

        assertThat(result.size()).isEqualTo(2);
    }
}

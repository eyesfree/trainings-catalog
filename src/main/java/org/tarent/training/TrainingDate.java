package org.tarent.training;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TrainingDate {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private LocalDateTime startDate;
	
	private int freePlaces = 10; 
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Participant> participants;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public int getFreePlaces() {
		return freePlaces;
	}

	public void setFreePlaces(int freePlaces) {
		this.freePlaces = freePlaces;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}
	
	public void addParticipant(Participant participant) {
		if(participants == null)
		{
			participants = new ArrayList<Participant>();
		}
		this.participants.add(participant);
		this.setFreePlaces(this.freePlaces - 1);
	}

	public TrainingDate() {
		super();
	}
	
	public TrainingDate(LocalDateTime startDate, int freePlaces,
			List<Participant> participants) {
		this(startDate, participants);
		this.freePlaces = freePlaces;
	}

	public TrainingDate(LocalDateTime startDate,
			List<Participant> participants) {
		this(startDate);
		this.participants = participants;
		if(this.participants != null) {
			this.freePlaces = 10 - this.participants.size();
		}
	}
	
	public TrainingDate(LocalDateTime startDate) {
		super();
		this.startDate = startDate;
	}
}

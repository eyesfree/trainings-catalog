package org.tarent.training;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Training {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private long id;
	
	private String name;
	
	private String description;
	
	private String teacher;
	
	private BigDecimal price;
	
	private Currency currency;
	
	private int duration; 
	
	private String durationFormat;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<TrainingDate> startDates;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDurationFormat() {
		return durationFormat;
	}

	public void setDurationFormat(String durationFormat) {
		this.durationFormat = durationFormat;
	}

	public List<TrainingDate> getStartDates() {
		return startDates;
	}

	public void setStartDates(List<TrainingDate> startDates) {
		this.startDates = startDates;
	}
	
	public void addStartDate(TrainingDate date) {
		if(this.startDates == null) {
			this.startDates = new ArrayList<TrainingDate>();
		}
		this.startDates.add(date);
	}

	public Training() {
		super();
	}

	public Training(String name, String description, String teacher, BigDecimal price, Currency currency) {
		super();
		this.name = name;
		this.description = description;
		this.teacher = teacher;
		this.price = price;
		this.currency = currency;
	}
}

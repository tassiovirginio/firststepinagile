package br.com.fa7.firststepinagile.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
public class activity {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	
	private String description;
	
	private int priority;
	
	private String color;
	
	private int state;
	
	private String currentResponsible;
	
	private String creator;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime dateCreation;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime dateStart;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime dateEnd;
	
	private int duration;

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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getCurrentResponsible() {
		return currentResponsible;
	}

	public void setCurrentResponsible(String currentResponsible) {
		this.currentResponsible = currentResponsible;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public DateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(DateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	public DateTime getDateStart() {
		return dateStart;
	}

	public void setDateStart(DateTime dateStart) {
		this.dateStart = dateStart;
	}

	public DateTime getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(DateTime dateEnd) {
		this.dateEnd = dateEnd;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	

}

package br.com.fa7.firststepinagile.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
public class Project implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String description;
	
	@ManyToOne
	private User creator;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime dateCreation;
	
	@OneToMany(mappedBy = "project", targetEntity = Sprint.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Sprint> projects;
	
	@OneToMany
	private Set<User> users;
	
	public Project() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public DateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(DateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Set<Sprint> getProjects() {
		return projects;
	}

	public void setProjects(Set<Sprint> projects) {
		this.projects = projects;
	}
	

}

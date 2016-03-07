package br.com.fa7.firststepinagile.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

@Entity
public class Story implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String description;
	
	private Double priority;
	
	private String color;
	
	private Long value;
	
	@ManyToOne
	private User currentResponsible;
	
	@ManyToOne
	private User creator;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime dateCreation;
	
	@OneToMany(mappedBy = "story", targetEntity = Activity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Activity> activitys;
	
	@ManyToOne
	@JoinColumn(name="sprint_id")
	private Sprint sprint;
	
	@ManyToOne
	private Project project;
	
	public Story() {}
	
	public Story(Sprint sprint) {
		this.sprint = sprint;
	}
	
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

	public Double getPriority() {
		return priority;
	}

	public void setPriority(Double priority) {
		this.priority = priority;
	}

	public String getColor() {
		if(color == null){
			color = "";
		}
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public User getCurrentResponsible() {
		return currentResponsible;
	}

	public void setCurrentResponsible(User currentResponsible) {
		this.currentResponsible = currentResponsible;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
	
	public Set<Activity> getActivitys() {
		return activitys;
	}

	public void setActivitys(Set<Activity> activitys) {
		this.activitys = activitys;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Story other = (Story) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

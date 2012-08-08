package br.com.fa7.firststepinagile.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
public class Sprint implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public enum States {
		OK, RUN, STOP ;
	}

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String description;
	
	private States state;
	
	@ManyToOne
	private User creator;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime dateCreation;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime dateStart;
	
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	private DateTime dateEnd;
	
	@OneToMany
	private Set<Story> storys;

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

	public States getState() {
		return state;
	}

	public void setState(States state) {
		this.state = state;
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
		Sprint other = (Sprint) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Set<Story> getStorys() {
		return storys;
	}

	public void setStorys(Set<Story> storys) {
		this.storys = storys;
	}


}
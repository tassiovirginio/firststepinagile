package br.com.fa7.firststepinagile.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	
	@ManyToOne
	private Project project;
	
	private Date dateCreation;
	
	private Date dateStart;
	
	private Date dateEnd;
	
	@OneToMany(mappedBy = "sprint", targetEntity = Story.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateStart() {
		return dateStart;
	}
	
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	
	public Date getDateStart2() {
		if(dateStart != null){
			return dateStart;
		}else{
			return null;	
		}
	}
	
	public void setDateStart2(Date dateStart) {
		this.dateStart = new Date();
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	
	public Date getDateEnd2() {
		if(dateEnd != null){
			return dateEnd;
		}
		return null;
	}

	public void setDateEnd2(Date dateEnd) {
		this.dateEnd = new Date();
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public Set<Story> getStorys() {
		return storys;
	}

	public void setStorys(Set<Story> storys) {
		this.storys = storys;
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

}

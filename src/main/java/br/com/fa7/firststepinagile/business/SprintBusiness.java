package br.com.fa7.firststepinagile.business;

import static org.hibernate.criterion.Order.desc;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.firststepinagile.business.dao.SprintDAO;
import br.com.fa7.firststepinagile.business.dao.StoryDAO;
import br.com.fa7.firststepinagile.entities.Project;
import br.com.fa7.firststepinagile.entities.Sprint;
import br.com.fa7.firststepinagile.entities.Story;

@Component
@Transactional 
public class SprintBusiness {
	
	@Autowired
	private SprintDAO sprintDAO;
	
	@Autowired
	private StoryDAO storyDAO;
	
	public int size(){
		return sprintDAO.listAll().size();
	}
	
	public void save(Sprint sprint){
		if(sprint.getId() == null){
			sprint.setDateCreation(new DateTime());
		}
		sprintDAO.save(sprint);
	}
	
	public void delete(Sprint sprint){
		sprintDAO.delete(sprint);
	}
	
	public List<Sprint> all(Project project){
		return sprintDAO.findByCriteriaReturnList(Restrictions.eq("project", project));
	}
	
	public List<Sprint> allOrderById(){
		return sprintDAO.findByCriteria(desc("id"));
	}
	
	public Sprint findById(Long id){
		Sprint sprint = sprintDAO.findById(id);
		sprint.getStorys().isEmpty();
		return sprint;
	}
	
	public void addStoryInSprint(Story story, Sprint sprint){
		sprint = sprintDAO.findById(sprint.getId());
		story = storyDAO.findById(story.getId());
		sprint.getStorys().add(story);
		story.setSprint(sprint);
		sprintDAO.save(sprint);
	}
	
	public void removeStoryInSprint(Story story, Sprint sprint){
		sprint = sprintDAO.findById(sprint.getId());
		story = storyDAO.findById(story.getId());
		sprint.getStorys().remove(story);
		story.setSprint(null);
		sprintDAO.save(sprint);
		storyDAO.save(story);
	}

}

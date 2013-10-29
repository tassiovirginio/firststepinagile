package br.com.fa7.firststepinagile.business;

import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.isNull;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.firststepinagile.business.dao.StoryDAO;
import br.com.fa7.firststepinagile.entities.Project;
import br.com.fa7.firststepinagile.entities.Sprint;
import br.com.fa7.firststepinagile.entities.Story;

@Component
@Transactional 
public class StoryBusiness {
	
	@Autowired
	private StoryDAO storyDAO; 
	
	public int size(){
		return storyDAO.listAll().size();
	}
	
	public void save(Story story){
		if(story.getId() == null){
			story.setPriority(nextStoryPriority(story.getProject()));
			story.setDateCreation(new DateTime());
		}
		storyDAO.save(story);
	}
	
	public void delete(Story story){
		storyDAO.delete(story);
	}
	
	public List<Story> all(){
		return storyDAO.listAll();
	}
	
	public List<Story> allOrderByAscPrioridade(Project project){
		return storyDAO.findByCriteria(asc("priority"),eq("project",project));
	}
	
	public List<Story> notSprintOrderByAscPrioridade(Project project){
		return storyDAO.findByCriteria(asc("priority"),isNull("sprint"),eq("project",project));
	}
	
	public List<Story> getStoryBySprint(Sprint sprint,Project project){
		return storyDAO.findByCriteria(asc("priority"),eq("sprint", sprint),eq("project",project));
	}
	
	public double lastStoryPriority(Project project){
		List<Story> list = allOrderByAscPrioridade(project);
		if(list.isEmpty())
			return 1000;	
		return list.get(list.size()-1).getPriority();
	}
	
	public double nextStoryPriority(Project project){
		return lastStoryPriority(project)+1;
	}
	
	public void upStoryPriority(Story story){
		story = storyDAO.findById(story.getId());
		List<Story> list = storyDAO.findByCriteria(asc("priority"));
		int index = list.indexOf(story);
		if(index != 0){
			Story story2 = list.get(index-1);
			double index1 = story2.getPriority();
			double index2 = story.getPriority();
			story2.setPriority(index2);
			story.setPriority(index1);
			save(story);
			save(story2);
		}
	}
	
	public void downStoryPriority(Story story){
		story = storyDAO.findById(story.getId());
		List<Story> list = storyDAO.findByCriteria(asc("priority"));
		int index = list.indexOf(story);
		if(list.size() > index+1){
			Story story2 = list.get(index+1);
			double index1 = story2.getPriority();
			double index2 = story.getPriority();
			story2.setPriority(index2);
			story.setPriority(index1);
			save(story);
			save(story2);
		}
	}

}

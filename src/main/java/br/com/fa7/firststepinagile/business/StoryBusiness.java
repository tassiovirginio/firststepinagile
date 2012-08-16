package br.com.fa7.firststepinagile.business;

import java.util.List;

import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.firststepinagile.business.dao.StoryDAO;
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
			story.setPriority(nextStoryPriority());
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
	
	public List<Story> allOrderByDescPrioridade(){
		return storyDAO.findByCriteria(	Order.asc("priority"));
	}
	
	public double lastStoryPriority(){
		List<Story> list = allOrderByDescPrioridade();
		if(list.isEmpty())
			return 1000;	
		return list.get(list.size()-1).getPriority();
	}
	
	public double nextStoryPriority(){
		return lastStoryPriority()+1;
	}
	
	public void upStoryPriority(Story story){
		story = storyDAO.findById(story.getId());
		List<Story> list = storyDAO.findByCriteria(Order.asc("priority"));
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
		List<Story> list = storyDAO.findByCriteria(Order.asc("priority"));
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

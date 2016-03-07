package br.com.fa7.firststepinagile.business;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.firststepinagile.business.dao.ActivityDAO;
import br.com.fa7.firststepinagile.entities.Activity;
import br.com.fa7.firststepinagile.entities.Story;
import br.com.fa7.firststepinagile.entities.User;

import static org.hibernate.criterion.Restrictions.*;
import static org.hibernate.criterion.Order.*;

@Component
@Transactional
public class ActivityBusiness {
	
	@Autowired
	private ActivityDAO activityDAO; 
	
	public int size(){
		return activityDAO.listAll().size();
	}
	
	public void save(Activity activity){
		if(activity.getId() == null){
			activity.setPriority(nextActivityPriority());
			activity.setDateCreation(new LocalDateTime());
		}
		activityDAO.save(activity);
	}
	
	public void delete(Activity activity){
		activityDAO.delete(activity);
	}
	
	public Activity findById(Long id){
		return activityDAO.findById(id);
	}
	
	public List<Activity> findActivityForUserAndState(User user, Integer state){
		return activityDAO.findByCriteria(
				desc("priority"),eq("currentResponsible", user),eq("state", state));
	}
	
	public List<Activity> findActivityForSprintAndState(Story story, Integer state){
		return activityDAO
				.findByCriteria(desc("priority"),eq("story", story),eq("state", state));
	}
	
	public List<Activity> findActivityByStory(Story story){
		return activityDAO.findByCriteria(
				asc("priority"),eq("story", story));
	}
	
	public List<Activity> allOrderByDescPrioridade(){
		return activityDAO.findByCriteria(asc("priority"));
	}
	
	public double lastActivityPriority(){
		List<Activity> list = allOrderByDescPrioridade();
		if(list.isEmpty())
			return 1000;	
		return list.get(list.size()-1).getPriority();
	}
	
	public double nextActivityPriority(){
		return lastActivityPriority()+1;
	}
	
	public void upActivityPriority(Activity activity, Story story){
		activity = activityDAO.findById(activity.getId());
		List<Activity> list = findActivityByStory(story);
		int index = list.indexOf(activity);
		if(index != 0){
			Activity activity2 = list.get(index-1);
			double index1 = activity2.getPriority();
			double index2 = activity.getPriority();
			activity2.setPriority(index2);
			activity.setPriority(index1);
			save(activity);
			save(activity2);
		}
	}
	
	public void downActivityPriority(Activity activity, Story story){
		activity = activityDAO.findById(activity.getId());
		List<Activity> list = findActivityByStory(story);
		int index = list.indexOf(activity);
		if(list.size() > index+1){
			Activity activity2 = list.get(index+1);
			double index1 = activity2.getPriority();
			double index2 = activity.getPriority();
			activity2.setPriority(index2);
			activity.setPriority(index1);
			save(activity);
			save(activity2);
		}
	}
	
	public void upActivityPriority(Activity activity, Story story, Integer state){
		activity = activityDAO.findById(activity.getId());
		List<Activity> list = findActivityForSprintAndState(story, state);
		int index = list.indexOf(activity);
		if(index != 0){
			Activity activity2 = list.get(index-1);
			double index1 = activity2.getPriority();
			double index2 = activity.getPriority();
			activity2.setPriority(index2);
			activity.setPriority(index1);
			save(activity);
			save(activity2);
		}
	}
	
	public void downActivityPriority(Activity activity, Story story, Integer state){
		activity = activityDAO.findById(activity.getId());
		List<Activity> list = findActivityForSprintAndState(story, state);
		int index = list.indexOf(activity);
		if(list.size() > index+1){
			Activity activity2 = list.get(index+1);
			double index1 = activity2.getPriority();
			double index2 = activity.getPriority();
			activity2.setPriority(index2);
			activity.setPriority(index1);
			save(activity);
			save(activity2);
		}
	}
	
	
	public void setActivityState(Activity activity,Integer state){
		activity = activityDAO.findById(activity.getId());
		activity.setState(state);
		activityDAO.save(activity);
	}
	
	
}

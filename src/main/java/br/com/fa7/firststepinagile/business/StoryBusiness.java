package br.com.fa7.firststepinagile.business;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.firststepinagile.business.dao.StoryDAO;
import br.com.fa7.firststepinagile.entities.Activity;
import br.com.fa7.firststepinagile.entities.Story;
import br.com.fa7.firststepinagile.entities.User;

@Component
@Transactional 
public class StoryBusiness {
	
	@Autowired
	private StoryDAO storyDAO; 
	
	public int size(){
		return storyDAO.listAll().size();
	}
	
	public void save(Story story){
		storyDAO.save(story);
	}
	
	public void delete(Story story){
		storyDAO.delete(story);
	}
	
	public List<Story> all(){
		return storyDAO.listAll();
	}
	
	public List<Story> allOrderByDescPrioridade(){
		return storyDAO.findByCriteria(	Order.desc("priority"));
	}

}

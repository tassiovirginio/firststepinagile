package br.com.fa7.firststepinagile.business;

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
		System.out.println("Salvando a Story");
		storyDAO.save(story);
	}

}

package br.com.fa7.firststepinagile.business;

import java.util.List;

import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.firststepinagile.business.dao.SprintDAO;
import br.com.fa7.firststepinagile.entities.Sprint;

@Component
@Transactional 
public class SprintBusiness {
	
	@Autowired
	private SprintDAO sprintDAO; 
	
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
	
	public List<Sprint> all(){
		return sprintDAO.listAll();
	}
	
	public List<Sprint> allOrderById(){
		return sprintDAO.findByCriteria(Order.desc("id"));
	}

}

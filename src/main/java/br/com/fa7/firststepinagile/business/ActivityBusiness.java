package br.com.fa7.firststepinagile.business;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.firststepinagile.business.dao.ActivityDAO;
import br.com.fa7.firststepinagile.entities.Activity;
import br.com.fa7.firststepinagile.entities.User;

@Component
@Transactional
public class ActivityBusiness {
	
	@Autowired
	private ActivityDAO activityDAO; 
	
	public int size(){
		return activityDAO.listAll().size();
	}
	
	
	public void save(Activity activity){
		activityDAO.save(activity);
	}
	
	public List<Activity> findActivityForUserAndState(User user, Integer state){
		return activityDAO.findByCriteria(
				Order.desc("priority"),
				Restrictions.eq("currentResponsible", user),
				Restrictions.eq("state", state)
				);
	}
}

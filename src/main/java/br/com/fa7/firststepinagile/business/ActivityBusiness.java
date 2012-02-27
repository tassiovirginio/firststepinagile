package br.com.fa7.firststepinagile.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fa7.firststepinagile.business.dao.ActivityDAO;
import br.com.fa7.firststepinagile.entities.Activity;

@Component
public class ActivityBusiness {
	
	@Autowired
	private ActivityDAO activityDAO;
	
	public int size(){
		return activityDAO.listAll().size();
	}
	
	
	public void save(Activity activity){
		activityDAO.save(activity);
	}
}

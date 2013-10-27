package br.com.fa7.firststepinagile.business;

import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Order.desc;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.firststepinagile.business.dao.ProjectDAO;
import br.com.fa7.firststepinagile.entities.Activity;
import br.com.fa7.firststepinagile.entities.Project;
import br.com.fa7.firststepinagile.entities.Story;
import br.com.fa7.firststepinagile.entities.User;

@Component
@Transactional
public class ProjectBusiness {
	
	@Autowired
	private ProjectDAO projectDAO; 
	
	public int size(){
		return projectDAO.listAll().size();
	}
	
	public void save(Project project){
		projectDAO.save(project);
	}
	
	public void delete(Project project){
		projectDAO.delete(project);
	}
	
	public Project findById(Long id){
		return projectDAO.findById(id);
	}
	
	public List<Project> listAll(){
		return projectDAO.listAll();
	}
	
}

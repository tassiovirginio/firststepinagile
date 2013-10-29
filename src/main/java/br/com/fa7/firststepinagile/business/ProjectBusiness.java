package br.com.fa7.firststepinagile.business;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.firststepinagile.business.dao.ProjectDAO;
import br.com.fa7.firststepinagile.entities.Project;

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
	
	public Set<Project> listAll(){
		return new HashSet<Project>(projectDAO.listAll());
	}
	
}

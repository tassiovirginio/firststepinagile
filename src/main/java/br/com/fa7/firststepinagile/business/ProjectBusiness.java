package br.com.fa7.firststepinagile.business;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.firststepinagile.business.dao.ConvitesDAO;
import br.com.fa7.firststepinagile.business.dao.ProjectDAO;
import br.com.fa7.firststepinagile.entities.Convite;
import br.com.fa7.firststepinagile.entities.Project;
import br.com.fa7.firststepinagile.entities.User;

@Component
@Transactional
public class ProjectBusiness {
	
	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private ConvitesDAO convitesDAO;
	
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
	
	public Set<Project> listAllByUser(User user){
		return new HashSet<Project>(projectDAO.findByCriteriaReturnList(Restrictions.eq("creator", user)));
	}
	
	public Set<Project> listAllByConvite(User user){
		Set<Project> setProject = new HashSet<Project>();
		List<Convite> listConvite = convitesDAO.findByCriteriaReturnList(Restrictions.eq("email", user.getLogin()));
		for (Convite convite : listConvite) {
			setProject.add(convite.getProject());
		}
		return setProject;
	}

    public List<Project> find(Long first, Long count){
        return projectDAO.findByCriteria(Order.asc("id"),first.intValue(),count.intValue());
    }
}

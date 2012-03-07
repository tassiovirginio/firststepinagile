package br.com.fa7.firststepinagile.business;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.firststepinagile.business.dao.UserDAO;
import br.com.fa7.firststepinagile.entities.User;

@Component
@Transactional 
public class UserBusiness {
	
	@Autowired
	private UserDAO userDAO; 
	
	public int size(){
		return userDAO.listAll().size();
	}
	
	
	public void save(User user){
		userDAO.save(user);
	}


	public boolean login(User user) {
		return true;
	}
	
	
	public boolean login(String login, String password) {
		List<User> listUser = userDAO.findByCriteria(Restrictions.eq("login", login),Restrictions.eq("password", password));
		if(listUser.size() > 0){
			return true;
		}
		return false;
	}
	
	
	public User findForLogin(String login){
		return userDAO.findByCriteria(Restrictions.eq("login", login)).get(0);
	}
}

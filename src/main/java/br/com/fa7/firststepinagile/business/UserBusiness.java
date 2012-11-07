package br.com.fa7.firststepinagile.business;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.firststepinagile.business.dao.UserDAO;
import br.com.fa7.firststepinagile.entities.User;

@Component
@Transactional 
public class UserBusiness {
	
	final static Logger logger = LoggerFactory.getLogger(UserBusiness.class);
	
	@Autowired
	private UserDAO userDAO; 
	
	public void delete(User user){
		userDAO.delete(user);
	}
	
	public int size(){
		logger.debug("Size List Users");
		return userDAO.listAll().size();
	}

	
	public void save(User user){
		logger.debug("Save User: " + user);
		userDAO.save(user);
	}


	public boolean login(String login, String password) {
		logger.debug("Login User: " + login);
		List<User> listUser = userDAO.findByCriteriaReturnList(Restrictions.eq("login", login),Restrictions.eq("password", password));
		if(listUser.size() > 0){
			return true;
		}
		return false;
	}
	
	
	public User findForLogin(String login){
		logger.debug("Find User: " + login);
		return userDAO.findByCriteriaReturnUniqueResult(Restrictions.eq("login", login));
	}
	
	public List<User> loadAllUser(){
		logger.debug("Load All User");
		return userDAO.listAll();
	}
}

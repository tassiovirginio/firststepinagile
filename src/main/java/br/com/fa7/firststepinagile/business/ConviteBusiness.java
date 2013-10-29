package br.com.fa7.firststepinagile.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fa7.firststepinagile.business.dao.ConvitesDAO;
import br.com.fa7.firststepinagile.entities.Convite;

@Component
@Transactional
public class ConviteBusiness {
	
	@Autowired
	private ConvitesDAO conviteDAO; 
	
	public int size(){
		return conviteDAO.listAll().size();
	}
	
	public void save(Convite convite){
		conviteDAO.save(convite);
	}
	
	public void delete(Convite convite){
		conviteDAO.delete(convite);
	}
	
	public Convite findById(Long id){
		return conviteDAO.findById(id);
	}
	
	public List<Convite> listAll(){
		return conviteDAO.listAll();
	}
	
}

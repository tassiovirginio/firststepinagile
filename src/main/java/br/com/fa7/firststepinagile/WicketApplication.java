package br.com.fa7.firststepinagile;

import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fa7.firststepinagile.business.ActivityBusiness;
import br.com.fa7.firststepinagile.entities.Activity;
import br.com.fa7.firststepinagile.pages.HomePage;

@Component
public class WicketApplication extends WebApplication{
	
	@Autowired
	private ActivityBusiness activityBusiness;
	
	@Override
	public Class<HomePage> getHomePage(){
		return HomePage.class;
	}

	@Override
	public void init(){
		super.init();
		System.out.println("----> "+activityBusiness.size());
		
		Activity activity = new Activity();
		activity.setName("Teste");
		activityBusiness.save(activity);
		
		System.out.println("----> "+activityBusiness.size());
		
		// add your configuration here
	}
}

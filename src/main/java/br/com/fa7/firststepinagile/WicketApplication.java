package br.com.fa7.firststepinagile;

import org.apache.wicket.protocol.http.WebApplication;

public class WicketApplication extends WebApplication{
	
	@Override
	public Class<HomePage> getHomePage(){
		return HomePage.class;
	}

	@Override
	public void init(){
		super.init();
		// add your configuration here
	}
}

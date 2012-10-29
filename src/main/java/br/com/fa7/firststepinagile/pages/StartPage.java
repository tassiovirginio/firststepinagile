package br.com.fa7.firststepinagile.pages;

import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.base.PageBase;

public class StartPage extends PageBase {
	
	private static final long serialVersionUID = 1L;
	
	public StartPage(User user) {
		super(user,"/tutorial/tutorial1.html");
	}

}

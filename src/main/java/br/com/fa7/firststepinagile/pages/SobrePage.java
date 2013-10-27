package br.com.fa7.firststepinagile.pages;

import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.base.PageBase;

public class SobrePage extends PageBase {
	
	private static final long serialVersionUID = 1L;
	
	public SobrePage(User user) {
		super(user);
		super.lkSobre.setEnabled(false);
	}

}

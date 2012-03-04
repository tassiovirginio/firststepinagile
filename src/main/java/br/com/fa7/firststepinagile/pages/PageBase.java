package br.com.fa7.firststepinagile.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.firststepinagile.business.ActivityBusiness;
import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.User;

public class PageBase extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private UserBusiness userBusiness;
	
	@SpringBean
	private ActivityBusiness activityBusiness;
	
	@SuppressWarnings({ "rawtypes", "serial" })
    public PageBase(final User user) {

		Link linkKanban = new Link("lkKanban"){
			@Override
    		public void onClick() {
    			setResponsePage(new KanbanPage(user));
    		}
    	};
    	add(linkKanban);
    	
    	Link lkManager = new Link("lkManager"){
    		@Override
    		public void onClick() {
    			setResponsePage(new ManagerPage(user));
    		}
    	};
    	add(lkManager);
    	
    	Link lkSobre = new Link("lkSobre"){
    		@Override
    		public void onClick() {
    			setResponsePage(new SobrePage(user));
    		}
    	};
    	add(lkSobre);
    	
    }
}

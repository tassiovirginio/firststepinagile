package br.com.fa7.firststepinagile.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class PageBase extends WebPage {
	private static final long serialVersionUID = 1L;

    public PageBase(final PageParameters parameters) {

    	Link linkKanban = new Link("lkKanban"){
    		@Override
    		public void onClick() {
    			setResponsePage(new KanbanPage(new PageParameters()));
    		}
    	};
    	add(linkKanban);
    	
    	Link lkManager = new Link("lkManager"){
    		@Override
    		public void onClick() {
    			setResponsePage(new ManagerPage(new PageParameters()));
    		}
    	};
    	add(lkManager);
    	
    	Link lkSobre = new Link("lkSobre"){
    		@Override
    		public void onClick() {
    			setResponsePage(new SobrePage(new PageParameters()));
    		}
    	};
    	add(lkSobre);
    	
    	
    }
}

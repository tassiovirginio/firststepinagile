package br.com.fa7.firststepinagile.pages.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.KanbanPage;
import br.com.fa7.firststepinagile.pages.SobrePage;
import br.com.fa7.firststepinagile.pages.SprintsPage;
import br.com.fa7.firststepinagile.pages.StartPage;
import br.com.fa7.firststepinagile.pages.StorysPage;

@SuppressWarnings({ "rawtypes", "serial" })
public class PageBase extends WebPage {
	
	private static final long serialVersionUID = 1L;

	public PageBase(final User user) {
		
		Link linkStart = new Link("lkStart") {
			@Override
			public void onClick() {
				setResponsePage(new StartPage(user));
			}
		};
		add(linkStart);

		Link lkStorys = new Link("lkStorys") {
			@Override
			public void onClick() {
				setResponsePage(new StorysPage(user));
			}
		};
		add(lkStorys);
		
		Link lkSprints = new Link("lkSprints") {
			@Override
			public void onClick() {
				setResponsePage(new SprintsPage(user));
			}
		};
		add(lkSprints);
		
		Link linkKanban = new Link("lkKanban") {
			@Override
			public void onClick() {
				setResponsePage(new KanbanPage(user));
			}
		};
		add(linkKanban);

		Link lkSobre = new Link("lkSobre") {
			@Override
			public void onClick() {
				setResponsePage(new SobrePage(user));
			}
		};
		add(lkSobre);

	}
	
}

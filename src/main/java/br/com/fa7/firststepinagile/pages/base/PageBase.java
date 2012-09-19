package br.com.fa7.firststepinagile.pages.base;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.KanbanPage;
import br.com.fa7.firststepinagile.pages.SobrePage;
import br.com.fa7.firststepinagile.pages.SprintsPage;
import br.com.fa7.firststepinagile.pages.StartPage;
import br.com.fa7.firststepinagile.pages.StorysPage;
import br.com.fa7.firststepinagile.pages.TaskPage;
import br.com.fa7.firststepinagile.pages.modal.UserModalPage;

import com.google.code.jqwicket.ui.mb.extruder.ExtruderOptions;
import com.google.code.jqwicket.ui.mb.extruder.ExtruderOptions.Position;
import com.google.code.jqwicket.ui.mb.extruder.ExtruderWebMarkupContainer;

@SuppressWarnings({ "rawtypes", "serial" })
public class PageBase extends WebPage {
	
	private static final long serialVersionUID = 1L;

	public PageBase(final User user) {
		
		createUserModal(user);
		
		createTutorial();
		
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
		
		Link lkTasks = new Link("lkTasks") {
			@Override
			public void onClick() {
				setResponsePage(new TaskPage(user));
			}
		};
		add(lkTasks);
		
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
	
	private void createTutorial(){
		add(new ExtruderWebMarkupContainer("extruderLeft1",  
                new ExtruderOptions("Tutorial",  
                        "_static/extruder/extruderLeft1.html")  
                        .position(Position.LEFT).width(300)  
                        .extruderOpacity(0.8f)));
	}
	
	private void createUserModal(final User user) {
		final ModalWindow userModal;
		add(userModal = new ModalWindow("userModal"));
		userModal.setCookieName("userModal-cookie");
		userModal.setCssClassName(ModalWindow.CSS_CLASS_GRAY);

		userModal.setPageCreator(new ModalWindow.PageCreator() {
			public Page createPage() {
				return new UserModalPage(
						PageBase.this.getPageReference(), userModal, user);
			}
		});
		
		userModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			public void onClose(AjaxRequestTarget target) {
//				setResponsePage(new StorysPage(user));
			}
		});

		AjaxLink ajLkUser = new AjaxLink<Void>("ajLkUser") {
			
			@Override
			public void onClick(AjaxRequestTarget target) {
				userModal.show(target);
			}
		};
		
		Label lbUser = new Label("lbUser",user.getName());
		ajLkUser.add(lbUser);
		add(ajLkUser);
	}
	
}

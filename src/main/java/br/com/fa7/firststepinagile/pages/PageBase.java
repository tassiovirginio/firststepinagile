package br.com.fa7.firststepinagile.pages;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.firststepinagile.business.ActivityBusiness;
import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.Activity;
import br.com.fa7.firststepinagile.entities.User;

public class PageBase extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private UserBusiness userBusiness;

	@SpringBean
	private ActivityBusiness activityBusiness;
	
	private ModalWindow activityModal;

	@SuppressWarnings({ "rawtypes", "serial" })
	public PageBase(final User user) {

		Link linkKanban = new Link("lkKanban") {
			@Override
			public void onClick() {
				setResponsePage(new KanbanPage(user));
			}
		};
		add(linkKanban);

		Link lkManager = new Link("lkManager") {
			@Override
			public void onClick() {
				setResponsePage(new ManagerPage(user));
			}
		};
		add(lkManager);

		Link lkSobre = new Link("lkSobre") {
			@Override
			public void onClick() {
				setResponsePage(new SobrePage(user));
			}
		};
		add(lkSobre);
		
		AjaxLink ajaxLink = new AjaxLink("lkNewActivity") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				getActivityModal(new Activity()).show(target);
			}
		};
		add(ajaxLink);
		
		
		activityModal = new ModalWindow("activityModal");
		add(activityModal);
		activityModal.setCookieName("Editar Atividade");
		activityModal.setResizable(false);
		activityModal.setInitialWidth(120);
		activityModal.setInitialHeight(40);
		activityModal.setWidthUnit("em");
		activityModal.setHeightUnit("em");
		activityModal.setCssClassName(ModalWindow.CSS_CLASS_GRAY);


	}
	
	public ModalWindow getActivityModal(final Activity activity){
		activityModal.setPageCreator(new ModalWindow.PageCreator() {
			public Page createPage() {
				return new ActivityPage(activityModal, activity);
			}
		});
		return activityModal;
	}
}

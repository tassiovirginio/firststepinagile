package br.com.fa7.firststepinagile.pages.modal;

import java.util.List;

import org.apache.wicket.PageReference;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.firststepinagile.business.SprintBusiness;
import br.com.fa7.firststepinagile.entities.Sprint;
import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.StartPage;

public class SprintModalPage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private SprintBusiness sprintBusiness;
	
	public SprintModalPage(final PageReference pageRefOrigem, final ModalWindow window, final User user){
		this(pageRefOrigem, window, user, null);
	}
	
	public SprintModalPage(final PageReference pageRefOrigem, final ModalWindow window, final User user, Sprint sprintSelected) {
		
		if(sprintSelected != null){
			System.out.println("Escolheu -> " + sprintSelected.getName());
		}
		
		List<Sprint> listSprints = sprintBusiness.allOrderById();
		
		ListView<Sprint> listViewSprint = new ListView<Sprint>("listViewSprint", listSprints) {
			@Override
			protected void populateItem(ListItem<Sprint> item) {
				final Sprint sprint = (Sprint)item.getModelObject();
				Label lbName = new Label("lbName", sprint.getName());
//				Label lbDescription = new Label("lbDescription", sprint.getDescription());
//				lbDescription.setEscapeModelStrings(false);
				Label lbId = new Label("lbId", sprint.getId().toString());
//				Label lbDateCreate = new Label("lbDateCreate", sprint.getDateCreation().toString("dd/MM/yyyy - HH:mm"));
				item.add(lbName);
//				item.add(lbDescription);
				item.add(lbId);
//				item.add(lbDateCreate);
				
//				Link lkStorys = new Link("lkDelete") {
//					@Override
//					public void onClick() {
//						System.out.println("Delete");
//					}
//				};
//				item.add(lkStorys);
//				
//				
//				item.add(new AjaxLink<Void>("lkEdit") {
//					@Override
//					public void onClick(AjaxRequestTarget target) {
//						System.out.println("Edit");
//					}
//				});
//				
				item.add(new Link("lkRight") {
					@Override
					public void onClick() {
						setResponsePage(new SprintModalPage(pageRefOrigem, window, user, sprint));
					}
				});
				
			}
		};
		add(listViewSprint);

	}
}

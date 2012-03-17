package br.com.fa7.firststepinagile.pages.panels;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.firststepinagile.business.ActivityBusiness;
import br.com.fa7.firststepinagile.entities.Activity;
import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.KanbanPage;
import br.com.fa7.firststepinagile.pages.PageBase;

public class ActivityStatePanel extends Panel {
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private ActivityBusiness activityBusiness;

	public ActivityStatePanel(String id, String stateName, final User user,Integer state, final PageBase pageResponse) {
		super(id);
		
		Label lbStateName = new Label("lbStateName", stateName);
		add(lbStateName);
		
		
		List<Activity> activities = activityBusiness.findActivityForUserAndState(user, state);
		
		ListView<Activity> listView = new ListView<Activity>("listActivity", activities) {

			@Override
			protected void populateItem(ListItem<Activity> item) {
				final Activity activity = item.getModelObject();
				
				WebMarkupContainer container = new WebMarkupContainer("color");
				container.add(new SimpleAttributeModifier("style", "background-color: #" + activity.getColor() +" !important;background-image: none!important;"));
				item.add(container);
				
				String nameStr = activity.getName();
				if(nameStr.length() > 41){
					nameStr = nameStr.substring(0,41);
				}
				
				AjaxLink ajaxLink = new AjaxLink("lkEditActivity") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						pageResponse.getActivityModal(activity).show(target);
					}
				};
				ajaxLink.add(new Label("lbId", activity.getId().toString() + " - "  + nameStr));
				container.add(ajaxLink);
				
				String descriptionStr = activity.getDescription().replace("<p>","").replace("</p>","");
				if(descriptionStr.length() > 41){
					descriptionStr = descriptionStr.substring(0,41);
				}
				container.add(new Label("lbDesc",descriptionStr+"..."));
				container.add(new Label("lbDateCreate", activity.getDateCreation().toString("HH:mm:ss dd/MM/yyyy")));
				container.add(new Label("lbPrio", activity.getPriority().toString()));
				
				container.add(new Link("lkUp"){
					@Override
					public void onClick() {
						activity.setPriority(activity.getPriority()+1);
						activityBusiness.save(activity);
						setResponsePage(new KanbanPage(user));
					}
				});
				
				container.add(new Link("lkNext"){
					@Override
					public void onClick() {
						if(activity.getState() < 3){
							activity.setState(activity.getState()+1);
							activityBusiness.save(activity);
							setResponsePage(new KanbanPage(user));
						}
					}
				});
				
				container.add(new Link("lkPrev"){
					@Override
					public void onClick() {
						if(activity.getState() > 1){
							activity.setState(activity.getState()-1);
							activityBusiness.save(activity);
							setResponsePage(new KanbanPage(user));
						}
					}
				});
				
				container.add(new Link("lkDown"){
					@Override
					public void onClick() {
						activity.setPriority(activity.getPriority()-1);
						activityBusiness.save(activity);
						setResponsePage(pageResponse);
					}
				});
				
			}
			
		};
		add(listView);
		
		
	}

}

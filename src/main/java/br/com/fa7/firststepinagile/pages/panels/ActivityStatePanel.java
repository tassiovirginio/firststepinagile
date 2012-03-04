package br.com.fa7.firststepinagile.pages.panels;

import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.firststepinagile.business.ActivityBusiness;
import br.com.fa7.firststepinagile.entities.Activity;
import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.KanbanPage;

public class ActivityStatePanel extends Panel {
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private ActivityBusiness activityBusiness;

	public ActivityStatePanel(String id, String stateName, final User user,Integer state, final Page pageResponse) {
		super(id);
		
		Label lbStateName = new Label("lbStateName", stateName);
		add(lbStateName);
		
		List<Activity> activities = activityBusiness.findActivityForUserAndState(user, state);
		
		ListView<Activity> listView = new ListView<Activity>("listActivity", activities) {

			@Override
			protected void populateItem(ListItem<Activity> item) {
				final Activity activity = item.getModelObject();
				
				item.add(new Label("lbId", activity.getId().toString()));
				item.add(new Label("lbDesc", activity.getDescription()));
				item.add(new Label("lbDateCreate", activity.getDateCreation().toString()));
				item.add(new Label("lbPrio", activity.getPriority().toString()));
				
				item.add(new Link("lkUp"){
					@Override
					public void onClick() {
						activity.setPriority(activity.getPriority()+1);
						activityBusiness.save(activity);
						setResponsePage(new KanbanPage(user));
					}
				});
				
				item.add(new Link("lkNext"){
					@Override
					public void onClick() {
						activity.setState(activity.getState()+1);
						activityBusiness.save(activity);
						setResponsePage(new KanbanPage(user));
					}
				});
				
				item.add(new Link("lkPrev"){
					@Override
					public void onClick() {
						activity.setState(activity.getState()-1);
						activityBusiness.save(activity);
						setResponsePage(new KanbanPage(user));
					}
				});
				
				item.add(new Link("lkDown"){
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

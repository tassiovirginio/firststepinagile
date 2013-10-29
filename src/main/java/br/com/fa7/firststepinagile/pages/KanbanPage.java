package br.com.fa7.firststepinagile.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.firststepinagile.business.ActivityBusiness;
import br.com.fa7.firststepinagile.business.SprintBusiness;
import br.com.fa7.firststepinagile.business.StoryBusiness;
import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.Activity;
import br.com.fa7.firststepinagile.entities.Story;
import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.base.PageBase;
import br.com.fa7.firststepinagile.pages.modal.ActivityModalPage;
import br.com.fa7.firststepinagile.pages.modal.StoryModalPage;

@SuppressWarnings({ "serial", "deprecation","rawtypes"})
public class KanbanPage extends PageBase {
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private StoryBusiness storyBusiness;
	
	@SpringBean
	private SprintBusiness sprintBusiness;
	
	@SpringBean
	private UserBusiness userBusiness;
	
	@SpringBean
	private ActivityBusiness activityBusiness;

	private ModalWindow storyModal;
	
	private ModalWindow activityModal;
	
	public KanbanPage(User user) {
		super(user);
		
		super.linkKanban.setEnabled(false);
		
		createPanelBacklog(user);
		createStoryModal(user);
		createActivityModal(user);
		
	}
	
	private void createStoryModal(final User user) {
		add(storyModal = new ModalWindow("storyModal"));
		storyModal.setCookieName("storyModal-cookie");
		storyModal.setCssClassName(ModalWindow.CSS_CLASS_GRAY);
		storyModal.setResizable(false);

		storyModal.setPageCreator(new ModalWindow.PageCreator() {
			public Page createPage() {
				return new StoryModalPage(KanbanPage.this.getPageReference(), storyModal, user, new Story());
			}
		});
		
		storyModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			public void onClose(AjaxRequestTarget target) {
				setResponsePage(new KanbanPage(user));
			}
		});

	}
	
	
	private void createPanelBacklog(final User user) {
		
		List<Story> listAllStory = new ArrayList<Story>();
		
		if(user.getSprint() != null){
			listAllStory = storyBusiness.getStoryBySprint(user.getSprint());
		}
		
		ListView<Story> listViewStoryBacklog = new ListView<Story>("lvStory", listAllStory) {
			@Override
			protected void populateItem(ListItem<Story> item) {
				final Story story = (Story)item.getModelObject();
				
				WebMarkupContainer webContainer = new WebMarkupContainer("tableStory");
				webContainer.add(new SimpleAttributeModifier("style","background-color: #" +story.getColor()));
				item.add(webContainer);
				
				Label lbId = new Label("lbId", story.getId().toString());
				Label lbName = new Label("lbName", story.getName());
//				Label lbDescription = new Label("lbDescription", story.getDescription());
				Label lbDateCreate = new Label("lbDateCreate", story.getDateCreation().toString("HH:mm dd/MM/yyyy"));
				
				webContainer.add(lbId);
				webContainer.add(lbName);
//				webContainer.add(lbDescription);
				webContainer.add(lbDateCreate);
				
				Link lkStorys = new Link("lkDelete") {
					@Override
					public void onClick() {
						storyBusiness.delete(story);
						setResponsePage(new StorysPage(user));
					}
				};
				webContainer.add(lkStorys);
				
				
				webContainer.add(new AjaxLink<Void>("lkEdit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						storyModal.setPageCreator(new ModalWindow.PageCreator() {
							public Page createPage() {
								return new StoryModalPage(KanbanPage.this.getPageReference(), storyModal, user, story);
							}
						});
						storyModal.show(target);
					}
				});
				
				
				createListActivity("listActivityStop",user,item,story,1);
				
				createListActivity("listActivityStarted",user,item,story,2);

				createListActivity("listActivityTest",user,item,story,3);
				
				createListActivity("listActivityDone",user,item,story,4);
				
				
			}
		};
		add(listViewStoryBacklog);
	}
	
	
	
	private void createListActivity(String name,final User user, WebMarkupContainer item, final Story story, final Integer state){
		
		List<Activity> listActivityStop = activityBusiness.findActivityForSprintAndState(story,state);
		
		item.add(new ListView<Activity>(name, listActivityStop) {
			@Override
			protected void populateItem(ListItem<Activity> item) {
				
				final Activity activity = (Activity)item.getModelObject();
				
				Label lbId = new Label("lbId", activity.getId().toString());
				Label lbName = new Label("lbName", activity.getName());
//				Label lbDescription = new Label("lbDescription", activity.getDescription());
				Label lbDateCreate = new Label("lbDateCreate", activity.getDateCreation().toString("HH:mm dd/MM/yyyy"));
				
				item.add(lbId);
				item.add(lbName);
//				item.add(lbDescription);
				item.add(lbDateCreate);
				
				item.add(new Link("lkDelete") {
					@Override
					public void onClick() {
						activityBusiness.delete(activity);
						setResponsePage(new KanbanPage(user));
					}
				});
				
				item.add(new AjaxLink<Void>("lkEdit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						activityModal.setPageCreator(new ModalWindow.PageCreator() {
							public Page createPage() {
								return new ActivityModalPage(KanbanPage.this.getPageReference(), storyModal, user, story, activity);
							}
						});
						activityModal.show(target);
					}
				});
				
				item.add(new Link("lkUp") {
					@Override
					public void onClick() {
						activityBusiness.upActivityPriority(activity,story,state);
						setResponsePage(new KanbanPage(user));
					}
				});
				
				item.add(new Link("lkDown") {
					@Override
					public void onClick() {
						activityBusiness.downActivityPriority(activity,story,state);
						setResponsePage(new KanbanPage(user));
					}
				});
				
				item.add(new Link("lkR") {
					@Override
					public void onClick() {
						if(state+1 <= 4)
						activityBusiness.setActivityState(activity,state+1);
						setResponsePage(new KanbanPage(user));
					}
				});
				
				item.add(new Link("lkL") {
					@Override
					public void onClick() {
						if(state-1 >= 1)
						activityBusiness.setActivityState(activity,state-1);
						setResponsePage(new KanbanPage(user));
					}
				});
				
				
				
			}
		});
	}
	
	private void createActivityModal(final User user) {
		add(activityModal = new ModalWindow("activityModal"));
		activityModal.setCookieName("activityModal-cookie");
		activityModal.setCssClassName(ModalWindow.CSS_CLASS_GRAY);
		activityModal.setWidthUnit("600px");
		activityModal.setHeightUnit("400px");
		
		activityModal.setPageCreator(new ModalWindow.PageCreator() {
			public Page createPage() {
				return new KanbanPage(user);
			}
		});
		
		activityModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			public void onClose(AjaxRequestTarget target) {
				setResponsePage(new KanbanPage(user));
			}
		});

	}

}

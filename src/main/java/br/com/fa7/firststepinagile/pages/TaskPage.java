package br.com.fa7.firststepinagile.pages;

import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.firststepinagile.business.ActivityBusiness;
import br.com.fa7.firststepinagile.business.StoryBusiness;
import br.com.fa7.firststepinagile.entities.Activity;
import br.com.fa7.firststepinagile.entities.Story;
import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.base.PageBase;
import br.com.fa7.firststepinagile.pages.modal.ActivityModalPage;
import br.com.fa7.firststepinagile.pages.modal.StoryModalPage;

public class TaskPage extends PageBase {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private StoryBusiness storyBusiness;
	
	@SpringBean
	private ActivityBusiness activityBusiness;
	
	private ModalWindow storyModal;
	
	private ModalWindow activityModal;
	
	private Story storySelected;
	
	public TaskPage(final User user) {
		this(user,null);
	}
	
	public TaskPage(final User user, Story story) {
		super(user);
		
		storySelected = story;
		
		createStoryModal(user);
		
		createActivityModal(user);
		
		createPanelBacklog(user);
		
		createPanelTasks(user,story);
		
		add(new AjaxLink<Void>("showStoryModal") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				storyModal.setPageCreator(new ModalWindow.PageCreator() {
					public Page createPage() {
						return new StoryModalPage(TaskPage.this.getPageReference(), storyModal, user, new Story());
					}
				});
				storyModal.show(target);
			}
		});
		
		
		add(new AjaxLink<Void>("showActivityModal") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				activityModal.setPageCreator(new ModalWindow.PageCreator() {
					public Page createPage() {
						return new ActivityModalPage(TaskPage.this.getPageReference(), activityModal, user, storySelected, new Activity());
					}
				});
				activityModal.show(target);
			}
		});
		
	}
	
	private void createPanelTasks(final User user, final Story story) {
		List<Activity> listActivity = activityBusiness.findActivityByStory(story);
		
		if(story != null){
			add(new Label("lbStoryId", story.getId().toString()));
		}else{
			add(new Label("lbStoryId",""));	
		}
		
		ListView<Activity> listViewActivity = new ListView<Activity>("listViewActivity", listActivity) {
			@Override
			protected void populateItem(ListItem<Activity> item) {
				final Activity activity = (Activity)item.getModelObject();
				Label lbName = new Label("lbName", activity.getName());
				Label lbDescription = new Label("lbDescription", activity.getDescription());
				lbDescription.setEscapeModelStrings(false);
				Label lbId = new Label("lbId", activity.getId().toString());
				Label lbDateCreate = new Label("lbDateCreate", activity.getDateCreation().toString("dd/MM/yyyy - HH:mm"));
				item.add(lbName);
				item.add(lbDescription);
				item.add(lbId);
				item.add(lbDateCreate);
				
				item.add(new Link("lkDelete") {
					@Override
					public void onClick() {
						activityBusiness.delete(activity);
						setResponsePage(new TaskPage(user,storySelected));
					}
				});
				
				item.add(new AjaxLink<Void>("lkEdit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						System.out.println("activity Edit -> " + activity.getId());
						activityModal.setPageCreator(new ModalWindow.PageCreator() {
							public Page createPage() {
								return new ActivityModalPage(TaskPage.this.getPageReference(), storyModal, user, story, activity);
							}
						});
						activityModal.show(target);
					}
				});
				
				item.add(new Link("lkUp") {
					@Override
					public void onClick() {
						activityBusiness.upActivityPriority(activity,story);
						setResponsePage(new TaskPage(user,storySelected));
					}
				});
				
				item.add(new Link("lkDown") {
					@Override
					public void onClick() {
						activityBusiness.downActivityPriority(activity,story);
						setResponsePage(new TaskPage(user,storySelected));
					}
				});
			}
		};
		
		add(listViewActivity);
	}

	private void createPanelBacklog(final User user) {
		List<Story> listAllStory = storyBusiness.allOrderByDescPrioridade();
		
		ListView<Story> listViewStoryBacklog = new ListView<Story>("lvStory", listAllStory) {
			@Override
			protected void populateItem(ListItem<Story> item) {
				final Story story = (Story)item.getModelObject();
				Label lbName = new Label("lbName", story.getName());
				Label lbDescription = new Label("lbDescription", story.getDescription());
				lbDescription.setEscapeModelStrings(false);
				Label lbId = new Label("lbId", story.getId().toString());
				Label lbDateCreate = new Label("lbDateCreate", story.getDateCreation().toString("dd/MM/yyyy - HH:mm"));
				item.add(lbName);
				item.add(lbDescription);
				item.add(lbId);
				item.add(lbDateCreate);
				
				Link lkSelect = new Link("lkSelect") {
					@Override
					public void onClick() {
						storySelected = story;
						setResponsePage(new TaskPage(user,story));
					}
				};
				item.add(lkSelect);
				
				Link lkStorys = new Link("lkDelete") {
					@Override
					public void onClick() {
						storyBusiness.delete(story);
						setResponsePage(new TaskPage(user));
					}
				};
				item.add(lkStorys);
				
				
				item.add(new AjaxLink<Void>("lkEdit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						storyModal.setPageCreator(new ModalWindow.PageCreator() {
							public Page createPage() {
								return new StoryModalPage(TaskPage.this.getPageReference(), storyModal, user, story);
							}
						});
						storyModal.show(target);
					}
				});
				
				item.add(new Link("lkUp") {
					@Override
					public void onClick() {
						storyBusiness.upStoryPriority(story);
						setResponsePage(new TaskPage(user,storySelected));
					}
				});
				
				item.add(new Link("lkDown") {
					@Override
					public void onClick() {
						storyBusiness.downStoryPriority(story);
						setResponsePage(new TaskPage(user,storySelected));
					}
				});
			}
		};
		add(listViewStoryBacklog);
	}

	private void createStoryModal(final User user) {
		add(storyModal = new ModalWindow("storyModal"));
		storyModal.setCookieName("storyModal-cookie");
		storyModal.setCssClassName(ModalWindow.CSS_CLASS_GRAY);
		storyModal.setResizable(false);

		storyModal.setPageCreator(new ModalWindow.PageCreator() {
			public Page createPage() {
				return new StoryModalPage(TaskPage.this.getPageReference(), storyModal, user, new Story());
			}
		});
		
		storyModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			public void onClose(AjaxRequestTarget target) {
				setResponsePage(new TaskPage(user));
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
				return new ActivityModalPage(TaskPage.this.getPageReference(), activityModal, user,storySelected, new Activity());
			}
		});
		
		activityModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			public void onClose(AjaxRequestTarget target) {
				setResponsePage(new TaskPage(user,storySelected));
			}
		});

	}

}

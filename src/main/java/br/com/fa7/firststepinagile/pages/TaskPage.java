package br.com.fa7.firststepinagile.pages;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
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

@SuppressWarnings({ "unchecked", "serial", "deprecation","rawtypes"})
public class TaskPage extends PageBase {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private StoryBusiness storyBusiness;
	
	@SpringBean
	private ActivityBusiness activityBusiness;
	
	@SpringBean
	private UserBusiness userBusiness;
	
	@SpringBean
	private SprintBusiness sprintBusiness;
	
	private ModalWindow storyModal;
	
	private ModalWindow activityModal;
	
	private Story storySelected;
	
	public TaskPage(final User user) {
		this(user,null,2);
	}
	
	public TaskPage(final User user, int filter) {
		this(user,null,filter);
	}
	
	public TaskPage(final User user, Story story) {
		this(user,story,2);
	}
	
	public TaskPage(final User user, Story story, int filter) {
		super(user);
		
		super.lkTasks.setEnabled(false);
		
		storySelected = story;
		
		createStoryModal(user,filter);
		
		createActivityModal(user,filter);
		
		createPanelBacklog(user,filter);
		
		createRadioGroup(user,filter);
		
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
//				Label lbDescription = new Label("lbDescription", activity.getDescription());
//				lbDescription.setEscapeModelStrings(false);
				Label lbId = new Label("lbId", activity.getId().toString());
				Label lbDateCreate = new Label("lbDateCreate", activity.getDateCreation().toString("dd/MM/yyyy - HH:mm"));
				
				WebMarkupContainer webContainer = new WebMarkupContainer("tableStory2");
				webContainer.add(new SimpleAttributeModifier("style","background-color: #" +story.getColor()));
				item.add(webContainer);
				
				webContainer.add(lbName);
//				webContainer.add(lbDescription);
				webContainer.add(lbId);
				webContainer.add(lbDateCreate);
				
				webContainer.add(new Link("lkDelete") {
					@Override
					public void onClick() {
						activityBusiness.delete(activity);
						setResponsePage(new TaskPage(user,storySelected));
					}
				});
				
				webContainer.add(new AjaxLink<Void>("lkEdit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						activityModal.setPageCreator(new ModalWindow.PageCreator() {
							public Page createPage() {
								return new ActivityModalPage(TaskPage.this.getPageReference(), storyModal, user, story, activity);
							}
						});
						activityModal.show(target);
					}
				});
				
				webContainer.add(new Link("lkUp") {
					@Override
					public void onClick() {
						activityBusiness.upActivityPriority(activity,story);
						setResponsePage(new TaskPage(user,storySelected));
					}
				});
				
				webContainer.add(new Link("lkDown") {
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

	private void createPanelBacklog(final User user, final int filter) {
		
		List<Story> listAllStory = null;
		
		if(filter == 1){
			listAllStory = storyBusiness.allOrderByAscPrioridade(user.getProjectAtual());
		}else if(filter == 2){
			listAllStory = storyBusiness.notSprintOrderByAscPrioridade(user.getProjectAtual());
		}else if(filter == 3){
			listAllStory = storyBusiness.getStoryBySprint(user.getSprint(),user.getProjectAtual());
		}
		
		ListView<Story> listViewStoryBacklog = new ListView<Story>("lvStory", listAllStory) {
			@Override
			protected void populateItem(ListItem<Story> item) {
				final Story story = (Story)item.getModelObject();
				Label lbName = new Label("lbName", story.getName());
//				Label lbDescription = new Label("lbDescription", story.getDescription());
				Label lbActivitysSize = new Label("lbActivitysSize", story.getActivitys().size()+"");
//				lbDescription.setEscapeModelStrings(false);
				Label lbId = new Label("lbId", story.getId().toString());
				Label lbDateCreate = new Label("lbDateCreate", story.getDateCreation().toString("dd/MM/yyyy - HH:mm"));
				
				WebMarkupContainer webContainer = new WebMarkupContainer("tableStory");
				webContainer.add(new SimpleAttributeModifier("style","background-color: #" +story.getColor()));
				item.add(webContainer);
				
				webContainer.add(lbName);
//				webContainer.add(lbDescription);
				webContainer.add(lbActivitysSize);
				webContainer.add(lbId);
				webContainer.add(lbDateCreate);
				
				Link lkSelect = new Link("lkSelect") {
					@Override
					public void onClick() {
						storySelected = story;
						setResponsePage(new TaskPage(user,story,filter));
					}
				};
				webContainer.add(lkSelect);
				
				Link lkStorys = new Link("lkDelete") {
					@Override
					public void onClick() {
						storyBusiness.delete(story);
						setResponsePage(new TaskPage(user,filter));
					}
				};
				webContainer.add(lkStorys);
				
				
				webContainer.add(new AjaxLink<Void>("lkEdit") {
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
				
				webContainer.add(new Link("lkUp") {
					@Override
					public void onClick() {
						storyBusiness.upStoryPriority(story);
						setResponsePage(new TaskPage(user,storySelected,filter));
					}
				});
				
				webContainer.add(new Link("lkDown") {
					@Override
					public void onClick() {
						storyBusiness.downStoryPriority(story);
						setResponsePage(new TaskPage(user,storySelected,filter));
					}
				});
			}
		};
		add(listViewStoryBacklog);
	}
	
	
	private void createRadioGroup(final User user, int filter){
		RadioGroup group = new RadioGroup("group", new Model("radio1"));
		add(group);
		
		Radio radio1 = new Radio("radio1");
		radio1.add(new AjaxEventBehavior("onclick") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				setResponsePage(new TaskPage(user,1));
			}
		});
		
		Radio radio2 = new Radio("radio2");
		radio2.add(new AjaxEventBehavior("onclick") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				setResponsePage(new TaskPage(user,2));
			}
		});
		
		Radio radio3 = new Radio("radio3");
		radio3.add(new AjaxEventBehavior("onclick") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				setResponsePage(new TaskPage(user,3));
			}
		});
		
		if(filter == 1){
			radio1.add( new AttributeModifier( "checked", new Model( "true" ) ) );
		}else if(filter == 2){
			radio2.add( new AttributeModifier( "checked", new Model( "true" ) ) );
		}else if(filter == 3){
			radio3.add( new AttributeModifier( "checked", new Model( "true" ) ) );
		}
		
		group.add(radio1);
		group.add(radio2);
		group.add(radio3);
	}

	private void createStoryModal(final User user, final int filter) {
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
				setResponsePage(new TaskPage(user,filter));
			}
		});

	}
	
	private void createActivityModal(final User user,final int filter) {
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
				setResponsePage(new TaskPage(user,storySelected,filter));
			}
		});

	}

}

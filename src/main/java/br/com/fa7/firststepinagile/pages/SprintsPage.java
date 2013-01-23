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
import org.apache.wicket.util.string.StringValue;

import br.com.fa7.firststepinagile.business.SprintBusiness;
import br.com.fa7.firststepinagile.business.StoryBusiness;
import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.Sprint;
import br.com.fa7.firststepinagile.entities.Story;
import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.base.PageBase;
import br.com.fa7.firststepinagile.pages.modal.SprintModalPage;
import br.com.fa7.firststepinagile.pages.modal.StoryModalPage;

public class SprintsPage extends PageBase {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private StoryBusiness storyBusiness;
	
	@SpringBean
	private SprintBusiness sprintBusiness;
	
	@SpringBean
	private UserBusiness userBusiness;

	private ModalWindow sprintModal;
	
	private ModalWindow storyModal;
	
	public SprintsPage(User user) {
		this(user,user.getSprint());	
	}

	public SprintsPage(User user, Sprint sprint) {
		super(user,"/tutorial/tutorial3.html");
		
		createSprintModal(user);
		
		createBarSprintModal(user);
		
		createPanelBacklog(user,sprint);
		
		createPanelSprint(user,sprint);
		
		createStoryModal(user);
		
	}
	
	private void createStoryModal(final User user) {
		
		add(new AjaxLink<Void>("showStoryModal") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				storyModal.setPageCreator(new ModalWindow.PageCreator() {
					public Page createPage() {
						return new StoryModalPage(SprintsPage.this.getPageReference(), storyModal, user, new Story());
					}
				});
				storyModal.show(target);
			}
		});
		
		add(new AjaxLink<Void>("showStoryModal2") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				storyModal.setPageCreator(new ModalWindow.PageCreator() {
					public Page createPage() {
						return new StoryModalPage(SprintsPage.this.getPageReference(), storyModal, user, new Story(user.getSprint()));
					}
				});
				storyModal.show(target);
			}
		});
		
		add(storyModal = new ModalWindow("storyModal"));
		storyModal.setCookieName("storyModal-cookie");
		storyModal.setCssClassName(ModalWindow.CSS_CLASS_GRAY);
		storyModal.setResizable(false);

		storyModal.setPageCreator(new ModalWindow.PageCreator() {
			public Page createPage() {
				return new StoryModalPage(SprintsPage.this.getPageReference(), storyModal, user, new Story());
			}
		});
		
		storyModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			public void onClose(AjaxRequestTarget target) {
				setResponsePage(new SprintsPage(user));
			}
		});

	}
	
	private void createBarSprintModal(final User user) {
		
		String dateEnd = "";
		
		if(user.getSprint() != null && user.getSprint().getDateEnd() != null)
		dateEnd = user.getSprint().getDateEnd().toString("dd/MM/yyyy");
		
		if(user.getSprint() != null){
			add(new Label("lbSprintName",user.getSprint().getName() + " - " + user.getSprint().getDateStart().toString("dd/MM/yyyy") 
					+ " - " + dateEnd));
		}else{
			add(new Label("lbSprintName",""));	
		}
		
		add(new AjaxLink<Void>("lkSprintModal") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				sprintModal.setPageCreator(new ModalWindow.PageCreator() {
					public Page createPage() {
						return new SprintModalPage(SprintsPage.this.getPageReference(), sprintModal, user);
					}
				});
				sprintModal.show(target);
			}
		});
		
	}

	private void createSprintModal(final User user) {
		add(sprintModal = new ModalWindow("sprintModal"));
		sprintModal.setCookieName("storyModal-cookie");
		sprintModal.setCssClassName(ModalWindow.CSS_CLASS_GRAY);
		sprintModal.setResizable(false);

		sprintModal.setPageCreator(new ModalWindow.PageCreator() {
			public Page createPage() {
				return new SprintModalPage(SprintsPage.this.getPageReference(), sprintModal, user);
			}
		});
		
		
		sprintModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			@Override
			public void onClose(AjaxRequestTarget target) {
				StringValue sprintId = SprintsPage.this.getPageParameters().get("sprintId");
				if(!sprintId.isNull()){
					Sprint sprint = sprintBusiness.findById(sprintId.toLong());;
					user.setSprint(sprint);
					userBusiness.save(user);
					setResponsePage(new SprintsPage(user,sprint));
				}
				setResponsePage(new SprintsPage(user));
			}
		});

	}
	
	
	private void createPanelBacklog(final User user, final Sprint sprint) {
		List<Story> listAllStory = storyBusiness.allOrderByAscPrioridade();
		
		ListView<Story> listViewStoryBacklog = new ListView<Story>("lvStory", listAllStory) {
			@Override
			protected void populateItem(ListItem<Story> item) {
				final Story story = (Story)item.getModelObject();
				Label lbName = new Label("lbName", story.getName());
				Label lbDescription = new Label("lbDescription", story.getDescription());
				Label lbActivitysSize = new Label("lbActivitysSize", story.getActivitys().size()+"");
				lbDescription.setEscapeModelStrings(false);
				Label lbId = new Label("lbId", story.getId().toString());
				Label lbDateCreate = new Label("lbDateCreate", story.getDateCreation().toString("dd/MM/yyyy - HH:mm"));
				
				
				WebMarkupContainer webContainer = new WebMarkupContainer("tableStory");
				webContainer.add(new SimpleAttributeModifier("style","background-color: #" +story.getColor()));
				item.add(webContainer);
				
				
				webContainer.add(lbName);
				webContainer.add(lbDescription);
				webContainer.add(lbActivitysSize);
				webContainer.add(lbId);
				webContainer.add(lbDateCreate);
				
				Link lkStorys = new Link("lkDelete") {
					@Override
					public void onClick() {
						storyBusiness.delete(story);
						setResponsePage(new SprintsPage(user));
					}
				};
				webContainer.add(lkStorys);
				
				
				webContainer.add(new AjaxLink<Void>("lkEdit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						storyModal.setPageCreator(new ModalWindow.PageCreator() {
							public Page createPage() {
								return new StoryModalPage(SprintsPage.this.getPageReference(), storyModal, user, story);
							}
						});
						storyModal.show(target);
					}
				});
				
				webContainer.add(new AjaxLink("lkRight") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						if(sprint != null){
							sprintBusiness.addStoryInSprint(story,sprint);
							setResponsePage(new SprintsPage(user,sprint));
						}else{
							notifier.create(target,
			                        "Selecione um Sprint","");
						}
					}
				});
				
			}
		};
		add(listViewStoryBacklog);
	}
	
	
	private void createPanelSprint(final User user, final Sprint sprint) {
		
		List<Story> listAllStory = new ArrayList<Story>();
		
		if(sprint != null){
			listAllStory = storyBusiness.getStoryBySprint(sprint);
		}
		
		ListView<Story> listViewStoryBacklog = new ListView<Story>("lvStorySprint", listAllStory) {
			@Override
			protected void populateItem(ListItem<Story> item) {
				final Story story = (Story)item.getModelObject();
				Label lbName = new Label("lbName", story.getName());
				Label lbDescription = new Label("lbDescription", story.getDescription());
				Label lbActivitysSize = new Label("lbActivitysSize", story.getActivitys().size()+"");
				lbDescription.setEscapeModelStrings(false);
				Label lbId = new Label("lbId", story.getId().toString());
				Label lbDateCreate = new Label("lbDateCreate", story.getDateCreation().toString("dd/MM/yyyy - HH:mm"));
				
				WebMarkupContainer webContainer = new WebMarkupContainer("tableStory2");
				webContainer.add(new SimpleAttributeModifier("style","background-color: #" +story.getColor()));
				item.add(webContainer);
				
				webContainer.add(lbName);
				webContainer.add(lbDescription);
				webContainer.add(lbActivitysSize);
				webContainer.add(lbId);
				webContainer.add(lbDateCreate);
				
				Link lkStorys = new Link("lkDelete") {
					@Override
					public void onClick() {
						sprintBusiness.delete(sprint);
						setResponsePage(new SprintsPage(user,sprint));
					}
				};
				webContainer.add(lkStorys);
				
				
				webContainer.add(new AjaxLink<Void>("lkEdit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						storyModal.setPageCreator(new ModalWindow.PageCreator() {
							public Page createPage() {
								return new StoryModalPage(SprintsPage.this.getPageReference(), storyModal, user, story);
							}
						});
						storyModal.show(target);
					}
				});
				
				webContainer.add(new Link("lkRight") {
					@Override
					public void onClick() {
						story.setSprint(null);
						sprintBusiness.removeStoryInSprint(story,sprint);
						setResponsePage(new SprintsPage(user,sprint));
					}
				});
				
			}
		};
		add(listViewStoryBacklog);
	}

}

package br.com.fa7.firststepinagile.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.firststepinagile.business.SprintBusiness;
import br.com.fa7.firststepinagile.business.StoryBusiness;
import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.Sprint;
import br.com.fa7.firststepinagile.entities.Story;
import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.base.PageBase;
import br.com.fa7.firststepinagile.pages.modal.StoryModalPage;

@SuppressWarnings({ "serial", "deprecation","rawtypes"})
public class SprintsPage extends PageBase {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private StoryBusiness storyBusiness;
	
	@SpringBean
	private SprintBusiness sprintBusiness;
	
	@SpringBean
	private UserBusiness userBusiness;

	private ModalWindow storyModal;
	
	public SprintsPage(User user) {
		this(user,user.getSprint());	
	}

	public SprintsPage(User user, Sprint sprint) {
		super(user,"tutorial5.html");
		
		super.lkSprints.setEnabled(false);
		
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
	

	
	
	private void createPanelBacklog(final User user, final Sprint sprint) {
		List<Story> listAllStory = storyBusiness.allOrderByAscPrioridade(user.getProjectAtual());
		
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
                webContainer.add(AttributeModifier.replace("style", "background-color: #" + story.getColor()));
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
				
				AjaxLink lkRight = new AjaxLink("lkRight") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						if(sprint != null){
							sprintBusiness.addStoryInSprint(story,sprint);
							setResponsePage(new SprintsPage(user,sprint));
						}else{
//							notifier.create(target,"Selecione um Sprint","");
						}
					}
				};
				
				lkRight.setEnabled(user.getSprint() != null);
				
				webContainer.add(lkRight);
				
			}
		};
		add(listViewStoryBacklog);
	}
	
	
	private void createPanelSprint(final User user, final Sprint sprint) {
		
		List<Story> listAllStory = new ArrayList<Story>();
		
		if(sprint != null){
			listAllStory = storyBusiness.getStoryBySprint(sprint,user.getProjectAtual());
		}
		
		Label sprintName = null;
		if(sprint != null){
			sprintName = new Label("sprintName", sprint.getName());
		}else{
			sprintName = new Label("sprintName", "Não Selecionado");
		}
		add(sprintName);
		
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
                webContainer.add(AttributeModifier.replace("style","background-color: #" +story.getColor()));
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

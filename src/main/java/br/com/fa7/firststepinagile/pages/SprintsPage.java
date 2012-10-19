package br.com.fa7.firststepinagile.pages;

import java.util.ArrayList;
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
import org.apache.wicket.util.string.StringValue;

import br.com.fa7.firststepinagile.business.SprintBusiness;
import br.com.fa7.firststepinagile.business.StoryBusiness;
import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.Sprint;
import br.com.fa7.firststepinagile.entities.Story;
import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.base.PageBase;
import br.com.fa7.firststepinagile.pages.modal.SprintModalPage;

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
		this(user,null);
	}

	public SprintsPage(User user, Sprint sprint) {
		super(user);
		
		System.out.println(sprint);
		
		createSprintModal(user);
		
		createBarSprintModal(user);
		
		createPanelBacklog(user,sprint);
		
		createPanelSprint(user,sprint);
		
	}
	
	private void createBarSprintModal(final User user) {
		
		add(new AjaxLink<Void>("lkSprintModal") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				storyModal.setPageCreator(new ModalWindow.PageCreator() {
					public Page createPage() {
						return new SprintModalPage(SprintsPage.this.getPageReference(), storyModal, user);
					}
				});
				storyModal.show(target);
			}
		});
		
	}

	private void createSprintModal(final User user) {
		add(storyModal = new ModalWindow("sprintModal"));
		storyModal.setCookieName("storyModal-cookie");
		storyModal.setCssClassName(ModalWindow.CSS_CLASS_GRAY);
		storyModal.setResizable(false);

		storyModal.setPageCreator(new ModalWindow.PageCreator() {
			public Page createPage() {
				return new SprintModalPage(SprintsPage.this.getPageReference(), storyModal, user);
			}
		});
		
		
		storyModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			@Override
			public void onClose(AjaxRequestTarget target) {
				StringValue sprintId = SprintsPage.this.getPageParameters().get("sprintId");
				Sprint sprint = sprintBusiness.findById(sprintId.toLong());;
				user.setSprint(sprint);
				userBusiness.save(user);
				setResponsePage(new SprintsPage(user,sprint));
			}
		});

	}
	
	
	private void createPanelBacklog(final User user, final Sprint sprint) {
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
				
				Link lkStorys = new Link("lkDelete") {
					@Override
					public void onClick() {
						System.out.println("Delete");
					}
				};
				item.add(lkStorys);
				
				
				item.add(new AjaxLink<Void>("lkEdit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						System.out.println("Edit");
					}
				});
				
				item.add(new Link("lkRight") {
					@Override
					public void onClick() {
						sprintBusiness.addStoryInSprint(story,sprint);
						setResponsePage(new SprintsPage(user,sprint));
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
				lbDescription.setEscapeModelStrings(false);
				Label lbId = new Label("lbId", story.getId().toString());
				Label lbDateCreate = new Label("lbDateCreate", story.getDateCreation().toString("dd/MM/yyyy - HH:mm"));
				item.add(lbName);
				item.add(lbDescription);
				item.add(lbId);
				item.add(lbDateCreate);
				
				Link lkStorys = new Link("lkDelete") {
					@Override
					public void onClick() {
						sprintBusiness.delete(sprint);
						setResponsePage(new SprintsPage(user,sprint));
					}
				};
				item.add(lkStorys);
				
				
				item.add(new AjaxLink<Void>("lkEdit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
					}
				});
				
				item.add(new Link("lkRight") {
					@Override
					public void onClick() {
						
					}
				});
				
			}
		};
		add(listViewStoryBacklog);
	}

}

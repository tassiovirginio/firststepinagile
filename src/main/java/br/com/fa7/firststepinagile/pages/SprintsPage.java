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

import br.com.fa7.firststepinagile.business.StoryBusiness;
import br.com.fa7.firststepinagile.entities.Story;
import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.base.PageBase;
import br.com.fa7.firststepinagile.pages.modal.SprintModalPage;
import br.com.fa7.firststepinagile.pages.modal.StoryModalPage;

public class SprintsPage extends PageBase {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private StoryBusiness storyBusiness;
	
	private ModalWindow storyModal;

	public SprintsPage(User user) {
		super(user);
		
		createSprintModal(user);
		
		createBarSprintModal(user);
		
		createPanelBacklog(user);
		
		createPanelSprint(user);
		
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
			public void onClose(AjaxRequestTarget target) {
				setResponsePage(new SprintsPage(user));
			}
		});

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
						System.out.println("Direita");
					}
				});
				
			}
		};
		add(listViewStoryBacklog);
	}
	
	
	private void createPanelSprint(final User user) {
		List<Story> listAllStory = storyBusiness.allOrderByDescPrioridade();
		
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

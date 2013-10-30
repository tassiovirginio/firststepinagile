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
import org.apache.wicket.util.string.StringValue;

import br.com.fa7.firststepinagile.business.SprintBusiness;
import br.com.fa7.firststepinagile.business.StoryBusiness;
import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.Activity;
import br.com.fa7.firststepinagile.entities.Sprint;
import br.com.fa7.firststepinagile.entities.Story;
import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.base.PageBase;
import br.com.fa7.firststepinagile.pages.modal.ActivityModalPage;
import br.com.fa7.firststepinagile.pages.modal.SprintModalPage;
import br.com.fa7.firststepinagile.pages.modal.StoryModalPage;

@SuppressWarnings({ "serial", "deprecation","rawtypes", "unchecked"})
public class StorysPage extends PageBase {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private StoryBusiness storyBusiness;
	
	@SpringBean
	private UserBusiness userBusiness;
	
	@SpringBean
	private SprintBusiness sprintBusiness;
	
	private ModalWindow storyModal;
	
	private ModalWindow sprintModal;
	
	private ModalWindow activityModal;
	
	private Story storySelected;
	
	public StorysPage(final User user) {
		this(user,user.getSprint(),2);
	}
	
	public StorysPage(final User user, int filter) {
		this(user,user.getSprint(),filter);
	}
	
	public StorysPage(final User user, Story story) {
		this(user,user.getSprint(),2);
	}
	
	public StorysPage(final User user, Story story, int filter) {
		this(user,user.getSprint(),filter);
	}
	
	public StorysPage(final User user, Sprint sprint, Story story) {
		this(user,sprint,2);
	}
	
	public StorysPage(final User user, Sprint sprint, int filter) {
		super(user,"tutorial2.html");
		
		super.lkStorys.setEnabled(false);
		
		createStoryModal(user,filter);
		
		createActivityModal(user);
		
		createPanelBacklog(user,sprint,filter);
		
		createRadioGroup(user,filter);
		
		createSprintModal(user);
		
		
		add(new AjaxLink<Void>("showStoryModal") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				storyModal.setPageCreator(new ModalWindow.PageCreator() {
					public Page createPage() {
						return new StoryModalPage(StorysPage.this.getPageReference(), storyModal, user, new Story());
					}
				});
				storyModal.show(target);
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
				return new SprintModalPage(StorysPage.this.getPageReference(), sprintModal, user);
			}
		});
		
		
		sprintModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			@Override
			public void onClose(AjaxRequestTarget target) {
				StringValue sprintId = StorysPage.this.getPageParameters().get("sprintId");
				if(!sprintId.isNull()){
					Sprint sprint = sprintBusiness.findById(sprintId.toLong());
					user.setSprint(sprint);
					userBusiness.save(user);
				}
				setResponsePage(new StorysPage(user));
			}
		});

	}
	
//	private void createBarSprintModal(final User user, final Sprint sprint, int filter) {
//		
//		String dateEnd = "";
//		
//		if(user.getSprint() != null && user.getSprint().getDateEnd() != null)
//		dateEnd = user.getSprint().getDateEnd().toString("dd/MM/yyyy");
//		
//		if(user.getSprint() != null){
//			add(new Label("lbSprintName",user.getSprint().getName() + " - " + user.getSprint().getDateStart().toString("dd/MM/yyyy") 
//					+ " - " + dateEnd));
//		}else{
//			add(new Label("lbSprintName",""));	
//		}
//		
//		add(new AjaxLink<Void>("lkSprintModal") {
//			@Override
//			public void onClick(AjaxRequestTarget target) {
//				sprintModal.setPageCreator(new ModalWindow.PageCreator() {
//					public Page createPage() {
//						return new SprintModalPage(StorysPage.this.getPageReference(), storyModal, user);
//					}
//				});
//				sprintModal.show(target);
//			}
//		});
//		
//		
//	}
	

	private void createPanelBacklog(final User user, final Sprint sprint, final int filter) {
		
		List<Story> listAllStory = null;
		
		if(filter == 1){
			listAllStory = storyBusiness.allOrderByAscPrioridade(user.getProjectAtual());
		}else if(filter == 2){
			listAllStory = storyBusiness.notSprintOrderByAscPrioridade(user.getProjectAtual());
		}else if(filter == 3){
			listAllStory = storyBusiness.getStoryBySprint(sprint,user.getProjectAtual());
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
				
				Link lkStorys = new Link("lkDelete") {
					@Override
					public void onClick() {
						storyBusiness.delete(story);
						setResponsePage(new StorysPage(user,filter));
					}
				};
				webContainer.add(lkStorys);
				
				
				webContainer.add(new AjaxLink<Void>("lkEdit") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						storyModal.setPageCreator(new ModalWindow.PageCreator() {
							public Page createPage() {
								return new StoryModalPage(StorysPage.this.getPageReference(), storyModal, user, story);
							}
						});
						storyModal.show(target);
					}
				});
				
				webContainer.add(new Link("lkUp") {
					@Override
					public void onClick() {
						storyBusiness.upStoryPriority(story);
						setResponsePage(new StorysPage(user,storySelected,filter));
					}
				});
				
				webContainer.add(new Link("lkDown") {
					@Override
					public void onClick() {
						storyBusiness.downStoryPriority(story);
						setResponsePage(new StorysPage(user,storySelected,filter));
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
				setResponsePage(new StorysPage(user,1));
			}
		});
		
		Radio radio2 = new Radio("radio2");
		radio2.add(new AjaxEventBehavior("onclick") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				setResponsePage(new StorysPage(user,2));
			}
		});
		
		Radio radio3 = new Radio("radio3");
		radio3.add(new AjaxEventBehavior("onclick") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				setResponsePage(new StorysPage(user,user.getSprint(),3));
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
				return new StoryModalPage(StorysPage.this.getPageReference(), storyModal, user, new Story());
			}
		});
		
		storyModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			public void onClose(AjaxRequestTarget target) {
				setResponsePage(new StorysPage(user,filter));
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
				return new ActivityModalPage(StorysPage.this.getPageReference(), activityModal, user,storySelected, new Activity());
			}
		});
		
		activityModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			public void onClose(AjaxRequestTarget target) {
				setResponsePage(new StorysPage(user,storySelected));
			}
		});

	}

}

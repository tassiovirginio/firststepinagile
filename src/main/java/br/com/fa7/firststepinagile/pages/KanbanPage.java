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
import br.com.fa7.firststepinagile.pages.modal.StoryModalPage;

import com.google.code.jqwicket.ui.mb.extruder.ExtruderOptions;
import com.google.code.jqwicket.ui.mb.extruder.ExtruderOptions.Position;
import com.google.code.jqwicket.ui.mb.extruder.ExtruderWebMarkupContainer;

public class KanbanPage extends PageBase {
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private StoryBusiness storyBusiness;
	
	public KanbanPage(User user) {
		super(user);
		
		createPanelBacklog(user);
	}
	
	
	private void createPanelBacklog(final User user) {
		List<Story> listAllStory = storyBusiness.allOrderByDescPrioridade();
		
		ListView<Story> listViewStoryBacklog = new ListView<Story>("lvStory", listAllStory) {
			@Override
			protected void populateItem(ListItem<Story> item) {
				final Story story = (Story)item.getModelObject();
				
				Label lbId = new Label("lbId", story.getId().toString());
				Label lbName = new Label("lbName", story.getName());
				Label lbDescription = new Label("lbDescription", story.getDescription());
				Label lbDateCreate = new Label("lbDateCreate", story.getDateCreation().toString("HH:mm dd/MM/yyyy"));
				
				item.add(lbId);
				item.add(lbName);
				item.add(lbDescription);
				item.add(lbDateCreate);
			}
		};
		add(listViewStoryBacklog);
	}

}

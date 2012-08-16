package br.com.fa7.firststepinagile.pages.modal;

import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import br.com.fa7.firststepinagile.business.StoryBusiness;
import br.com.fa7.firststepinagile.entities.Story;
import br.com.fa7.firststepinagile.entities.User;

public class StoryModalPage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private StoryBusiness storyBusiness;
	
	private Story story;

	public StoryModalPage(final PageReference pageRefOrigem,	final ModalWindow window, final User user, final Story story) {
		
		if (story == null) {
			this.story = new Story();
			this.story.setDateCreation(new DateTime());
			add(new Label("story.id", "Novo"));
		} else if (story.getId() == null) {
			this.story = story;
			this.story.setDateCreation(new DateTime());
			add(new Label("story.id", "Novo"));
		} else {
			this.story = story;
			add(new Label("story.id", story.getId().toString()));
		}
		
		Form<Story> form = new Form<Story>("form");
		form.add(new TextField("tfName", new PropertyModel(this.story,"name")).setRequired(true));
		TextArea tfDescription = new TextArea<String>("tfDescription", new PropertyModel<String>(this.story, "description"));
		form.add(tfDescription);
		form.add(new TextField("tfValue", new PropertyModel(this.story,"value")).setRequired(true));
		
		if(story.getPriority() != null){
			form.add(new Label("story.priority", story.getPriority().toString()));
		}else{
			form.add(new Label("story.priority",""));
		}
		
		form.add(new AjaxButton("ajax-button", form){
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form){
            	storyBusiness.save(StoryModalPage.this.story);
				window.close(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form){
				window.close(target);
            }
        });
		
		add(form);
		
//		add(new AjaxLink<Void>("closeOK") {
//			@Override
//			public void onClick(AjaxRequestTarget target) {
//				System.out.println(StoryModalPage.this.story.getName());
//				storyBusiness.save(StoryModalPage.this.story);
//				window.close(target);
//			}
//		});
//
//		add(new AjaxLink<Void>("closeCancel") {
//			@Override
//			public void onClick(AjaxRequestTarget target) {
//				window.close(target);
//			}
//		});

	}
}

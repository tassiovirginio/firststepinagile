package br.com.fa7.firststepinagile.pages.modal;

import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.firststepinagile.business.StoryBusiness;
import br.com.fa7.firststepinagile.entities.Story;
import br.com.fa7.firststepinagile.entities.User;

public class StoryModalPage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private StoryBusiness storyBusiness;

	public StoryModalPage(final PageReference pageRefOrigem,	final ModalWindow window, final User user, final Story story) {
		
		story.setName("Teste");
		
		add(new Label("user.name",user.getName()));
		
		Form form = new Form("form");
		form.add(new TextField("tfName", new PropertyModel(story,"name")));
		add(form);
		
		add(new AjaxLink<Void>("closeOK") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				System.out.println(story.getName());
				storyBusiness.save(story);
				window.close(target);
			}
		});

		add(new AjaxLink<Void>("closeCancel") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				window.close(target);
			}
		});

	}
}

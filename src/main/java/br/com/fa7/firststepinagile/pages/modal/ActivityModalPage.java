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

import br.com.fa7.firststepinagile.business.ActivityBusiness;
import br.com.fa7.firststepinagile.entities.Activity;
import br.com.fa7.firststepinagile.entities.Story;
import br.com.fa7.firststepinagile.entities.User;
import org.joda.time.LocalDateTime;

@SuppressWarnings({ "serial","rawtypes", "unchecked"})
public class ActivityModalPage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private ActivityBusiness activityBusiness;
	
	private Activity activity;

	public ActivityModalPage(final PageReference pageRefOrigem, final ModalWindow window, final User user, final Story story, final Activity activity) {
		
		if (activity == null) {
			this.activity = new Activity();
			this.activity.setDateCreation(new LocalDateTime());
			this.activity.setCreator(user);
			this.activity.setStory(story);
			add(new Label("activity.id", "Novo"));
			
		} else if (activity.getId() == null) {
			this.activity = activity;
			this.activity.setDateCreation(new LocalDateTime());
			this.activity.setCreator(user);
			this.activity.setStory(story);
			add(new Label("activity.id", "Novo"));
			
		} else {
			this.activity = activity;
			add(new Label("activity.id", activity.getId().toString()));
		}
		
		Form<Activity> form = new Form<Activity>("form");
		form.add(new TextField("tfName", new PropertyModel(this.activity,"name")).setRequired(true));
		form.add(new TextArea<String>("tfDescription", new PropertyModel<String>(this.activity, "description")));
		form.add(new TextField("tfDuration", new PropertyModel(this.activity,"duration")).setRequired(true));
		
		form.add(new AjaxButton("ajax-button", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				activityBusiness.save(ActivityModalPage.this.activity);
				window.close(target);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				window.close(target);
			}
		});
		
		add(form);

	}
}

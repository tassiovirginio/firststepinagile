package br.com.fa7.firststepinagile.pages.modal;

import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.User;
import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

@SuppressWarnings({ "serial","rawtypes"})
public class UserModalPage extends WebPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private UserBusiness userBusiness;
	
	public UserModalPage(final PageReference pageRefOrigem,final ModalWindow window){
		this(pageRefOrigem,window,new User());
	}

	public UserModalPage(final PageReference pageRefOrigem,final ModalWindow window, final User userSelected) {
		
		Form<User> form = new Form<User>("form");
        form.add(new TextField<String>("tfLogin", new PropertyModel<String>(userSelected, "login")));
		form.add(new TextField<String>("tfName", new PropertyModel<String>(userSelected, "name")));
		form.add(new TextField<String>("tfEmail", new PropertyModel<String>(userSelected, "email")));
		form.add(new PasswordTextField("tfPassword", new PropertyModel<String>(userSelected, "password")));
		add(form);

		form.add(new AjaxButton("ajax-button", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				userBusiness.save(userSelected);
				window.close(target);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				window.close(target);
			}
		});
		
		
		form.add(new AjaxButton("ajax-button-new", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				setResponsePage(new UserModalPage(pageRefOrigem, window));
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				window.close(target);
			}
		});
		
		List<User> listUsers = userBusiness.loadAllUser();
		
		ListView<User> listViewSprint = new ListView<User>("listViewUsers", listUsers) {
			@Override
			protected void populateItem(ListItem<User> item) {
				final User user = (User)item.getModelObject();
				Label lbName = new Label("lbName", user.getName());
				Label lbId = new Label("lbId", user.getId().toString());
				item.add(lbName);
				item.add(lbId);
				
				item.add(new Link("lkRight") {
					@Override
					public void onClick() {
						setResponsePage(new UserModalPage(pageRefOrigem, window, user));
					}
				});
				
				item.add(new Link("lkDelete") {
					@Override
					public void onClick() {
						userBusiness.delete(user);
						setResponsePage(new UserModalPage(pageRefOrigem, window));
					}
				});
				
			}
		};
		add(listViewSprint);

	}
}
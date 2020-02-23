package br.com.fa7.firststepinagile.pages.modal;

import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.User;

@SuppressWarnings({"serial"})
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
		form.add(new PasswordTextField("tfPassword", new PropertyModel<String>(userSelected, "password")));
		add(form);

		form.add(new AjaxButton("ajax-button", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				userBusiness.save(userSelected);
				window.close(target);
			}

			@Override
			protected void onError(AjaxRequestTarget target) {
				window.close(target);
			}
		});

	}
}
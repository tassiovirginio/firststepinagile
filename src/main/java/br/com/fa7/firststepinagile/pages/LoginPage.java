package br.com.fa7.firststepinagile.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import br.com.fa7.firststepinagile.business.ActivityBusiness;
import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.User;

public class LoginPage extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private UserBusiness userBusiness;

	@SpringBean
	private ActivityBusiness activityBusiness;

	private String login;
	
	private String password;
	
	public LoginPage() {
		
		setDefaultModel(new CompoundPropertyModel<LoginPage>(this));

//		WebClientInfo info2 = (WebClientInfo) getSession().getClientInfo();

		getSession().clear();

		Form<User> form = new Form<User>("form") {

			private static final long serialVersionUID = 1L;

			protected void onSubmit() {

				boolean login = userBusiness.login(LoginPage.this.login,LoginPage.this.password);

				if (login) {
					User user = userBusiness.findForLogin(LoginPage.this.login);
					setResponsePage(new KanbanPage(user));
				} else {
					info("Login Incorretor!");
				}
				
			}
		};
		
		form.add(new TextField<String>("login").setRequired(true));

		form.add(new PasswordTextField("password").add(StringValidator.lengthBetween(2,6)));

		form.add(new FeedbackPanel("feedback"));
		
		add(form);
		
		User user = userBusiness.findForLogin("test01");
		setResponsePage(new KanbanPage(user));

	}
}

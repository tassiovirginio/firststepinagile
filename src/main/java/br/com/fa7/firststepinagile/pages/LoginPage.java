package br.com.fa7.firststepinagile.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.User;

@SuppressWarnings({"rawtypes","serial"})
public class LoginPage extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private UserBusiness userBusiness;

	private String login;
	
	private String password;
	
	public LoginPage() {
		
		setDefaultModel(new CompoundPropertyModel<LoginPage>(this));

		getSession().clear();
		
		Form<User> form = new Form<User>("form") {

			private static final long serialVersionUID = 1L;

			protected void onSubmit() {

				boolean login = userBusiness.login(LoginPage.this.login,LoginPage.this.password);

				if (login) {
					User user = userBusiness.findForLogin(LoginPage.this.login);
					getSession().setAttribute("user.login", user.getLogin());
					setResponsePage(new StartPage(user));
				} else {
					info("Login Incorretor!");
				}
				
			}
		};
		
		form.add(new TextField<String>("login").setRequired(true));

		form.add(new PasswordTextField("password").add(StringValidator.lengthBetween(2,6)));

		form.add(new FeedbackPanel("feedback"));
		
		Link lkCadastro = new Link("lkCadastro") {
			@Override
			public void onClick() {
				setResponsePage(new CadastrarUsuarioPage());
			}
		};
		form.add(lkCadastro);
		
		add(form);
		
//		User user = userBusiness.findForLogin("test01");
//		getSession().setAttribute("user", user);
//		setResponsePage(new KanbanPage(user));

	}
}

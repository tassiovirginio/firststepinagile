package br.com.fa7.firststepinagile.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.firststepinagile.business.ActivityBusiness;
import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.User;

public class LoginPage extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private UserBusiness userBusiness;

	@SpringBean
	private ActivityBusiness activityBusiness;

	private User user;
	
	@SuppressWarnings({ "rawtypes", "serial" })
	public LoginPage() {
		
		user = new User();

		WebClientInfo info2 = (WebClientInfo) getSession().getClientInfo();

		getSession().clear();

		Form<User> form = new Form<User>("form") {

			private static final long serialVersionUID = 1L;

			protected void onSubmit() {

				boolean login = userBusiness.login(user);

				if (login) {
					user = userBusiness.findForLogin(user.getLogin());
					setResponsePage(new KanbanPage(user));
				} else {
					info("Login Incorretor!");
				}
			}
		};
		
		TextField<String> login = new TextField<String>("login", new PropertyModel<String>(user, "login"));
		login.setRequired(true);
		form.add(login);

		form.add(new PasswordTextField("senha", new PropertyModel<String>(user, "password")));

		form.add(new FeedbackPanel("feedback"));
		
		add(form);

	}
}

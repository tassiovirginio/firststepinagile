package br.com.fa7.firststepinagile.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.User;

public class CadastrarUsuarioPage extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private UserBusiness userBusiness;

	private String login;
	
	private String name;
	
	private String password1;
	
	private String password2;
	
	public CadastrarUsuarioPage() {
		
		setDefaultModel(new CompoundPropertyModel<CadastrarUsuarioPage>(this));

		getSession().clear();

		Form<User> form = new Form<User>("form") {

			private static final long serialVersionUID = 1L;

			protected void onSubmit() {
				
				if(password1.equals(password2)){
					User user = new User();
					user.setLogin(login);
					user.setPassword(password1);
					user.setName(name);
					userBusiness.save(user);
					info("Usuario Criado Com Sucesso !!!");
					setResponsePage(new LoginPage());
				}else{
					error("As Senhas Não Conferem!!");
				}
				
			}
		};
		
		form.add(new TextField<String>("name").setRequired(true));
		
		form.add(new TextField<String>("login").setRequired(true));

		form.add(new PasswordTextField("password1").add(StringValidator.lengthBetween(6,6)));
		
		form.add(new PasswordTextField("password2").add(StringValidator.lengthBetween(6,6)));

		form.add(new FeedbackPanel("feedback"));
		
		add(form);
		
	}
}

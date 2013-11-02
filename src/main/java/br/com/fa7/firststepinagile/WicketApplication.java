package br.com.fa7.firststepinagile;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fa7.firststepinagile.business.ActivityBusiness;
import br.com.fa7.firststepinagile.business.ConviteBusiness;
import br.com.fa7.firststepinagile.business.ProjectBusiness;
import br.com.fa7.firststepinagile.business.SprintBusiness;
import br.com.fa7.firststepinagile.business.StoryBusiness;
import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.Activity;
import br.com.fa7.firststepinagile.entities.Convite;
import br.com.fa7.firststepinagile.entities.Project;
import br.com.fa7.firststepinagile.entities.Sprint;
import br.com.fa7.firststepinagile.entities.Story;
import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.LoginPage;

import com.google.code.jqwicket.JQComponentOnBeforeRenderListener;
import com.google.code.jqwicket.JQContributionConfig;

@Component
public class WicketApplication extends WebApplication{
	
	@Autowired
	private ActivityBusiness activityBusiness;
	
	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private StoryBusiness storyBusiness;
	
	@Autowired
	private SprintBusiness sprintBusiness;
	
	@Autowired
	private ProjectBusiness projectBusiness;
	
	@Autowired
	private ConviteBusiness conviteBusiness;
	
	@Override
	public Class<LoginPage> getHomePage(){
		return LoginPage.class;
	}

	@Override
	public void init(){
		
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8"); 
        getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		
		JQContributionConfig config = new JQContributionConfig().withDefaultJQueryUi(); 
		getComponentPreOnBeforeRenderListeners().add(new JQComponentOnBeforeRenderListener(config));
		
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		getDebugSettings().setAjaxDebugModeEnabled(false);

        getApplicationSettings().setInternalErrorPage(LoginPage.class);

        getApplicationSettings().setPageExpiredErrorPage(LoginPage.class);
		
		criarDadosTeste();
		
		// add your configuration here
	}
	
	
	private void criarDadosTeste(){
		
		User userAdmin = userBusiness.findForLogin("admin");
		
		if(userAdmin == null){
			userAdmin = new User();
			userAdmin.setName("Admin");
			userAdmin.setLogin("admin@admin.com");
			userAdmin.setPassword("admin");
			userBusiness.save(userAdmin);
		}

		
		Project project1 = new Project();
		project1.setName("Projeto 1");
		project1.setDescription("Projeto 1");
		project1.setDateCreation(new DateTime());
		project1.setCreator(userAdmin);
		projectBusiness.save(project1);
		
		Project project2 = new Project();
		project2.setName("Projeto 2");
		project2.setDescription("Projeto 2");
		project2.setDateCreation(new DateTime());
		project2.setCreator(userAdmin);
		projectBusiness.save(project2);
		
		Convite convite1 = new Convite();
		convite1.setEmail("teste@teste.com");
		convite1.setProject(project1);
		conviteBusiness.save(convite1);
		
		Convite convite2 = new Convite();
		convite2.setEmail("teste2@teste2.com");
		convite2.setProject(project2);
		conviteBusiness.save(convite2);
		
		userAdmin.setProjectAtual(project2);
		userBusiness.save(userAdmin);
		
		Sprint sprint1 = new Sprint();
		sprint1.setCreator(userAdmin);
		sprint1.setDateCreation(new DateTime());
		sprint1.setDateStart(new DateTime());
		sprint1.setDateEnd(new DateTime().plusDays(10));
		sprint1.setDescription("Teste...");
		sprint1.setName("Sprint Test01");
		sprint1.setProject(project1);
		sprintBusiness.save(sprint1);
		
		Sprint sprint2 = new Sprint();
		sprint2.setCreator(userAdmin);
		sprint2.setDateCreation(new DateTime());
		sprint2.setDateStart(new DateTime());
		sprint2.setDateEnd(new DateTime().plusDays(10));
		sprint2.setDescription("Teste...");
		sprint2.setName("Sprint Test02");
		sprint1.setProject(project2);
		sprintBusiness.save(sprint2);
		
		Story story1 = new Story();
		story1.setName("Historia 01");
		story1.setCreator(userAdmin);
		story1.setDescription("Teste Story.........");
		story1.setSprint(sprint1);
		story1.setColor("c2ceff");
		story1.setProject(project1);
		storyBusiness.save(story1);
		
		Story story2 = new Story();
		story2.setName("Historia 02");
		story2.setCreator(userAdmin);
		story2.setDescription("Teste Story.........");
		story2.setSprint(sprint2);
		story2.setColor("c2ceff");
		story2.setProject(project2);
		storyBusiness.save(story2);
		
		Story story3 = new Story();
		story3.setName("Historia 03");
		story3.setCreator(userAdmin);
		story3.setDescription("Teste Story.... asdas asda  asda sda .....");
		story3.setSprint(sprint1);
		story3.setColor("c2ceff");
		story3.setProject(project1);
		storyBusiness.save(story3);
		
		Activity activity01 = new Activity();
		activity01.setName("Teste1");
		activity01.setDescription("bla bla bla bla bla bla bla bla bla bla");
		activity01.setCreator(userAdmin);
		activity01.setCurrentResponsible(userAdmin);
//		activity01.setDateCreation(new DateTime());
//		activity01.setPriority(1000.0);
		activity01.setState(1);
		activity01.setStory(story1);
		activityBusiness.save(activity01);
		
		Activity activity01_2 = new Activity();
		activity01_2.setName("Teste1");
		activity01_2.setDescription("bla bla bla bla bla bla bla bla bla bla");
		activity01_2.setCreator(userAdmin);
		activity01_2.setCurrentResponsible(userAdmin);
//		activity01_2.setDateCreation(new DateTime());
//		activity01_2.setPriority(1000.0);
		activity01_2.setState(1);
		activityBusiness.save(activity01_2);
		
		Activity activity01_3 = new Activity();
		activity01_3.setName("Teste1");
		activity01_3.setDescription("bla bla bla bla bla bla bla bla bla bla");
		activity01_3.setCreator(userAdmin);
		activity01_3.setCurrentResponsible(userAdmin);
//		activity01_3.setDateCreation(new DateTime());
//		activity01_3.setPriority(1000.0);
		activity01_3.setState(1);
		activityBusiness.save(activity01_3);
		
		Activity activity02 = new Activity();
		activity02.setName("Teste2");
		activity02.setDescription("bla bla bla bla bla bla bla bla bla bla");
		activity02.setCreator(userAdmin);
		activity02.setCurrentResponsible(userAdmin);
//		activity02.setDateCreation(new DateTime());
//		activity02.setPriority(1000.0);
		activity02.setState(2);
		activityBusiness.save(activity02);
		
		
		Activity activity03 = new Activity();
		activity03.setName("Teste3");
		activity03.setDescription("bla bla bla bla bla bla bla bla bla bla");
		activity03.setCreator(userAdmin);
		activity03.setCurrentResponsible(userAdmin);
//		activity03.setDateCreation(new DateTime());
//		activity03.setPriority(1000.0);
		activity03.setState(3);
		activityBusiness.save(activity03);
		
		
		User userTest02 = new User();
		userTest02.setName("userTest02");
		userTest02.setLogin("userTest02@userTest02.com");
		userTest02.setPassword("userTest02");
		userBusiness.save(userTest02);
		
		User userTest03 = new User();
		userTest03.setName("userTest03");
		userTest03.setLogin("userTest03@userTest03.com");
		userTest03.setPassword("userTest03");
		userBusiness.save(userTest03);
		
		
	}
}

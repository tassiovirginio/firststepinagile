package br.com.fa7.firststepinagile;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fa7.firststepinagile.business.ActivityBusiness;
import br.com.fa7.firststepinagile.business.StoryBusiness;
import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.Activity;
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
		getDebugSettings().setAjaxDebugModeEnabled(true);
		
		criarDadosTeste();
		
		// add your configuration here
	}
	
	private void criarDadosTeste(){
		System.out.println("----> "+activityBusiness.size());
		
		User userTest01 = new User();
		userTest01.setEmail("test01@test01.com");
		userTest01.setName("Test01");
		userTest01.setLogin("test01");
		userTest01.setPassword("test01");
		userTest01.setAdmin(true);
		userBusiness.save(userTest01);
		
		Story story1 = new Story();
		story1.setName("Estoria 01");
		story1.setCreator(userTest01);
		story1.setDateCreation(new DateTime());
		story1.setDescription("Teste Story.........");
		storyBusiness.save(story1);
		
		Story story2 = new Story();
		story2.setName("Estoria 02");
		story2.setCreator(userTest01);
		story2.setDateCreation(new DateTime());
		story2.setDescription("Teste Story.........");
		storyBusiness.save(story2);
		
		Story story3 = new Story();
		story3.setName("Estoria 03");
		story3.setCreator(userTest01);
		story3.setDateCreation(new DateTime());
		story3.setDescription("Teste Story.... asdas asda  asda sda .....");
		storyBusiness.save(story3);
		
		Activity activity01 = new Activity();
		activity01.setName("Teste1");
		activity01.setDescription("bla bla bla bla bla bla bla bla bla bla");
		activity01.setCreator(userTest01);
		activity01.setCurrentResponsible(userTest01);
		activity01.setDateCreation(new DateTime());
		activity01.setPriority(1000);
		activity01.setState(1);
		activity01.setStory(story1);
		activityBusiness.save(activity01);
		
		Activity activity01_2 = new Activity();
		activity01_2.setName("Teste1");
		activity01_2.setDescription("bla bla bla bla bla bla bla bla bla bla");
		activity01_2.setCreator(userTest01);
		activity01_2.setCurrentResponsible(userTest01);
		activity01_2.setDateCreation(new DateTime());
		activity01_2.setPriority(1000);
		activity01_2.setState(1);
		activityBusiness.save(activity01_2);
		
		Activity activity01_3 = new Activity();
		activity01_3.setName("Teste1");
		activity01_3.setDescription("bla bla bla bla bla bla bla bla bla bla");
		activity01_3.setCreator(userTest01);
		activity01_3.setCurrentResponsible(userTest01);
		activity01_3.setDateCreation(new DateTime());
		activity01_3.setPriority(1000);
		activity01_3.setState(1);
		activityBusiness.save(activity01_3);
		
		Activity activity02 = new Activity();
		activity02.setName("Teste2");
		activity02.setDescription("bla bla bla bla bla bla bla bla bla bla");
		activity02.setCreator(userTest01);
		activity02.setCurrentResponsible(userTest01);
		activity02.setDateCreation(new DateTime());
		activity02.setPriority(1000);
		activity02.setState(2);
		activityBusiness.save(activity02);
		
		
		Activity activity03 = new Activity();
		activity03.setName("Teste3");
		activity03.setDescription("bla bla bla bla bla bla bla bla bla bla");
		activity03.setCreator(userTest01);
		activity03.setCurrentResponsible(userTest01);
		activity03.setDateCreation(new DateTime());
		activity03.setPriority(1000);
		activity03.setState(3);
		activityBusiness.save(activity03);
		
		
		User userTest02 = new User();
		userTest02.setEmail("userTest02@userTest02.com");
		userTest02.setName("userTest02");
		userTest02.setLogin("userTest02");
		userTest02.setPassword("userTest02");
		userBusiness.save(userTest02);
		
		User userTest03 = new User();
		userTest03.setEmail("userTest03@userTest03.com");
		userTest03.setName("userTest03");
		userTest03.setLogin("userTest03");
		userTest03.setPassword("userTest03");
		userBusiness.save(userTest03);
		
		System.out.println("----> "+activityBusiness.size());
	}
}

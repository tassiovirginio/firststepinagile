package br.com.fa7.firststepinagile.pages.base;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.joda.time.DateTime;

import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.KanbanPage;
import br.com.fa7.firststepinagile.pages.LoginPage;
import br.com.fa7.firststepinagile.pages.SobrePage;
import br.com.fa7.firststepinagile.pages.SprintsPage;
import br.com.fa7.firststepinagile.pages.SprintsPage2;
import br.com.fa7.firststepinagile.pages.StartPage;
import br.com.fa7.firststepinagile.pages.StorysPage;
import br.com.fa7.firststepinagile.pages.TaskPage;
import br.com.fa7.firststepinagile.pages.modal.UserModalPage;

import java.util.Date;

@SuppressWarnings({ "serial","rawtypes"})
public class PageBase extends WebPage {
	
	private static final long serialVersionUID = 1L;
	
	protected Link linkStart;
	protected Link lkStorys;
	protected Link lkSprints;
	protected Link lkSprints2;
	protected Link lkTasks;
	protected Link linkKanban;
	protected Link lkSobre;
	
	private void allEnable(){
		linkStart.setEnabled(true);
		lkStorys.setEnabled(true);
		lkSprints.setEnabled(true);
		lkSprints2.setEnabled(true);
		lkTasks.setEnabled(true);
		linkKanban.setEnabled(true);
		lkSobre.setEnabled(true);
	}
	
	public PageBase(final User user, String tutorial) {
		
		createUserModal(user);
		
		linkStart = new Link("lkStart") {
			@Override
			public void onClick() {
				allEnable();
				setResponsePage(new StartPage(user));
			}
		};
		add(linkStart);

		lkStorys = new Link("lkStorys") {
			@Override
			public void onClick() {
				allEnable();
				setResponsePage(new StorysPage(user));
			}
		};
		add(lkStorys);
		
		lkSprints2 = new Link("lkSprints2") {
			@Override
			public void onClick() {
				allEnable();
				setResponsePage(new SprintsPage2(user));
			}
		};
		add(lkSprints2);
		
		lkSprints = new Link("lkSprints") {
			@Override
			public void onClick() {
				allEnable();
				setResponsePage(new SprintsPage(user));
			}
		};
		add(lkSprints);
		
		lkTasks = new Link("lkTasks") {
			@Override
			public void onClick() {
				allEnable();
				setResponsePage(new TaskPage(user));
			}
		};
		add(lkTasks);
		
		linkKanban = new Link("lkKanban") {
			@Override
			public void onClick() {
				allEnable();
				setResponsePage(new KanbanPage(user));
			}
		};
		add(linkKanban);

		lkSobre = new Link("lkSobre") {
			@Override
			public void onClick() {
				allEnable();
				setResponsePage(new SobrePage(user));
			}
		};
		add(lkSobre);
		
		
		Link lkExit = new Link("lkExit") {
			@Override
			public void onClick() {
				getSession().invalidateNow();
				setResponsePage(new LoginPage());
			}
		};
		add(lkExit);

        calcularTimeBox(user);
        
        if(user.getProjectAtual() != null){
			lkStorys.setVisible(true);
			lkSprints.setVisible(true);
			lkSprints2.setVisible(true);
			lkTasks.setVisible(true);
			linkKanban.setVisible(true);
		}else{
			lkStorys.setVisible(false);
			lkSprints.setVisible(false);
			lkSprints2.setVisible(false);
			lkTasks.setVisible(false);
			linkKanban.setVisible(false);
		}
        
        WebMarkupContainer tutorialIframe = new WebMarkupContainer("tutorial");
        tutorialIframe.add(new AttributeModifier("src", "../tutorial/"+tutorial));
		add(tutorialIframe);

	}
	
	private String red(String texto){
		return "<font style=\"color: #64B0FC;\">"+texto+"</font>";
	}

    public void calcularTimeBox(final User user){
    	
    	String retorno = "";
    	
    	if(user.getProjectAtual() == null){
    		retorno = retorno + red("Sem Projeto Selecionado");
    	}else{
    		retorno = retorno + "Projeto: " + red(user.getProjectAtual().getName()) + " - ";
    	}
    	
    	if(user.getProjectAtual() != null && user.getSprint() == null){
    		retorno = retorno + red("Sem Um Sprint Selecionado");
    	}else if(user.getProjectAtual() != null && user.getSprint() != null){
    		retorno = retorno + "Sprint: " + red(user.getSprint().getName()) + " - ";
    	}

        Label lbTimeBox = new Label("lbTimeBox",retorno);

        if(user.getSprint() != null){
            Date endDate2 = user.getSprint().getDateEnd();
            DateTime endDate = new DateTime(endDate2);
            DateTime now = new DateTime();

            DateTime dateCalc = endDate.minus(now.getMillis());

            if(dateCalc.getDayOfMonth() > 1){
            	retorno = retorno + "TimeBox: " + red(dateCalc.getDayOfMonth()+"") + " dias restantes";
            }else{
            	retorno = retorno + "TimeBox: " + red(dateCalc.getDayOfMonth()+"") + " dia restante";
            }
        }
        
        lbTimeBox = new Label("lbTimeBox",retorno);

        lbTimeBox.setEscapeModelStrings(false);
        
        add(lbTimeBox);
    }
	
	private void createUserModal(final User user) {
		final ModalWindow userModal;
		add(userModal = new ModalWindow("userModal"));
		userModal.setCookieName("userModal-cookie");
		userModal.setCssClassName(ModalWindow.CSS_CLASS_GRAY);
		userModal.setMinimalHeight(200);
		userModal.setMinimalWidth(200);
		userModal.setInitialHeight(200);
		userModal.setInitialWidth(200);
		userModal.setResizable(false);

		userModal.setPageCreator(new ModalWindow.PageCreator() {
			public Page createPage() {
				return new UserModalPage(
						PageBase.this.getPageReference(), userModal, user);
			}
		});
		
		userModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			public void onClose(AjaxRequestTarget target) {
//				setResponsePage(new StorysPage(user));
			}
		});

		AjaxLink ajLkUser = new AjaxLink<Void>("ajLkUser") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				userModal.show(target);
			}
		};
		
		Label lbUser = new Label("lbUser",user.getName());
		ajLkUser.add(lbUser);
		add(ajLkUser);
	}
	
}

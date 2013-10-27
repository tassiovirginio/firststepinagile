package br.com.fa7.firststepinagile.pages.base;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
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
	
	public PageBase(final User user) {
		
		createUserModal(user);
		
		linkStart = new Link("lkStart") {
			@Override
			public void onClick() {
				allEnable();
				this.setEnabled(false);
				setResponsePage(new StartPage(user));
			}
		};
		add(linkStart);

		lkStorys = new Link("lkStorys") {
			@Override
			public void onClick() {
				allEnable();
				this.setEnabled(false);
				setResponsePage(new StorysPage(user));
			}
		};
		add(lkStorys);
		
		lkSprints2 = new Link("lkSprints2") {
			@Override
			public void onClick() {
				allEnable();
				this.setEnabled(false);
				setResponsePage(new SprintsPage2(user));
			}
		};
		add(lkSprints2);
		
		lkSprints = new Link("lkSprints") {
			@Override
			public void onClick() {
				allEnable();
				this.setEnabled(false);
				setResponsePage(new SprintsPage(user));
			}
		};
		add(lkSprints);
		
		lkTasks = new Link("lkTasks") {
			@Override
			public void onClick() {
				allEnable();
				this.setEnabled(false);
				setResponsePage(new TaskPage(user));
			}
		};
		add(lkTasks);
		
		linkKanban = new Link("lkKanban") {
			@Override
			public void onClick() {
				allEnable();
				this.setEnabled(false);
				setResponsePage(new KanbanPage(user));
			}
		};
		add(linkKanban);

		lkSobre = new Link("lkSobre") {
			@Override
			public void onClick() {
				allEnable();
				this.setEnabled(false);
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

	}

    public void calcularTimeBox(final User user){

        Label lbTimeBox = new Label("lbTimeBox","Sem Um Sprint Selecionado");

        if(user.getSprint() != null){
            DateTime endDate = user.getSprint().getDateEnd();
            DateTime now = new DateTime();

            DateTime dateCalc = endDate.minus(now.getMillis());

            if(dateCalc.getDayOfMonth() > 1){
            	lbTimeBox = new Label("lbTimeBox","Sprint: "+ user.getSprint().getName() + " - TimeBox:" + dateCalc.getDayOfMonth()+" dias restantes ");
            }else{
            	lbTimeBox = new Label("lbTimeBox","Sprint: "+ user.getSprint().getName() + " - TimeBox:" + dateCalc.getDayOfMonth()+" dia restante ");
            }
        }

        add(lbTimeBox);
    }
	
	private void createUserModal(final User user) {
		final ModalWindow userModal;
		add(userModal = new ModalWindow("userModal"));
		userModal.setCookieName("userModal-cookie");
		userModal.setCssClassName(ModalWindow.CSS_CLASS_GRAY);

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
		ajLkUser.setVisible(user.isAdmin());
		add(ajLkUser);
	}
	
}

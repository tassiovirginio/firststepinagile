package br.com.fa7.firststepinagile.pages;

import org.apache.wicket.markup.html.basic.Label;

import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.panels.ActivityStatePanel;

public class KanbanPage extends PageBase {
	
	private static final long serialVersionUID = 1L;
	
	public KanbanPage(User user) {
		super(user);
		
		Label lbName = new Label("lbName",user.getName());
		add(lbName);
		
		ActivityStatePanel statePanelStop = new ActivityStatePanel("statePanelStop", "Stop", user, 1, this);
		add(statePanelStop);
		
		ActivityStatePanel statePanelRunning = new ActivityStatePanel("statePanelRunning", "Running", user, 2, this);
		add(statePanelRunning);
		
		ActivityStatePanel statePanelCompleted = new ActivityStatePanel("statePanelCompleted", "Completed", user, 3, this);
		add(statePanelCompleted);
		
		
	}

}

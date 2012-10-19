package br.com.fa7.firststepinagile.pages.modal;

import java.util.Date;
import java.util.List;

import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import br.com.fa7.firststepinagile.business.SprintBusiness;
import br.com.fa7.firststepinagile.entities.Activity;
import br.com.fa7.firststepinagile.entities.Sprint;
import br.com.fa7.firststepinagile.entities.User;

public class SprintModalPage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private SprintBusiness sprintBusiness;
	
	private Sprint sprintLocal;
	
	private Date dateStart;
	
	private Date dateEnd;
	
	public SprintModalPage(final PageReference pageRefOrigem, final ModalWindow window, final User user){
		this(pageRefOrigem, window, user, null);
	}
	
	public SprintModalPage(final PageReference pageRefOrigem, final ModalWindow window, final User user,Sprint sprintSelected) {
		
		if(sprintSelected != null){
			System.out.println("Escolheu -> " + sprintSelected.getName());
			if(sprintSelected.getDateStart()!=null)
			dateStart = sprintSelected.getDateStart().toDate();
			if(sprintSelected.getDateEnd()!=null)
			dateEnd = sprintSelected.getDateEnd().toDate();
		}else{
			sprintSelected = new Sprint();
		}
		
		sprintLocal = sprintSelected;
		
		Form<Activity> form = new Form<Activity>("form");
		form.add(new TextField("tfName", new PropertyModel(sprintSelected,"name")).setRequired(true));
		form.add(new TextArea<String>("tfDescription", new PropertyModel<String>(sprintSelected, "description")));
		form.add(new DateField("dfStart", new PropertyModel<Date>(this, "dateStart")));
		form.add(new DateField("dfEnd", new PropertyModel<Date>(this, "dateEnd")));
		
		form.add(new Button("btSalvar") {
			@Override
			public void onSubmit() {
				System.out.println("Gravando a atividade..." + sprintLocal);
				sprintLocal.setDateStart(new DateTime(dateStart.getTime()));
				sprintLocal.setDateEnd(new DateTime(dateEnd.getTime()));
				sprintBusiness.save(sprintLocal);
				setResponsePage(new SprintModalPage(pageRefOrigem, window, user, sprintLocal));
			}
		});
		
		
		form.add(new AjaxButton("btSelecionar", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				pageRefOrigem.getPage().getPageParameters().set("sprintId", sprintLocal.getId());
				window.close(target);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				window.close(target);
			}
		});
		
		add(form);
		
		
		List<Sprint> listSprints = sprintBusiness.allOrderById();
		
		ListView<Sprint> listViewSprint = new ListView<Sprint>("listViewSprint", listSprints) {
			@Override
			protected void populateItem(ListItem<Sprint> item) {
				final Sprint sprint = (Sprint)item.getModelObject();
				Label lbName = new Label("lbName", sprint.getName());
				Label lbId = new Label("lbId", sprint.getId().toString());
				item.add(lbName);
				item.add(lbId);
				
				item.add(new Link("lkRight") {
					@Override
					public void onClick() {
						sprintLocal = sprint;
						setResponsePage(new SprintModalPage(pageRefOrigem, window, user, sprint));
					}
				});
				
				item.add(new Link("lkDelete") {
					@Override
					public void onClick() {
						sprintBusiness.delete(sprint);
						setResponsePage(new SprintModalPage(pageRefOrigem, window, user, sprintLocal));
					}
				});
				
			}
		};
		add(listViewSprint);

	}
}

package br.com.fa7.firststepinagile.pages;

import java.util.Date;
import java.util.List;

import br.com.fa7.firststepinagile.entities.*;
import br.com.fa7.firststepinagile.pages.provider.ProjectProvider;
import br.com.fa7.firststepinagile.pages.provider.SprintProvider;
import br.com.fa7.firststepinagile.pages.provider.StoryProvider;
//import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.firststepinagile.business.SprintBusiness;
import br.com.fa7.firststepinagile.pages.base.PageBase;
import org.wicketstuff.datetime.extensions.yui.calendar.DateField;

@SuppressWarnings({ "serial","rawtypes", "unchecked"})
public class SprintsPage2 extends PageBase {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private SprintBusiness sprintBusiness;
	
	private Sprint sprintLocal;
	
	public SprintsPage2(final User user) {
		this(user,new Sprint());
	}

	public SprintsPage2(final User user, Sprint sprintEditar) {
		super(user,"tutorial4.html");
		
		super.lkSprints2.setEnabled(false);
		
		sprintLocal = sprintEditar;
		
		Form<Activity> form = new Form<Activity>("form");
		form.add(new TextField("tfName", new PropertyModel(sprintLocal,"name")).setRequired(true));
		form.add(new TextArea<String>("tfDescription", new PropertyModel<String>(sprintLocal, "description")));
		form.add(new DateField("dfStart", new PropertyModel<Date>(sprintLocal, "dateStart2")).setRequired(true));
		form.add(new DateField("dfEnd", new PropertyModel<Date>(sprintLocal, "dateEnd2")).setRequired(true));
		
		form.add(new Button("btSalvar") {
			@Override
			public void onSubmit() {
				sprintLocal.setProject(user.getProjectAtual());
				sprintBusiness.save(sprintLocal);
				setResponsePage(new SprintsPage2(user));
			}
		});
		
		add(form);
		
		List<Sprint> listSprints = sprintBusiness.all(user.getProjectAtual());

        DataView<Sprint> dataView = new DataView<Sprint>("listViewSprint", new SprintProvider(sprintBusiness, listSprints,true)) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<Sprint> item) {
                final Sprint sprint = item.getModelObject();

                String nameSprint = sprint.getName();
                if(user.getSprint() != null && user.getSprint().getId() != null && user.getSprint().getId().equals(sprint.getId())){
                    nameSprint = " (Selecionado) "+sprint.getName();
                }

				Label lbName = new Label("lbName", nameSprint);
				Label lbId = new Label("lbId", sprint.getId().toString());
				item.add(lbName);
				item.add(lbId);
				
				Link lkSelect = new Link<Sprint>("lkSelect") {
					@Override
					public void onClick() {
						user.setSprint(sprint);
						setResponsePage(new SprintsPage2(user));
					}
				};
				
				if(user.getSprint() != null && user.getSprint().getId().equals(sprint.getId())){
					lkSelect.setEnabled(false);
				}
				
				item.add(lkSelect);
				
				item.add(new Link<Sprint>("lkRight") {
					@Override
					public void onClick() {
						setResponsePage(new SprintsPage2(user,sprint));
					}
				});
				
				item.add(new Link<Sprint>("lkDelete") {
					@Override
					public void onClick() {
						sprintBusiness.delete(sprint);
						setResponsePage(new SprintsPage2(user));
					}
				});
				
			}
		};
        dataView.setItemsPerPage(6L);
        add(dataView);
        add(new PagingNavigator("navigator", dataView));

	}
}

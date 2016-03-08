package br.com.fa7.firststepinagile.pages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.fa7.firststepinagile.pages.provider.ProjectProvider;
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
import org.joda.time.DateTime;

import br.com.fa7.firststepinagile.business.ConviteBusiness;
import br.com.fa7.firststepinagile.business.ProjectBusiness;
import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.Convite;
import br.com.fa7.firststepinagile.entities.Project;
import br.com.fa7.firststepinagile.entities.User;
import br.com.fa7.firststepinagile.pages.base.PageBase;
import org.joda.time.LocalDateTime;

@SuppressWarnings({"serial","rawtypes"})
public class StartPage extends PageBase {
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private ProjectBusiness projectBusiness;
	
	@SpringBean
	private UserBusiness userBusiness;
	
	@SpringBean
	private ConviteBusiness conviteBusiness;
	
	public StartPage(final User user) {
		this(user,new Project());
	}
	
	public StartPage(final User user, final Project projectEditar) {
		super(user,"tutorial1.html");
		
		Form<Project> form = new Form<Project>("form");
		form.add(new TextField<String>("tfName", new PropertyModel<String>(projectEditar,"name")).setRequired(true));
		form.add(new TextArea<String>("tfDescription", new PropertyModel<String>(projectEditar, "description")));
		
		form.add(new Button("btSalvar") {
			@Override
			public void onSubmit() {
				System.out.println(projectEditar.getName());
				projectEditar.setCreator(user);
				projectEditar.setDateCreation(new LocalDateTime());
				projectBusiness.save(projectEditar);
				setResponsePage(new StartPage(user));
			}
		});
		add(form);
		
		final Convite conviteNovo = new Convite();
		Form<Project> form2 = new Form<Project>("form2");
		form2.add(new TextField<String>("email", new PropertyModel<String>(conviteNovo,"email")).setRequired(true));
		form2.add(new Button("salvar") {
			@Override
			public void onSubmit() {
				Convite convite = new Convite();
				convite.setEmail(conviteNovo.getEmail());
				convite.setProject(projectEditar);
				if(projectEditar.getId() != null){
					conviteBusiness.save(convite);
				}
				projectEditar.getConvites().add(convite);
				setResponsePage(new StartPage(user,projectEditar));
			}
		});
		
		add(form2);
		
		ListView<Convite> ListViewConvite = createListConvites(user,projectEditar);
		add(ListViewConvite);
		
		List<Project> projetosUser = new ArrayList<Project>(projectBusiness.listAllByUser(user));

        DataView<Project> dataView = new DataView<Project>("listViewProjectUsers", new ProjectProvider(projectBusiness, projetosUser,true)) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<Project> item) {
                final Project project = item.getModelObject();

                Label lbName = new Label("lbName", project.getName().trim());
                item.add(lbName);

                Label lbSizeSprints = new Label("lbSizeSprints", project.getSprints().size()+"");
                item.add(lbSizeSprints);

                Link lkSelect = new Link("lkSelect") {
                    @Override
                    public void onClick() {
                        user.setProjectAtual(project);
                        userBusiness.save(user);
                        setResponsePage(new StartPage(user));
                    }
                };

                if(user.getProjectAtual() != null && user.getProjectAtual().equals(project)){
                    lkSelect.setEnabled(false);
                }else{
                    lkSelect.setEnabled(true);
                }

                item.add(lkSelect);

                item.add(new Link("lkEditor") {
                    @Override
                    public void onClick() {
                        setResponsePage(new StartPage(user,project));
                    }
                });

                item.add(new Link("lkDelete") {
                    @Override
                    public void onClick() {
                        projectBusiness.delete(project);
                        setResponsePage(new StartPage(user));
                    }
                });
            }
        };

        dataView.setItemsPerPage(6L);
        add(dataView);
        add(new PagingNavigator("navigator", dataView));
		

		List<Project> projetosConvidados = new ArrayList<Project>(projectBusiness.listAllByConvite(user));

        DataView<Project> dataView2 = new DataView<Project>("listViewProjectConvites", new ProjectProvider(projectBusiness, projetosConvidados,true)) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final Item<Project> item) {
                final Project project = item.getModelObject();
		

				Label lbName = new Label("lbName", project.getName().trim());
				item.add(lbName);
				
				Link lkSelect = new Link("lkSelect") {
					@Override
					public void onClick() {
						user.setProjectAtual(project);
						userBusiness.save(user);
						setResponsePage(new StartPage(user));
					}
				};
					
				if(user.getProjectAtual() != null && user.getProjectAtual().equals(project)){
					lkSelect.setEnabled(false);
				}else{
					lkSelect.setEnabled(true);
				}
				
				item.add(lkSelect);
			}
		};
        dataView2.setItemsPerPage(6L);
        add(dataView2);
        add(new PagingNavigator("navigator2", dataView2));
		
		super.linkStart.setEnabled(false);
	}
	
	
	private ListView<Convite> createListConvites(final User user, final Project project){
		
		ListView<Convite> listViewConvite = new ListView<Convite>("listViewConvite", new ArrayList<Convite>(project.getConvites())) {
			@Override
			protected void populateItem(ListItem<Convite> item) {
				final Convite convite = item.getModelObject();

				Label lbEmail = new Label("lbEmail", convite.getEmail().trim());
				item.add(lbEmail);
				
				item.add(new Link("lkDelete") {
					@Override
					public void onClick() {
						project.getConvites().remove(convite);
						conviteBusiness.delete(convite);
						setResponsePage(new StartPage(user,project));
					}
				});
				
			}
		};
		
		return listViewConvite;
	}

}

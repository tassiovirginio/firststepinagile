package br.com.fa7.firststepinagile.pages.provider;

import br.com.fa7.firststepinagile.business.ProjectBusiness;
import br.com.fa7.firststepinagile.entities.Project;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.Iterator;
import java.util.List;

/**
 * Created by tassio on 04/03/15.
 */
public class ProjectProvider implements IDataProvider<Project> {

    private ProjectBusiness projectBusiness;

    private List<Project> listProject;

    private Boolean busca;

    public ProjectProvider(ProjectBusiness projectBusiness,List<Project> listProject, Boolean busca){
        this.projectBusiness = projectBusiness;
        this.listProject = listProject;
        this.busca = busca;
    }

    @Override
    public Iterator<? extends Project> iterator(long first, long count) {
        List<Project> lista = null;
        if(busca) {
            lista = this.listProject;
        }else{
            lista = projectBusiness.find(first, count);
        }
        return lista.iterator();
    }

    @Override
    public long size() {
        Integer qtd = 0;
        if(busca){
            qtd = listProject.size();
        }else{
            qtd = projectBusiness.size();
        }
        return qtd;
    }

    @Override
    public IModel<Project> model(Project project) {
        return new DetachableModel(project);
    }

    @Override
    public void detach() {}

    class DetachableModel extends LoadableDetachableModel<Project> {

        private final long id;

        public DetachableModel(long id) {
            if (id == 0)
            {
                throw new IllegalArgumentException();
            }
            this.id = id;
        }

        public DetachableModel(Project i)
        {
            this(i.getId());
        }

        protected Project load()
        {
            // loads contact from the database
            return projectBusiness.findById(id);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DetachableModel that = (DetachableModel) o;

            if (id != that.id) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return (int) (id ^ (id >>> 32));
        }
    }
}




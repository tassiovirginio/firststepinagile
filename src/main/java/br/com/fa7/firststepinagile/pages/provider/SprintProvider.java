package br.com.fa7.firststepinagile.pages.provider;

import br.com.fa7.firststepinagile.business.ProjectBusiness;
import br.com.fa7.firststepinagile.business.SprintBusiness;
import br.com.fa7.firststepinagile.entities.Project;
import br.com.fa7.firststepinagile.entities.Sprint;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.Iterator;
import java.util.List;

/**
 * Created by tassio on 04/03/15.
 */
public class SprintProvider implements IDataProvider<Sprint> {

    private SprintBusiness sprintBusiness;

    private List<Sprint> listSprint;

    private Boolean busca;

    public SprintProvider(SprintBusiness sprintBusiness, List<Sprint> listSprint, Boolean busca){
        this.sprintBusiness = sprintBusiness;
        this.listSprint = listSprint;
        this.busca = busca;
    }

    @Override
    public Iterator<? extends Sprint> iterator(long first, long count) {
        List<Sprint> lista = null;
        if(busca) {
            lista = this.listSprint;
        }else{
            lista = sprintBusiness.find(first, count);
        }
        return lista.iterator();
    }

    @Override
    public long size() {
        Integer qtd = 0;
        if(busca){
            qtd = listSprint.size();
        }else{
            qtd = sprintBusiness.size();
        }
        return qtd;
    }

    @Override
    public IModel<Sprint> model(Sprint sprint) {
        return new DetachableModel(sprint);
    }

    @Override
    public void detach() {}

    class DetachableModel extends LoadableDetachableModel<Sprint> {

        private final long id;

        public DetachableModel(long id) {
            if (id == 0)
            {
                throw new IllegalArgumentException();
            }
            this.id = id;
        }

        public DetachableModel(Sprint i)
        {
            this(i.getId());
        }

        protected Sprint load()
        {
            // loads contact from the database
            return sprintBusiness.findById(id);
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




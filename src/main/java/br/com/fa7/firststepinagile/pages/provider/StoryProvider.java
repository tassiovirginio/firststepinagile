package br.com.fa7.firststepinagile.pages.provider;

import br.com.fa7.firststepinagile.business.ProjectBusiness;
import br.com.fa7.firststepinagile.business.StoryBusiness;
import br.com.fa7.firststepinagile.entities.Project;
import br.com.fa7.firststepinagile.entities.Story;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.Iterator;
import java.util.List;

/**
 * Created by tassio on 04/03/15.
 */
public class StoryProvider implements IDataProvider<Story> {

    private StoryBusiness storyBusiness;

    private List<Story> listStory;

    private Boolean busca;

    public StoryProvider(StoryBusiness storyBusiness, List<Story> listStory, Boolean busca){
        this.storyBusiness = storyBusiness;
        this.listStory = listStory;
        this.busca = busca;
    }

    @Override
    public Iterator<? extends Story> iterator(long first, long count) {
        List<Story> lista = null;
        if(busca) {
            lista = this.listStory;
        }else{
            lista = storyBusiness.find(first, count);
        }
        return lista.iterator();
    }

    @Override
    public long size() {
        Integer qtd = 0;
        if(busca){
            qtd = listStory.size();
        }else{
            qtd = storyBusiness.size();
        }
        return qtd;
    }

    @Override
    public IModel<Story> model(Story story) {
        return new DetachableModel(story);
    }

    @Override
    public void detach() {}

    class DetachableModel extends LoadableDetachableModel<Story> {

        private final long id;

        public DetachableModel(long id) {
            if (id == 0)
            {
                throw new IllegalArgumentException();
            }
            this.id = id;
        }

        public DetachableModel(Story i)
        {
            this(i.getId());
        }

        protected Story load()
        {
            // loads contact from the database
            return storyBusiness.findById(id);
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




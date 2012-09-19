package br.com.fa7.firststepinagile.pages.modal;

import java.util.List;

import org.apache.wicket.PageReference;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.firststepinagile.business.SprintBusiness;
import br.com.fa7.firststepinagile.entities.Sprint;
import br.com.fa7.firststepinagile.entities.User;

public class SprintModalPage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	@SpringBean
	private SprintBusiness sprintBusiness;
	
	public SprintModalPage(final PageReference pageRefOrigem, final ModalWindow window, final User user) {
		
		List<Sprint> listSprints = sprintBusiness.allOrderById();

	}
}

package br.com.fa7.firststepinagile.pages.modal;

import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;

public class StoryModalPage extends WebPage {

	private static final long serialVersionUID = 1L;

	public StoryModalPage(final PageReference pageRefOrigem,	final ModalWindow window) {
		
		add(new AjaxLink<Void>("closeOK") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				window.close(target);
			}
		});

		add(new AjaxLink<Void>("closeCancel") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				window.close(target);
			}
		});

	}
}

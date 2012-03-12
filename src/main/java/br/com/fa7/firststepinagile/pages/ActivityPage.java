package br.com.fa7.firststepinagile.pages;

import java.util.Date;
import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import br.com.fa7.firststepinagile.business.ActivityBusiness;
import br.com.fa7.firststepinagile.entities.Activity;

public class ActivityPage extends WebPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private ActivityBusiness activityBusiness;

	public ActivityPage(ModalWindow modalWindow, final Activity activity) {

		setDefaultModel(new CompoundPropertyModel<Activity>(activity));

		Form form = new Form("form") {
			protected void onSubmit() {
				activityBusiness.save(activity);
			};
		};
		add(form);

		TextField<String> tfName = new TextField<String>("name");
		form.add(tfName);

		TextArea<String> taDescription = new TextArea<String>("description");
		form.add(taDescription);

//		DateField dfDateStart = new DateField("dateStart");

		final Locale selectedLocale = Session.get().getLocale();

//		DateTextField dfDateStart = new DateTextField("dateStart",new StyleDateConverter("S-", true)) {
//			@Override
//			public Locale getLocale() {
//				return selectedLocale;
//			}
//		};
		
//		DateTextField dfDateStart = new DateTextField("dateStart",new StyleDateConverter("S-", true));
		
		DateTextField dfDateStart = DateTextField.forDateStyle("dateStart", "M-");
		dfDateStart.add(new DatePicker());
		
		form.add(dfDateStart);
		
//		DatePicker datePicker = new DatePicker(){
//            @Override
//            protected String getAdditionalJavaScript(){
//                return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render();";
//            }
//        };
//        datePicker.setShowOnFieldClick(true);
//        datePicker.setAutoHide(true);
//        dfDateStart.add(datePicker);
        
		add(new Label("dateCreation"));
		add(new Label("creator.name"));
		add(new Label("currentResponsible.name"));

	}

}

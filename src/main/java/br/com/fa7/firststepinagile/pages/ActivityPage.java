package br.com.fa7.firststepinagile.pages;

import java.util.Date;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import br.com.fa7.firststepinagile.business.ActivityBusiness;
import br.com.fa7.firststepinagile.business.UserBusiness;
import br.com.fa7.firststepinagile.entities.Activity;
import br.com.fa7.firststepinagile.entities.User;

import com.google.code.jqwicket.ui.ckeditor.CKEditorOptions;
import com.google.code.jqwicket.ui.ckeditor.CKEditorTextArea;
import com.google.code.jqwicket.ui.colorpicker.ColorPickerTextField;

public class ActivityPage extends WebPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private ActivityBusiness activityBusiness;
	
	@SpringBean
	private UserBusiness userBusiness;

	private Date startDate;

	private Activity activity;

	public ActivityPage(final ModalWindow modalWindow, Activity activityId, final Page pageOrigen) {

		final User user = (User) getSession().getAttribute("user");
		
		if(activityId.getId() != null){
			this.activity = activityBusiness.findById(activityId.getId());
		}else{
			this.activity = activityId;
			this.activity.setCreator(user);
			this.activity.setCurrentResponsible(user);
			this.activity.setDateCreation(new DateTime());
			this.activity.setPriority(1000);
			this.activity.setState(1); 
		}

		setDefaultModel(new CompoundPropertyModel<Activity>(this.activity));

		if (this.activity.getDateStart() != null) {
			startDate = this.activity.getDateStart().toDate();
		}
		
		add(new Label("id"));
		add(new Label("dateCreation", this.activity.getDateCreation().toString("HH:mm:ss dd/MM/yyyy")));
		add(new Label("creator.name"));

		Form<Activity> form = new Form<Activity>("form") {
			private static final long serialVersionUID = 1L;
			protected void onSubmit() {
				ActivityPage.this.activity.setDateStart(new DateTime(startDate));
				activityBusiness.save(ActivityPage.this.activity);
//				modalWindow.close(new AjaxRequestTarget(pageOrigen));
//				modalWindow.setResponsePage(pageOrigen);
			};
		};
		add(form);

		form.add(new TextField<String>("name"));

		form.add(new CKEditorTextArea<String>("description",new CKEditorOptions()
				.dialog_backgroundCoverColor("red").language("pt")
				.toolbar(new CharSequence[][] {	
				{ "Bold", "Italic", "-","NumberedList", "BulletedList","-", "Link", "Unlink" },{ "UIColor" } })));


		DateTextField dfDateStart = DateTextField.forDateStyle("dateStart",new PropertyModel<Date>(this, "startDate"), "M-");
		dfDateStart.add(new DatePicker());
		form.add(dfDateStart);

		
		ColorPickerTextField<String> colorPickerTextField = new ColorPickerTextField<String>("color");
		colorPickerTextField.add(new SimpleAttributeModifier("style", "color: #" + activity.getColor() +" !important;"));
		form.add(colorPickerTextField);
		
		
		form.add(new DropDownChoice<User>("currentResponsible",userBusiness.loadAllUser(),new ChoiceRenderer("name","id")));

	}

}

package views.user;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.GetUser;
import servicelayer.service.ServiceWebStub.GetUserResponse;
import servicelayer.service.ServiceWebStub.VOUser;
import utils.PopupWindow;
import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import controllers.UserController;
import entities.RequestContext;

public class ModifyProfileView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btn_cancel;
	@AutoGenerated
	private Button btn_modify;
	@AutoGenerated
	private Label lblTitle;
	@AutoGenerated
	private TextField txt_email;
	@AutoGenerated
	private TextField txt_lastName;
	@AutoGenerated
	private TextField txt_name;
	private VOUser userToShow;
	// private int
	private int idUser = 1;

	private static final long serialVersionUID = 1L;

	public ModifyProfileView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		btn_modify.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (validateModifyProfileUser()) {
					ServiceWebStub modifyProfileService;

					UserController modifyUser = new UserController();

					VOUser voUser = new VOUser();
					voUser.setEmail(txt_email.getValue());
					voUser.setLastName(txt_lastName.getValue());
					voUser.setName(txt_name.getValue());
					voUser.setUserType(userToShow.getUserType());

					modifyUser.modifyUser(voUser, idUser);

//					Notification notif = new Notification("Aviso: ", "Usuario modificado correctamente", Notification.TYPE_HUMANIZED_MESSAGE);
//					notif.setDelayMsec(2000);
//					notif.show(Page.getCurrent());
					PopupWindow popup = new PopupWindow("AVISO", "Usuario creado correctamente");
					
				}
			}
		});

		btn_cancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);

			}
		});

	}

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			idUser = RequestContext.getRequestContext().getId();
			txt_name.focus();
			ServiceWebStub uploadUser;
			try {
				uploadUser = new ServiceWebStub();
				GetUser user = new GetUser();
				user.setId(idUser);

				GetUserResponse userFromBase = uploadUser.getUser(user);

				userToShow = userFromBase.get_return();

				txt_name.setValue(userToShow.getName());
				txt_lastName.setValue(userToShow.getLastName());
				txt_email.setValue(userToShow.getEmail());

			} catch (AxisFault e1) {
				String error = e1.getMessage().replace("<faultstring>", "");
//				Notification notif = new Notification (error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
//				notif.setDelayMsec(2000);
//				notif.show(Page.getCurrent());
				PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
			} catch (RemoteException e1) {
				e1.printStackTrace();
//				String error = e1.getMessage().replace("<faultstring>", "");
//				Notification notif = new Notification (error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
//				notif.setDelayMsec(2000);
//				notif.show(Page.getCurrent());
//				PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
			}
		}
	}

	public boolean validateModifyProfileUser() {
		boolean validate = true;
		String errors = "";

		String name = txt_name.getValue();
		if (name.isEmpty()) {
			validate = false;
			errors += "Debes ingresar el nombre\n";
		}

		String lastName = txt_lastName.getValue();
		if (lastName.isEmpty()) {
			validate = false;
			errors += "Debes ingresar el apellido\n";
		}

		String email = txt_email.getValue();
		// if (StringUtils.isBlank(email)) {
		if (email.isEmpty()) {
			validate = false;
			errors += "Debes ingresar el apellido\n";
		}

		if (!validate) {
//			Notification notif = new Notification(errors,
//					Notification.TYPE_ERROR_MESSAGE);
//			notif.setDelayMsec(2000);
//			notif.show(Page.getCurrent());
			PopupWindow popup = new PopupWindow("ERROR", errors);
		}
		return validate;
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("360px");
		mainLayout.setHeight("501px");
		
		// top-level component properties
		setWidth("360px");
		setHeight("501px");
		
		// txt_name
		txt_name = new TextField();
		txt_name.setCaption("Nombre");
		txt_name.setImmediate(false);
		txt_name.setWidth("240px");
		txt_name.setHeight("-1px");
		txt_name.setTabIndex(1);
		txt_name.setRequired(true);
		mainLayout.addComponent(txt_name, "top:120.0px;left:0.0px;");
		
		// txt_lastName
		txt_lastName = new TextField();
		txt_lastName.setCaption("Apellido");
		txt_lastName.setImmediate(false);
		txt_lastName.setWidth("241px");
		txt_lastName.setHeight("-1px");
		txt_lastName.setTabIndex(2);
		txt_lastName.setRequired(true);
		txt_lastName.setNullSettingAllowed(true);
		mainLayout.addComponent(txt_lastName, "top:180.0px;left:0.0px;");
		
		// txt_email
		txt_email = new TextField();
		txt_email.setCaption("Email");
		txt_email.setImmediate(false);
		txt_email.setWidth("240px");
		txt_email.setHeight("-1px");
		txt_email.setTabIndex(3);
		txt_email.setRequired(true);
		mainLayout.addComponent(txt_email, "top:240.0px;left:0.0px;");
		
		// label_2
		lblTitle = new Label();
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Modificar usuario");
		lblTitle.setStyleName("titleLabel");
		mainLayout.addComponent(lblTitle, "top:62.0px;left:0.0px;");
		
		// btn_modify
		btn_modify = new Button();
		btn_modify.setCaption("Modificar");
		btn_modify.setImmediate(true);
		btn_modify.setWidth("101px");
		btn_modify.setHeight("-1px");
		btn_modify.setTabIndex(4);
		mainLayout.addComponent(btn_modify, "top:300.0px;left:0.0px;");
		
		// btn_cancel
		btn_cancel = new Button();
		btn_cancel.setCaption("Cancelar");
		btn_cancel.setImmediate(true);
		btn_cancel.setWidth("100px");
		btn_cancel.setHeight("-1px");
		btn_cancel.setTabIndex(5);
		mainLayout.addComponent(btn_cancel, "top:300.0px;left:140.0px;");
		
		return mainLayout;
	}

}
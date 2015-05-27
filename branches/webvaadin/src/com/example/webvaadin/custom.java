package com.example.webvaadin;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.GetUser;
import servicelayer.service.ServiceWebStub.VOUser;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class custom extends CustomComponent implements Button.ClickListener {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private AbsoluteLayout absoluteLayout_2;
	@AutoGenerated
	private Label label_1;
	@AutoGenerated
	private Label lblUserName;
	@AutoGenerated
	private TextField txtUserName;
	@AutoGenerated
	private TextField txtUserId;
	@AutoGenerated
	private Label lblUserId;
	@AutoGenerated
	private Button btnSearch;
	@AutoGenerated
	private TextField txtUserIdSearch;
	@AutoGenerated
	private Label lblEnterUserId;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3346934168591535350L;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	
	public custom() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		// TODO add user code here
		
		btnSearch.addClickListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {
		    	
		    	ServiceWebStub userService;
		    	
		    	try {
					userService = new ServiceWebStub();
					
			        GetUser getUser = new GetUser();
			        getUser.setId(Integer.parseInt(txtUserIdSearch.getValue()));
			        VOUser user = userService.getUser(getUser).get_return();
			        
			        txtUserId.setValue(String.valueOf(user.getId()));
			        txtUserName.setValue(user.getName());
			        
				} catch (AxisFault e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// absoluteLayout_2
		absoluteLayout_2 = buildAbsoluteLayout_2();
		mainLayout.addComponent(absoluteLayout_2,
				"top:60.0px;right:159.0px;bottom:270.0px;left:40.0px;");
		
		return mainLayout;
	}

	@AutoGenerated
	private AbsoluteLayout buildAbsoluteLayout_2() {
		// common part: create layout
		absoluteLayout_2 = new AbsoluteLayout();
		absoluteLayout_2.setImmediate(false);
		absoluteLayout_2.setWidth("100.0%");
		absoluteLayout_2.setHeight("100.0%");
		
		// lblEnterUserId
		lblEnterUserId = new Label();
		lblEnterUserId.setImmediate(false);
		lblEnterUserId.setWidth("-1px");
		lblEnterUserId.setHeight("-1px");
		lblEnterUserId.setValue("ENTER USER ID");
		absoluteLayout_2
				.addComponent(lblEnterUserId, "top:62.0px;left:42.0px;");
		
		// txtUserIdSearch
		txtUserIdSearch = new TextField();
		txtUserIdSearch.setImmediate(false);
		txtUserIdSearch.setWidth("-1px");
		txtUserIdSearch.setHeight("-1px");
		absoluteLayout_2.addComponent(txtUserIdSearch,
				"top:56.0px;left:165.0px;");
		
		// btnSearch
		btnSearch = new Button();
		btnSearch.setCaption("Search");
		btnSearch.setImmediate(true);
		btnSearch.setWidth("-1px");
		btnSearch.setHeight("-1px");
		absoluteLayout_2.addComponent(btnSearch, "top:54.0px;left:320.0px;");
		
		// lblUserId
		lblUserId = new Label();
		lblUserId.setImmediate(false);
		lblUserId.setWidth("-1px");
		lblUserId.setHeight("-1px");
		lblUserId.setValue("USER ID");
		absoluteLayout_2.addComponent(lblUserId, "top:102.0px;left:71.0px;");
		
		// txtUserId
		txtUserId = new TextField();
		txtUserId.setImmediate(false);
		txtUserId.setWidth("-1px");
		txtUserId.setHeight("-1px");
		absoluteLayout_2.addComponent(txtUserId, "top:96.0px;left:165.0px;");
		
		// txtUserName
		txtUserName = new TextField();
		txtUserName.setImmediate(false);
		txtUserName.setWidth("-1px");
		txtUserName.setHeight("-1px");
		absoluteLayout_2.addComponent(txtUserName, "top:136.0px;left:165.0px;");
		
		// lblUserName
		lblUserName = new Label();
		lblUserName.setImmediate(false);
		lblUserName.setWidth("-1px");
		lblUserName.setHeight("-1px");
		lblUserName.setValue("USER NAME");
		absoluteLayout_2.addComponent(lblUserName, "top:142.0px;left:60.0px;");
		
		// label_1
		label_1 = new Label();
		label_1.setImmediate(false);
		label_1.setWidth("-1px");
		label_1.setHeight("-1px");
		label_1.setValue("Search user");
		absoluteLayout_2.addComponent(label_1, "top:22.0px;left:190.0px;");
		
		return absoluteLayout_2;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}

}

package com.example.webvaadin;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.example.service.WebServiceStub;
import com.example.service.WebServiceStub.GetValue;
import com.example.service.WebServiceStub.GetValueResponse;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("webvaadin")
public class WebvaadinUI extends UI {
	
	@Override
	protected void init(VaadinRequest request) {
		// Create the content root layout for the UI
        VerticalLayout content = new VerticalLayout();
        setContent(content);

        // Needed because the composites are full size
        content.setSizeFull();

        WebServiceStub service;
        String response = null;
		try {
			service = new WebServiceStub();
			GetValue operation = new GetValue();
	        operation.setValue(" Hi, Hola mundo!! Message from server");
	        response = service.getValue(operation).get_return();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
        custom myComposite = new custom();
        Label label_1 = new Label();
		label_1.setCaption(response);
		label_1.setImmediate(false);
		label_1.setWidth("-1px");
		label_1.setHeight("-1px");
		label_1.setValue("Label");
		
		content.addComponent(label_1);
        content.addComponent(myComposite);
		
	}

}
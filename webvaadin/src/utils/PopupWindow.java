package utils;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PopupWindow extends Window{
	
	public PopupWindow(String title ,String message){
		super(title);
		center();
		
		VerticalLayout content = new VerticalLayout();
		content.addComponent(new Label(message));
		content.setMargin(true);
		setContent(content);
		
		setClosable(true);
		setModal(true);
		setDraggable(false);
		setResizable(true);
		setWidth("400px");
		setHeight("200px");
	
	WebvaadinUI.getCurrent().addWindow(this);
	
	}

}

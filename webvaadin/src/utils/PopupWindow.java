package utils;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PopupWindow extends Window{
	
	public PopupWindow(String title ,String message){
		super(title);
		center();
		
		VerticalLayout content = new VerticalLayout();
		TextArea textMessage = new TextArea();
		content.addComponent(textMessage);
		
		content.setMargin(true);
		setContent(content);
		
		setClosable(true);
		setModal(true);
		setDraggable(false);
		setResizable(true);
		setWidth("400px");
		setHeight("200px");
		
		textMessage.setValue(message);
		textMessage.setWidth("360px");
		textMessage.setHeight("120px");
		textMessage.setReadOnly(true);
		
		if(title == "ERROR"){
			setStyleName("popupStyle");
		}
		
	WebvaadinUI.getCurrent().addWindow(this);
	
	}

}

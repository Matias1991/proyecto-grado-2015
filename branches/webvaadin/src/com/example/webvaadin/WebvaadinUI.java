package com.example.webvaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
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

        custom myComposite = new custom();
        content.addComponent(myComposite);
	}
}
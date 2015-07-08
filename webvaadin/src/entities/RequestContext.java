package entities;

import com.vaadin.server.VaadinService;

public class RequestContext {

	public static UserData getRequestContext()
	{
		if(VaadinService.getCurrentRequest() != null)
		{
			UserData userData = (UserData) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("UserContext");
			return userData;
		}
		return null;
	}
	
	public static void setRequestContext(UserData userData)
	{
		VaadinService.getCurrentRequest().getWrappedSession().setAttribute("UserContext", userData);
	}
}

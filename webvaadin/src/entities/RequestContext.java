package entities;

import com.vaadin.server.VaadinService;

public class RequestContext {

	public static UserData getRequestContext()
	{
		if(VaadinService.getCurrentRequest() != null)
		{
			UserData userData = (UserData)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("UserRequest");
			return userData;
		}
		return null;
	}
	
	public static void setRequestContext(UserData userData)
	{
		if(VaadinService.getCurrentRequest() != null)
		{
			VaadinService.getCurrentRequest().getWrappedSession().setAttribute("UserRequest", userData);
		}
	}
}
